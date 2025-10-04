package ir.rayanovinmt.rnt_social_api.userprofile.dto;

import ir.rayanovinmt.core.entity.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ir.rayanovinmt.rnt_social_api.userprofile.constant.UserProfileRoleEnum;
import ir.rayanovinmt.rnt_social_api.city.dto.CityLoadDto;
import ir.rayanovinmt.core.security.user.UserLoadDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileUpdateDto extends BaseDto {
    @NotNull(message = "name is required")
    @Size(max = 100)
    String name;

    @NotNull(message = "role is required")
    UserProfileRoleEnum role;

    CityLoadDto affiliatedCity;
    UserLoadDto coreUser;
}
