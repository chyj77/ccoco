package cn.ccoco.platform.common.utils;

import cn.ccoco.platform.common.constants.Charsets;
import cn.ccoco.platform.common.constants.TokenConstant;
import cn.ccoco.platform.common.constants.TokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class SecureUtil {

    private final static String HEADER = TokenConstant.HEADER;
    private final static String BEARER = TokenConstant.BEARER;
    private final static String ACCOUNT = TokenConstant.ACCOUNT;
    private final static String USER_ID = TokenConstant.USER_ID;
    private final static String ROLE_ID = TokenConstant.ROLE_ID;
    private final static String USER_NAME = TokenConstant.USER_NAME;
    private final static String ROLE_NAME = TokenConstant.ROLE_NAME;
    private final static String CLIENT_ID = TokenConstant.CLIENT_ID;
    private final static Integer AUTH_LENGTH = TokenConstant.AUTH_LENGTH;
    private static String BASE64_SECURITY = Base64.getEncoder().encodeToString(TokenConstant.SIGN_KEY.getBytes(Charsets.UTF_8));

    /**
     * 获取Claims
     *
     * @param exchange
     * @return Claims
     */
    public static Claims getClaims(ServerWebExchange exchange) {
        String auth = exchange.getRequest().getHeaders().getFirst(SecureUtil.HEADER);
        if (StringUtils.isNotBlank(auth) && auth.length() > AUTH_LENGTH) {
            String headStr = auth.substring(0, 6).toLowerCase();
            if (headStr.compareTo(SecureUtil.BEARER) == 0) {
                auth = auth.substring(7);
                return SecureUtil.parseJWT(auth);
            }
        }
        return null;
    }


    /**
     * 解析jsonWebToken
     *
     * @param jsonWebToken jsonWebToken
     * @return Claims
     */
    public static Claims parseJWT(String jsonWebToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(Base64.getDecoder().decode(BASE64_SECURITY))
                    .parseClaimsJws(jsonWebToken).getBody();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 创建令牌
     *
     * @param user      user
     * @param audience  audience
     * @param issuer    issuer
     * @param tokenType tokenType
     * @return jwt
     */
    public static TokenInfo createJWT(Map<String, String> user, String audience, String issuer, String tokenType) {



        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(BASE64_SECURITY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的类
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JsonWebToken")
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(signatureAlgorithm, signingKey);

        //设置JWT参数
        user.forEach(builder::claim);

        //添加Token过期时间
        long expireMillis= getExpire();
        long expMillis = nowMillis + expireMillis;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp).setNotBefore(now);

        // 组装Token信息
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setToken(builder.compact());
        tokenInfo.setExpire((int) expireMillis / 1000);

        return tokenInfo;
    }

    /**
     * 获取过期时间(次日凌晨3点)
     *
     * @return expire
     */
    public static long getExpire() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 3);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis() - System.currentTimeMillis();
    }


}
