package com.byoutline.androidstubserver;

import android.content.Context;

import com.byoutline.mockserver.ConfigReader;
import com.byoutline.mockserver.HttpMockServer;
import com.byoutline.mockserver.NetworkType;

/**
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public final class AndroidStubServer {
    private AndroidStubServer() {
    }

    public static void start(Context context, NetworkType networkType) {
        start(new AndroidConfigReader(context), networkType);
    }

    public static void start(ConfigReader configReader, NetworkType networkType) {
        HttpMockServer.startMockApiServer(configReader, networkType);
    }
}