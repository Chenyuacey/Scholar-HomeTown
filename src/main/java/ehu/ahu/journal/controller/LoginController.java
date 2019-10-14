package ehu.ahu.journal.controller;

import ehu.ahu.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author:Keyu
 */
@Controller
public class LoginController {

    @Autowired
    UserService userService;



    /**
     * 登录
     * @param model
     * @param username
     * @param password
     * @param rememberme
     * @param response
     * @return
     */
    @RequestMapping(path = {"/login"},method = {RequestMethod.POST})
    public String login(Model model, @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value="rememberme", defaultValue = "false") boolean rememberme,
                        HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme) {
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                response.addCookie(cookie);

                return "redirect:/";
            }

        } catch (Exception e) {
            model.addAttribute("failedtologin","服务器处理失败");
            return "login";

        }
        model.addAttribute("failedtologin","用户名不存在或者密码错误");
        return "login";
    }

    /**
     * 注册
     * @param model
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(path = {"/reg"},method = {RequestMethod.POST})
    public String register(Model model, @RequestParam("username") String username,
                           @RequestParam("password") String password
    ){
        try {
            Map<String, Object> map = userService.register(username, password);
            if (map.get("msg") == null){
                model.addAttribute("failedtologin","注册成功，请登录");
            }else {
                model.addAttribute("failedtologin",map.get("msg"));
            }

            return "login";
        } catch (Exception e) {
            model.addAttribute("failedtologin","服务器处理失败");
            return "login";
        }
    }

    /**
     * 注销
     * @param ticket
     * @return
     */
    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }

}
