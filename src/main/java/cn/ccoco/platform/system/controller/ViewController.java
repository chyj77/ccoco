package cn.ccoco.platform.system.controller;

import cn.ccoco.platform.common.authentication.ShiroHelper;
import cn.ccoco.platform.common.controller.BaseController;
import cn.ccoco.platform.common.entity.CCocoConstant;
import cn.ccoco.platform.common.utils.DateUtil;
import cn.ccoco.platform.common.utils.CcocoUtil;
import cn.ccoco.platform.system.entity.User;
import cn.ccoco.platform.system.service.IUserDataPermissionService;
import cn.ccoco.platform.system.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author CCoco
 */
@Controller("systemView")
@RequiredArgsConstructor
public class ViewController extends BaseController {

    private final IUserService userService;
    private final ShiroHelper shiroHelper;
    private final IUserDataPermissionService userDataPermissionService;

    @GetMapping("login")
    @ResponseBody
    public Object login(HttpServletRequest request) {
        if (CcocoUtil.isAjaxRequest(request)) {
            throw new ExpiredSessionException();
        } else {
            ModelAndView mav = new ModelAndView();
            mav.setViewName(CcocoUtil.view("login"));
            return mav;
        }
    }

    @GetMapping("unauthorized")
    public String unauthorized() {
        return CcocoUtil.view("error/403");
    }


    @GetMapping("/")
    public String redirectIndex() {
        return "redirect:/index";
    }

    @GetMapping("index")
    public String index(Model model) {
        AuthorizationInfo authorizationInfo = shiroHelper.getCurrentUserAuthorizationInfo();
        User user = super.getCurrentUser();
        User currentUserDetail = userService.findByName(user.getUsername());
        currentUserDetail.setPassword("It's a secret");
        model.addAttribute("user", currentUserDetail);
        model.addAttribute("permissions", authorizationInfo.getStringPermissions());
        model.addAttribute("roles", authorizationInfo.getRoles());
        return "index";
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "layout")
    public String layout() {
        return CcocoUtil.view("layout");
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "password/update")
    public String passwordUpdate() {
        return CcocoUtil.view("system/user/passwordUpdate");
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "user/profile")
    public String userProfile() {
        return CcocoUtil.view("system/user/userProfile");
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "user/avatar")
    public String userAvatar() {
        return CcocoUtil.view("system/user/avatar");
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "user/profile/update")
    public String profileUpdate() {
        return CcocoUtil.view("system/user/profileUpdate");
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "system/user")
    @RequiresPermissions("user:view")
    public String systemUser() {
        return CcocoUtil.view("system/user/user");
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "system/user/add")
    @RequiresPermissions("user:add")
    public String systemUserAdd() {
        return CcocoUtil.view("system/user/userAdd");
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "system/user/detail/{username}")
    @RequiresPermissions("user:view")
    public String systemUserDetail(@PathVariable String username, Model model) {
        resolveUserModel(username, model, true);
        return CcocoUtil.view("system/user/userDetail");
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "system/user/update/{username}")
    @RequiresPermissions("user:update")
    public String systemUserUpdate(@PathVariable String username, Model model) {
        resolveUserModel(username, model, false);
        return CcocoUtil.view("system/user/userUpdate");
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "system/role")
    @RequiresPermissions("role:view")
    public String systemRole() {
        return CcocoUtil.view("system/role/role");
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "system/menu")
    @RequiresPermissions("menu:view")
    public String systemMenu() {
        return CcocoUtil.view("system/menu/menu");
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "system/dept")
    @RequiresPermissions("dept:view")
    public String systemDept() {
        return CcocoUtil.view("system/dept/dept");
    }

    @RequestMapping(CCocoConstant.VIEW_PREFIX + "index")
    public String pageIndex() {
        return CcocoUtil.view("index");
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "404")
    public String error404() {
        return CcocoUtil.view("error/404");
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "403")
    public String error403() {
        return CcocoUtil.view("error/403");
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "500")
    public String error500() {
        return CcocoUtil.view("error/500");
    }

    private void resolveUserModel(String username, Model model, Boolean transform) {
        User user = userService.findByName(username);
        String deptIds = userDataPermissionService.findByUserId(String.valueOf(user.getUserId()));
        user.setDeptIds(deptIds);
        model.addAttribute("user", user);
        if (transform) {
            String sex = user.getSex();
            if (User.SEX_MALE.equals(sex)) {
                user.setSex("男");
            } else if (User.SEX_FEMALE.equals(sex)) {
                user.setSex("女");
            } else {
                user.setSex("保密");
            }
        }
        if (user.getLastLoginTime() != null) {
            model.addAttribute("lastLoginTime", DateUtil.getDateFormat(user.getLastLoginTime(), DateUtil.FULL_TIME_SPLIT_PATTERN));
        }
    }
}
