package ir.rayanovinmt.rnt_social_api.messagingplatform.dto;

import ir.rayanovinmt.core.entity.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagingPlatformUpdateDto extends BaseDto {
    @NotNull(message = "name is required")
    @Size(max = 100)
    String name;

    @NotNull(message = "apiUrl is required")
    @Size(max = 255)
    String apiUrl;

    @Size(max = 255)
    String apiKey;

    @Size(max = 255)
    String apiSecret;

}
