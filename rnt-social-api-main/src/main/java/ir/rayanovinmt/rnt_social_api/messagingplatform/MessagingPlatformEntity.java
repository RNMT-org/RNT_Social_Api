package ir.rayanovinmt.rnt_social_api.messagingplatform;

import ir.rayanovinmt.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "messaging_platforms"
    , uniqueConstraints = {
        @UniqueConstraint(name = "messaging_platforms_name_unique", columnNames = "name")
    }
)
public class MessagingPlatformEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    String name;

    @Column(nullable = false)
    String apiUrl;

    @Column()
    String apiKey;

    @Column()
    String apiSecret;

}