package com.byoutline.androidstubserver;

import android.content.Context;
import com.byoutline.androidstubserver.internal.ImageGenerator;
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

    public static final String CONFIG_FOLDER_PATH = "mock/";
    public static final String STATIC_FILE_PATH = CONFIG_FOLDER_PATH + "static";
    public static final String GENERATE_IMAGES_PATH = "/" + CONFIG_FOLDER_PATH + "img/";
    private static final String DEFAULT_CONFIG_FILE_LOCATION = CONFIG_FOLDER_PATH + "config.json";
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
        return context.getAssets().open(CONFIG_FOLDER_PATH + relativePath);
    }

    @Override
    public InputStream getStaticFile(String relativePath) throws IOException {
        if(relativePath.startsWith(GENERATE_IMAGES_PATH)) {
            return ImageGenerator.generateImage(relativePath.substring(GENERATE_IMAGES_PATH.length()));
        }
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
        return STATIC_FILE_PATH + relativePath;
    }
}
