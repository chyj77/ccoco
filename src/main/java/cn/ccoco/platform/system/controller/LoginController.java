package cn.ccoco.platform.system.controller;

import cn.ccoco.platform.common.annotation.Limit;
import cn.ccoco.platform.common.constants.CacheNames;
import cn.ccoco.platform.common.controller.BaseController;
import cn.ccoco.platform.common.entity.CcocoResponse;
import cn.ccoco.platform.common.exception.CcocoException;
import cn.ccoco.platform.common.service.RedisService;
import cn.ccoco.platform.common.service.ValidateCodeService;
import cn.ccoco.platform.common.utils.Md5Util;
import cn.ccoco.platform.monitor.entity.LoginLog;
import cn.ccoco.platform.monitor.service.ILoginLogService;
import cn.ccoco.platform.system.entity.User;
import cn.ccoco.platform.system.service.IUserDataPermissionService;
import cn.ccoco.platform.system.service.IUserService;
import cn.ccoco.platform.thirdparty.ebxrook.service.IEbxRookBoxesService;
import com.wf.captcha.SpecCaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author CCoco
 */
@Validated
@RestController
@RequiredArgsConstructor
public class LoginController extends BaseController {

    private final IUserService userService;
    private final ILoginLogService loginLogService;
    private final IUserDataPermissionService iuserDataPermissionService;
    private final ValidateCodeService validateCodeService;
    private final IEbxRookBoxesService iebxRookBoxesService;
    private final RedisService redisService;

    @PostMapping("login")
    @Limit(key = "login", period = 60, count = 10, name = "登录接口", prefix = "limit")
    public CcocoResponse login(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password,
            @NotBlank(message = "{required}") String verifyCode,
            boolean rememberMe, ServerWebExchange exchange) throws CcocoException {
        WebSession session = exchange.getSession().block();
        validateCodeService.check(session.getId(), verifyCode);
        password = Md5Util.md5Hex(password);

        // 保存登录日志
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setSystemBrowserInfo();
        this.loginLogService.saveLoginLog(loginLog);

        return CcocoResponse.success();
    }

    @PostMapping("regist")
    public CcocoResponse regist(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password) throws CcocoException {
        User user = userService.findByName(username);
        if (user != null) {
            throw new CcocoException("该用户名已存在");
        }
        this.userService.regist(username, password);
        return CcocoResponse.success();
    }

    @GetMapping("index/{username}")
    public CcocoResponse index(@NotBlank(message = "{required}") @PathVariable String username) {
        // 更新登录时间
        this.userService.updateLoginTime(username);
        User param = this.userService.findByName(username);
        String deptIds = iuserDataPermissionService.findByUserId(String.valueOf(param.getUserId()));
        Map<String, Object> data = new HashMap<>(5);
        // 获取系统访问记录
//        Long totalVisitCount = this.loginLogService.findTotalVisitCount();
        Long totalVisitCount = this.iebxRookBoxesService.findTotalBox(deptIds);
        data.put("totalVisitCount", totalVisitCount);
//        Long todayVisitCount = this.loginLogService.findTodayVisitCount();
        Long todayVisitCount = this.iebxRookBoxesService.findOnlineBox(deptIds);
        data.put("todayVisitCount", todayVisitCount);
//        Long todayIp = this.loginLogService.findTodayIp();
        Long todayIp = this.iebxRookBoxesService.findUnderlineBox(deptIds);
        data.put("todayIp", todayIp);
        // 获取近期系统访问记录
        List<Map<String, Object>> lastSevenVisitCount = this.loginLogService.findLastSevenDaysVisitCount(null);
        data.put("lastSevenVisitCount", lastSevenVisitCount);

        List<Map<String, Object>> lastSevenUserVisitCount = this.loginLogService.findLastSevenDaysVisitCount(param);
        data.put("lastSevenUserVisitCount", lastSevenUserVisitCount);
        return new CcocoResponse().success().data(data);
    }

    @GetMapping("/captcha")
    @Limit(key = "get_captcha", period = 60, count = 10, name = "获取验证码", prefix = "limit")
    public CcocoResponse captcha(ServerWebExchange exchange) throws IOException, CcocoException {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String key = exchange.getSession().block().getId();
        // 存入redis并设置过期时间为30分钟
        redisService.set(CacheNames.CAPTCHA_KEY + key, verCode, 30L, TimeUnit.MINUTES);
        // 将key和base64返回给前端
        LinkedCaseInsensitiveMap map = new LinkedCaseInsensitiveMap(2);
        map.put("key",key);
        map.put("image",specCaptcha.toBase64());
        return CcocoResponse.data(map);
    }
}
