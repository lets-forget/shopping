package com.ning.home_admin.sytem.service.impl;


import com.ning.home_admin.commons.exception.LoginException;
import com.ning.home_admin.commons.utils.MD5Util;
import com.ning.home_admin.commons.utils.MainUtils;
import com.ning.home_admin.commons.utils.UUIDUtils;
import com.ning.home_admin.sytem.mapper.UserMapper;
import com.ning.home_admin.sytem.pojo.User;
import com.ning.home_admin.sytem.service.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private MainUtils mainUtils;

    @Autowired
    private UserMapper userMapper;

    @Value("${email.addressAndProt}")
    private String emailAndProt;

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Async  //使用异步注册，加快运行速度
    @Override
    public void RegisterSendEmail(String code,String email,String username) throws MessagingException {

        String text="<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 600px; border: 1px solid #ddd; border-radius: 3px; color: #555; font-family: 'Helvetica Neue Regular',Helvetica,Arial,Tahoma,'Microsoft YaHei','San Francisco','微软雅黑','Hiragino Sans GB',STHeitiSC-Light; font-size: 12px; height: auto; margin: auto; overflow: hidden; text-align: left; word-break: break-all; word-wrap: break-word;\"> \n" +
                "        <tbody style=\"margin: 0; padding: 0;\"> \n" +
                "            <tr style=\"background-color: #393D49; height: 60px; margin: 0; padding: 0;\"> \n" +
                "                <td style=\"margin: 0; padding: 0;\"> \n" +
                "                    <div style=\"color: #5EB576; margin: 0; margin-left: 30px; padding: 0;\">\n" +
                "                        <a style=\"font-size: 14px; margin: 0; padding: 0; color: #5EB576; text-decoration: none;\" href=\"http://www.ningkangwen.com/\" target=\"_blank\" rel=\"noopener\">购物商城 -- 开发者：宁康文 -- QQ：2055569134</a></div> </td> </tr> <tr style=\"margin: 0; padding: 0;\"> \n" +
                "                            <td style=\"margin: 0; padding: 30px;\"> <p style=\"line-height: 20px; margin: 0; margin-bottom: 10px; padding: 0;\"> Hi，<em style=\"font-weight: 700;\">"+username+"</em>，请完成以下操作： </p> \n" +
                "                                <div> <a href=\"http://"+emailAndProt+"/user/activate/?token="+code+"\" style=\"background-color: #009E94; color: #fff; display: inline-block; height: 32px; line-height: 32px; margin: 0 15px 0 0; padding: 0 15px; text-decoration: none;\" target=\"_blank\" rel=\"noopener\">立即激活邮箱</a> \n" +
                "                            </div> <p style=\"line-height: 20px; margin-top: 20px; padding: 10px; background-color: #f2f2f2; font-size: 12px;\"> 如果该邮件不是由你本人操作，请勿进行激活！否则你的邮箱将会被他人绑定。 </p> </td> </tr> <tr style=\"background-color: #fafafa; color: #999; height: 35px; margin: 0; padding: 0; text-align: center;\"> <td style=\"margin: 0; padding: 0;\">系统邮件，请勿直接回复。</td> </tr> \n" +
                "                        </tbody> \n" +
                "    </table>\n" +
                "</body>\n" +
                "</html>";
        //发送邮件 激活码  yjlarinxyteibicf
        mainUtils.sendMain(email,"激活",text);
    }

    //注册
    @Async  //使用异步注册，加快运行速度
    @Override
    @Transactional()
    public void RegisterUser(User user) {
        user.setAvatar("/userImage/newUser.jpg");
        user.setCreateTime(new Date());
        user.setStatus(1);
        user.setActivate(0);
        user.setPassword(MD5Util.encrypt(user.getUsername(),user.getPassword()));
        int insert = userMapper.insert(user);
        if (insert<=0)
            throw new RuntimeException("注册失败");
    }

    //激活
    @Override
    public boolean updatecheckCode(String code) {
        int isecheck=userMapper.updatecheckCode(code);
        return isecheck>0?true:false;
    }
    //修改资料
    @Override
    public boolean updateMessage(User user) {
        int isupate=userMapper.updateByPrimaryKey(user);
        return isupate>0?true:false;
    }

    @Override
    public boolean updateImage(Integer id, String toString) {
        int isupdate=userMapper.updateImage(id,toString);
        return isupdate>0?true:false;
    }

    //修改密码
    @Override
    public boolean updatePassword(User user) {
        // 将用户名作为盐值

        int isupdate=userMapper.updatePassword(user.getId(),MD5Util.encrypt(user.getUsername(),user.getPassword()));
        return isupdate>0?true:false;
    }

    //步骤1查询邮箱
    @Override
    public User selectIsByEmail(String email) {

        return userMapper.selectIsByEmail(email);
    }

    @Override
    public boolean updateBypasswordAndEmail(String username,String email, String password) {
        // 将用户名作为盐值
        ByteSource salt = ByteSource.Util.bytes(username);

        String newPs = new SimpleHash("MD5", password, salt, 1024).toHex();
        int isupdate=userMapper.updateByUsernameAndPassword(username,newPs);
        return isupdate>0?true:false;
    }

    @Override
    public List<User> selectIsByEmailList(String email) {
        return userMapper.selectIsByEmailList(email);
    }

}
