package cn.ccoco.platform.thirdparty.ebxrook.api;

import cn.ccoco.platform.thirdparty.ebxrook.constants.EbxrookProperties;
import cn.ccoco.platform.thirdparty.ebxrook.entity.EbxRookProjecinfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
@Slf4j
public class EbxRookProjectInfoService {

    @Resource
    private EbxrookProperties ebxRook;
    @Resource
    private EbxRookTokenCache ebxRookTokenCache;


    public EbxRookProjecinfo getProjectInfo(EbxRookProjecinfo ebxRookProjectInfo) throws Exception {
        String method = "GET_PROJECT_INFO";
        String accessToken = ebxRookTokenCache.getToken();


        Map<String, String> param = EbxRookStaticService.getParamMap(method, ebxRook.getAppKey(), accessToken);
        param.put("projectCode", ebxRookProjectInfo.getProjectCode());
        param.put("sign", EbxRookStaticService.getSign(param, ebxRook.getAppSecret()));
        String resstring = EbxRookStaticService.getResponse(param, ebxRook.getInvokeUrl());

        JSONObject map = new JSONObject(resstring);
        String code = map.getString("code");
        if (StringUtils.isNotBlank(code) && "0".equalsIgnoreCase(code)) {
            JSONObject data = map.getJSONObject("data");
            log.info(data.toString());
            ebxRookProjectInfo.setCityName(data.optString("cityName"));
            ebxRookProjectInfo.setStreet(data.optString("street"));
            ebxRookProjectInfo.setLatitude(data.optDouble("latitude"));
            ebxRookProjectInfo.setProvinceName(data.optString("provinceName"));
            ebxRookProjectInfo.setProjectName(data.optString("projectName"));
            ebxRookProjectInfo.setStatus(data.optString("status"));
            ebxRookProjectInfo.setCountyName(data.optString("countyName"));
            ebxRookProjectInfo.setLongitude(data.optDouble("longitude"));
            ebxRookProjectInfo.setImgUrl(data.optString("imgUrl"));
        } else {
            log.info(resstring);
            throw  new Exception(resstring);
        }
        return ebxRookProjectInfo;
    }

}
