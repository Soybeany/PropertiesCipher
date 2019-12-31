package com.soybeany.properties_cipher.util;

import java.security.MessageDigest;

/**
 * <br>Created by Soybeany on 2019/12/25.
 */
public class CipherInfo {
    /**
     * 使用的算法
     */
    public static String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    /**
     * 使用的密钥
     */
    public String secretKey = "This is Key";

    public void copy(CipherInfo info) {
        secretKey = info.secretKey;
    }

    public String getAlgorithm() {
        return TRANSFORMATION.split("/")[0];
    }

    public byte[] getSecretKeyInBytes() {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            return instance.digest(secretKey.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("无法得到密钥的摘要信息", e);
        }
    }
}
