package ehu.ahu.journal.interceptor;

import ehu.ahu.journal.dao.LoginTicketMapper;
import ehu.ahu.journal.dao.UserMapper;
import ehu.ahu.journal.pojo.HostHolder;
import ehu.ahu.journal.pojo.LoginTicket;
import ehu.ahu.journal.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Author:Keyu
 * 拦截器，用于验证用户身份
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {


    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        if (httpServletRequest.getCookies() != null) {
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();

                    break;
                }
            }
        }

        if (ticket != null) {//找到ticket
            LoginTicket loginTicket = loginTicketMapper.selectByTicket(ticket);
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
                //失效返回
                return true;
            }
            int  id = loginTicket.getUserId();
            User user = userMapper.selectById(id);

            //将用户信息放入上下文
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && hostHolder.getUser() != null) {
            //送出用户信息
            //保证验证成功的时候，所有页面都有用户信息保持
            User user = hostHolder.getUser();
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

        //清空
        hostHolder.clear();
    }
}
