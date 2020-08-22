package com.pay.typay.utils;

import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName UtilsUser
 * @Description
 * @Author JS-oswald
 * @Date 2020/1/13 下午9:05
 **/
public class UtilsUser {
    /**
     * 贯穿所有功能,所有用户请求都要带上supplierbranchid
     * @return
     */
    public static Long getUserSupplierbranchid(){
        SysUser sysUser = ShiroUtils.getSysUser();
        // setup supplierbranchid
        Long supplierbranchid = sysUser.getSupplierbranchid();
        return supplierbranchid;
    }
    public static String getSupplierBranchIdGroup(){
        SysUser sysUser = ShiroUtils.getSysUser();
        return sysUser.getSupplierbranchidgroup();
    }
    public static String getUserName(){
        SysUser sysUser = ShiroUtils.getSysUser();
        // setup supplierbranchid
        return sysUser.getUserName();
    }
    public static String getLoginName(){
        SysUser sysUser = ShiroUtils.getSysUser();
        // setup supplierbranchid
        return sysUser.getLoginName();
    }
    public static Long getUserId(){
        SysUser sysUser = ShiroUtils.getSysUser();
        // setup supplierbranchid
        return sysUser.getUserId();
    }


    public  static boolean checkPassWord (String passWord){
        String reg = "^(?=.*[A-Z].*)(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{8,20}$";
        Pattern regex = Pattern.compile(reg);
        Matcher matcher = regex.matcher(passWord);
        return  matcher.matches();
    }
}
