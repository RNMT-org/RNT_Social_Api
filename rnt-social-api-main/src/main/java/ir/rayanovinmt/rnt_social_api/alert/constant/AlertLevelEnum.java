package ir.rayanovinmt.rnt_social_api.alert.constant;

import lombok.Getter;

@Getter
public enum AlertLevelEnum {
    INFO("Info"),
    WARNING("Warning"),
    CRITICAL("Critical");

    private final String value;

    AlertLevelEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AlertLevelEnum fromValue(String value) {
        for (AlertLevelEnum field : AlertLevelEnum.values()) {
            if (field.getValue().equalsIgnoreCase(value)) {
                return field;
            }
        }
        throw new IllegalArgumentException("No enum constant with value " + value);
    }
}