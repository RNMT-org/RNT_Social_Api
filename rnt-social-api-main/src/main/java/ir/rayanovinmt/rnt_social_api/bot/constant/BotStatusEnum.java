package ir.rayanovinmt.rnt_social_api.bot.constant;

import lombok.Getter;

@Getter
public enum BotStatusEnum {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String value;

    BotStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static BotStatusEnum fromValue(String value) {
        for (BotStatusEnum field : BotStatusEnum.values()) {
            if (field.getValue().equalsIgnoreCase(value)) {
                return field;
            }
        }
        throw new IllegalArgumentException("No enum constant with value " + value);
    }
}