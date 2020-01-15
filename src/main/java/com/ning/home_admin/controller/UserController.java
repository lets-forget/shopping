package com.ning.home_admin.controller;


import com.ning.home_admin.commons.exception.LoginException;
import com.ning.home_admin.commons.utils.FastDFSClient;
import com.ning.home_admin.commons.utils.GeneralResultInfo;
import com.ning.home_admin.commons.utils.MainUtils;
import com.ning.home_admin.commons.utils.UUIDUtils;
import com.ning.home_admin.sytem.pojo.User;
import com.ning.home_admin.sytem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MainUtils mainUtils;


    private static final List<String> ALLOW_TYPES = Arrays.asList("image/jpeg","image/png","image/bmp","image/gif");


    //激活
    @RequestMapping("/activate")
    public String checkCode(String token){
        try{
            boolean isCheck=userService.updatecheckCode(token);
            if (isCheck)
                return "redirect:/success";
            else
                return "redirect:/fail";

        }catch (Exception e){
            throw new RuntimeException("激活失败");
        }
    }

    //发送重新密码的邮件
    @GetMapping("/sendEmail")
    @ResponseBody
    public GeneralResultInfo sendEmail(String email,HttpSession session) throws LoginException {
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for (int i = 1; i <=6 ; i++) {
            sb.append(random.nextInt(10));
        }
        session.setAttribute("remima_code",sb.toString());
        String text="<p>重置验证码<span style='color:red;font-size:28px;padding:7px;'>"+sb.toString()+"</span>请在1分钟之内修改密码</p>";
        try {
            mainUtils.sendMain(email,"重置密码",text);
            Timer timer=new Timer();
            //定时60秒删除session
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    session.removeAttribute("remima_code");
                }
            },60*1000);
        } catch (Exception e) {
            throw new LoginException("请检查邮箱是否合法，或网络设置");
        }
        return new GeneralResultInfo().success();
    }

    //修改密码
    @PostMapping("/remima")
    @ResponseBody
    public GeneralResultInfo remima(@RequestParam(value = "password",required = false) String password,@RequestParam(value = "checkCode",required = false) String checkCode,HttpSession session,Model model) throws LoginException {

        if(session.getAttribute("remima_code")==null)
            throw new LoginException("你没有发送验证码");
        if (!checkCode.equals(session.getAttribute("remima_code").toString()))
            throw new LoginException("验证码错误，请重新输入");

        User session_user= (User) session.getAttribute("user");
        User user=new User();
        user.setPassword(password);
        user.setUserId(session_user.getId());
        user.setUsername(session_user.getUsername());
        boolean isupdate=userService.updatePassword(user);
        if (!isupdate)
            throw new LoginException("修改失败");

        return new GeneralResultInfo().success();
    }
    //修改基础信息
    @RequestMapping("/updateMessage")
    @ResponseBody
    public GeneralResultInfo updateMessage(User user,HttpSession session) throws LoginException {
        User session_user= (User) session.getAttribute("user");
        //修改资料
        boolean isUpdate=userService.updateMessage(user);
        if (!isUpdate)
            throw new LoginException("修改失败");

        User newUser = userService.selectByUsername(session_user.getUsername());
        session.setAttribute("user",newUser);
        return new GeneralResultInfo().success();
    }

    //修改头像
    @RequestMapping("/uploadImage")
    @ResponseBody
    public GeneralResultInfo UpdateImage(MultipartFile file,HttpSession session) throws IOException, LoginException {
        //校验图片的类型
        if(!ALLOW_TYPES.contains(file.getContentType()))
            throw new LoginException("上传的文件类型无效");
        //校验文件内容
        BufferedImage image= ImageIO.read(file.getInputStream());
        if (image==null)
            throw new LoginException("上传文件类型不符合，只允许上传图片");

        User session_user= (User) session.getAttribute("user");

        try {
            //保存文件
            String path = FastDFSClient.saveFile(file);
            boolean b = userService.updateImage(session_user.getId(), path);
            if (b) {
                //查看用户的图片地址是否包含http
                if(session_user.getAvatar().contains("http")){
                    String[] split = session_user.getAvatar().split("/");
                    String group=null;
                    String roteme = "";
                    for (int i = 0; i <split.length ; i++) {
                        group=split[3];
                        if (i>3){
                            roteme+=split[i]+"/";
                        }
                    }
                    //删除原来的文件
                    FastDFSClient.deleteFile(group,roteme.substring(0,roteme.lastIndexOf("/")));
                }

                User newUser = userService.selectByUsername(session_user.getUsername());
                session.setAttribute("user",newUser);
            }
        } catch (Exception e) {
            throw new LoginException("上传文件失败");
        }
        return new GeneralResultInfo().success();
    }


    //修改密码步骤1
    @PostMapping("/forget1")
    @ResponseBody
    public GeneralResultInfo forget1(@RequestParam(value = "email",required = false) String email) throws LoginException {
        User user = userService.selectIsByEmail(email);
        if (user == null)
            throw new LoginException("没有该邮箱，请重新输入");
        return new GeneralResultInfo().success();
    }
    //修改密码步骤2 校验验证码
    @PostMapping("/verifyCode")
    @ResponseBody
    public GeneralResultInfo checkCode(String code,HttpSession session) throws LoginException {
        if (!code.equalsIgnoreCase(session.getAttribute("remima_code").toString())){
            throw new LoginException("验证码错误请重新输入，您只有60秒的时间");
        }
        return new GeneralResultInfo().success();
    }

    //修改密码，步骤3
    @PostMapping("/repassword")
    @ResponseBody
    public GeneralResultInfo repassword(@RequestParam(value = "password",required = false) String password,@RequestParam(value = "email",required = false)String email,HttpSession session,Model model) throws LoginException {
        User byEmail = userService.selectIsByEmail(email);
        if (byEmail==null)
            throw new LoginException("没有该邮箱，请重新输入");

        User user=new User();
        user.setUsername(byEmail.getUsername());
        user.setPassword(password);
        user.setUserId(byEmail.getId());
        //修改密码
        try{
            boolean isupdate=userService.updatePassword(user);
            if (!isupdate){
                throw new LoginException("修改密码失败");
            }
        }catch (Exception e){
            throw new LoginException("修改密码失败");
        }

        return new GeneralResultInfo().success();
    }

    /**
     * 不使用fastdfs
     *    //修改头像
     *     @RequestMapping("/uploadImage")
     *     @ResponseBody
     *     public GeneralResultInfo UpdateImage(MultipartFile file,HttpSession session) throws IOException, LoginException {
     *         //校验图片的类型
     *         if(!ALLOW_TYPES.contains(file.getContentType()))
     *             throw new LoginException("上传的文件类型无效");
     *         //校验文件内容
     *         BufferedImage image= ImageIO.read(file.getInputStream());
     *         if (image==null)
     *             throw new LoginException("上传文件类型不符合，只允许上传图片");
     *
     *         User session_user= (User) session.getAttribute("user");
     *         //获取原始的文件名
     *         String fileName = file.getOriginalFilename();
     *         String newFileName=fileName.substring(fileName.indexOf("."),fileName.length());
     *
     *         //保存文件
     *         try {
     *             //获取跟目录
     *             //获取项目classes/static的地址
     *             String path = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
     *             //文件名
     *             String imageName= "/userImage/" +uuidUtils.getUUID()+newFileName;
     *             // 绝对路径=项目路径+自定义路径
     *             File upload = new File(path+imageName);
     *             //拷贝图片
     *             file.transferTo(upload);
     *             boolean b = userService.updateImage(session_user.getId(), imageName);
     *             if (b) {
     *                 //删除原来的文件
     *                 File delete=new File(path+"/"+session_user.getAvatar());
     *                 if (!session_user.getAvatar().contains("newUser.jpg")){
     *                     delete.delete();
     *                 }
     *                 User newUser = userService.selectByUsername(session_user.getUsername());
     *                 session.setAttribute("user",newUser);
     *             }
     *         } catch (IOException e) {
     *             throw new LoginException("上传文件失败");
     *         }
     *         return new GeneralResultInfo().success();
     *     }
     */
}
