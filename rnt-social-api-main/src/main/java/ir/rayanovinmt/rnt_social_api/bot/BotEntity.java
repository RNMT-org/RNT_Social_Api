package ir.rayanovinmt.rnt_social_api.bot;

import ir.rayanovinmt.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ir.rayanovinmt.rnt_social_api.bot.constant.BotStatusEnum;
import ir.rayanovinmt.rnt_social_api.messagingplatform.MessagingPlatformEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "bots"
)
public class BotEntity extends BaseEntity {

    @Column(nullable = false)
    String token;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    BotStatusEnum status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "platform_id", nullable = false)
    MessagingPlatformEntity platform;

}