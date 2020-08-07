package cn.ccoco.platform.monitor.controller;

import cn.ccoco.platform.common.entity.CcocoResponse;
import cn.ccoco.platform.monitor.entity.ActiveUser;
import cn.ccoco.platform.monitor.service.ISessionService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
@RestController
@RequestMapping("session")
@RequiredArgsConstructor
public class SessionController {

    private final ISessionService sessionService;

    @GetMapping("list")
    @RequiresPermissions("online:view")
    public CcocoResponse list(String username) {
        List<ActiveUser> list = sessionService.list(username);
        Map<String, Object> data = new HashMap<>(2);
        data.put("rows", list);
        data.put("total", CollectionUtils.size(list));
        return new CcocoResponse().success().data(data);
    }

    @GetMapping("delete/{id}")
    @RequiresPermissions("user:kickout")
    public CcocoResponse forceLogout(@PathVariable String id) {
        sessionService.forceLogout(id);
        return new CcocoResponse().success();
    }
}
