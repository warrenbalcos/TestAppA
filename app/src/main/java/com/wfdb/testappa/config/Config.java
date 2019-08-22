package com.wfdb.testappa.config;

import android.content.ComponentName;

/**
 * Created by warren on 2019-08-21.
 */
public class Config {

    private static final Config INSTANCE = new Config();

    private static final String API_SERVICE_APP_ID = "com.wfdb.testappb";
    private static final String API_SERVICE_BROADCAST_RECEIVER = "com.wfdb.testappb.MainBroadcastReceiver";

    private Config() {
    }

    public static synchronized Config getInstance() {
        return INSTANCE;
    }

    public ComponentName getAPIServiceComponentName() {
        return new ComponentName(API_SERVICE_APP_ID, API_SERVICE_BROADCAST_RECEIVER);
    }
}
