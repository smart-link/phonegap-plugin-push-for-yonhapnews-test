document.addEventListener("deviceready", function() {
    var push = PushNotification.init({
        android: {
            senderID: "219619268924"
        },
        ios: {
            alert: "true",
            badge: "true",
            sound: "true"
        },
        windows: {}
    });

    push.on('registration', function(data) {
        // data.registrationId
        console.log('registration:', data);
    });

    push.on('notification', function(data) {
        // data.message,
        // data.title,
        // data.count,
        // data.sound,
        // data.image,
        // data.additionalData
        console.log('notification:', data);
    });

    push.on('error', function(e) {
        // e.message
        console.log('error:', e);
    });
}, false);
