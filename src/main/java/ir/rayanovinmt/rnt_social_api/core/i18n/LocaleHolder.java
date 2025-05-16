package ir.rayanovinmt.rnt_social_api.core.i18n;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Locale;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocaleHolder {
    Locale currentLocale;
}
