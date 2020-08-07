package cn.ccoco.platform.thirdparty.ebxrook.controller;


import cn.ccoco.platform.common.annotation.ControllerEndpoint;
import cn.ccoco.platform.common.controller.BaseController;
import cn.ccoco.platform.common.entity.CCocoConstant;
import cn.ccoco.platform.common.entity.CcocoResponse;
import cn.ccoco.platform.common.entity.QueryRequest;
import cn.ccoco.platform.common.utils.CcocoUtil;
import cn.ccoco.platform.thirdparty.ebxrook.entity.EbxRookBoxChannelsRealtime;
import cn.ccoco.platform.thirdparty.ebxrook.service.IEbxRookBoxChannelsRealtimeService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 智慧式用电数据开放平台--设备实时状态数据 Controller
 *
 * @author Ccoco
 * @date 2020-07-23 14:15:54
 */
@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class EbxRookBoxChannelsRealtimeController extends BaseController {

    private final IEbxRookBoxChannelsRealtimeService ebxRookBoxChannelsRealtimeService;

    @GetMapping(CCocoConstant.VIEW_PREFIX + "ebxRookBoxChannelsRealtime")
    public String ebxRookBoxChannelsRealtimeIndex(){
        return CcocoUtil.view("ebxRookBoxChannelsRealtime/ebxRookBoxChannelsRealtime");
    }

    @GetMapping("ebxRookBoxChannelsRealtime")
    @ResponseBody
    @RequiresPermissions("ebxRookBoxChannelsRealtime:list")
    public CcocoResponse getAllEbxRookBoxChannelsRealtimes(EbxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime) {
        return new CcocoResponse().success().data(ebxRookBoxChannelsRealtimeService.findEbxRookBoxChannelsRealtimes(ebxRookBoxChannelsRealtime));
    }

    @GetMapping("ebxRookBoxChannelsRealtime/list")
    @ResponseBody
    @RequiresPermissions("ebxRookBoxChannelsRealtime:list")
    public CcocoResponse ebxRookBoxChannelsRealtimeList(QueryRequest request, EbxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime) {
        Map<String, Object> dataTable = getDataTable(this.ebxRookBoxChannelsRealtimeService.findEbxRookBoxChannelsRealtimes(request, ebxRookBoxChannelsRealtime));
        return new CcocoResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增EbxRookBoxChannelsRealtime", exceptionMessage = "新增EbxRookBoxChannelsRealtime失败")
    @PostMapping("ebxRookBoxChannelsRealtime")
    @ResponseBody
    @RequiresPermissions("ebxRookBoxChannelsRealtime:add")
    public CcocoResponse addEbxRookBoxChannelsRealtime(@Valid EbxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime) {
        this.ebxRookBoxChannelsRealtimeService.createEbxRookBoxChannelsRealtime(ebxRookBoxChannelsRealtime);
        return new CcocoResponse().success();
    }

    @ControllerEndpoint(operation = "删除EbxRookBoxChannelsRealtime", exceptionMessage = "删除EbxRookBoxChannelsRealtime失败")
    @GetMapping("ebxRookBoxChannelsRealtime/delete")
    @ResponseBody
    @RequiresPermissions("ebxRookBoxChannelsRealtime:delete")
    public CcocoResponse deleteEbxRookBoxChannelsRealtime(EbxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime) {
        this.ebxRookBoxChannelsRealtimeService.deleteEbxRookBoxChannelsRealtime(ebxRookBoxChannelsRealtime);
        return new CcocoResponse().success();
    }

    @ControllerEndpoint(operation = "修改EbxRookBoxChannelsRealtime", exceptionMessage = "修改EbxRookBoxChannelsRealtime失败")
    @PostMapping("ebxRookBoxChannelsRealtime/update")
    @ResponseBody
    @RequiresPermissions("ebxRookBoxChannelsRealtime:update")
    public CcocoResponse updateEbxRookBoxChannelsRealtime(EbxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime) {
        this.ebxRookBoxChannelsRealtimeService.updateEbxRookBoxChannelsRealtime(ebxRookBoxChannelsRealtime);
        return new CcocoResponse().success();
    }

    @ControllerEndpoint(operation = "修改EbxRookBoxChannelsRealtime", exceptionMessage = "导出Excel失败")
    @PostMapping("ebxRookBoxChannelsRealtime/excel")
    @ResponseBody
    @RequiresPermissions("ebxRookBoxChannelsRealtime:export")
    public void export(QueryRequest queryRequest, EbxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime, HttpServletResponse response) {
        List<EbxRookBoxChannelsRealtime> ebxRookBoxChannelsRealtimes = this.ebxRookBoxChannelsRealtimeService.findEbxRookBoxChannelsRealtimes(queryRequest, ebxRookBoxChannelsRealtime).getRecords();
        ExcelKit.$Export(EbxRookBoxChannelsRealtime.class, response).downXlsx(ebxRookBoxChannelsRealtimes, false);
    }
}
