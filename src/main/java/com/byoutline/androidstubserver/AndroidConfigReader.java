package com.byoutline.androidstubserver;

import android.content.Context;

import com.byoutline.mockserver.ConfigReader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Default Android <code>ConfigReader</code>.<br />
 * Reads configuration and responses from assets <code>mock</code> folder.
 *
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public class AndroidConfigReader implements ConfigReader {

    private static final String DEFAULT_CONFIG_FILE_LOCATION = "mock/config.json";
    private final Context context;
    private final String configFileLocation;

    public AndroidConfigReader(Context context) {
        this(context, DEFAULT_CONFIG_FILE_LOCATION);
    }

    public AndroidConfigReader(Context context, String configFileLocation) {
        this.context = context;
        this.configFileLocation = configFileLocation;
    }

    @Override
    public InputStream getMainConfigFile() {
        try {
            return context.getAssets().open(configFileLocation);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public InputStream getPartialConfigFromFile(String relativePath) throws IOException {
        return context.getAssets().open("mock/" + relativePath);
    }

    @Override
    public InputStream getStaticFile(String relativePath) throws IOException {
        String path = getStaticFilePath(relativePath);
        return context.getAssets().open(path);
    }

    @Override
    public boolean isFolder(String relativePath) {
        try {
            String path = getStaticFilePath(relativePath);
            return context.getAssets().list(path).length > 0;
        } catch (IOException e) {
            return false;
        }
    }

    private String getStaticFilePath(String relativePath) {
        return "mock/static" + relativePath;
    }
}
