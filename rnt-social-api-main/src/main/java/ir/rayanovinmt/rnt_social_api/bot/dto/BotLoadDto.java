package ir.rayanovinmt.rnt_social_api.bot.dto;

import ir.rayanovinmt.core.entity.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ir.rayanovinmt.rnt_social_api.bot.constant.BotStatusEnum;
import ir.rayanovinmt.rnt_social_api.messagingplatform.dto.MessagingPlatformLoadDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BotLoadDto extends BaseDto {
    String token;

    BotStatusEnum status;

    MessagingPlatformLoadDto platform;
}
