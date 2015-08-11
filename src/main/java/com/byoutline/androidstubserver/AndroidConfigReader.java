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

    private final Context context;

    public AndroidConfigReader(Context context) {
        this.context = context;
    }

    @Override
    public InputStream getMainConfigFile() {
        try {
            return context.getAssets().open("mock/config.json");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public InputStream getResponseConfigFromFile(String relativePath) throws IOException {
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
