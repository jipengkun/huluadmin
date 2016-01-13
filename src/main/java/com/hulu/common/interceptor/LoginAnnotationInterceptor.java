package com.hulu.common.interceptor;

import com.hulu.base.springmvc.annotation.Login;
import com.hulu.user.bean.UserBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by huangyiwei on 15/10/23.
 */
public class LoginAnnotationInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        Method method = handlerMethod.getMethod();
//        Login login = method.getAnnotation(Login.class);

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Login login = handlerMethod.getMethodAnnotation(Login.class);

        if (null == login) {
            // 没有声明权限,放行
            return true;
        }

        HttpSession session = request.getSession();
        //取得session中的用户信息, 以便判断是否登录了系统
        UserBean userBean = (UserBean) session.getAttribute(UserBean.USER_SESSION_KEY);

        if (null == userBean) {
            // 需要登录
            if (login.value() == Login.ResultTypeEnum.page) {
                //传统页面的登录
                request.getRequestDispatcher("/user/toLoginPage").forward(request, response);
            } else if (login.value() == Login.ResultTypeEnum.json) {
                //ajax页面的登录
                response.setCharacterEncoding("utf-8");
                response.setContentType("text/html;charset=UTF-8");
                OutputStream out = response.getOutputStream();
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out,"utf-8"));
                //返回json格式的提示
                pw.println("{\"errorMsg\":\"current user is not login.\"}");
                pw.flush();
                pw.close();
            }
            return false;
        }
        return true;
    }
}