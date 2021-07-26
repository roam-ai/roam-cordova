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

## Documentation

Please visit our [Developer Center](https://github.com/roam-ai/roam-cordova/wiki) for instructions on other SDK methods.

## Contributing
- For developing the SDK, please visit our [CONTRIBUTING.md](https://github.com/roam-ai/roam-cordova/blob/master/CONTRIBUTING.md) to get started.

## Need Help?
If you have any problems or issues over our SDK, feel free to create a github issue or submit a request on [Roam Help](https://geosparkai.atlassian.net/servicedesk/customer/portal/2).
