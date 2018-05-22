package com.tcc.web.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.EncodeException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcc.common.utils.JwtUtils;
import com.tcc.common.utils.Md5Util;
import com.tcc.common.utils.StrUtils;
import com.tcc.common.utils.VerificationUtil;
import com.tcc.web.bean.LoginMsgBean;
import com.tcc.web.entity.User;
import com.tcc.web.enums.LoginStatus;
import com.tcc.web.store.Constants;
import com.tcc.web.utils.CookieUtils;
import com.tcc.web.websocket.MsWebSocket;
import com.tcc.web.websocket.bean.MsMsg;

import io.jsonwebtoken.Claims;

@Controller
public class LoginController {
	
	private final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Resource
    private RedisTemplate<String, Object> redisTemplate;
	
	@PostMapping("/login")
	@ResponseBody
	public LoginMsgBean login(User user, String uri, String verification, String autoLogin, HttpServletRequest requset, HttpServletResponse response) throws IOException, EncodeException, NoSuchAlgorithmException {
		Cookie cookie = CookieUtils.getCookie(requset, Constants.JWT_COOKIE_NAME);
		if(cookie != null){
			Claims claims = JwtUtils.parseJWT(cookie.getValue());
			if(claims != null){
				if(JwtUtils.isAutoLogin(claims) && !JwtUtils.isExpirated(claims)){
					logger.info("{} login used authrization:{}", claims.get("userInfo"), cookie.getValue());
					return LoginMsgBean.create(LoginStatus.SUCCESS);
				}
			}
		}
		ValueOperations<String, Object> voper = redisTemplate.opsForValue();
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>)voper.get(uri);
		if(map == null){
			return LoginMsgBean.create(LoginStatus.INVALID_URI);
		}
		else if(map.get("verification") == null){
			return LoginMsgBean.create(LoginStatus.EXPIRE_VERFICATION);
		}
		else if(!Md5Util.b64Md5(verification.toLowerCase(), "verification").equals(map.get("verification"))){
			return LoginMsgBean.create(LoginStatus.ERROR_VERFICATION);
		}
		else {
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
			try {  
		        subject.login(token);
		        String jsonWebToken = JwtUtils.createJWT(user.getUsername(), !StrUtils.isBlank(autoLogin), "", "");
		        logger.info("{} login get authrization:{}", user.getUsername(), jsonWebToken);
		        CookieUtils.addJwtCookie(response, jsonWebToken);
		        return LoginMsgBean.create(LoginStatus.SUCCESS);
			}
			catch(UnknownAccountException e){
				return LoginMsgBean.create(LoginStatus.UNKOWN_ACCOUNT);
			}
			catch(IncorrectCredentialsException e){
				return LoginMsgBean.create(LoginStatus.ERROR_PASSWORD);
			}
			catch(LockedAccountException e){
				return LoginMsgBean.create(LoginStatus.LOCKED_ACCOUNT);
			}
			catch(DisabledAccountException e){
				return LoginMsgBean.create(LoginStatus.DISABLED_ACCOUNT);
			}
			finally {
			}
		}
	}
	@RequestMapping("/login")
	public String login(HttpServletRequest requset, ModelMap map) {
		Cookie cookie = CookieUtils.getCookie(requset, Constants.JWT_COOKIE_NAME);
		if(cookie != null){
			Claims claims = JwtUtils.parseJWT(cookie.getValue());
			if(claims != null && claims.get("userInfo") != null){
				map.put("userInfo", claims.get("userInfo"));
			}
		}
		return "login";
	}
	
	@RequestMapping("logon")
	public String logon(){
		return "logon";
	}
	
	@RequestMapping("/register")
	public String register() {
		return "register";
	}
	
	@RequestMapping("/verification")
	public void verification(@RequestParam(value="uri", required = true)String uri, HttpServletResponse response,@Value("${frontVerification}")Boolean frontVerification) throws IOException, EncodeException, NoSuchAlgorithmException {
		ValueOperations<String, Object> voper = redisTemplate.opsForValue();
		HashMap<String, Object> map = new HashMap<>();
		response.setContentType("image/jpeg");
		com.tcc.common.utils.VerificationUtil.Result result = VerificationUtil.create();
		String encryptionCode = Md5Util.b64Md5(result.getCode().toLowerCase(), "verification");
		logger.info("generate verification {} for {}, encryptionCode is {}",result.getCode(), uri, encryptionCode);
		map.put("verification", encryptionCode);
		voper.set(uri, map);
		//redisTemplate.expire(uri, 30, TimeUnit.SECONDS);
		if(frontVerification){
			MsWebSocket.send(uri, MsMsg.msg(encryptionCode, "verification"));
		}
		ImageIO.write(result.getImage(), "JPEG", response.getOutputStream());
	}
}
