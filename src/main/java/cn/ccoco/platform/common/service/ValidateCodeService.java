package cn.ccoco.platform.common.service;

import cn.ccoco.platform.common.entity.CCocoConstant;
import cn.ccoco.platform.common.exception.CcocoException;
import cn.ccoco.platform.common.properties.CcocoProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


/**
 * 验证码服务
 *
 * @author MrBird
 */
@Service
@RequiredArgsConstructor
public class ValidateCodeService {

    private final RedisService redisService;

    public void check(String key, String value) throws CcocoException {
        Object codeInRedis = redisService.get(CCocoConstant.CODE_PREFIX + key);
        if (StringUtils.isBlank(value)) {
            throw new CcocoException("请输入验证码");
        }
        if (codeInRedis == null) {
            throw new CcocoException("验证码已过期");
        }
        if (!StringUtils.equalsIgnoreCase(value, String.valueOf(codeInRedis))) {
            throw new CcocoException("验证码不正确");
        }
    }



}
