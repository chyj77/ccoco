package cn.ccoco.platform.thirdparty.ebxrook.controller;


import cn.ccoco.platform.common.annotation.ControllerEndpoint;
import cn.ccoco.platform.common.controller.BaseController;
import cn.ccoco.platform.common.entity.CcocoResponse;
import cn.ccoco.platform.common.entity.QueryRequest;
import cn.ccoco.platform.system.entity.User;
import cn.ccoco.platform.thirdparty.ebxrook.entity.EbxRookProjecinfo;
import cn.ccoco.platform.thirdparty.ebxrook.service.IEbxRookProjecinfoService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 智慧式用电数据开放平台--项目信息 Controller
 *
 * @author Ccoco
 * @date 2020-07-20 16:41:47
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class EbxRookProjecinfoController extends BaseController {

    private final IEbxRookProjecinfoService ebxRookProjecinfoService;


    @GetMapping("dev/ebxrook/projecinfo")
    @ResponseBody
    @RequiresPermissions("ebxRookProjecinfo:view")
    public CcocoResponse getAllEbxRookProjecinfos(EbxRookProjecinfo ebxRookProjecinfo) {
        return new CcocoResponse().success().data(ebxRookProjecinfoService.findEbxRookProjecinfos(ebxRookProjecinfo));
    }

    @GetMapping("dev/ebxrook/projecinfo/list")
    @ResponseBody
    @RequiresPermissions("ebxRookProjecinfo:view")
    public CcocoResponse ebxRookProjecinfoList(QueryRequest request, EbxRookProjecinfo ebxRookProjecinfo) {
        Map<String, Object> dataTable = getDataTable(this.ebxRookProjecinfoService.findEbxRookProjecinfos(request, ebxRookProjecinfo));
        return new CcocoResponse().success().data(dataTable);
    }

    @GetMapping("dev/ebxrook/projecinfo/detail")
    @ResponseBody
    @RequiresPermissions("ebxRookProjecinfo:view")
    public CcocoResponse ebxRookProjecinfoDetail(Long id) {
        return new CcocoResponse().success().data(this.ebxRookProjecinfoService.getById(id));
    }

    @ControllerEndpoint(operation = "新增EbxRookProjecinfo", exceptionMessage = "新增EbxRookProjecinfo失败")
    @PostMapping("dev/ebxrook/projecinfo/add")
    @ResponseBody
    @RequiresPermissions("ebxRookProjecinfo:add")
    public CcocoResponse addEbxRookProjecinfo(@Valid EbxRookProjecinfo ebxRookProjecinfo) throws Exception {
        Long dept = ebxRookProjecinfo.getDeptId();
        if(null==dept){
            User user = this.getCurrentUser();
            dept = user.getDeptId();
            ebxRookProjecinfo.setDeptId(dept);
        }
        this.ebxRookProjecinfoService.createEbxRookProjecinfo(ebxRookProjecinfo);
        return new CcocoResponse().success();
    }

    @ControllerEndpoint(operation = "删除EbxRookProjecinfo", exceptionMessage = "删除EbxRookProjecinfo失败")
    @GetMapping("dev/ebxrook/projecinfo/delete")
    @ResponseBody
    @RequiresPermissions("ebxRookProjecinfo:delete")
    public CcocoResponse deleteEbxRookProjecinfo(Long id) {
        this.ebxRookProjecinfoService.removeById(id);
        return new CcocoResponse().success();
    }

    @ControllerEndpoint(operation = "修改EbxRookProjecinfo", exceptionMessage = "修改EbxRookProjecinfo失败")
    @PostMapping("dev/ebxrook/projecinfo/update")
    @ResponseBody
    @RequiresPermissions("ebxRookProjecinfo:update")
    public CcocoResponse updateEbxRookProjecinfo(EbxRookProjecinfo ebxRookProjecinfo) {
        this.ebxRookProjecinfoService.updateEbxRookProjecinfo(ebxRookProjecinfo);
        return new CcocoResponse().success();
    }

    @ControllerEndpoint(operation = "修改EbxRookProjecinfo", exceptionMessage = "导出Excel失败")
    @PostMapping("dev/ebxrook/projecinfo/excel")
    @ResponseBody
    @RequiresPermissions("ebxRookProjecinfo:export")
    public void export(QueryRequest queryRequest, EbxRookProjecinfo ebxRookProjecinfo, HttpServletResponse response) {
        List<EbxRookProjecinfo> ebxRookProjecinfos = this.ebxRookProjecinfoService.findEbxRookProjecinfos(queryRequest, ebxRookProjecinfo).getRecords();
        ExcelKit.$Export(EbxRookProjecinfo.class, response).downXlsx(ebxRookProjecinfos, false);
    }

    @ControllerEndpoint(operation = "修改EbxRookProjecinfo", exceptionMessage = "修改EbxRookProjecinfo失败")
    @PostMapping("dev/ebxrook/projecinfo/batchupdate")
    @ResponseBody
    @RequiresPermissions("ebxRookProjecinfo:update")
    public CcocoResponse batchupdateEbxRookProjecinfo(String projectCodes,Long deptId) throws Exception {
        this.ebxRookProjecinfoService.batchupdateEbxRookProjecinfo(projectCodes,deptId);
        return new CcocoResponse().success();
    }
}
