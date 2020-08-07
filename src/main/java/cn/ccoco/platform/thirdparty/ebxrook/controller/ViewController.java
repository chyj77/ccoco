package cn.ccoco.platform.thirdparty.ebxrook.controller;

import cn.ccoco.platform.common.authentication.ShiroHelper;
import cn.ccoco.platform.common.controller.BaseController;
import cn.ccoco.platform.common.entity.CCocoConstant;
import cn.ccoco.platform.common.utils.CcocoUtil;
import cn.ccoco.platform.common.utils.DateUtil;
import cn.ccoco.platform.system.entity.User;
import cn.ccoco.platform.system.service.IUserDataPermissionService;
import cn.ccoco.platform.system.service.IUserService;
import cn.ccoco.platform.thirdparty.ebxrook.entity.EbxRookBoxes;
import cn.ccoco.platform.thirdparty.ebxrook.entity.EbxRookProjecinfo;
import cn.ccoco.platform.thirdparty.ebxrook.service.IEbxRookBoxesService;
import cn.ccoco.platform.thirdparty.ebxrook.service.IEbxRookProjecinfoService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author CCoco
 */
@Controller("devView")
@RequiredArgsConstructor
public class ViewController extends BaseController {

    private final IEbxRookProjecinfoService iEbxRookProjecinfoService;
    private final IEbxRookBoxesService ebxRookBoxesService;

    @GetMapping(CCocoConstant.VIEW_PREFIX + "dev/ebxrook/projecinfo/list")
    public String projecinfo() {
        return CcocoUtil.view("thirtyparty/ebxrook/projectinfo");
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "dev/ebxrook/projecinfo/add")
    @RequiresPermissions("ebxRookProjecinfo:add")
    public String projectInfoAdd() {
        return CcocoUtil.view("thirtyparty/ebxrook/projectAdd");
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "dev/ebxrook/projecinfo/update/{id}")
    @RequiresPermissions("ebxRookProjecinfo:update")
    public String projectInfoUpdate(@PathVariable Long id, Model model) {
        EbxRookProjecinfo ebxRookProjecinfo = iEbxRookProjecinfoService.getById(id);
        model.addAttribute("project",ebxRookProjecinfo);
        return CcocoUtil.view("thirtyparty/ebxrook/projectUpdate");
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "dev/ebxrook/projecinfo/batchUpdate/{projectCodes}")
    @RequiresPermissions("ebxRookProjecinfo:update")
    public String projectInfoUpdate(@PathVariable String projectCodes, Model model) {
        model.addAttribute("projectCodes",projectCodes);
        return CcocoUtil.view("thirtyparty/ebxrook/projectBatchUpdate");
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "dev/ebxrook/boxes/list")
    public String boxesList() {
        return CcocoUtil.view("thirtyparty/ebxrook/boxes");
    }

    @GetMapping(CCocoConstant.VIEW_PREFIX + "dev/ebxrook/boxes/info/{id}")
    public String boxesInfo(@PathVariable Long id, Model model) {
        EbxRookBoxes ebxRookBoxes = ebxRookBoxesService.getById(id);
        model.addAttribute("ebxRookBoxes",ebxRookBoxes);
        return CcocoUtil.view("thirtyparty/ebxrook/boxesInfo");
    }
}
