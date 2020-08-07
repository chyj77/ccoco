package cn.ccoco.platform.thirdparty.ebxrook.constants;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:ebxrook.properties"})
@ConfigurationProperties
public class EbxrookProperties {
    private String uname;
    private String password;
    private String appKey;
    private String appSecret;
    private String redirectUrl;
    private String authUrl;
    private String invokeUrl;
    private String tokenUrl;
    private String refreshtokenUrl;

}
