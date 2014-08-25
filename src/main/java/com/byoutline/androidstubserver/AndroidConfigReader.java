package com.byoutline.androidstubserver;

import android.content.Context;

import com.byoutline.mockserver.ConfigReader;

import java.io.IOException;
import java.io.InputStream;

/**
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
    public InputStream getResponseConfigFromFile(String fileName) throws IOException {
        return context.getAssets().open("mock/" + fileName);
    }

    @Override
    public InputStream getStaticFile(String fileName) throws IOException {
        return context.getAssets().open("mock/static" + fileName);
    }
}
