package com.ning.home_admin.sytem.service;


import com.ning.home_admin.sytem.pojo.User;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService {

    User selectByUsername(String username);

    void RegisterSendEmail(String code,String email,String username) throws MessagingException;

    void RegisterUser(User user) throws MessagingException;

    boolean updatecheckCode(String code);

    boolean updateMessage(User user);

    boolean updateImage(Integer id, String toString);

    boolean updatePassword(User user);

    User selectIsByEmail(String email);

    boolean updateBypasswordAndEmail(String username, String email, String password);

    List<User> selectIsByEmailList(String email);
}
