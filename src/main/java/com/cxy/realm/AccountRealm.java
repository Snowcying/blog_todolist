package com.cxy.realm;

import com.cxy.entity.Account;
import com.cxy.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

//@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    private AccountService accountService;

    // 角色的信息权限集合  授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Subject subject = SecurityUtils.getSubject();
        Account account = (Account) subject.getPrincipal();


        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 设置角色
        Set<String> roles = new HashSet<>();
        roles.add(account.getRole());
        info.setRoles(roles);

        // 设置权限
        info.addStringPermission(account.getPerms());

        return info;
    }


    // 用户的角色信息集合  认证（登录)
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        Account account = accountService.findByUsername(token.getUsername());
        if (account != null) {
            return new SimpleAuthenticationInfo(account, account.getPassword(), getName());  // 第一个参数account为principal ,之后在授权的地方.getPrincipal()
        }

        return null;
    }
}
