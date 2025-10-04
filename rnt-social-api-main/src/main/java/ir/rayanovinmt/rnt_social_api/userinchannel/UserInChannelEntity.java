package ir.rayanovinmt.rnt_social_api.userinchannel;

import ir.rayanovinmt.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ir.rayanovinmt.rnt_social_api.userinchannel.constant.UserInChannelRoleEnum;
import ir.rayanovinmt.core.security.user.User;
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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "core_user_id", unique = true, nullable = false)
    User coreUser;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    ChannelEntity channel;

}