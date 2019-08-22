# Test App A

A test app to broadcast echo requests to another app and listen to the echo responses 


## Getting Started


### Configuration

External Echo Receiver App

com.wfdb.testappa.config.Config.java 

```
API_SERVICE_APP_ID = "com.wfdb.testappb";
API_SERVICE_BROADCAST_RECEIVER = "com.wfdb.testappb.MainBroadcastReceiver";
```

### Building the project

building the binaries 

```
./gradlew build
```

Or

- import the project on android studio.
- build and install on an emulator or on a device

### Running Tests

```
./gradlew test
```

### Author

* **Warren Balcos** - *Initial work* - [warrenbalcos](https://github.com/warrenbalcos)

### License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details