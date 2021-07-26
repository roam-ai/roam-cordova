var exec = require('cordova/exec');
var roam = {};

roam.disableBatteryOptimization = function(callback) {
     exec( callback, null, 'roam', "disableBatteryOptimization",[]);
};

roam.isBatteryOptimizationEnabled = function(callback) {
     exec( callback, null, 'roam', "isBatteryOptimizationEnabled",[]);
};


roam.checkLocationPermission = function(callback) {
     exec( callback, null, 'roam', "checkLocationPermission",[]);
};

roam.requestLocationPermission = function(callback) {
     exec( callback, null, 'roam', "requestLocationPermission",[]);
};

roam.checkLocationServices = function(callback) {
     exec( callback, null, 'roam', "checkLocationServices",[]);
};

roam.requestLocationServices = function(callback) {
     exec( callback, null, 'roam', "requestLocationServices",[]);
};

roam.checkBackgroundLocationPermission = function(callback) {
     exec( callback, null, 'roam', "checkBackgroundLocationPermission",[]);
};

roam.requestBackgroundLocationPermission = function(callback) {
     exec( callback, null, 'roam', "requestBackgroundLocationPermission",[]);
};

roam.getDeviceToken = function(callback) {
     exec( callback, null, 'roam', "getDeviceToken",[]);
};

roam.createUser = function(description, success, error) {
     exec( success, error, 'roam', "createUser",[description]);
};

roam.setDescription = function(description, success, error) {
     exec( success, error, 'roam', "setDescription",[description]);
};

roam.getUser = function(userId, success, error) {
     exec( success, error, 'roam', "getUser",[userId]);
};

roam.subscribe = function(type, userId) {
     exec( null, null, 'roam', "subscribe",[type, userId]);
};

roam.unSubscribe = function(utype, userId) {
     exec( null, null, 'roam', "unSubscribe",[type, userId]);
};

roam.toggleEvents = function(geofenceEvents,tripEvents, locationEvents, movingGeofenceEvents, success, error) {
     exec( success, error, 'roam', "toggleEvents",[geofenceEvents, tripEvents, locationEvents, movingGeofenceEvents]);
};

roam.getEventsStatus = function(success, error) {
     exec( success, error, 'roam', "getEventsStatus",[]);
};

roam.toggleListener = function(locations,events, success, error) {
     exec( success, error, 'roam', "toggleListener",[locations, events]);
};

roam. getListenerStatus = function(success, error) {
     exec( success, error, 'roam', "getListenerStatus",[]);
};

roam.getCurrentLocation = function(accuracy, desiredAccuracy, success, error) {
     exec( success, error, 'roam', "getCurrentLocation",[accuracy, desiredAccuracy]);
};

roam.updateCurrentLocation = function(accuracy, desiredAccuracy) {
     exec( null, null, 'roam', "updateCurrentLocation",[accuracy, desiredAccuracy]);
};

roam.startTracking = function(trackingMode) {
     exec( null, null, 'roam', "startTracking",[trackingMode]);
};

roam.publishAndSave = function() {
    exec( null, null, 'roam', "publishAndSave",[]);
};

roam.stopTracking = function() {
     exec( null, null, 'roam', "stopTracking",[]);
};

roam.stopTracking = function() {
     exec( null, null, 'roam', "stopTracking",[]);
};

roam.stopPublishing = function() {
     exec( null, null, 'roam', "stopPublishing",[]);
};

roam.logout = function(success, error) {
     exec( success, error, 'roam', "logout",[]);
};

module.exports = roam;