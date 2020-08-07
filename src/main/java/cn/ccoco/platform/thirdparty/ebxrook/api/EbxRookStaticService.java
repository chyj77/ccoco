package cn.ccoco.platform.thirdparty.ebxrook.api;

import cn.ccoco.platform.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class EbxRookStaticService {

    private static char[] hc = "0123456789abcdef".toCharArray();

    public static String md5(String param) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.reset();
        md.update(param.getBytes("utf-8"));
        byte[] d = md.digest();
        StringBuilder r = new StringBuilder(d.length * 2);
        for (byte b : d) {
            r.append(hc[(b >> 4) & 0xF]);
            r.append(hc[(b & 0xF)]);
        }
        return r.toString();
    }


    public static String getSign(Map<String,String> paramap,String appSecret) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map<String, String> result = paramap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : result.entrySet()) {
            String value = entry.getValue();
//            System.out.println("key="+entry.getKey()+"  value="+value);
            sb.append(value);
        }
        sb.append(appSecret);
//        System.out.println(sb.toString());
        String sign = md5(sb.toString());
//        System.out.println("sign="+sign);
        return sign;
    }


    public static Map getParamMap(String method,String appKey,String accessToken){
        Map<String, String> param = new HashMap<>();
        param.put("method", method);
        param.put("client_id", appKey);
        param.put("access_token", accessToken);
        param.put("timestamp", DateUtil.getDateFormat(new Date(),DateUtil.FULL_TIME_PATTERN));
        return param;
    }

    public static String getResponse(Map<String,String> map, String url) {
        OutputStreamWriter wr = null;
        StringBuilder sb = new StringBuilder();
        String res=null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setDoInput(true);// 从服务器获取数据
            conn.setDoOutput(true);// 向服务器写入数据

            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");

            if (map == null) {
                conn.setRequestProperty("Content-Lenth", "0");
            } else {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                sb.deleteCharAt(sb.length()-1);
                String bodyData = sb.toString();
                log.info("bodyData={}",bodyData);
                conn.setRequestProperty("Content-Lenth", String.valueOf(bodyData.getBytes("UTF-8").length));
                // 获得输出流，向服务器输出数据
                wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                wr.write(bodyData);
                wr.flush();
            }

            // 获得服务器响应的结果和状态码
            int responseCode = conn.getResponseCode();

            if (HttpStatus.OK.value()==responseCode) {
                InputStream is = conn.getInputStream();
                res =toStringResult(is);
            } else {
                InputStream is = conn.getErrorStream();
                res =toStringResult(is);

                log.error("request failure, url:{}, params:{}\nresponse code:{}\nresponse error:{}", url, sb.toString(), responseCode, res);
            }
        } catch (IOException e) {
            log.error("http post failure,err:{}. url:{}, params:{}", e.getMessage(), url, sb.toString());
        } finally {
            if (wr != null) {
                try {
                    wr.close();
                } catch (IOException e) {
                    log.error("close error", e);
                }
            }
        }
        return res;
    }

    /**
     * 把从输入流InputStream按指定编码格式encode变成字符串String
     *
     * @param inputStream
     * @return
     */
    private static String toStringResult(InputStream inputStream) {
        String result = null;
        if (inputStream == null) {
            return result;
        }
        int len = 0;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
            result = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }
}
