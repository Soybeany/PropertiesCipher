package com.soybeany.properties_cipher.util;

/**
 * <br>Created by Soybeany on 2019/12/31.
 */
class HexUtils {

    private static final char[] HEX_CHAR_ARR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final String HexStr = "0123456789abcdef";

    public static String byteArrToHex(byte[] btArr) {
        char[] strArr = new char[btArr.length * 2];
        int i = 0;
        for (byte bt : btArr) {
            strArr[i++] = HEX_CHAR_ARR[bt >>> 4 & 0xf];
            strArr[i++] = HEX_CHAR_ARR[bt & 0xf];
        }
        return new String(strArr);
    }

    public static byte[] hexToByteArr(String hexStr) {
        char[] charArr = hexStr.toCharArray();
        byte[] btArr = new byte[charArr.length / 2];
        int index = 0;
        for (int i = 0; i < charArr.length; i++) {
            int highBit = HexStr.indexOf(charArr[i]);
            int lowBit = HexStr.indexOf(charArr[++i]);
            btArr[index] = (byte) (highBit << 4 | lowBit);
            index++;
        }
        return btArr;
    }
}
