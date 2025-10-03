package ir.rayanovinmt.rnt_social_api.alert.dto;

import ir.rayanovinmt.core.entity.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ir.rayanovinmt.rnt_social_api.alert.constant.AlertLevelEnum;
import ir.rayanovinmt.rnt_social_api.message.dto.MessageLoadDto;
import ir.rayanovinmt.rnt_social_api.keyword.dto.KeywordLoadDto;
import ir.rayanovinmt.rnt_social_api.userprofile.dto.UserProfileLoadDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlertCreateDto extends BaseDto {
    @NotNull(message = "details is required")
    @Size(max = 1024)
    String details;

    @NotNull(message = "level is required")
    AlertLevelEnum level;

}
