package ir.rayanovinmt.rnt_social_api.city;

import ir.rayanovinmt.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ir.rayanovinmt.core.security.user.User;

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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "core_user_id", unique = true, nullable = false)
    User coreUser;

}