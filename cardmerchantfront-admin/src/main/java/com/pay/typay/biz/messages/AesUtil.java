package com.pay.typay.biz.messages;


import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.*;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesUtil{

    private static String IV = "typay!@#qwerasdf";

    private static String password = "tianxiazhifuxbtx";

    /**
     * AES加密
     *
     * @param str
     *            明文
     * @param key
     *            秘钥
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
            return  byteToHex(encrypted);
        }catch (Exception e){

            return  e.toString();
        }

    }

    /**
     * AES解密
     *
     * @param input
     * @param key
     * @return
     */
    public static String decrypt(String input) {
        try {
            byte[] raw = password.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
             Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
             IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
             cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] content = hexToByte(input);
            byte[] original = cipher.doFinal(content);
             String originalString = new String(original,"UTF8");
             return originalString;
         } catch (Exception ex) {
            return null;
         }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String data = "order=dsadsadsa&key=dadsa2321"; // 明文
        String enStr = AesUtil.encrypt(data);
        System.out.println(URLEncoder.encode(enStr, "UTF-8"));
        System.out.println(AesUtil.decrypt(enStr));
    }

    public static String byteToHex(byte[] bytes){
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
     * @param hex
     * @return
     */
    public static byte[] hexToByte(String hex){
        int m = 0, n = 0;
        int byteLen = hex.length() / 2; // 每两个字符描述一个字节
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[i] = Byte.valueOf((byte)intVal);
        }
        return ret;
    }
}