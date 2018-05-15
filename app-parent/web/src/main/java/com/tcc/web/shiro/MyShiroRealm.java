package com.tcc.web.shiro;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.tcc.web.entity.User;
import com.tcc.web.service.UserService;
import com.tcc.web.store.Constants;

public class MyShiroRealm extends AuthorizingRealm{
	
	@Resource
	private UserService userserive;
	/**
	 * 认证用户信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();  
        User user = userserive.selectByUsername(username);
        if(user == null){
        	return null;
        }
        int state = user.getState();
        if(state == Constants.STATE_INVAILD){//无效
        	throw new DisabledAccountException();
        }
        else if(state == Constants.STATE_LOCK){//锁住
        	throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(  
        		user, //用户名  
        		user.getPassword(), //密码  
                ByteSource.Util.bytes(user.getUsername() + user.getPassword()),//盐 
                getName()
        );  
        return authenticationInfo;
	}
	/**
	 * 权限信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}
}

