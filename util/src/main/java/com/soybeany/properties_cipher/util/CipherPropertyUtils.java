package com.soybeany.properties_cipher.util;

import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * <br>Created by Soybeany on 2019/12/26.
 */
public class CipherPropertyUtils {

    public static final String CIPHER_INFO_PROPERTIES = "BD_CipherInfo.properties";

    private static final String WRITE_HINT = "THIS PROPERTIES IS AUTO-GENERATED, DO NOT EDIT";

    private static final IPropertiesHandler ENCRYPT_PROPERTIES_HANDLER = new IPropertiesHandler() {
        @Override
        public String getHandledValue(UserInfo info, String value) {
            return CipherUtils.encrypt(info, value);
        }
    };

    private static final IPropertiesHandler DECRYPT_PROPERTIES_HANDLER = new IPropertiesHandler() {
        @Override
        public String getHandledValue(UserInfo info, String value) {
            return CipherUtils.decrypt(info, value);
        }
    };

    public static Properties read(CipherInfo info, String resourceName) throws IOException {
        Properties properties = new Properties();
        Enumeration<URL> urls = CipherPropertyUtils.class.getClassLoader().getResources(resourceName);
        while (urls.hasMoreElements()) {
            try (InputStream in = urls.nextElement().openConnection().getInputStream()) {
                properties.load(in);
            }
        }
        read(info, properties);
        return properties;
    }

    public static void read(CipherInfo info, Properties properties) {
        decryptProperties(toUserInfo(info), properties);
    }

    public static void write(CipherInfo info, Properties properties, File file) throws IOException {
        encryptProperties(toUserInfo(info), properties);
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            properties.store(out, WRITE_HINT);
        }
    }

    public static void encryptProperties(UserInfo info, Properties properties) {
        handleProperties(info, properties, ENCRYPT_PROPERTIES_HANDLER);
    }

    public static void decryptProperties(UserInfo info, Properties properties) {
        handleProperties(info, properties, DECRYPT_PROPERTIES_HANDLER);
    }

    private static UserInfo toUserInfo(CipherInfo info) {
        if (info instanceof UserInfo) {
            return (UserInfo) info;
        }
        return UserInfo.wrap("", info);
    }

    private static void handleProperties(UserInfo info, Properties properties, IPropertiesHandler handler) {
        Pattern pattern = Pattern.compile(info.matchRegex);
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = (String) entry.getKey();
            if (!pattern.matcher(key).find()) {
                continue;
            }
            properties.setProperty(key, handler.getHandledValue(info, (String) entry.getValue()));
        }
    }

    private interface IPropertiesHandler {
        String getHandledValue(UserInfo info, String value);
    }
}
