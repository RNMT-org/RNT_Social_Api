package ir.rayanovinmt.rnt_social_api.alert;

import ir.rayanovinmt.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ir.rayanovinmt.rnt_social_api.alert.constant.AlertLevelEnum;
import ir.rayanovinmt.rnt_social_api.message.MessageEntity;
import ir.rayanovinmt.rnt_social_api.keyword.KeywordEntity;
import ir.rayanovinmt.rnt_social_api.userprofile.UserProfileEntity;

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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_user_id", nullable = false)
    UserProfileEntity recipient;

}