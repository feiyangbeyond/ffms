package cn.edu.tju.config;

import cn.edu.tju.controller.UserInfoController;
import cn.edu.tju.utils.Config;
import cn.edu.tju.entity.UserInfo;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

public class SessionInterceptor implements HandlerInterceptor {

    private static final List<String> uris = Arrays.asList("/", "/login.html", "/login.do", "/registry", "/user/registry", "/static/");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        //若session中没有用户信息 但cookie中存在用户信息，
        //则通过cookie中的信息重新初始化该用户信息，达到免登录的效果
        if (session.getAttribute(Config.CURRENT_USERNAME) == null && getCookieUser(request) != null) {
            if (HandlerMethod.class.equals(handler.getClass())) {
                Object controller = ((HandlerMethod) handler).getBean();
                if (controller instanceof UserInfoController) {
                    UserInfoController userInfoController = (UserInfoController) controller;
                    String userinfoStr = getCookieUser(request);
                    UserInfo userInfo = new UserInfo();
                    userInfo.setId(Integer.parseInt(userinfoStr.split("_")[1]));
                    userInfo.setUsername(userinfoStr.split("_")[0]);
                    userInfo = userInfoController.getUserInfo(userInfo);
                    userInfoController.setSessionUserInfo(userInfo, session);
                    return true;
                }
            }
        }

        String uri = request.getRequestURI();
        //是登录页面或者静态资源，不拦截
        if (!uris.contains(uri) && !uri.contains("/static/")) {
            if (session.getAttribute(Config.CURRENT_USERNAME) == null) {
                response.sendRedirect("/login.html");
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String uri = request.getRequestURI();
        if("/logout".equals(uri) && request.getSession().getAttribute(Config.CURRENT_USERNAME) == null){
           response.sendRedirect("/login.html");
        }

    }

    /**
     * 获取cookie中的用户信息
     * @param request
     * @return
     */
    private String getCookieUser(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return null;
        }else {
            for (Cookie cookie : cookies){
                if (Config.CURRENT_USERNAME.equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
            return null;
        }
    }
}
