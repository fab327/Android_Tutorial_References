package com.google.firebase.udacity.friendlychat.background

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)

        val token = FirebaseInstanceId.getInstance().getToken()
        Log.d(TAG, "FCM Token: " + token)

        // Once a token is generated, we subscribe to topic
        FirebaseMessaging.getInstance().subscribeToTopic(FRIENDLY_ENGAGE_TOPIC)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle data payload of FCM messages
        Log.d(TAG, "FCM Message Id: " + remoteMessage.messageId);
        Log.d(TAG, "FCM Notification Message: " + remoteMessage.notification);
        Log.d(TAG, "FCM Data Message: " + remoteMessage.data);
    }

    companion object {
        const val TAG = "MyFMService"
        const val FRIENDLY_ENGAGE_TOPIC = "friendly_engage"
    }
}
