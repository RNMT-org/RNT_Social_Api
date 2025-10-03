package ir.rayanovinmt.rnt_social_api.userinchannel;

import ir.rayanovinmt.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ir.rayanovinmt.rnt_social_api.userinchannel.constant.UserInChannelRoleEnum;
import ir.rayanovinmt.rnt_social_api.userprofile.UserProfileEntity;
import ir.rayanovinmt.rnt_social_api.channel.ChannelEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user_in_channels"
)
public class UserInChannelEntity extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    UserInChannelRoleEnum role;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    UserProfileEntity user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    ChannelEntity channel;

}