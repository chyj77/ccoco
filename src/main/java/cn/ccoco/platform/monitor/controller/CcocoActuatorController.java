package cn.ccoco.platform.monitor.controller;

import cn.ccoco.platform.common.annotation.ControllerEndpoint;
import cn.ccoco.platform.common.entity.CcocoResponse;
import cn.ccoco.platform.common.utils.DateUtil;
import cn.ccoco.platform.monitor.endpoint.CcocoHttpTraceEndpoint;
import cn.ccoco.platform.monitor.entity.CcocoHttpTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
@Slf4j
@RestController
@RequestMapping("ccoco/actuator")
@RequiredArgsConstructor
public class CcocoActuatorController {

    private final CcocoHttpTraceEndpoint httpTraceEndpoint;

    @GetMapping("httptrace")
    @RequiresPermissions("httptrace:view")
    @ControllerEndpoint(exceptionMessage = "请求追踪失败")
    public CcocoResponse httpTraces(String method, String url) {
        CcocoHttpTraceEndpoint.CcocoHttpTraceDescriptor traces = httpTraceEndpoint.traces();
        List<HttpTrace> httpTraceList = traces.getTraces();
        List<CcocoHttpTrace> ccocoHttpTraces = new ArrayList<>();
        httpTraceList.forEach(t -> {
            CcocoHttpTrace ccocoHttpTrace = new CcocoHttpTrace();
            ccocoHttpTrace.setRequestTime(DateUtil.formatInstant(t.getTimestamp(), DateUtil.FULL_TIME_SPLIT_PATTERN));
            ccocoHttpTrace.setMethod(t.getRequest().getMethod());
            ccocoHttpTrace.setUrl(t.getRequest().getUri());
            ccocoHttpTrace.setStatus(t.getResponse().getStatus());
            ccocoHttpTrace.setTimeTaken(t.getTimeTaken());
            if (StringUtils.isNotBlank(method) && StringUtils.isNotBlank(url)) {
                if (StringUtils.equalsIgnoreCase(method, ccocoHttpTrace.getMethod())
                        && StringUtils.containsIgnoreCase(ccocoHttpTrace.getUrl().toString(), url)) {
                    ccocoHttpTraces.add(ccocoHttpTrace);
                }
            } else if (StringUtils.isNotBlank(method)) {
                if (StringUtils.equalsIgnoreCase(method, ccocoHttpTrace.getMethod())) {
                    ccocoHttpTraces.add(ccocoHttpTrace);
                }
            } else if (StringUtils.isNotBlank(url)) {
                if (StringUtils.containsIgnoreCase(ccocoHttpTrace.getUrl().toString(), url)) {
                    ccocoHttpTraces.add(ccocoHttpTrace);
                }
            } else {
                ccocoHttpTraces.add(ccocoHttpTrace);
            }
        });

        Map<String, Object> data = new HashMap<>(2);
        data.put("rows", ccocoHttpTraces);
        data.put("total", ccocoHttpTraces.size());
        return new CcocoResponse().success().data(data);
    }
}
