package ir.rayanovinmt.rnt_social_api.person;

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
@Table(name = "persons")
public class PersonEntity extends BaseEntity {

    String name;

}