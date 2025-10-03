package ir.rayanovinmt.rnt_social_api.keyword.dto;

import ir.rayanovinmt.core.entity.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ir.rayanovinmt.rnt_social_api.keyword.constant.KeywordSensitivityLevelEnum;
import ir.rayanovinmt.rnt_social_api.message.dto.MessageLoadDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KeywordLoadDto extends BaseDto {
    String text;

    KeywordSensitivityLevelEnum sensitivityLevel;

}
