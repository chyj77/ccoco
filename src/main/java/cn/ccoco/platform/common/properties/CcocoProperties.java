package cn.ccoco.platform.common.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author CCoco
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:ccoco.properties"})
@ConfigurationProperties(prefix = "ccoco")
public class CcocoProperties {

    private boolean autoOpenBrowser = false;
    private SwaggerProperties swagger = new SwaggerProperties();

    private int maxBatchInsertNum = 1000;

    private ValidateCodeProperties code = new ValidateCodeProperties();
}
