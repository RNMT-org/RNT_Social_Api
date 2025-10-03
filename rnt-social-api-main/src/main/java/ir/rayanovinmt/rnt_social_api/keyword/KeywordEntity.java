package ir.rayanovinmt.rnt_social_api.keyword;

import ir.rayanovinmt.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ir.rayanovinmt.rnt_social_api.keyword.constant.KeywordSensitivityLevelEnum;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import ir.rayanovinmt.rnt_social_api.message.MessageEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "keywords"
    , uniqueConstraints = {
        @UniqueConstraint(name = "keywords_text_unique", columnNames = "text")
    }
)
public class KeywordEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    String text;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    KeywordSensitivityLevelEnum sensitivityLevel;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
        name = "keyword_message",
        joinColumns = @JoinColumn(name = "keyword_id"),
        inverseJoinColumns = @JoinColumn(name = "message_id")
    )
    @Builder.Default
    Set<MessageEntity> messages = new HashSet<>();

}