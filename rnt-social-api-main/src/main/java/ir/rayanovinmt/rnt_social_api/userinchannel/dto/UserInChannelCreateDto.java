package ir.rayanovinmt.rnt_social_api.userinchannel.dto;

import ir.rayanovinmt.core.entity.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ir.rayanovinmt.rnt_social_api.userinchannel.constant.UserInChannelRoleEnum;
import ir.rayanovinmt.core.security.user.UserLoadDto;
import ir.rayanovinmt.rnt_social_api.channel.dto.ChannelLoadDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInChannelCreateDto extends BaseDto {
    @NotNull(message = "role is required")
    UserInChannelRoleEnum role;

    UserLoadDto coreUser;
    ChannelLoadDto channel;
}
