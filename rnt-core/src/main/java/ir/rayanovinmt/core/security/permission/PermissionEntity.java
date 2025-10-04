package ir.rayanovinmt.core.security.permission;

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
@Table(name = "permissions")
public class PermissionEntity extends BaseEntity {

    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String showName;

}