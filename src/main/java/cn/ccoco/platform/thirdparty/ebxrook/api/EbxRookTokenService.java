package cn.ccoco.platform.thirdparty.ebxrook.api;


import cn.ccoco.platform.common.utils.Md5Util;
import cn.ccoco.platform.thirdparty.ebxrook.constants.EbxrookProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EbxRookTokenService {

    @Resource
    private EbxrookProperties ebxRook;


    public  String getCode() throws IOException {
        Map<String,String> param = new HashMap<>();
        param.put("response_type","code");
        param.put("client_id", ebxRook.getAppKey());
        param.put("redirect_uri", ebxRook.getRedirectUrl());
        param.put("uname", ebxRook.getUname());
        param.put("passwd", ebxRook.getPassword());
        String resstring = EbxRookStaticService.getResponse(param,ebxRook.getAuthUrl());
        log.info("【获取code】返回：{}",resstring);
        try {
            JSONObject map = new JSONObject(resstring);
            String success = map.optString("success");
            String code = map.optString("code");
            if (StringUtils.isNotBlank(success) && "true".equalsIgnoreCase(success)) {
                return code;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getToken(String code) throws Exception {
        String grant_type = "authorization_code";
        String clientSecret = ebxRook.getAppKey()+grant_type+ebxRook.getRedirectUrl()+code+ebxRook.getAppSecret();
        Map<String,String> param = new HashMap<>();
        param.put("client_id", ebxRook.getAppKey());
        param.put("client_secret", Md5Util.md5Hex(clientSecret));
        param.put("grant_type", grant_type);
        param.put("redirect_uri", ebxRook.getRedirectUrl());
        param.put("code",code);
        String resstring = EbxRookStaticService.getResponse(param,ebxRook.getTokenUrl());
        log.info("【获取token】返回：{}",resstring);
        JSONObject map =new JSONObject(resstring);
        String retCode = map.optString("code");
        String token = null;
        if (retCode.equalsIgnoreCase("0")) {
            JSONObject jsonObject =  map.optJSONObject("data");
            token = jsonObject.toString();
        }
        return token;
    }

    public String getRefreshToken(String refreshToken)throws Exception {
        String grant_type = "refresh_token";
        String clientSecret = ebxRook.getAppKey()+grant_type+ebxRook.getRedirectUrl()+refreshToken+ebxRook.getAppSecret();
        Map<String,String> param = new HashMap<>();
        param.put("client_id", ebxRook.getAppKey());
        param.put("client_secret", Md5Util.md5Hex(clientSecret));
        param.put("grant_type", grant_type);
        param.put("redirect_uri", ebxRook.getRedirectUrl());
        param.put("refresh_token",refreshToken);
        String resstring = EbxRookStaticService.getResponse(param,ebxRook.getRefreshtokenUrl());
        log.info("【获取refreshtoken】返回：{}",resstring);
        JSONObject map =new JSONObject(resstring);
        String retCode = map.optString("code");
        String token = null;
        if (retCode.equalsIgnoreCase("0")) {
            JSONObject jsonObject =  map.optJSONObject("data");
            token = jsonObject.toString();
        }
        return token;
    }



}
