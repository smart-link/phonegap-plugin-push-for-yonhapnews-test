cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "file": "plugins/cordova-plugin-whitelist/whitelist.js",
        "id": "cordova-plugin-whitelist.whitelist",
        "pluginId": "cordova-plugin-whitelist",
        "runs": true
    },
    {
        "file": "plugins/phonegap-plugin-push-for-yonhapnews/www/push.js",
        "id": "phonegap-plugin-push-for-yonhapnews.PushNotification",
        "pluginId": "phonegap-plugin-push-for-yonhapnews",
        "clobbers": [
            "PushNotification"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "cordova-plugin-whitelist": "1.2.1",
    "phonegap-plugin-push-for-yonhapnews": "1.5.3"
}
// BOTTOM OF METADATA
});