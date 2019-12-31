package com.soybeany.properties_cipher.spring;

import com.soybeany.properties_cipher.util.CipherPropertyUtils;
import com.soybeany.properties_cipher.util.CipherUtils;
import com.soybeany.properties_cipher.util.UserInfo;

import java.io.IOException;
import java.util.Properties;

/**
 * <br>Created by Soybeany on 2019/12/25.
 */
public class CipherPropertyLoader {

    public static Properties load(String resourceName) throws IOException {
        return CipherPropertyUtils.read(Singleton.INSTANCE.get(), resourceName);
    }

    public static void load(Properties properties) {
        CipherPropertyUtils.read(Singleton.INSTANCE.get(), properties);
    }

    private enum Singleton {
        INSTANCE;

        private UserInfo mUserInfo;

        Singleton() {
            try {
                Properties properties = CipherPropertyUtils.read(CipherUtils.DEFAULT_INFO, CipherPropertyUtils.CIPHER_INFO_PROPERTIES);
                mUserInfo = UserInfo.fromString((String) properties.get(CipherUtils.CIPHER_INFO_KEY));
            } catch (Exception e) {
                throw new RuntimeException("初始化用户信息失败", e);
            }
        }

        UserInfo get() {
            return mUserInfo;
        }
    }
}
