// Import the Firebase SDK for Google Cloud Functions
const functions = require('firebase-functions');

// Import and initialize the Firebase Admin SDK
const admin = require('firebase-admin');
admin.initializeApp();

// Images moderation
const Vision = require('@google-cloud/vision');
const spawn = require('child-process-promise').spawn;

const path = require('path');
const os = require('os');
const fs = require('fs');

// https://firebase.google.com/docs/functions/write-firebase-functions

exports.emojify = functions.database.ref('/messages/{pushId}/text').onWrite((snapshot, context) => {
    // Database write events include new, modified, or deleted database nodes.
    // All three types of events at the specific database path trigger this cloud function.
    // For this function, we only want to emojify new database nodes, so we'll first check
    // to exit out of the function early if this isn't a new message.

    // !snapshot.data.val() is a deleted snapshot
    // snapshot.data.previous.val() is a modified snapshot
    if (!snapshot.after.exists() || snapshot.before.exists()) {
        console.log("not a new write snapshot");
        return;
    }

    // We can proceed with the transformation
    console.log("emojifying");

    // Get the value from the 'text' key of the message
    const originalText = snapshot.after.val();
    const emojifiedText = emojifyText(originalText);

    // Return a JavaScript Promise to update the database node
    return snapshot.after.ref.set(emojifiedText);
});

exports.addWelcomeMessages = functions.auth.user().onCreate(async (user) => {
    console.log('A new user signed in for the first time.');
    const fullName = user.displayName || 'Anonymous';

    // Saves the new welcome message into the database
    await admin.database().ref('/messages').push().set({
        'name': 'Firebase Bot',
        'text': `${fullName} signed in for the first time! Welcome!`
    });
    console.log('Welcome message written to database');
});

// exports.blurOffensiveImages = functions.runWith({memory: '2GB'}).storage.object().onFinalize(
//     async (object) => {
//         if (object.name.startsWith('chat_photos/')) {
//             console.log(`Object: ${object}`);
//             console.log(`Object name: ${object.name}`);

//             const image = {
//                 source: {imageUri: `gs://${object.bucket}/${object.name}`},
//             };
//             const vision = new Vision.ImageAnnotatorClient();
    
//             // Check the image content using the Cloud Vision API.
//             const batchAnnotateImagesResponse = await vision.safeSearchDetection(image);
//             console.log('batchAnnotateImagesResponse succeeded');
//             const safeSearchResult = batchAnnotateImagesResponse[0].safeSearchAnnotation;
//             console.log('safeSearchResult succeeded');
//             const Likelihood = Vision.types.Likelihood;
//             if (Likelihood[safeSearchResult.adult] >= Likelihood.LIKELY
//                 || Likelihood[safeSearchResult.violence] >= Likelihood.LIKELY) {
//                     console.log('The image', object.name, 'has been detected as inapp')
//                     return blurImage(object.name);
//             }
//             console.log('The image', object.name, 'has been detected as OK.');
//         }
//     }
// )

exports.blurOffensiveImages = functions.storage.object().onFinalize(event => {
    const object = event.data;
    
    // Exit if this is a deletion or a deploy event.
    if (object.resourceState === 'not_exists') {
        return console.log('This is a deletion event.');
    } else if (!object.name) {
        return console.log('This is a deploy event.');
    }
  
    // Check the image content using the Cloud Vision API.
    return vision.safeSearchDetection(`gs://${object.bucket}/${object.name}`).then(batchAnnotateImagesResponse => {
            console.log("batchAnnotateImagesResponse");
            console.log(batchAnnotateImagesResponse);
            console.log("batchAnnotateImagesResponse[0]");
            console.log(batchAnnotateImagesResponse[0]);
            const safeSearchResult = batchAnnotateImagesResponse[0].safeSearchAnnotation;
            console.log("safeSearchResult.adult");
            console.log(safeSearchResult.adult);
            //const Likelihood = Vision.types.Likelihood;
            if (Likelihood[safeSearchResult.adult] >= Likelihood.LIKELY ||
                Likelihood[safeSearchResult.violence] >= Likelihood.LIKELY) {
                console.log('The image', object.name, 'has been detected as inappropriate.');
                return blurImage(object.name, object.bucket);
            } else {
                console.log('The image', object.name,'has been detected as OK.');
            }
        })
        .catch(err => {
            console.log('Search Detecion failed.', err);
        });
});

// Returns text with keywords replaced by emoji
// Replacing with the regular expression /.../ig does a case-insensitive
// search (i flag) for all occurrences (g flag) in the string
function emojifyText(text) {
    var emojifiedText = text;
    emojifiedText = emojifiedText.replace(/\blol\b/ig, "😂");
    emojifiedText = emojifiedText.replace(/\bcat\b/ig, "😸");
    return emojifiedText;
}

// Blurs the given image located in the given bucket using ImageMagick
async function blurImage(filePath) {
    const tempLocalFile = path.join(os.tmpdir(), patch.basename(filePath));
    const messageId = filePath.split(path.sep)[1];
    const bucket = admin.storage().bucket();

    // Download file from bucket.
    await bucket.file(filePath).download({destination: tempLocalFile});
    console.log('Image has been downloaded to', tempLocalFile);
    // Blur the image using ImageMagick.
    await spawn('convert', [tempLocalFile, '-channel', 'RGBA', '-blur', '0x24', tempLocalFile]);
    console.log('Image has been blurred');
    // Uploading the Blurred image back into the bucket.
    await bucket.upload(tempLocalFile, {destination: filePath});
    console.log('Blurred image has been uploaded to', filePath);
    // Deleting the local file to free up disk space.
    fs.unlinkSync(tempLocalFile);
    console.log('Deleted local file.');
    // Indicate that the message has been moderated.
    await admin.firestore().collection('messages').doc(messageId).update({moderated: true});
    console.log('Marked the image as moderated in the database.');
}