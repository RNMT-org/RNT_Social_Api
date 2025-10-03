package ir.rayanovinmt.rnt_social_api.channel;

import ir.rayanovinmt.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ir.rayanovinmt.rnt_social_api.channel.constant.ChannelStatusEnum;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import ir.rayanovinmt.rnt_social_api.messagingplatform.MessagingPlatformEntity;
import ir.rayanovinmt.rnt_social_api.city.CityEntity;
import ir.rayanovinmt.rnt_social_api.bot.BotEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "channels"
    , uniqueConstraints = {
        @UniqueConstraint(name = "channels_name_unique", columnNames = "name")
    }
)
public class ChannelEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ChannelStatusEnum status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "platform_id", nullable = false)
    MessagingPlatformEntity platform;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
        name = "channel_city",
        joinColumns = @JoinColumn(name = "channel_id"),
        inverseJoinColumns = @JoinColumn(name = "city_id")
    )
    @Builder.Default
    Set<CityEntity> cities = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
        name = "channel_bot",
        joinColumns = @JoinColumn(name = "channel_id"),
        inverseJoinColumns = @JoinColumn(name = "bot_id")
    )
    @Builder.Default
    Set<BotEntity> bots = new HashSet<>();

}