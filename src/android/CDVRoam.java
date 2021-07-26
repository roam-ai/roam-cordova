package com.roam.cordova;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.provider.Settings;

import com.google.gson.GsonBuilder;
import com.roam.sdk.Roam;
import com.roam.sdk.RoamPublish;
import com.roam.sdk.RoamTrackingMode;
import com.roam.sdk.callback.RoamCallback;
import com.roam.sdk.callback.RoamLocationCallback;
import com.roam.sdk.callback.RoamLogoutCallback;
import com.roam.sdk.models.RoamError;
import com.roam.sdk.models.RoamUser;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CDVRoam extends CordovaPlugin {
    private static CallbackContext eventsCallbackContext;
    private static CallbackContext errorCallbackContext;
    private static CallbackContext permissionCallbackContext;
    private Activity context;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        context = this.cordova.getActivity();
        Application application = this.cordova.getActivity().getApplication();
        Roam.initialize(application, "YOUR-PUBLISHABLE-KEY");
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        switch (action) {
            case "disableBatteryOptimization":
                this.disableBatteryOptimization();
                return true;

            case "isBatteryOptimizationEnabled":
                this.isBatteryOptimizationEnabled(callbackContext);
                return true;

            case "checkLocationPermission":
                this.checkLocationPermission(callbackContext);
                return true;

            case "requestLocationPermission":
                this.requestLocationPermission(callbackContext);
                return true;

            case "checkLocationServices":
                this.checkLocationServices(callbackContext);
                return true;

            case "requestLocationServices":
                this.requestLocationServices(callbackContext);
                return true;

            case "checkBackgroundLocationPermission":
                this.checkBackgroundLocationPermission(callbackContext);
                return true;

            case "requestBackgroundLocationPermission":
                this.requestBackgroundLocationPermission(callbackContext);
                return true;

            case "getDeviceToken":
                this.getDeviceToken(callbackContext);
                return true;

            case "createUser":
                String description = args.getString(0);
                this.createUser(description, callbackContext);
                return true;

            case "setDescription":
                String desc = args.getString(0);
                this.setDescription(desc);
                return true;

            case "getUser":
                String userId = args.getString(0);
                this.getUser(userId, callbackContext);
                return true;

            case "toggleEvents":
                boolean geofenceEvents = args.getBoolean(0);
                boolean tripEvents = args.getBoolean(1);
                boolean locationEvents = args.getBoolean(2);
                boolean movingGeofenceEvents = args.getBoolean(3);
                this.toggleEvents(geofenceEvents, tripEvents, locationEvents, movingGeofenceEvents, callbackContext);
                return true;

            case "getEventsStatus":
                this.getEventsStatus(callbackContext);
                return true;

            case "toggleListener":
                boolean locations = args.getBoolean(0);
                boolean events = args.getBoolean(1);
                this.toggleListeners(locations, events, callbackContext);
                return true;

            case "getListenerStatus":
                this.getListenersStatus(callbackContext);
                return true;

            case "subscribe":
                String type = args.getString(0);
                String sUserId = args.getString(1);
                this.subscribe(type, sUserId);
                return true;

            case "unSubscribe":
                String uSType = args.getString(0);
                String uSUserId = args.getString(1);
                this.unSubscribe(uSType, uSUserId);
                return true;

            case "startTracking":
                String trackingMode = args.getString(0);
                this.startTracking(trackingMode);

            case "startTrackingTimeInterval":
                int timeInterval = args.getInt(0);
                String desiredAccuracy = args.getString(1);
                this.startTrackingTimeInterval(timeInterval, desiredAccuracy);

            case "startTrackingDistanceInterval":
                int distance = args.getInt(0);
                int stationary = args.getInt(1);
                String dDesiredAccuracy = args.getString(3);
                this.startTrackingDistanceInterval(distance, stationary, dDesiredAccuracy);

            case "publishAndSave":
                this.publishAndSave();
                return true;

            case "updateCurrentLocation":
                int updateAccuracy = args.getInt(0);
                String updateDesiredAccuracy = args.getString(1);
                this.updateCurrentLocation(updateAccuracy, updateDesiredAccuracy);
                return true;
 
             case "getCurrentLocation":
                 int accuracy = args.getInt(0);
                 String desired_Accuracy = args.getString(1);
                 this.getCurrentLocation(accuracy, desired_Accuracy, callbackContext);
                 return true;
            case "stopTracking":
                this.stopTracking();
                return true;

            case "stopPublishing":
                this.stopPublishing();
                return true;      
            
            case "logout":
                this.logout(callbackContext);
                return true;
        }
        return false;
    }

    private void disableBatteryOptimization() {
        Roam.disableBatteryOptimization();
    }

    private void isBatteryOptimizationEnabled(CallbackContext callbackContext) {
        String status = enabledStatus(Roam.isBatteryOptimizationEnabled());
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, status));
    }

    private void checkLocationPermission(CallbackContext callbackContext) {
        String status = permissionsStatus(Roam.checkLocationPermission());
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, status));
    }

    private void requestLocationPermission(CallbackContext callbackContext) {
        permissionCallbackContext = callbackContext;
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        cordova.requestPermissions(this, Roam.REQUEST_CODE_LOCATION_PERMISSION, permissions);
    }

    private void checkLocationServices(CallbackContext callbackContext) {
        String status = enabledStatus(Roam.checkLocationServices());
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, status));
    }

    private void requestLocationServices(CallbackContext callbackContext) {
        permissionCallbackContext = callbackContext;
        cordova.startActivityForResult(this, new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), Roam.REQUEST_CODE_LOCATION_ENABLED);
    }

    private void checkBackgroundLocationPermission(CallbackContext callbackContext) {
        String status = permissionsStatus(Roam.checkBackgroundLocationPermission());
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, status));
    }

    private void requestBackgroundLocationPermission(CallbackContext callbackContext) {
        permissionCallbackContext = callbackContext;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            String[] permissions = {Manifest.permission.ACCESS_BACKGROUND_LOCATION};
            cordova.requestPermissions(this, Roam.REQUEST_CODE_BACKGROUND_LOCATION_PERMISSION, permissions);
        } else {
            permissionCallbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, "UNKNOWN"));
        }
    }

    private void getDeviceToken(CallbackContext callbackContext) {
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, Roam.getDeviceToken()));
    }

    private void createUser(String description, final CallbackContext callbackContext) {
        Roam.createUser(description, new RoamCallback() {
            @Override
            public void onSuccess(RoamUser roamUser) {
                String serializedUser = new GsonBuilder().create().toJson(roamUser);
                callbackContext.success(serializedUser);
            }

            @Override
            public void onFailure(RoamError roamError) {
                String serializedError = new GsonBuilder().create().toJson(roamError);
                callbackContext.error(serializedError);
            }
        });
    }

    private void setDescription(String description) {
        Roam.setDescription(description);
    }

    private void getUser(String userId, final CallbackContext callbackContext) {
        Roam.getUser(userId, new RoamCallback() {
            @Override
            public void onSuccess(RoamUser roamUser) {
                String serializedUser = new GsonBuilder().create().toJson(roamUser);
                callbackContext.success(serializedUser);
            }

            @Override
            public void onFailure(RoamError roamError) {
                String serializedError = new GsonBuilder().create().toJson(roamError);
                callbackContext.error(serializedError);
            }
        });
    }

    private void toggleEvents(boolean geofenceEvents, boolean tripEvents, boolean locationEvents, boolean movingGeofenceEvents, final CallbackContext callbackContext) {
        Roam.toggleEvents(geofenceEvents, tripEvents, locationEvents, movingGeofenceEvents, new RoamCallback() {
            @Override
            public void onSuccess(RoamUser roamUser) {
                String serializedUser = new GsonBuilder().create().toJson(roamUser);
                callbackContext.success(serializedUser);
            }

            @Override
            public void onFailure(RoamError roamError) {
                String serializedError = new GsonBuilder().create().toJson(roamError);
                callbackContext.error(serializedError);
            }
        });
    }

    private void getEventsStatus(final CallbackContext callbackContext) {
        Roam.getEventsStatus(new RoamCallback() {
            @Override
            public void onSuccess(RoamUser roamUser) {
                String serializedUser = new GsonBuilder().create().toJson(roamUser);
                callbackContext.success(serializedUser);
            }

            @Override
            public void onFailure(RoamError roamError) {
                String serializedError = new GsonBuilder().create().toJson(roamError);
                callbackContext.error(serializedError);
            }
        });
    }

    private void toggleListeners(boolean locations, boolean events, final CallbackContext callbackContext) {
        Roam.toggleListener(locations, events, new RoamCallback() {
            @Override
            public void onSuccess(RoamUser roamUser) {
                String serializedUser = new GsonBuilder().create().toJson(roamUser);
                callbackContext.success(serializedUser);
            }

            @Override
            public void onFailure(RoamError roamError) {
                String serializedError = new GsonBuilder().create().toJson(roamError);
                callbackContext.error(serializedError);
            }
        });

    }

    private void getListenersStatus(final CallbackContext callbackContext) {
        Roam.getEventsStatus(new RoamCallback() {
            @Override
            public void onSuccess(RoamUser roamUser) {
                String serializedUser = new GsonBuilder().create().toJson(roamUser);
                callbackContext.success(serializedUser);
            }

            @Override
            public void onFailure(RoamError roamError) {
                String serializedError = new GsonBuilder().create().toJson(roamError);
                callbackContext.error(serializedError);
            }
        });
    }

    private void updateCurrentLocation(int accuracy, String desiredAccuracy) {
        switch (desiredAccuracy) {
            case "MEDIUM":
                Roam.updateCurrentLocation(RoamTrackingMode.DesiredAccuracy.MEDIUM, accuracy);
                break;
            case "LOW":
                Roam.updateCurrentLocation(RoamTrackingMode.DesiredAccuracy.LOW, accuracy);
                break;
            default:
                Roam.updateCurrentLocation(RoamTrackingMode.DesiredAccuracy.HIGH, accuracy);
                break;
        }
    }

    public void getCurrentLocation(int accuracy, String desired_Accuracy, final CallbackContext callbackContext) {
        RoamTrackingMode.DesiredAccuracy desiredAccuracy = null;
        switch (desired_Accuracy) {
            case "MEDIUM":
                desiredAccuracy = RoamTrackingMode.DesiredAccuracy.MEDIUM;
                break;
            case "LOW":
                desiredAccuracy = RoamTrackingMode.DesiredAccuracy.LOW;
                break;
            default:
                desiredAccuracy = RoamTrackingMode.DesiredAccuracy.HIGH;
                break;
        }
        Roam.getCurrentLocation(desiredAccuracy, accuracy, new RoamLocationCallback() {
            @Override
            public void location(Location location) {
                String serializedLocation = new GsonBuilder().create().toJson(location);
                callbackContext.success(serializedLocation);
            }

            @Override
            public void onFailure(RoamError roamError) {
                String serializedError = new GsonBuilder().create().toJson(roamError);
                callbackContext.error(serializedError);
            }
        });
    }

    private void subscribe(String type, String userId){
        switch (type) {
            case "EVENTS":
                Roam.subscribe(Roam.Subscribe.EVENTS, userId);
                break;
            case "LOCATION":
                Roam.subscribe(Roam.Subscribe.LOCATION, userId);
                break;
            case "BOTH":
                Roam.subscribe(Roam.Subscribe.BOTH, userId);
                break;
        }
    }

    private void unSubscribe(String type, String userId) {
        switch (type) {
            case "EVENTS":
                Roam.unSubscribe(Roam.Subscribe.EVENTS, userId);
                break;
            case "LOCATION":
                Roam.unSubscribe(Roam.Subscribe.LOCATION, userId);
                break;
            case "BOTH":
                Roam.unSubscribe(Roam.Subscribe.BOTH, userId);
                break;
        }
    }

    private void startTracking(String trackingMode){
        switch (trackingMode) {
            case "ACTIVE":
                Roam.startTracking(RoamTrackingMode.ACTIVE);
                break;
            case "BALANCED":
                Roam.startTracking(RoamTrackingMode.BALANCED);
                break;
            case "PASSIVE":
                Roam.startTracking(RoamTrackingMode.PASSIVE);
                break;
        }
    }

    private void startTrackingTimeInterval(int timeInterval, String desiredAccuracy) {
        RoamTrackingMode.Builder builder = new RoamTrackingMode.Builder(timeInterval);
        switch (desiredAccuracy) {
            case "MEDIUM":
                builder.setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.MEDIUM);
                break;
            case "LOW":
                builder.setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.LOW);
                break;
            default:
                builder.setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.HIGH);
                break;
        }
        Roam.startTracking(builder.build());
    }

    private void startTrackingDistanceInterval(int distance, int stationary, String desiredAccuracy) {
        RoamTrackingMode.Builder builder = new RoamTrackingMode.Builder(distance, stationary);
        switch (desiredAccuracy) {
            case "MEDIUM":
                builder.setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.MEDIUM);
                break;
            case "LOW":
                builder.setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.LOW);
                break;
            default:
                builder.setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.HIGH);
                break;
        }
        Roam.startTracking(builder.build());
    }

    private void stopTracking() {
        Roam.stopTracking();
    }

    public void publishAndSave() {
        RoamPublish roamPublish = new RoamPublish.Builder().build();
        Roam.publishAndSave(roamPublish);
    }

    public void stopPublishing() {
        Roam.stopPublishing();
    }

    private void logout(final CallbackContext callbackContext) {
        Roam.logout(new RoamLogoutCallback() {
            @Override
            public void onSuccess(String s) {
                callbackContext.success(s);
            }

            @Override
            public void onFailure(RoamError roamError) {
                String serializedError = new GsonBuilder().create().toJson(roamError);
                callbackContext.error(serializedError);
            }
        });
    }

    private static String enabledStatus(boolean hasEnabled) {
        if (hasEnabled) {
            return "ENABLED";
        }
        return "DISABLED";
    }

    private static String permissionsStatus(boolean hasPermissionsGranted) {
        if (hasPermissionsGranted)
            return "GRANTED";
        return "DENIED";
    }
}