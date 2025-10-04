package ir.rayanovinmt.rnt_social_api.alert;

import ir.rayanovinmt.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ir.rayanovinmt.rnt_social_api.alert.constant.AlertLevelEnum;
import ir.rayanovinmt.core.security.user.User;
import ir.rayanovinmt.rnt_social_api.message.MessageEntity;
import ir.rayanovinmt.rnt_social_api.keyword.KeywordEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "alerts"
)
public class AlertEntity extends BaseEntity {

    @Column(nullable = false)
    String details;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    AlertLevelEnum level;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    MessageEntity message;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id", nullable = false)
    KeywordEntity keyword;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "core_user_id", unique = true, nullable = false)
    User coreUser;

}