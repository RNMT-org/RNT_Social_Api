package ir.rayanovinmt.rnt_social_api.userinchannel.constant;

import lombok.Getter;

@Getter
public enum UserInChannelRoleEnum {
    ADMIN("Admin"),
    MEMBER("Member");

    private final String value;

    UserInChannelRoleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UserInChannelRoleEnum fromValue(String value) {
        for (UserInChannelRoleEnum field : UserInChannelRoleEnum.values()) {
            if (field.getValue().equalsIgnoreCase(value)) {
                return field;
            }
        }
        throw new IllegalArgumentException("No enum constant with value " + value);
    }
}