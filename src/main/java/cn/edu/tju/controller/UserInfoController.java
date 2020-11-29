package cn.edu.tju.controller;

import cn.edu.tju.entity.Privilege;
import cn.edu.tju.entity.Role;
import cn.edu.tju.entity.UserInfo;
import cn.edu.tju.service.PrivilegeService;
import cn.edu.tju.service.UserInfoService;
import cn.edu.tju.utils.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private PrivilegeService privilegeService;

    @RequestMapping(value = {"/", "login.html"})
    public String toLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute(Config.CURRENT_USERNAME) == null) {
            return "login";
        } else {
            return "redirect:/pages/index";
        }
    }

    @RequestMapping(value = "/login.do")
    @ResponseBody
    public Result getUserInfo(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response) {
        boolean userIsExisted = userInfoService.userIsExisted(userInfo);

        if (!userIsExisted) {
            return ResultUtil.unSuccess("用户名或密码错误！");
        } else {
            userInfo = getUserInfo(userInfo);
            //将用户信息存入session
            setSessionUserInfo(userInfo, request.getSession());
            //将当前用户信息存入cookie
            setCookieUser(request, response);
            return ResultUtil.success("登录成功", userInfo);
        }
    }

    @ResponseBody
    @RequestMapping("/users/getUsersByWhere/{pageNo}/{pageSize}")
    public Result getUsersByWhere(UserInfo userInfo, @PathVariable int pageNo, @PathVariable int pageSize, HttpSession session) {
        if ("".equals(userInfo.getHouseid())) {
            userInfo.setHouseid(null);
        }
        if (userInfo.getRoleid() == -1) {
            userInfo.setRoleid(Config.getSessionUser(session).getRoleid());
        }
        PageModel model = new PageModel<>(pageNo, userInfo);
        model.setPageSize(pageSize);
        return userInfoService.getUsersByWhere(model);
    }

    @ResponseBody
    @RequestMapping("/user/add")
    public Result addUser(UserInfo userInfo) {
        try {
            int num = userInfoService.add(userInfo);
            if (num > 0) {
                return ResultUtil.success();
            } else {
                return ResultUtil.unSuccess();
            }
        } catch (Exception e) {
            return ResultUtil.error(e);
        }
    }

    @ResponseBody
    @RequestMapping("/user/update")
    public Result updateUser(UserInfo userInfo) {
        try {
            int num = userInfoService.update(userInfo);
            if (num > 0) {
                return ResultUtil.success();
            } else {
                return ResultUtil.unSuccess();
            }
        } catch (Exception e) {
            return ResultUtil.error(e);
        }
    }

    @ResponseBody
    @RequestMapping("/user/del/{id}")
    public Result deleteUser(@PathVariable String id) {
        try {
            int num = userInfoService.delete(id);
            if (num > 0) {
                return ResultUtil.success();
            } else {
                return ResultUtil.unSuccess();
            }
        } catch (Exception e) {
            return ResultUtil.error(e);
        }
    }

    @RequestMapping("/getSessionUser")
    @ResponseBody
    public UserInfo getSessionUser(HttpSession session) {
        UserInfo sessionUser = (UserInfo) session.getAttribute(Config.CURRENT_USERNAME);
        sessionUser.setPassword(null);
        return sessionUser;
    }

    @RequestMapping("/logout")
    @ResponseBody
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        delCookieUser(request, response);
        request.getSession().removeAttribute(Config.CURRENT_USERNAME);
    }

    @ResponseBody
    @RequestMapping("/getAllRoles")
    public Result<Role> getAllRoles() {
        try {
            List<Role> roles = userInfoService.getAllRoles();
            if (roles.size() > 0) {
                return ResultUtil.success(roles);
            } else {
                return ResultUtil.unSuccess();
            }
        } catch (Exception e) {
            return ResultUtil.error(e);
        }
    }

    @ResponseBody
    @RequestMapping("/role/add")
    public Result addRole(Role role) {
        try {
            int num = userInfoService.addRole(role);
            if (num > 0) {
                privilegeService.addDefaultPrivilegesWhenAddRole(role.getRoleid().toString());
                return ResultUtil.success();
            } else {
                return ResultUtil.unSuccess();
            }
        } catch (Exception e) {
            return ResultUtil.error(e);
        }
    }

    @ResponseBody
    @RequestMapping("/role/update")
    public Result updateRole(Role role) {
        try {
            int num = userInfoService.updateRole(role);
            if (num > 0) {
                return ResultUtil.success();
            } else {
                return ResultUtil.unSuccess();
            }
        } catch (Exception e) {
            return ResultUtil.error(e);
        }
    }

    @ResponseBody
    @RequestMapping("/role/del/{roleid}")
    public Result deleteRole(@PathVariable String roleid) {
        try {
            privilegeService.delPrivilegesWenDelRole(roleid);
            int num = userInfoService.deleteRole(roleid);
            if (num > 0) {
                return ResultUtil.success();
            } else {
                privilegeService.addDefaultPrivilegesWhenAddRole(roleid);
                return ResultUtil.unSuccess();
            }
        } catch (Exception e) {
            return ResultUtil.error(e);
        }
    }

    @ResponseBody
    @RequestMapping("/getRole/{id}")
    public Result getRoleById(@PathVariable String id) {
        try {
            Role role = userInfoService.getRoleById(id);
            if (role != null) {
                return ResultUtil.success(role);
            } else {
                return ResultUtil.unSuccess();
            }
        } catch (Exception e) {
            return ResultUtil.error(e);
        }
    }

    /**
     * 登录时将用户信息加入cookie中
     *
     * @param response
     */
    private void setCookieUser(HttpServletRequest request, HttpServletResponse response) {
        UserInfo user = getSessionUser(request.getSession());
        Cookie cookie = new Cookie(Config.CURRENT_USERNAME, user.getUsername() + "_" + user.getId());
        //cookie 保存7天
        cookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(cookie);
    }

    /**
     * 注销时删除cookie信息
     *
     * @param request
     * @param response
     */
    private void delCookieUser(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(Config.CURRENT_USERNAME, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * 通过用户信息获取用户权限信息，并存入session中
     *
     * @param userInfo
     * @param session
     * @return
     */
    public void setSessionUserInfo(UserInfo userInfo, HttpSession session) {
        List<Privilege> privileges = privilegeService.getPrivilegeByRoleid(userInfo.getRoleid());
        userInfo.setPrivileges(privileges);
        session.setAttribute(Config.CURRENT_USERNAME, userInfo);
    }

    public UserInfo getUserInfo(UserInfo userInfo) {
        userInfo.setPassword(null);
        return userInfoService.getUserInfo(userInfo);
    }

    @ResponseBody
    @PostMapping("/user/registry")
    public Result registry(UserInfo userInfo) {
        int registry = userInfoService.registry(userInfo);
        if (registry == 1) {
            return ResultUtil.success();
        }
        return ResultUtil.unSuccess();
    }

    @GetMapping("/registry")
    public String toRegistry() {
        return "registry";
    }
}
