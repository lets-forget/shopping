package com.ning.home_admin.sytem.controller;

import com.ning.home_admin.commons.connotation.Limit;
import com.ning.home_admin.commons.connotation.LimitType;
import com.ning.home_admin.commons.controller.BaseController;
import com.ning.home_admin.commons.exception.LoginException;
import com.ning.home_admin.commons.utils.CaptchaUtil;
import com.ning.home_admin.commons.utils.GeneralResultInfo;
import com.ning.home_admin.commons.utils.MD5Util;
import com.ning.home_admin.commons.utils.UUIDUtils;
import com.ning.home_admin.sytem.pojo.User;
import com.ning.home_admin.sytem.service.UserService;
import com.wf.captcha.base.Captcha;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UUIDUtils uuidUtils;


    @Limit(key = "login", period = 60, count = 20, name = "登录接口", prefix = "limit",limitType = LimitType.CUSTOMER)
    @PostMapping("/user/login")
    public GeneralResultInfo login(@RequestParam(value = "username",required = false) String username,
                                   @RequestParam(value = "password",required = false) String password, @RequestParam(value = "checkcode",required = false) String code,
                                   Model model, HttpServletRequest request) throws LoginException {
        if (!CaptchaUtil.verify(code, request))
            throw new LoginException("验证码错误");
            Subject subject= SecurityUtils.getSubject();
            //未认证用户
            if (!subject.isAuthenticated()){
                password = MD5Util.encrypt(username.toLowerCase(), password);
                UsernamePasswordToken token=new UsernamePasswordToken(username,password);
                try {
                    subject.login(token);
                }catch (Exception e){
                    throw new LoginException(e.getMessage());
                }

            }else{
                //已经登录
                throw new LoginException("你已登录，请勿重复登录");
            }

        return new GeneralResultInfo().success();
    }

    //注册
    @PostMapping("/user/register")
    public GeneralResultInfo register(User user, @RequestParam(value = "checkcode",required = false) String checkcode,
                                      HttpServletRequest request) throws LoginException {
        if (!CaptchaUtil.verify(checkcode,request))
            throw new LoginException("验证码错误");

        User u = userService.selectByUsername(user.getUsername());
        if (u!=null)
            throw new LoginException("用户名已存在，请重新输入");

        User email = userService.selectIsByEmail(user.getEmail());
        if (email!=null)
            throw new LoginException("邮箱已存在，请重新输入");

        try {
            user.setCode(uuidUtils.getUUID());
            userService.RegisterUser(user);
            userService.RegisterSendEmail(user.getCode(),user.getEmail(),user.getUsername());
        } catch (Exception e) {
            throw new LoginException("注册失败，请检查邮箱是否合法，或网络设置");
        }

        return new GeneralResultInfo().success();
    }



    //验证码
    @GetMapping("/images/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.outPng(110, 34, 4, Captcha.TYPE_ONLY_NUMBER, request, response);
    }
}
