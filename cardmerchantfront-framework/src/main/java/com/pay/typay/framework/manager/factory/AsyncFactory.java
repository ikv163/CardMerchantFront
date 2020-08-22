package com.pay.typay.framework.manager.factory;

import com.pay.typay.common.constant.Constants;

import com.pay.typay.common.utils.ServletUtils;
import com.pay.typay.common.utils.spring.SpringUtils;
import com.pay.typay.framework.util.LogUtils;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysLogininfor;
import com.pay.typay.system.domain.SysOperLog;
import com.pay.typay.system.service.ISysOperLogService;
import com.pay.typay.system.service.impl.SysLogininforServiceImpl;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @author liuhulu
 */
public class AsyncFactory {
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    /**
     * 操作日志记录
     *
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final SysOperLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                // 远程查询操作地点
              //  operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringUtils.getBean(ISysOperLogService.class).insertOperlog(operLog);
            }
        };
    }

    /**
     * 记录登陆信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     * @return 任务task
     */
    public static TimerTask recordLogininfor(final String username, final String status, final String message, final Object... args) {
        HttpServletRequest request = ServletUtils.getRequest();

        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));

       // final String ip = IpUtils.getIpAddr(request);
        final  String ip = ShiroUtils.getIp();
        return new TimerTask() {
            @Override
            public void run() {
                String address = "";//AddressUtils.getRealAddressByIP(ip);
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(address);
                s.append(LogUtils.getBlock(username));
                s.append(LogUtils.getBlock(status));
                s.append(LogUtils.getBlock(message));
                // 打印信息到日志
                sys_user_logger.info(s.toString(), args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装对象
                SysLogininfor logininfor = new SysLogininfor();
                logininfor.setLoginName(username);
                logininfor.setIpaddr(ip);
                logininfor.setLoginLocation(address);
                logininfor.setBrowser(browser);
                logininfor.setOs(os);
                logininfor.setMsg(message);
                // 日志状态
                if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status)) {
                    logininfor.setStatus(Constants.SUCCESS);
                } else if (Constants.LOGIN_FAIL.equals(status)) {
                    logininfor.setStatus(Constants.FAIL);
                }
                // 插入数据
                SpringUtils.getBean(SysLogininforServiceImpl.class).insertLogininfor(logininfor);
            }
        };
    }
}
