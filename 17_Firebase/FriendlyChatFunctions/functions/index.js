const functions = require('firebase-functions');

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

// Returns text with keywords replaced by emoji
// Replacing with the regular expression /.../ig does a case-insensitive
// search (i flag) for all occurrences (g flag) in the string
function emojifyText(text) {
    var emojifiedText = text;
    emojifiedText = emojifiedText.replace(/\blol\b/ig, "😂");
    emojifiedText = emojifiedText.replace(/\bcat\b/ig, "😸");
    return emojifiedText;
}