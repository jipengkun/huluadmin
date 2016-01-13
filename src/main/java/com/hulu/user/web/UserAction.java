package com.hulu.user.web;

import com.google.code.kaptcha.Constants;
import com.hulu.base.core.web.BaseAction;
import com.hulu.base.springmvc.annotation.Login;
import com.hulu.user.bean.UserBean;
import com.hulu.user.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("user")
public class UserAction extends BaseAction {
	@Autowired
	private UserService userService;

	@RequestMapping("toLoginPage")
	public String toLoginPage(){
		return "user/login";
	}

	@Login(Login.ResultTypeEnum.page)
	@RequestMapping("toCurrentPage")
	public String toCurrentPage(){
		return "user/current";
	}

	@ResponseBody
	@RequestMapping("login")
	public String login(HttpServletRequest request,HttpServletResponse response){
		if(request.getSession().getAttribute(UserBean.USER_SESSION_KEY) != null){

		}else{
			Object validateCodeObj = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
			String validateCodeStr = validateCodeObj != null ? validateCodeObj.toString() : null;
			if(validateCodeStr != null){
				String validateCode = request.getParameter("validateCode");
				if(!validateCode.equalsIgnoreCase(validateCodeStr) ){
					return "validateCodeError";
				}
			}
			String phone = request.getParameter("phone");
			String password = request.getParameter("password");
			UserBean userBean = userService.queryByPhone(phone);
			if(userBean != null){
				String md5Pwd = DigestUtils.md5Hex(password);
				if(userBean.getPassword() != null && userBean.getPassword().equals(md5Pwd)){
					request.getSession().setAttribute(UserBean.USER_SESSION_KEY, userBean);
					return "success";
				}
			}
			return "nameOrPwdError";
		}
		return "";
	}

	@RequestMapping("unLogin")
	public String unLogin(HttpServletRequest request,HttpServletResponse response){
		request.getSession().removeAttribute(UserBean.USER_SESSION_KEY);
		return "user/login";
	}

}
