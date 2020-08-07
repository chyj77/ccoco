package cn.ccoco.platform.monitor.controller;

import cn.ccoco.platform.common.entity.CCocoConstant;
import cn.ccoco.platform.common.utils.CcocoUtil;
import cn.ccoco.platform.monitor.entity.JvmInfo;
import cn.ccoco.platform.monitor.entity.ServerInfo;
import cn.ccoco.platform.monitor.entity.TomcatInfo;
import cn.ccoco.platform.monitor.helper.CcocoActuatorHelper;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static cn.ccoco.platform.monitor.endpoint.CcocoMetricsEndpoint.CcocoMetricResponse;

/**
 * @author MrBird
 */
@Controller("monitorView")
@RequestMapping(CCocoConstant.VIEW_PREFIX + "monitor")
@RequiredArgsConstructor
public class ViewController {

    private final CcocoActuatorHelper actuatorHelper;

    @GetMapping("online")
    @RequiresPermissions("online:view")
    public String online() {
        return CcocoUtil.view("monitor/online");
    }

    @GetMapping("log")
    @RequiresPermissions("log:view")
    public String log() {
        return CcocoUtil.view("monitor/log");
    }

    @GetMapping("loginlog")
    @RequiresPermissions("loginlog:view")
    public String loginLog() {
        return CcocoUtil.view("monitor/loginLog");
    }

    @GetMapping("httptrace")
    @RequiresPermissions("httptrace:view")
    public String httptrace() {
        return CcocoUtil.view("monitor/httpTrace");
    }

    @GetMapping("jvm")
    @RequiresPermissions("jvm:view")
    public String jvmInfo(Model model) {
        List<CcocoMetricResponse> jvm = actuatorHelper.getMetricResponseByType("jvm");
        JvmInfo jvmInfo = actuatorHelper.getJvmInfoFromMetricData(jvm);
        model.addAttribute("jvm", jvmInfo);
        return CcocoUtil.view("monitor/jvmInfo");
    }

    @GetMapping("tomcat")
    @RequiresPermissions("tomcat:view")
    public String tomcatInfo(Model model) {
        List<CcocoMetricResponse> tomcat = actuatorHelper.getMetricResponseByType("tomcat");
        TomcatInfo tomcatInfo = actuatorHelper.getTomcatInfoFromMetricData(tomcat);
        model.addAttribute("tomcat", tomcatInfo);
        return CcocoUtil.view("monitor/tomcatInfo");
    }

    @GetMapping("server")
    @RequiresPermissions("server:view")
    public String serverInfo(Model model) {
        List<CcocoMetricResponse> jdbcInfo = actuatorHelper.getMetricResponseByType("jdbc");
        List<CcocoMetricResponse> systemInfo = actuatorHelper.getMetricResponseByType("system");
        List<CcocoMetricResponse> processInfo = actuatorHelper.getMetricResponseByType("process");

        ServerInfo serverInfo = actuatorHelper.getServerInfoFromMetricData(jdbcInfo, systemInfo, processInfo);
        model.addAttribute("server", serverInfo);
        return CcocoUtil.view("monitor/serverInfo");
    }

    @GetMapping("swagger")
    public String swagger() {
        return CcocoUtil.view("monitor/swagger");
    }
}
