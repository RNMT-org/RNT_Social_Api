package ir.rayanovinmt.rnt_social_api.tag;

import ir.rayanovinmt.rnt_social_api.core.entity.BaseEntity;
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
@Table(name = "tags"
)
public class TagEntity extends BaseEntity {

    @Column()
    String name;

}
