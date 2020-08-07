package cn.ccoco.platform.common.validator;

import cn.ccoco.platform.common.annotation.IsMobile;
import cn.ccoco.platform.common.entity.Regexp;
import cn.ccoco.platform.common.utils.CcocoUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验是否为合法的手机号码
 *
 * @author CCoco
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String> {

    @Override
    public void initialize(IsMobile isMobile) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StringUtils.isBlank(s)) {
                return true;
            } else {
                String regex = Regexp.MOBILE_REG;
                return CcocoUtil.match(regex, s);
            }
        } catch (Exception e) {
            return false;
        }
    }
}
