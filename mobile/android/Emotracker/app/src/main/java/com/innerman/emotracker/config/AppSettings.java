package com.innerman.emotracker.config;

import android.content.res.Resources;

import com.innerman.emotracker.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by petrpopov on 08.03.14.
 */
public class AppSettings {

    public static final Integer REQUEST_ENABLE_BT = 42;
    public static final Integer REQUEST_ENABLE_BT_FROM_SCAN = 43;

    public static String APP_SERVER_HOST;
    public static String APP_SERVER_PORT;
    public static String APP_SERVER_API;

    public static void loadConfig(Resources res) {
        try {
            InputStream rawResource = res.openRawResource(R.raw.config);
            Properties properties = new Properties();
            properties.load(rawResource);

            AppSettings.APP_SERVER_HOST = properties.get("app_server_host").toString();
            AppSettings.APP_SERVER_PORT = properties.get("app_server_port").toString();
            AppSettings.APP_SERVER_API = properties.get("app_server_api").toString();

            System.out.println("The properties are now loaded");
            System.out.println("properties: " + properties);
        } catch (Resources.NotFoundException e) {
            System.err.println("Did not find raw resource: " + e);
        } catch (IOException e) {
            System.err.println("Failed to open microlog property file");
        }
    }
}
