package ir.rayanovinmt.rnt_social_api.userprofile.constant;

import lombok.Getter;

@Getter
public enum UserProfileRoleEnum {
    CITY_USER("City User"),
    CITY_MANAGER("City Manager"),
    COUNTRY_MANAGER("Country Manager");

    private final String value;

    UserProfileRoleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UserProfileRoleEnum fromValue(String value) {
        for (UserProfileRoleEnum field : UserProfileRoleEnum.values()) {
            if (field.getValue().equalsIgnoreCase(value)) {
                return field;
            }
        }
        throw new IllegalArgumentException("No enum constant with value " + value);
    }
}