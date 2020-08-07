package cn.ccoco.platform.thirdparty.ebxrook.api;

import cn.ccoco.platform.thirdparty.ebxrook.constants.EbxrookProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
@Slf4j
public class EbxRookBoxControlService {

    @Resource
    private EbxRookTokenCache ebxRookTokenCache;
    @Resource
    private EbxrookProperties ebxRook;

    private final String method="PUT_BOX_CONTROL";

    private final String OCSWITCH = "OCSWITCH";

    public String ocSwitch(String projectCode,String mac,String status,String addrs) throws Exception {
        String accessToken = ebxRookTokenCache.getToken();

//        long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        Map<String, String> param = EbxRookStaticService.getParamMap(method,ebxRook.getAppKey(),accessToken);

        param.put("projectCode", projectCode);
        param.put("mac", mac);
        param.put("cmd", OCSWITCH);
        param.put("value1",status);
        param.put("value2",addrs);
        param.put("sign", EbxRookStaticService.getSign(param, ebxRook.getAppSecret()));
        String resstring = EbxRookStaticService.getResponse(param, ebxRook.getInvokeUrl());
        JSONObject jsonObject = new JSONObject(resstring);
        String res = jsonObject.toString();
        log.info(res);
        return res;
    }

}
