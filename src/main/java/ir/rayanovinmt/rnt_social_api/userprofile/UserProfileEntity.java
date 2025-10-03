package ir.rayanovinmt.rnt_social_api.userprofile;

import ir.rayanovinmt.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ir.rayanovinmt.rnt_social_api.userprofile.constant.UserProfileRoleEnum;
import ir.rayanovinmt.core.security.user.User;
import ir.rayanovinmt.rnt_social_api.city.CityEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user_profiles"
)
public class UserProfileEntity extends BaseEntity {

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    UserProfileRoleEnum role;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    CityEntity affiliatedCity;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "core_user_id", unique = true, nullable = false)
    User coreUser;

}