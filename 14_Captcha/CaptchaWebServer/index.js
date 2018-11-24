const express = require('express');
const request = require('request');

const myApp = express();

//Create endpoint for handling mobile client's request
myApp.get('/validate', function(req, resp) {
    //Prepare the object to send to Google's server
    const postData = { 
        secret: '6Ldou3wUAAAAAC-VNVRQ6t9c5YFxFPmkN-yOiIQ_',
        response: req.user_token
    }

    //Make a request to Google's recaptcha's service
    request.post({
        url: 'https://www.google.com/recaptcha/api/siteverify',
        form: postData
    }, function(error, response, body) {
        jsonData = JSON.parse(body); //Parse the JSON document

        if (jsonData.success) { //User passed the test
            resp.send('PASS');
        } else { // User didn't pass the test
            resp.send('FAIL')
        }
    })
});

myApp.listen(8000);