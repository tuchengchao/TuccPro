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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.web.entity.User;
import com.tcc.web.enums.LoginType;
import com.tcc.web.service.UserService;
import com.tcc.web.store.Constants;

public class MyShiroRealm extends AuthorizingRealm{
	
	private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);
	
	@Resource
	private UserService userserive;
	
	/**
	 * 认证用户信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();  
        logger.info("doGetAuthenticationInfo for {}", username);
        User user = userserive.selectByUsername(username);
        if(user == null){
        	return null;
        }
        int state = user.getState();
        if(state == Constants.STATE_DISABLED){//无效
        	throw new DisabledAccountException();
        }
        else if(state == Constants.STATE_LOCKED){//锁住
        	throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(  
        		user, //用户名  
        		user.getPassword(), //密码  
                ByteSource.Util.bytes(user.getUsername()),//盐 
                getName()
        );  
        return authenticationInfo;
	}
	/**
	 * 权限信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String)principals.getPrimaryPrincipal();
        logger.info("doGetAuthenticationInfo for {}", username);
		return null;
	}
	
	/**
	 * 令牌验证
	 */
	@Override
	protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info)
			throws AuthenticationException {
		MyUsernamePasswordToken myToken = (MyUsernamePasswordToken) token;
		logger.info("{} login with loginType {}", myToken.getUsername(), myToken.getLoginType());
		LoginType loginType = myToken.getLoginType();
		if(loginType.equals(LoginType.AUTO)){
			//自动登录 免密码验证
		}
		else if(loginType.equals(LoginType.PASSWORD)){
			super.assertCredentialsMatch(myToken, info);
		}
	}
}

