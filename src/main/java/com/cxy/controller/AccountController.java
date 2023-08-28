package com.cxy.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccountController {


    @GetMapping("/{url}")
    public String redirect(@PathVariable("url") String url) {
        return url;
    }

    @PostMapping("/login")
    public String login(String username, String password, Model model) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        HashSet<Integer> h1=new HashSet<>();

        try {
            subject.login(token);
            // 进入AccountRealm的登录函数
            return "index";
        } catch (UnknownAccountException e) {
//            throw new RuntimeException(e);
            model.addAttribute("msg", "wrong username");
            e.printStackTrace();
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "password username");
            e.printStackTrace();
            return "login";
        }
//        return null;
    }

    @GetMapping("/unauth")
    @ResponseBody
    public String unauth() {
        return "未授权";
    }
}
