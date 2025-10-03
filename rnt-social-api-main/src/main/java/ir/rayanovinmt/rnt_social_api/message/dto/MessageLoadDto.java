package ir.rayanovinmt.rnt_social_api.message.dto;

import ir.rayanovinmt.core.entity.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(value = {"keywords"}, ignoreUnknown = true)
public class MessageLoadDto extends BaseDto {
    String text;

    Date timeReceived;

    ChannelLoadDto channel;
}
