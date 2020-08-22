package com.pay.typay.common.exception.user;

/**
 * 用户密码不正确或不符合规范异常类
 *
 * @author js-oswald
 */
public class UserPasswordExpiredException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordExpiredException() {
        super("user.expired" , null);
    }
}
