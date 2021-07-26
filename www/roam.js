var exec = require('cordova/exec');
var roam = {};

roam.disableBatteryOptimization = function(callback) {
     exec( callback, null, 'roam', "disableBatteryOptimization",[]);
};

roam.isBatteryOptimizationEnabled = function(callback) {
     exec( callback, null, 'roam', "isBatteryOptimizationEnabled",[]);
};

roam.checkActivityPermission = function(callback) {
     exec( callback, null, 'roam', "checkActivityPermission",[]);
};

roam.requestActivityPermission = function(callback) {
     exec( callback, null, 'roam', "requestActivityPermission",[]);
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

roam.toggleEvents = function(geofenceEvents,tripEvents, activityEvents, success, error) {
     exec( success, error, 'roam', "toggleEvents",[geofenceEvents, tripEvents, activityEvents]);
};

roam.getEventsStatus = function(success, error) {
     exec( success, error, 'roam', "getEventsStatus",[]);
};

roam.startTrip = function(tripId,tripDescription, success, error) {
     exec( success, error, 'roam', "startTrip",[tripId, tripDescription]);
};

roam.resumeTrip = function(tripId, success, error) {
     exec( success, error, 'roam', "resumeTrip",[tripId]);
};

roam.pauseTrip = function(tripId, success, error) {
     exec( success, error, 'roam', "pauseTrip",[tripId]);
};

roam.endTrip = function(tripId, success, error) {
     exec( success, error, 'roam', "endTrip",[tripId]);
};

roam.activeTrips = function(success, error) {
     exec( success, error, 'roam', "activeTrips",[]);
};

roam.getCurrentLocation = function(accuracy, success, error) {
     exec( success, error, 'roam', "getCurrentLocation",[accuracy]);
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

roam.isLocationTracking = function(callback) {
     exec( callback, null,'roam', "isLocationTracking",[]);
};

roam.logout = function(success, error) {
     exec( success, error, 'roam', "logout",[]);
};

roam.setTrackingInAppState = function(jsonarray) {
     exec( null, null, 'roam', "setTrackingInAppState",[jsonarray]);
};

roam.setTrackingInMotion = function(jsonarray) {
     exec( null, null, 'roam', "setTrackingInMotion",[jsonarray]);
};

roam.onEvents = function(callback) {
	exec( callback, null, 'roam', "onEvents", []);
};

roam.onError = function(callback) {
	exec( callback, null, 'roam', "onError", []);
};

roam.offEvents = function() {
	exec( null, null, 'roam', "offEvents", []);
};

roam.offError = function() {
	exec( null, null, 'roam', "offError", []);
};

module.exports = roam;