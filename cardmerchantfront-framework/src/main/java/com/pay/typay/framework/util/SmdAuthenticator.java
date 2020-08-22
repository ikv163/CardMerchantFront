package com.pay.typay.framework.util;

import org.springframework.stereotype.Component;
import seamoonotp.seamoonapi;

/**
 * @ClassName SmdAuthenticator
 * @Description
 * @Author JS-oswald
 * @Date 2020/2/9 下午4:29
 **/
@Component
public class SmdAuthenticator {

    public boolean verifySmdCode(String sninfo, String code) {
        seamoonapi sc = new seamoonapi();
        String str = sc.checkpassword(sninfo, code);
        return str.length() > 3;
    }

    public static void main(String[] args) {
        SmdAuthenticator smdAuthenticator = new SmdAuthenticator();
        boolean b = smdAuthenticator.verifySmdCode("799285293", "");

    }
}
