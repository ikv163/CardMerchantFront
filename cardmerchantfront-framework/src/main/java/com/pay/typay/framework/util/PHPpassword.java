package com.pay.typay.framework.util;


import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * @ClassName PHPpassword
 * @Description
 * @Author JS-oswald
 * @Date 2020/1/16 下午9:05
 **/
public class PHPpassword {
    public static void main(String[] args) {
        String password = "admin123";
        String hash = PHPpasswordHash(password);
        System.out.println(hash);
        boolean b = PHPpasswordVerify(password, hash);
        boolean b2 = PHPpasswordVerify(password, "$2y$13$FWT6LT6/5Jg4ahs/OYU2NuZ3WHYD5rnBVJardds7mJCjm1sFQ5U8y");
        System.out.println(b);
        System.out.println(b2);
    }

    /**
     * java版本的passwordHash
     * @param password
     * @return
     */
    public static String PHPpasswordHash(String password) {
        return BCrypt.withDefaults().hashToString(13, password.toCharArray());
    }
    /**
     * 验证passsword
     * @param password
     * @param hashphpassword
     * @return
     */
    public static boolean PHPpasswordVerify(String password, String hashphpassword) {
        BCrypt.Result res = BCrypt.verifyer().verify(password.toCharArray(), hashphpassword);
        return res.verified;
    }
}
