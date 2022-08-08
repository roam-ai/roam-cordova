<p align="center">
  <a href="https://roam.ai" target="_blank" align="left">
    <img src="https://github.com/geosparks/roam-flutter/blob/master/logo.png?raw=true" width="180">
  </a>
  <br />
</p>

# Official Roam Cordova SDK
This is the official [Roam](https://roam.ai) Cordova SDK developed and maintained by Roam B.V

Note: Before you get started [signup to our dashboard](https://roam.ai/dashboard/signup/) to get your API Keys.

# Quick Start
The Roam Cordova SDK makes it quick and easy to build a location tracker for your Cordova app. We provide powerful and customizable tracking modes and features that can be used to collect your users location updates.

### Requirements
To use the Roam SDK, the following things are required:
Get yourself a free Roam Account. No credit card required.

- [x] Create a project and add an iOS app to the project.
- [x] You need the SDK_KEY in your project settings which you’ll need to initialize the SDK.
- [x] Now you’re ready to integrate the SDK into your Cordova application.

# Add the Cordova Native SDK to your app

In your project directory, install the plugin.

```bash
cordova plugin add roam-cordova
```

Before making any changes to your javascript code, you would need to integrate and initialize Roam SDK in your Android and iOS(upcoming) applications. 

### Android
1. Before initializing the SDK, the below must be imported in your Main Activity.

    ```java
    import com.roam.sdk.Roam;
    ```

2. After import, add the below code under the Application class `onCreate()` method. The SDK must be initialised before calling any of the other SDK methods.

    ```java
    Roam.initialize(this, "YOUR-SDK-KEY-GOES-HERE");
    ```

# Finally, lets do some javascript

## Creating Users
Once the SDK is initialized, we need to *create* or *get a user* to start the tracking and use other methods. Every user created will have a unique Roam identifier which will be used later to login and access developer APIs. We can call it as Roam userId.

```javascript
cordova.plugins.roam.createUser("SET-USER-DESCRIPTION-HERE", function(success){
  // do something on success
}, function(error){
  // do something on error
});
```

The option *user description* can be used to update your user information such as name, address or add an existing user ID. Make sure the information is encrypted if you are planning to save personal user information like email or phone number.

You can always set or update user descriptions later using the below code.

```javascript
cordova.plugins.roam.setDescription("SET-USER-DESCRIPTION-HERE", function(success){
  // do something on success
}, function(error){
  // do something on error
});
```

If you already have a Roam userID which you would like to reuse instead of creating a new user, use the below to get user session.

```javascript
cordova.plugins.roam.getUser(userId, function(success){
  // do something on success
}, function(error){
  // do something on error
});
```

## Request Permissions

Get location permission from the App user on the device. Also check if the user has turned on location services for the device. 

```javascript
// Call this method to check Location Permission for Android & iOS
cordova.plugins.roam.checkLocationPermission(function(status){
  // do something with status
});

​// Call this method to request Location Permission for Android & iOS
cordova.plugins.roam.requestLocationPermission(function(status){
  // do something with status
});
```

### Android

```javascript
// Call this method to check Location services for Android
cordova.plugins.roam.checkLocationServices(function(status){
  // do something with status
});
​// Call this method to request Location services for Android
cordova.plugins.roam.requestLocationServices(function(status){
  // do something with status
});
```

In case of devices running above Anroid 10, you would need to get background location permissions to track locations in background.

```javascript
// Call this method to check background location permission for Android
cordova.plugins.roam.checkBackgroundLocationPermission(function(status){
  // do something with status
});
// Call this method to request background location Permission for Android
cordova.plugins.roam.requestBackgroundLocationPermission(function(status){
  // do something with status
});
```

### SDK Configurations

#### Accuracy Engine

For enabling accuracy engine for Passive, Active, and Balanced tracking.

```javascript
cordova.plugins.roam.enableAccuracyEngine();
```

For Custom tracking mores, you can pass the desired accuracy values in integers ranging from 25-150m.

```javascript
cordova.plugins.roam.enableAccuracyEngine(function(accuray));
```
To disable accuracy engine

```javascript
cordova.plugins.roam.disableAccuracyEngine();
```

#### Offline Location Tracking

To modify the offline location tracking configuration, which will enabled by default. 

```javascript
cordova.plugins.roam.offlineLocationTracking(function(true));
```

#### Allow Mock Location Tracking

To allow mock location tracking during development, use the below code. This will be disabled by default. 

```javascript
cordova.plugins.roam.allowMockLocation(function(true));
```

## Location Tracking

### Start Tracking

Use the tracking modes while you use the startTracking method `cordova.plugins.roam.startTracking()`

```javascript
cordova.plugins.roam.startTracking("TRACKING MODE");
```

### Tracking Modes

Roam has three default tracking modes along with a custom version. They differ based on the frequency of location updates and battery consumption. The higher the frequency, the higher is the battery consumption. You must use [foreground service](https://developer.android.com/about/versions/oreo/background-location-limits) for continuous tracking.

| **Mode** | **Battery usage** | **Updates every** | **Optimised for/advised for** |
| -------- | ----------------- | ----------------- | ----------------------------- |
| Active   | 6% - 12%          | 25 ~ 250 meters   | Ride Hailing / Sharing        |
| Balanced | 3% - 6%           | 50 ~ 500 meters   | On Demand Services            |
| Passive  | 0% - 1%           | 100 ~ 1000 meters | Social Apps                   |

```javascript
// active tracking
cordova.plugins.roam.startTracking(ACTIVE);
// balanced tracking
cordova.plugins.roam.startTracking(BALANCED);
// passive tracking
cordova.plugins.roam.startTracking(PASSIVE);
```

### Custom Tracking Modes

The SDK also allows you define a custom tracking mode that allows you to
customize and build your own tracking modes.

#### Android

| **Type**          | **Unit** | **Unit Range** |
| ----------------- | -------- | -------------- |
| Distance Interval | Meters   | 1m ~ 2500m     |
| Time Interval     | Seconds  | 10s ~ 10800s   |


**Distance between location updates example code:**

```javascript
//Update location based on distance between locations.
cordova.plugins.roam.startTrackingDistanceInterval("DISTANCE IN METERS", "STATIONARY DURATION IN SECONDS", "ACCURACY");
```

**Time between location updates example code:**

```javascript
//Update location based on time interval.
cordova.plugins.roam.startTrackingTimeInterval("INTERVAL IN SECONDS", "ACCURACY");
```

| **Accuracy**      | **Description** | 
| ----------------- | --------------- | 
| HIGH              | High Accuracy   | 
| MEDIUM            | Medium Accuracy | 
| LOW               | Low Accuracy    | 

## Stop Tracking

To stop the tracking use the below method.

```javascript
cordova.plugins.roam.stopTracking();
```

## Publish Messages

It will both publish location data and these data will be sent to Roam servers for further processing and data will be saved in our database servers.
We will now have an option to send meta-data as a parameter along with location updates in the below json format. (upcomming feature)

```javascript
cordova.plugins.roam.publishAndSave(null);
```
## Stop Publishing

It will stop publishing the location data to other clients.
``` javascript
cordova.plugins.roam.stopPublishing();
```

## Subscribe Messages

Now that you have enabled the location listener, use the below method to subscribe to your own or other user's location updates and events.

### Subscribe
``` javascript
cordova.plugins.roam.subscribe(TYPE, "USER-ID");
```

| **Type** | **Description**                                                                |
| ---------|--------------------------------------------------------------------------------| 
| LOCATION | Subscribe to your own location (or) other user's location updates.             |
| EVENTS   | Subscribe to your own events.                                                  |
| BOTH     | Subscribe to your own events and location (or) other user's location updates.  |


### UnSubscribe
``` javascript
cordova.plugins.roam.unSubscribe(TYPE, "USER-ID");
```

## Listeners

Now that the location tracking is set up, you can subscribe to locations and events and use the data locally on your device or send it directly to your own backend server.

To do that, you need to set the location and event listener to `true` using the below method. By default the status will set to `false` and needs to be set to `true` in order to stream the location and events updates to the same device or other devices.

```javascript
cordova.plugins.roam.toggleListener(true, true, function(success){
  // do something on success
}, function(error){
  // do something on error
});
```
Once the listener toggles are set to true, to listen to location updates, events and errors.

```javascript
cordova.plugins.roam.onLocation((location) => {
  // do something on location received
});

cordova.plugins.roam.onEvents((events) => {
  // do something on events received
});

cordova.plugins.roam.onError((error) => {
  // do something on error
});
```

Remove the listeners with below codes.

```javascript
cordova.plugins.roam.offLocation();

cordova.plugins.roam.offEvents();

cordova.plugins.roam.offError();
```

## Headless JS

Headless JS allows JS code to run in isolate when app is terminated. Cordova doesn't have a mechanism for headless JS. To use location updates for terminated state, business logic can be added in `onLocationUpdated` method in `RoamCordovaReceiver` class in `CDVROAM.java`

***NOTE***

`safeRemoveCallback` should be called in `onDestory` method of MainActivity to prevent terminated state crash.

## Set Foreground Notification

To set foreground notification, use the below method.

```javascript
cordova.plugins.roam.setForegroundNotification(boolean, "title", "description", "icon", "activity_path")
```

## Documentation

Please visit our [Developer Center](https://github.com/roam-ai/roam-cordova/wiki) for instructions on other SDK methods.

## Contributing
- For developing the SDK, please visit our [CONTRIBUTING.md](https://github.com/roam-ai/roam-cordova/blob/master/CONTRIBUTING.md) to get started.

## Need Help?
If you have any problems or issues over our SDK, feel free to create a github issue or submit a request on [Roam Help](https://geosparkai.atlassian.net/servicedesk/customer/portal/2).
