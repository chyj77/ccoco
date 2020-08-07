package cn.ccoco.platform.common.runner;

import cn.ccoco.platform.common.entity.CCocoConstant;
import cn.ccoco.platform.common.properties.CcocoProperties;
import cn.ccoco.platform.common.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @author CCoco
 * @author FiseTch
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CcocoStartedUpRunner implements ApplicationRunner {

    private final ConfigurableApplicationContext context;
    private final CcocoProperties ccocoProperties;
    private final RedisService redisService;

    @Value("${server.port:8080}")
    private String port;
    @Value("${server.servlet.context-path:}")
    private String contextPath;
    @Value("${spring.profiles.active}")
    private String active;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            // 测试 Redis连接是否正常
            redisService.hasKey("coco_test");
        } catch (Exception e) {
            log.error(" ____   __    _   _ ");
            log.error("| |_   / /\\  | | | |");
            log.error("|_|   /_/--\\ |_| |_|__");
            log.error("                        ");
            log.error("系统启动失败，{}", e.getMessage());
            log.error("Redis连接异常，请检查Redis连接配置并确保Redis服务已启动");
            // 关闭 ccoco
            context.close();
        }
        if (context.isActive()) {
            InetAddress address = InetAddress.getLocalHost();
            String url = String.format("http://%s:%s", address.getHostAddress(), port);
            if (StringUtils.isNotBlank(contextPath)) {
                url += contextPath;
            }
            log.info("管理平台 权限系统启动完毕，地址：{}", url);


        }
    }
}
