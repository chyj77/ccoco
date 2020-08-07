package cn.ccoco.platform.thirdparty.ebxrook.api;

import cn.ccoco.platform.common.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;


@Component
@Slf4j
public class EbxRookTokenCache {

    @Resource
    private RedisService template;

    @Resource
    private EbxRookTokenService ebxRookTokenService;

    private final String EBX_ROOK_TOKEN="ebx_rook_token";

    private final long expiredTime = 3400*1000L;


    public void set(String value){
        template.set(EBX_ROOK_TOKEN,value,expiredTime);
    }

    public String get(){
        return (String) template.get(EBX_ROOK_TOKEN);
    }

    public void initToken() throws Exception {
        String code = ebxRookTokenService.getCode();
        String value = ebxRookTokenService.getToken(code);
        if(StringUtils.isNotBlank(value)) {
            this.set(value);
        }
    }

    @Scheduled( fixedDelay = expiredTime)
    public void refreshToken() throws Exception {
        String value = this.get();
        if(StringUtils.isEmpty(value)){
            initToken();
        }else {
            JSONObject map = new JSONObject(value);
            log.info("redis token={}", map.toString());
            String refreshToken = map.optString("refreshToken");
            String retValue = ebxRookTokenService.getRefreshToken(refreshToken);
            if (StringUtils.isNotBlank(retValue)) {
                this.set(retValue);
            } else {
                initToken();
            }
        }
    }

    public String getToken()throws Exception{
        String value = this.get();
        JSONObject map = new JSONObject(value);
        String accessToken = map.optString("accessToken");
        return accessToken;
    }

}
