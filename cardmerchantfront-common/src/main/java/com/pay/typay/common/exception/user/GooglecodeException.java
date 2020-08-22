package com.pay.typay.common.exception.user;

/**
 * @ClassName GooglecodeException
 * @Description
 * @Author JS-oswald
 * @Date 2020/1/19 下午1:58
 **/
public class GooglecodeException extends UserException {
    private static final long serialVersionUID = 1L;
    public GooglecodeException() {
        super("user.googlecode.error", null);
    }
}
