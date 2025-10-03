package ir.rayanovinmt.rnt_social_api.post;

import ir.rayanovinmt.rnt_social_api.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import ir.rayanovinmt.rnt_social_api.person.PersonEntity;
import ir.rayanovinmt.rnt_social_api.tag.TagEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "posts"
)
public class PostEntity extends BaseEntity {

    @Column()
    String title;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    PersonEntity person;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
        name = "post_tag",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Builder.Default
    Set<TagEntity> tag = new HashSet<>();

}
