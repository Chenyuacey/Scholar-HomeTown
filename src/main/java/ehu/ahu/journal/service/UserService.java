package ehu.ahu.journal.service;

import ehu.ahu.journal.dao.LoginTicketMapper;
import ehu.ahu.journal.dao.UserMapper;
import ehu.ahu.journal.pojo.LoginTicket;
import ehu.ahu.journal.pojo.User;
import ehu.ahu.journal.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author:Keyu
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    LoginTicketMapper loginTicketMapper;

    /**
     * 用户注册
     * @param username
     * @param password
     */
    public Map<String, Object> register(String username, String password){
        Map<String, Object> map = new HashMap<String, Object>();
        //验证环节
        if (username == null) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        if (password == null) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userMapper.selectByName(username);

        if (user != null) {
            map.put("msg", "用户名已经被注册");
            return map;
        }
        //写入数据库
        user = new User();
        user.setName(username);

        user.setPassword(Md5Util.MD5(password));
        user.setAuthority(0);
        userMapper.insertUser(user);

        // 登陆
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }


    /**
     * 用户登录
     * @param username
     * @param password
     */
    public Map<String,Object> login(String username, String password){

        Map<String,Object> map = new HashMap<>();
        //验证环节
        if (username == null) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        if (password == null) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userMapper.selectByName(username);

        if (user == null) {
            map.put("msg", "用户名不存在");
            return map;
        }

        //验证密码
        if(Md5Util.MD5(password).equals( user.getPassword())){
//            System.out.println("用户登录成功");
            String ticket = addLoginTicket(user.getId());
            map.put("ticket",ticket);

            return map;
        }
        else {
//            System.out.println("密码错误"+Md5Util.MD5(password+user.getSalt()) + "!=" + user.getPassword());
            return map;
        }


    }

    public void logout(String ticket){
        loginTicketMapper.updateStatus(ticket,1);
    }


    public User getUser(int id){
        return userMapper.selectById(id);
    }


    public String addLoginTicket(int userId){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date now = new Date();

        now.setTime(3600*24*1000 + now.getTime());
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketMapper.addTicket(loginTicket);

        return  loginTicket.getTicket();


    }

    public User selectUserbyName(String name){
        return userMapper.selectByName(name);
    }

    public User selectUserbyId(int id){
        return userMapper.selectById(id);
    }

}
