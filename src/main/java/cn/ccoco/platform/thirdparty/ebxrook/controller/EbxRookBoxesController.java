package cn.ccoco.platform.thirdparty.ebxrook.controller;


import cn.ccoco.platform.common.annotation.ControllerEndpoint;
import cn.ccoco.platform.common.controller.BaseController;
import cn.ccoco.platform.common.entity.CcocoResponse;
import cn.ccoco.platform.common.entity.QueryRequest;
import cn.ccoco.platform.system.entity.User;
import cn.ccoco.platform.thirdparty.ebxrook.api.EbxRookBoxChannelsRealtimeService;
import cn.ccoco.platform.thirdparty.ebxrook.entity.EbxRookBoxChannelsRealtime;
import cn.ccoco.platform.thirdparty.ebxrook.entity.EbxRookBoxes;
import cn.ccoco.platform.thirdparty.ebxrook.entity.EbxRookProjecinfo;
import cn.ccoco.platform.thirdparty.ebxrook.service.IEbxRookBoxesService;
import cn.ccoco.platform.thirdparty.ebxrook.service.IEbxRookProjecinfoService;
import com.wuwenze.poi.ExcelKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
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
public class EbxRookBoxesController extends BaseController {

    private final IEbxRookBoxesService ebxRookBoxesService;
    private final EbxRookBoxChannelsRealtimeService ebxRookBoxChannelsRealtimeService;


    @PostMapping("dev/ebxrook/boxes/async")
    @RequiresPermissions("ebxRookProjecinfo:view")
    public CcocoResponse asyncBoxes(String projectCode, Long deptId) throws Exception {
        ebxRookBoxesService.asyncBoxes(projectCode,deptId,true);
        return new CcocoResponse().success();
    }

    @GetMapping("dev/ebxrook/boxes/list")
    @ResponseBody
    @RequiresPermissions("ebxRookmacinfo:view")
    public CcocoResponse ebxRookBoxesList(QueryRequest request, EbxRookBoxes ebxRookBoxes) {
        User user = this.getCurrentUser();
        Map<String, Object> dataTable = getDataTable(this.ebxRookBoxesService.findEbxRookBoxess(request, ebxRookBoxes,user));
        return new CcocoResponse().success().data(dataTable);
    }

    @GetMapping("dev/ebxrook/boxes/detail")
    @RequiresPermissions("ebxRookmacinfo:view")
    public CcocoResponse ebxRookBoxesDetail(Long id) {
        return new CcocoResponse().success().data(this.ebxRookBoxesService.getById(id));
    }
    @GetMapping("dev/ebxrook/boxes/info")
    @RequiresPermissions("ebxRookmacinfo:view")
    public CcocoResponse ebxRookBoxesInfol(String projectCode, String mac) throws Exception {
        List<EbxRookBoxChannelsRealtime> list=this.ebxRookBoxChannelsRealtimeService.getRealtimes(projectCode,mac);
        int total = list.size();
        Map<String, Object> data = new HashMap<>(2);
        data.put("rows", list);
        data.put("total", total);
        return new CcocoResponse().success().data(data);
    }
}
