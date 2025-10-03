package ir.rayanovinmt.rnt_social_api.channel.constant;

import lombok.Getter;

@Getter
public enum ChannelStatusEnum {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String value;

    ChannelStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ChannelStatusEnum fromValue(String value) {
        for (ChannelStatusEnum field : ChannelStatusEnum.values()) {
            if (field.getValue().equalsIgnoreCase(value)) {
                return field;
            }
        }
        throw new IllegalArgumentException("No enum constant with value " + value);
    }
}