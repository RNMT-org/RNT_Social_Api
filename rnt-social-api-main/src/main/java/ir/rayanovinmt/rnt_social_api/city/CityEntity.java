package ir.rayanovinmt.rnt_social_api.city;

import ir.rayanovinmt.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ir.rayanovinmt.rnt_social_api.userprofile.UserProfileEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "cities"
    , uniqueConstraints = {
        @UniqueConstraint(name = "cities_name_unique", columnNames = "name")
    }
)
public class CityEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_user_id")
    UserProfileEntity manager;

}