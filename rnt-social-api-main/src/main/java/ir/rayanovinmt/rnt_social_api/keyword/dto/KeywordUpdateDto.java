package ir.rayanovinmt.rnt_social_api.keyword.dto;

import ir.rayanovinmt.core.entity.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ir.rayanovinmt.rnt_social_api.keyword.constant.KeywordSensitivityLevelEnum;
import ir.rayanovinmt.rnt_social_api.message.dto.MessageLoadDto;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KeywordUpdateDto extends BaseDto {
    @NotNull(message = "text is required")
    @Size(max = 100)
    String text;

    @NotNull(message = "sensitivityLevel is required")
    KeywordSensitivityLevelEnum sensitivityLevel;

}
