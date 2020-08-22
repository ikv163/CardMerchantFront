package com.pay.typay.admin.controller.system;

import com.pay.typay.common.core.controller.BaseController;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.utils.StringUtils;
import com.pay.typay.common.utils.mango.MangoUtils;
import com.pay.typay.common.utils.telegram.TelegramUtils;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.framework.web.domain.server.Sys;
import com.pay.typay.system.domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 登录验证
 *
 * @author js-oswald
 */
@Controller
public class SysLoginController extends BaseController {
    @Autowired
    private TelegramUtils telegramUtils;


    @Value("${telegram.chatId}")
    private String telegarmChatId;

    @Value("${telegram.token}")
    private String telegarmToken;

    @Autowired
    private MangoUtils mangoUtils;

    @Value("${mango.userId}")
    private String userId;

    @Value("${mango.token}")
    private String mangoToken;

    @Value("${mango.roomname}")
    private String roomName;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe, String googlecodetop) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe, googlecodetop);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            String sendMsg = "【" + username + "】登录了卡分销前台";
            mangoUtils.sendMessage(userId,sendMsg,roomName,mangoToken);
            return AjaxResult.success();
        } catch (AuthenticationException e) {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            String sendMsg = "【" + username + "】登录卡分销前台失败，失败原因【"+ msg +"】";
            mangoUtils.sendMessage(userId,sendMsg,roomName,mangoToken);
            return error(msg);
        }
    }
    @GetMapping("/adminPage")
    public String adminPage(HttpServletRequest request, ModelMap mmap) {
        String  idCode=  request.getParameter("idCode");
        String   url= request.getParameter("url");
        mmap.put("idCode" , idCode);
        mmap.put("url" , url);
        return "adminPage";
    }
    @PostMapping("/adminPage")
    @ResponseBody
    public AjaxResult adminLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
           String idCode= request.getParameter("idCode");
            String url= request.getParameter("url");
            String[] idCodes=idCode.split("~");
            UsernamePasswordToken token = new UsernamePasswordToken(idCodes[0], "^#^admin^#^",false, idCodes[1]);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            return success(url);
        } catch (Exception e) {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
           return error(msg);
        }
    }

    @GetMapping("/unauth")
    public String unauth() {
        return "error/unauth";
    }
}
