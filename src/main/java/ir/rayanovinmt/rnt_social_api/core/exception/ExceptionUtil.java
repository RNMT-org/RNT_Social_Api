package ir.rayanovinmt.rnt_social_api.core.exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionUtil {

    public AppException make(ExceptionTemplate template) {
        return new AppException(template);
    }
}
