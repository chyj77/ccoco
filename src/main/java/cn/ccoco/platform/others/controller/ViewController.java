package cn.ccoco.platform.others.controller;

import cn.ccoco.platform.common.entity.CCocoConstant;
import cn.ccoco.platform.common.utils.CcocoUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author MrBird
 */
@Controller("othersView")
@RequestMapping(CCocoConstant.VIEW_PREFIX + "others")
public class ViewController {

    @GetMapping("ccoco/form")
    @RequiresPermissions("ccoco:form:view")
    public String ccocoForm() {
        return CcocoUtil.view("others/ccoco/form");
    }

    @GetMapping("ccoco/form/group")
    @RequiresPermissions("ccoco:formgroup:view")
    public String ccocoFormGroup() {
        return CcocoUtil.view("others/ccoco/formGroup");
    }

    @GetMapping("ccoco/tools")
    @RequiresPermissions("ccoco:tools:view")
    public String ccocoTools() {
        return CcocoUtil.view("others/ccoco/tools");
    }

    @GetMapping("ccoco/icon")
    @RequiresPermissions("ccoco:icons:view")
    public String ccocoIcon() {
        return CcocoUtil.view("others/ccoco/icon");
    }

    @GetMapping("ccoco/others")
    @RequiresPermissions("others:ccoco:others")
    public String ccocoOthers() {
        return CcocoUtil.view("others/ccoco/others");
    }

    @GetMapping("apex/line")
    @RequiresPermissions("apex:line:view")
    public String apexLine() {
        return CcocoUtil.view("others/apex/line");
    }

    @GetMapping("apex/area")
    @RequiresPermissions("apex:area:view")
    public String apexArea() {
        return CcocoUtil.view("others/apex/area");
    }

    @GetMapping("apex/column")
    @RequiresPermissions("apex:column:view")
    public String apexColumn() {
        return CcocoUtil.view("others/apex/column");
    }

    @GetMapping("apex/radar")
    @RequiresPermissions("apex:radar:view")
    public String apexRadar() {
        return CcocoUtil.view("others/apex/radar");
    }

    @GetMapping("apex/bar")
    @RequiresPermissions("apex:bar:view")
    public String apexBar() {
        return CcocoUtil.view("others/apex/bar");
    }

    @GetMapping("apex/mix")
    @RequiresPermissions("apex:mix:view")
    public String apexMix() {
        return CcocoUtil.view("others/apex/mix");
    }

    @GetMapping("map")
    @RequiresPermissions("map:view")
    public String map() {
        return CcocoUtil.view("others/map/gaodeMap");
    }

    @GetMapping("eximport")
    @RequiresPermissions("others:eximport:view")
    public String eximport() {
        return CcocoUtil.view("others/eximport/eximport");
    }

    @GetMapping("eximport/result")
    public String eximportResult() {
        return CcocoUtil.view("others/eximport/eximportResult");
    }

    @GetMapping("datapermission")
    public String dataPermissionTest() {
        return CcocoUtil.view("others/datapermission/test");
    }
}
