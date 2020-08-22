package com.pay.typay.biz.messages;


import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * 测渠道使用的解密类
 */
@Slf4j
public class AesTestUtil {

    private static String IV = "typay!@#qwerasdf";

    private static String password = "tianxiazhifuxqzx";

    /**
     * AES加密
     *
     * @param content
     * @return
     */
    public static String encrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] raw = password.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
            return byteToHex(encrypted);
        } catch (Exception e) {

            return e.toString();
        }

    }

    /**
     * AES解密
     *
     * @param input
     * @return
     */
    public static String decrypt(String input) {
        try {
            log.info("IV:{},password:{}", IV, password);
            byte[] raw = password.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] content = hexToByte(input);
            byte[] original = cipher.doFinal(content);
            String originalString = new String(original, "UTF8");
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        // 存款
        String data = "4293e5a303e75078689cc9abbec01ddf4efd44b9015e505d4db4fbd7ed5b8646bf407a11c4b9affae7c9761c1903320def143412e858b6e91f7e6e1005b05a58c807f918508d6b1e32a7502d322a8be3";
        // 提款
//        String data = "merchantid=xinfutongpay&user_id=10103&orderid=TX20200115155935195583";
        System.out.println("解密后：" + decrypt(data));
    }

    public static String byteToHex(byte[] bytes) {
        String strHex = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < bytes.length; n++) {
            strHex = Integer.toHexString(bytes[n] & 0xFF);
            sb.append((strHex.length() == 1) ? "0" + strHex : strHex); // 每个字节由两个字符表示，位数不够，高位补0
        }
        return sb.toString().trim();
    }

    /**
     * hex转byte数组
     *
     * @param hex
     * @return
     */
    public static byte[] hexToByte(String hex) {
        int m = 0, n = 0;
        int byteLen = hex.length() / 2; // 每两个字符描述一个字节
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[i] = Byte.valueOf((byte) intVal);
        }
        return ret;
    }
}