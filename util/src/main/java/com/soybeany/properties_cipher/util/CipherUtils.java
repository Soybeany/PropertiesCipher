package com.soybeany.properties_cipher.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 * <br>Created by Soybeany on 2019/12/25.
 */
public class CipherUtils {

    public static final CipherInfo DEFAULT_INFO = new CipherInfo();
    public static final String CIPHER_INFO_KEY = "data";

    public static String encrypt(CipherInfo info, String msg) {
        return HexUtils.byteArrToHex(handleMsg(info, Cipher.ENCRYPT_MODE, msg.getBytes()));
    }

    public static String decrypt(CipherInfo info, String msg) {
        return new String(handleMsg(info, Cipher.DECRYPT_MODE, HexUtils.hexToByteArr(msg)));
    }

    private static byte[] handleMsg(CipherInfo info, int opMode, byte[] msg) {
        try {
            Cipher cipher = Cipher.getInstance(CipherInfo.TRANSFORMATION);
            cipher.init(opMode, new SecretKeySpec(info.getSecretKeyInBytes(), info.getAlgorithm()));
            return cipher.doFinal(msg);
        } catch (Exception e) {
            throw new RuntimeException("cipher操作异常", e);
        }
    }
}
