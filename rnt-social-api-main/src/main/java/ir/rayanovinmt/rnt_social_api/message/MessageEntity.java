package ir.rayanovinmt.rnt_social_api.message;

import ir.rayanovinmt.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import ir.rayanovinmt.rnt_social_api.channel.ChannelEntity;
import ir.rayanovinmt.rnt_social_api.keyword.KeywordEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "messages"
)
public class MessageEntity extends BaseEntity {

    @Column(nullable = false)
    String text;

    @Column(nullable = false)
    Date timeReceived;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    ChannelEntity channel;

    @ManyToMany(mappedBy = "messages", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    Set<KeywordEntity> keywords = new HashSet<>();

}