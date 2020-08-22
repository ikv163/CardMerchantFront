package com.pay.typay.utils;

/**
 * @ClassName DesUntil
 * @Description
 * @Author JS-oswald
 * @Date 2020/1/14 下午2:11
 **/

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author caps
 * @date 2019/12/5 11:57
 * DES CBC  加密解密工具类
 */
@Slf4j
public class DesUntil {

    private static String IV = "iv201812";

    private static String KEY = "backcardpw@@@@";


    /**
     * 加密
     *
     * @param data 待加密的明文
     * @param KEY  加密密钥
     * @return 密文
     * @throws Exception
     */
    public static String anyLengthEncrypt(String data) throws Exception {
        log.info("加密原文:" + data);
//        byte[] dd = new byte[data.length()];
//        byte[] cc = data.getBytes("UTF-8");
//        for (int i = 0; i < cc.length; i++) {
//            if (i > 31) {
//                dd[i] = 8;
//            } else {
//
//                dd[i] = cc[i];
//            }
//        }
//        data = new String(dd);
        SecureRandom sr = new SecureRandom();
        DESKeySpec ks = new DESKeySpec(KEY.getBytes("UTF-8"));
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey sk = skf.generateSecret(ks);
        Cipher cip = Cipher.getInstance("DES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes("UTF-8"));
        cip.init(Cipher.ENCRYPT_MODE, sk, ivSpec);// IV的方式
        // cip.init(Cipher.ENCRYPT_MODE, sk, sr);//没有传递IV
        log.info("加密后:" + new String(new Base64().encode(cip.doFinal(data
                .getBytes("UTF-8")))));
        return new String(new Base64().encode(cip.doFinal(data
                .getBytes("UTF-8"))));
    }

    /**
     * 加密
     *
     * @param 32位data 待加密的明文
     * @param KEY  加密密钥
     * @return 密文
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        log.info("加密原文:" + data);
        byte[] dd = new byte[40];
        byte[] cc = data.getBytes("UTF-8");
        for (int i = 0; i < cc.length; i++) {
            if (i > 31) {
                dd[i] = 8;
            } else {

                dd[i] = cc[i];
            }
        }
        data = new String(dd);
        SecureRandom sr = new SecureRandom();
        DESKeySpec ks = new DESKeySpec(KEY.getBytes("UTF-8"));
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey sk = skf.generateSecret(ks);
        Cipher cip = Cipher.getInstance("DES/CBC/PKCS5Padding");// Cipher.getInstance("DES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes("UTF-8"));
        cip.init(Cipher.ENCRYPT_MODE, sk, ivSpec);// IV的方式
        // cip.init(Cipher.ENCRYPT_MODE, sk, sr);//没有传递IV
        log.info("加密后:" + new String(new Base64().encode(cip.doFinal(data
                .getBytes("UTF-8")))));
        return new String(new Base64().encode(cip.doFinal(data
                .getBytes("UTF-8"))));
    }

    /**
     * 解密
     *
     * @param data 待解密的密文
     * @return 明文
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data)  {

        String decryptStr = null;
        try {
            log.info("解密前:" + data);
            if (data == null) {
                return null;
            }
            byte[] buf = new Base64().decode(data.getBytes("UTF-8"));
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(KEY.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes("UTF-8"));
            cipher.init(Cipher.DECRYPT_MODE, securekey, ivSpec);
            // cipher.init(Cipher.DECRYPT_MODE, securekey, sr);//没有传递IV
            decryptStr = new String(cipher.doFinal(buf));
            // decryptStr = decryptStr.substring(0, 32);

            decryptStr = decryptStr.replaceAll("\b|\u0020|\u3000", "");
            decryptStr = decryptStr.replaceAll("\u0001|\u0002|\u0003|\u0004|\u0005|\u0006|\u0007|\b|\t|\n|\u000b|\f" +
                    "|\r|\u000e|\u000f|\u0010|\u0011|\u0012|\u0013|\u0014", "");
            log.info("解密后:" + decryptStr);
        }catch (Throwable e) {
            throw  new RuntimeException("md5解密异常",e);
        }

        return decryptStr;
    }


    public static void main(String[] args) throws Exception {
        DateFormat date = new SimpleDateFormat("yyyyMMdd");
        System.out.println(date.format(new Date()));

        System.out.println("加密===================================================================");
        String cryptStr = "sadsadsada#5235#643";
        System.out.println(cryptStr.length());
        System.out.println("原文" + cryptStr);
        String desKeys = "backcardpw@@@@";
        String encryptPrint = encrypt(cryptStr);
        System.out.println("加密后:" + encryptPrint);

        String desKey = "backcardpw@@@@";
        String encryptStr = encryptPrint;
//        String encryptStr = "ZG+eAqHggGwGICq5Ko8/RtJokWUIDqI+" ;
//        System.out.println("KEY：" + desKey);
        System.out.println("原文：" + encryptStr);
        String decryptStr = DesUntil.decrypt(encryptStr);
        System.out.println("decryptStr：" + decryptStr);
        String decryptStrNew = decryptStr.substring(0, 32);
        System.out.println("解密后：" + decryptStrNew);



//        String cryptStr = "oswald";
//        System.out.println(cryptStr.length());
//        System.out.println("原文" + cryptStr);
//        String encryptPrint = anyLengthEncrypt(cryptStr);
//        System.out.println("加密后:" + encryptPrint);
//        String str = DesUntil.decrypt(encryptPrint);
//        System.out.println(str);
    }

}
