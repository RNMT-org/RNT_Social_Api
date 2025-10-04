package ir.rayanovinmt.rnt_social_api.message.dto;

import ir.rayanovinmt.core.entity.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import java.util.Date;
import ir.rayanovinmt.rnt_social_api.channel.dto.ChannelLoadDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageCreateDto extends BaseDto {
    @NotNull(message = "text is required")
    @Size(max = 4096)
    String text;

    @NotNull(message = "timeReceived is required")
    Date timeReceived;

    ChannelLoadDto channel;
}
