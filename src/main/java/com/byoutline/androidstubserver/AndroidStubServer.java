package com.byoutline.androidstubserver;

import android.content.Context;

import com.byoutline.mockserver.ConfigReader;
import com.byoutline.mockserver.HttpMockServer;
import com.byoutline.mockserver.NetworkType;

/**
 * Local HTTP server.
 *
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public final class AndroidStubServer {
    private AndroidStubServer() {
    }

    /**
     * Starts local HTTP server with default <code>ConfigReader</code>. <br />
     * To configure it create <code>mock</code> folder
     * in assets.<br />
     * Put your static resources in <code>mock/static</code>. <br />
     * Configure port number and responses by creating <code>mock/config.json</code>.
     *
     * @param context     context that provides access to assets folder.
     * @param networkType network type to be simulated by adding extra delays.
     */
    public static void start(Context context, NetworkType networkType) {
        start(new AndroidConfigReader(context), networkType);
    }

    /**
     * Starts local HTTP server.
     *
     * @param configReader reader that provides access to responses configuration, responses and static files
     * @param networkType  network type to be simulated by adding extra delays.
     */
    public static void start(ConfigReader configReader, NetworkType networkType) {
        HttpMockServer.startMockApiServer(configReader, networkType);
    }
}