package com.pay.typay.framework.util;

import com.pay.typay.common.utils.DateUtils;
import com.pay.typay.framework.totp.TOTP;
import io.lettuce.core.codec.Base16;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import java.security.SecureRandom;

/**
 * @ClassName GoogleAuthenticator
 * @Description
 * @Author JS-oswald
 * @Date 2020/1/16 下午8:24
 **/
public class GoogleAuthenticator {
    /**
     * 必须:创建谷歌秘钥
     *
     * @return
     */
    public static String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[10];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    /**
     * 生成6位数密码跟前端匹配
     *
     * @param secretKey
     * @return
     */
    public static String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }

    /**
     * 生成6位数密码跟前端匹配
     * @param date
     * @param secretKey
     * @return
     */
    public static String getTOTPCode(String date,String secretKey) {
       long cNowTime= DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,date).getTime()/30000;
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(cNowTime,hexKey);
    }

    /**
     * 生成6位数密码跟前端匹配
     * @param date
     * @param secretKey
     * @return
     */
    public static String getTOTPCode(long date,String secretKey) {
        long cNowTime= date/30000;
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(cNowTime,hexKey);
    }
    /**
     * 只要秘钥一样.生成的6位数就是一样的.  配合前端谷歌身份证器测试一下
     * @param args
     */
    public static void main(String[] args) {
       String secretKey = generateSecretKey();
        String totpCode = getTOTPCode(secretKey);
        System.out.println(totpCode);
 //       String secretKey = "FIFAMOCVMDAMQNL525FU66JJIEKBKY6V";
//        String code = getTOTPCode(secretKey);
        System.out.println(secretKey);
    }
}
