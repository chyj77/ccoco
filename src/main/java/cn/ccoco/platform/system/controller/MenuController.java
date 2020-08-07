package cn.ccoco.platform.system.controller;


import cn.ccoco.platform.common.annotation.ControllerEndpoint;
import cn.ccoco.platform.common.controller.BaseController;
import cn.ccoco.platform.common.entity.CcocoResponse;
import cn.ccoco.platform.common.entity.MenuTree;
import cn.ccoco.platform.common.exception.CcocoException;
import cn.ccoco.platform.system.entity.Menu;
import cn.ccoco.platform.system.entity.User;
import cn.ccoco.platform.system.service.IMenuService;
import com.wuwenze.poi.ExcelKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author CCoco
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("menu")
public class MenuController extends BaseController {

    private final IMenuService menuService;

    @GetMapping("{username}")
    public CcocoResponse getUserMenus(@NotBlank(message = "{required}") @PathVariable String username) throws CcocoException {
        User currentUser = getCurrentUser();
        if (!StringUtils.equalsIgnoreCase(username, currentUser.getUsername())) {
            throw new CcocoException("您无权获取别人的菜单");
        }
        MenuTree<Menu> userMenus = this.menuService.findUserMenus(username);
        return new CcocoResponse().data(userMenus);
    }

    @GetMapping("tree")
    @ControllerEndpoint(exceptionMessage = "获取菜单树失败")
    public CcocoResponse getMenuTree(Menu menu) {
        MenuTree<Menu> menus = this.menuService.findMenus(menu);
        return new CcocoResponse().success().data(menus.getChilds());
    }

    @PostMapping
    @RequiresPermissions("menu:add")
    @ControllerEndpoint(operation = "新增菜单/按钮", exceptionMessage = "新增菜单/按钮失败")
    public CcocoResponse addMenu(@Valid Menu menu) {
        this.menuService.createMenu(menu);
        return new CcocoResponse().success();
    }

    @GetMapping("delete/{menuIds}")
    @RequiresPermissions("menu:delete")
    @ControllerEndpoint(operation = "删除菜单/按钮", exceptionMessage = "删除菜单/按钮失败")
    public CcocoResponse deleteMenus(@NotBlank(message = "{required}") @PathVariable String menuIds) {
        this.menuService.deleteMenus(menuIds);
        return new CcocoResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("menu:update")
    @ControllerEndpoint(operation = "修改菜单/按钮", exceptionMessage = "修改菜单/按钮失败")
    public CcocoResponse updateMenu(@Valid Menu menu) {
        this.menuService.updateMenu(menu);
        return new CcocoResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("menu:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(Menu menu, HttpServletResponse response) {
        List<Menu> menus = this.menuService.findMenuList(menu);
        ExcelKit.$Export(Menu.class, response).downXlsx(menus, false);
    }
}
