package ir.rayanovinmt.rnt_social_api.channel.dto;

import ir.rayanovinmt.core.entity.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ir.rayanovinmt.rnt_social_api.channel.constant.ChannelStatusEnum;
import ir.rayanovinmt.rnt_social_api.messagingplatform.dto.MessagingPlatformLoadDto;
import ir.rayanovinmt.rnt_social_api.city.dto.CityLoadDto;
import ir.rayanovinmt.rnt_social_api.bot.dto.BotLoadDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChannelLoadDto extends BaseDto {
    String name;

    ChannelStatusEnum status;

    MessagingPlatformLoadDto platform;
}
