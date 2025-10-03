package ir.rayanovinmt.rnt_social_api.keyword.constant;

import lombok.Getter;

@Getter
public enum KeywordSensitivityLevelEnum {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");

    private final String value;

    KeywordSensitivityLevelEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static KeywordSensitivityLevelEnum fromValue(String value) {
        for (KeywordSensitivityLevelEnum field : KeywordSensitivityLevelEnum.values()) {
            if (field.getValue().equalsIgnoreCase(value)) {
                return field;
            }
        }
        throw new IllegalArgumentException("No enum constant with value " + value);
    }
}