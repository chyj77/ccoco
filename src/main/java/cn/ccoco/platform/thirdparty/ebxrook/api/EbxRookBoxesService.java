package cn.ccoco.platform.thirdparty.ebxrook.api;

import cn.ccoco.platform.thirdparty.ebxrook.constants.EbxrookProperties;
import cn.ccoco.platform.thirdparty.ebxrook.entity.EbxRookBoxes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EbxRookBoxesService {

    @Resource
    private EbxrookProperties ebxRook;
    @Resource
    private EbxRookTokenCache ebxRookTokenCache;

    public List<EbxRookBoxes> getBoxes(String projectCode, Long deptId) throws Exception {
        String method = "GET_BOXES";
        String accessToken = ebxRookTokenCache.getToken();

        Map<String, String> param = EbxRookStaticService.getParamMap(method, ebxRook.getAppKey(), accessToken);
        param.put("projectCode", projectCode);
        param.put("sign", EbxRookStaticService.getSign(param, ebxRook.getAppSecret()));
        String resstring = EbxRookStaticService.getResponse(param, ebxRook.getInvokeUrl());

        JSONObject map = new JSONObject(resstring);
        String code = map.optString("code");
        if (StringUtils.isNotBlank(code) && "0".equalsIgnoreCase(code)) {
            JSONArray jsonArray = map.optJSONArray("data");
            log.info(jsonArray.toString());
            List<EbxRookBoxes> list = new ArrayList<>();
            int size = jsonArray.length();
            for (int i=0;i<size;i++) {
                Object obj = jsonArray.get(i);
                JSONObject jsonObject = (JSONObject) obj;
                String mac = jsonObject.optString("mac");
                EbxRookBoxes rookBoxes = new EbxRookBoxes(jsonObject, projectCode, mac, deptId);
                list.add(rookBoxes);
            }
            return list;
        } else {
            log.info(resstring);
            return null;
        }
    }

    public void getBox(String projectCode, String mac) throws Exception {
        String method = "GET_BOX";
        String accessToken = ebxRookTokenCache.getToken();

        long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        Map<String, String> param = new HashMap<>();
        param.put("method", method);
        param.put("client_id", ebxRook.getAppKey());
        param.put("access_token", accessToken);
        param.put("timestamp", String.valueOf(timestamp));
//        param.put("projectCode", projectCode);
        param.put("mac", mac);
        param.put("sign", EbxRookStaticService.getSign(param, ebxRook.getAppSecret()));
        String resstring = EbxRookStaticService.getResponse(param, ebxRook.getInvokeUrl());
        log.info(resstring);
        JSONObject map = new JSONObject(resstring);
        String code = map.optString("code");
        if (StringUtils.isNotBlank(code) && "0".equalsIgnoreCase(code)) {
            JSONObject jsonObject = map.optJSONObject("data");
        } else {
            log.info(resstring);
        }
    }
}
