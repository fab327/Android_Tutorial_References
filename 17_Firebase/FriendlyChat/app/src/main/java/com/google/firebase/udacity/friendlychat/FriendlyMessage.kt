package com.google.firebase.udacity.friendlychat

data class FriendlyMessage(var text: String?, var name: String?, var photoUrl: String?) {
    // Empty constructor required for firebase
    constructor() : this(null, null, null)
}