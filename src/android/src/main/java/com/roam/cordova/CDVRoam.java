package com.roam.cordova;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.roam.sdk.Roam;
import com.roam.sdk.RoamPublish;
import com.roam.sdk.RoamTrackingMode;
import com.roam.sdk.callback.RoamCallback;
import com.roam.sdk.callback.RoamLocationCallback;
import com.roam.sdk.callback.RoamLogoutCallback;
import com.roam.sdk.models.RoamError;
import com.roam.sdk.models.RoamLocation;
import com.roam.sdk.models.RoamLocationReceived;
import com.roam.sdk.models.RoamUser;
import com.roam.sdk.models.events.RoamEvent;
import com.roam.sdk.service.RoamReceiver;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

public class CDVRoam extends CordovaPlugin {
    private static CallbackContext locationCallbackContext;
    private static CallbackContext eventsCallbackContext;
    private static CallbackContext errorCallbackContext;

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
                this.requestLocationPermission();
                return true;

            case "checkLocationServices":
                this.checkLocationServices(callbackContext);
                return true;

            case "requestLocationServices":
                this.requestLocationServices();
                return true;

            case "checkBackgroundLocationPermission":
                this.checkBackgroundLocationPermission(callbackContext);
                return true;

            case "requestBackgroundLocationPermission":
                this.requestBackgroundLocationPermission();
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

            case "enableAccuracyEngine":
                int accuracyValue = args.getInt(0);
                this.enableAccuracyEngine(accuracyValue);
                return true;

            case "disableAccuracyEngine":
                this.disableAccuracyEngine();
                return true;

            case "offlineLocationTracking":
                Boolean value = args.getBoolean(0);
                this.offlineLocationTracking(value);
                return true;

            case "allowMockLocation":
                Boolean mockValue = args.getBoolean(0);
                this.allowMockLocation(mockValue);
                return true;

            case "toggleEvents":
                boolean geofenceEvents = args.getBoolean(0);
                boolean tripEvents = args.getBoolean(1);
                boolean locationEvents = args.getBoolean(2);
                boolean movingGeofenceEvents = args.getBoolean(3);
                this.toggleEvents(geofenceEvents, tripEvents, locationEvents, movingGeofenceEvents, callbackContext);
                return true;

            case "setForegroundNotification":
                boolean enabled = args.getBoolean(0);
                String notificationTitle = args.getString(1);
                String notificationDescription = args.getString(2);
                String notificationIcon = args.getString(3);
                String notificationActivity = args.getString(4);
                this.setForegroundNotification(enabled, notificationTitle, notificationDescription, notificationIcon, notificationActivity);
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
                String dDesiredAccuracy = args.getString(2);
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

            case "onLocation":
                this.onLocation(callbackContext);
                return true;

            case "onEvents":
                this.onEvents(callbackContext);
                return true;

            case "onError":
                this.onError(callbackContext);
                return true;

            case "offLocation":
                this.offLocation();
                return true;

            case "offEvents":
                this.offEvents();
                return true;

            case "offError":
                this.offError();
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

    private void requestLocationPermission() {
        Roam.requestLocationPermission(cordova.getActivity());
    }

    private void checkLocationServices(CallbackContext callbackContext) {
        String status = enabledStatus(Roam.checkLocationServices());
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, status));
    }

    private void requestLocationServices() {
        Roam.requestLocationServices(cordova.getActivity());
    }

    private void checkBackgroundLocationPermission(CallbackContext callbackContext) {
        String status = permissionsStatus(Roam.checkBackgroundLocationPermission());
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, status));
    }

    private void requestBackgroundLocationPermission() {
        Roam.requestBackgroundLocationPermission(cordova.getActivity());
    }

    private void getDeviceToken(CallbackContext callbackContext) {
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, Roam.getDeviceToken()));
    }

    private void setForegroundNotification(boolean enabled, String title, String description, String image, String activity){
        Log.e("TAG", "setForegroundNotification: " + enabled + title + description + image + activity);
        try{
            String[] split = image.split("/");
            String firstSubString = split[0];
            String secondSubString = split[1];
            int resId = cordova.getActivity().getResources()
                    .getIdentifier(
                            secondSubString,
                            firstSubString,
                            cordova.getActivity().getPackageName()
                    );
            Roam.setForegroundNotification(enabled, title, description, resId, activity);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createUser(String description, final CallbackContext callbackContext) {
        Roam.createUser(description,null, new RoamCallback() {
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

    private void enableAccuracyEngine(int accuracy) {
        Roam.enableAccuracyEngine(accuracy);
    }

    private void disableAccuracyEngine() {
        Roam.disableAccuracyEngine();
    }

    private void allowMockLocation(Boolean value) {
        Roam.allowMockLocation(value);
    }

    private void offlineLocationTracking(Boolean value) {
        Roam.offlineLocationTracking(value);
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
                Roam.updateCurrentLocation(RoamTrackingMode.DesiredAccuracy.MEDIUM, accuracy, null);
                break;
            case "LOW":
                Roam.updateCurrentLocation(RoamTrackingMode.DesiredAccuracy.LOW, accuracy, null);
                break;
            default:
                Roam.updateCurrentLocation(RoamTrackingMode.DesiredAccuracy.HIGH, accuracy, null);
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
                startReceiverService();
                break;
            case "BALANCED":
                Roam.startTracking(RoamTrackingMode.BALANCED);
                startReceiverService();
                break;
            case "PASSIVE":
                Roam.startTracking(RoamTrackingMode.PASSIVE);
                startReceiverService();
                break;
        }
    }

    private void startReceiverService(){
        Activity activity = cordova.getActivity();
        activity.startService(new Intent(activity, RoamCDVService.class));
    }

    private void stopReceiverService(){
        Activity activity = cordova.getActivity();
        activity.stopService(new Intent(activity, RoamCDVService.class));
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
        startReceiverService();
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
        startReceiverService();
    }

    private void stopTracking() {
        Roam.stopTracking();
        stopReceiverService();
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

    private void onLocation(final CallbackContext callbackContext) {
        locationCallbackContext = callbackContext;
    }

    private void onEvents(final CallbackContext callbackContext) {
        eventsCallbackContext = callbackContext;
    }

    private void onError(final CallbackContext callbackContext) {
        errorCallbackContext = callbackContext;
    }

    private void offLocation() {
        locationCallbackContext = null;
    }

    private void offEvents() {
        eventsCallbackContext = null;
    }

    private void offError() {
        errorCallbackContext = null;
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

    public static class RoamCordovaReceiver extends RoamReceiver{
        @Override
        public void onLocationUpdated(Context context, RoamLocation roamLocation) {
            super.onLocationUpdated(context, roamLocation);
            String serializedLocation = new GsonBuilder().create().toJson(roamLocation);
            locationCallbackContext.success(serializedLocation);
        }

        @Override
        public void onLocationReceived(Context context, RoamLocationReceived roamLocationReceived) {
            super.onLocationReceived(context, roamLocationReceived);
            String serializedLocation = new GsonBuilder().create().toJson(roamLocationReceived);
            locationCallbackContext.success(serializedLocation);
        }

        @Override
        public void onEventReceived(Context context, RoamEvent roamEvent) {
            super.onEventReceived(context, roamEvent);
            String serializedLocation = new GsonBuilder().create().toJson(roamEvent);
            eventsCallbackContext.success(serializedLocation);
        }

        @Override
        public void onError(Context context, RoamError roamError) {
            super.onError(context, roamError);
            String serializedLocation = new GsonBuilder().create().toJson(roamError);
            errorCallbackContext.success(serializedLocation);
        }
    }
}