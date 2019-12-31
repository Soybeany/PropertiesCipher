package com.soybeany.properties_cipher.util;

/**
 * <br>Created by Soybeany on 2019/12/25.
 */
public class UserInfo extends CipherInfo {

    private static final String SEPARATOR = "@@@cipher@@@";

    /**
     * 用于匹配待加密key的正则
     */
    public String matchRegex = "password";

    public static UserInfo wrap(String matchRegex, CipherInfo info) {
        UserInfo userInfo = new UserInfo();
        userInfo.matchRegex = matchRegex;
        userInfo.copy(info);
        return userInfo;
    }

    public static String toString(UserInfo info) {
        return info.secretKey + SEPARATOR + info.matchRegex;
    }

    public static UserInfo fromString(String string) {
        String[] strings = string.split(SEPARATOR);
        UserInfo info = new UserInfo();
        info.secretKey = strings[0];
        info.matchRegex = strings[1];
        return info;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "matchRegex='" + matchRegex + '\'' +
                ", secretKey='" + secretKey + '\'' +
                '}';
    }
}
