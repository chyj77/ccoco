package cn.ccoco.platform.thirdparty.ebxrook.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class EbxRookReceiveRealTimeService {

    private final String ZERO = "0";

    public String receiveRealTime(Map map) throws JSONException {
        log.info("接收到的实时信息：{}", map);
        String detect = (String) map.get("detect");
        if (ZERO.equalsIgnoreCase(detect)) {
            detect = "成功";
        }
        JSONObject result = new JSONObject();
        result.put("code",0);
        result.put("msg",detect);
        return result.toString();
    }
}
