package cn.ccoco.platform.thirdparty.ebxrook.api;

import cn.ccoco.platform.thirdparty.ebxrook.constants.EbxrookProperties;
import cn.ccoco.platform.thirdparty.ebxrook.entity.EbxRookBoxChannelsRealtime;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EbxRookBoxChannelsRealtimeService {

    @Resource
    private EbxrookProperties ebxRook;
    @Resource
    private EbxRookTokenCache ebxRookTokenCache;

    public List<EbxRookBoxChannelsRealtime> getRealtimes(String projectCode, String mac) throws Exception {
        String resstring = queryBoxChannelsRealtime(projectCode, mac);
        List<EbxRookBoxChannelsRealtime> list = new ArrayList<>();
        JSONObject resJson = new JSONObject(resstring);
        String code = resJson.getString("code");
        if (StringUtils.isNotBlank(code) && "0".equalsIgnoreCase(code)) {
            JSONArray jsonArray = resJson.optJSONArray("data");
            log.info(jsonArray.toString());
            int size = jsonArray.length();
            for (int i=0;i<size;i++) {
                Object obj = jsonArray.get(i);
                JSONObject jsonObject = (JSONObject) obj;
                EbxRookBoxChannelsRealtime boxChannels = new EbxRookBoxChannelsRealtime(jsonObject, projectCode);
                boxChannels.setId(Long.valueOf(i));
                list.add(boxChannels);
            }
        } else {
            log.info(resstring);
        }
        return list;
    }


    public String queryBoxChannelsRealtime(String projectCode, String mac) throws Exception {
        String method = "GET_BOX_CHANNELS_REALTIME";
        String accessToken = ebxRookTokenCache.getToken();

        Map<String, String> param = EbxRookStaticService.getParamMap(method, ebxRook.getAppKey(), accessToken);
        param.put("projectCode", projectCode);
        param.put("mac", mac);
        param.put("sign", EbxRookStaticService.getSign(param, ebxRook.getAppSecret()));
        String resstring = EbxRookStaticService.getResponse(param, ebxRook.getInvokeUrl());
        return resstring;
    }

    public JSONArray getBoxChannelsOc(String projectCode, String mac, Integer addr) throws Exception {
        String method = "GET_BOX_CHANNELS_OC";
        String accessToken = ebxRookTokenCache.getToken();

        Map<String, String> param = EbxRookStaticService.getParamMap(method, ebxRook.getAppKey(), accessToken);

        param.put("projectCode", projectCode);
        if (addr != null) {
            param.put("addr", String.valueOf(addr));
        }
        param.put("mac", mac);
        param.put("sign", EbxRookStaticService.getSign(param, ebxRook.getAppSecret()));
        String resstring = EbxRookStaticService.getResponse(param, ebxRook.getInvokeUrl());

        JSONObject map = new JSONObject(resstring);
        String code = map.optString("code");
        if (StringUtils.isNotBlank(code) && "0".equalsIgnoreCase(code)) {
//            log.info("成功 {}",resstring);
            JSONArray jsonArray = map.optJSONArray("data");
            log.info(jsonArray.toString());
            return jsonArray;
        } else {
            log.info(resstring);
        }
        return new JSONArray(resstring);
    }

}
