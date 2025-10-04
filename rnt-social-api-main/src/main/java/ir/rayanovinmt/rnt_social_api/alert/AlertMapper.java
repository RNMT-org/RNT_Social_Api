package ir.rayanovinmt.rnt_social_api.alert;

import ir.rayanovinmt.core.entity.BaseMapper;
import org.mapstruct.*;
import ir.rayanovinmt.rnt_social_api.alert.dto.*;
import ir.rayanovinmt.rnt_social_api.message.MessageMapper;
import ir.rayanovinmt.rnt_social_api.message.dto.MessageLoadDto;
import ir.rayanovinmt.rnt_social_api.message.MessageEntity;
import ir.rayanovinmt.rnt_social_api.keyword.KeywordMapper;
import ir.rayanovinmt.rnt_social_api.keyword.dto.KeywordLoadDto;
import ir.rayanovinmt.rnt_social_api.keyword.KeywordEntity;
import ir.rayanovinmt.core.security.user.UserMapper;
import ir.rayanovinmt.core.security.user.UserLoadDto;
import ir.rayanovinmt.core.security.user.User;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AlertMapper extends BaseMapper<AlertEntity, AlertCreateDto, AlertUpdateDto, AlertLoadDto> {

    @Override
    AlertLoadDto load(AlertEntity entity);

    @Override
    @Mappings({
        @Mapping(target = "message", ignore = true),
        @Mapping(target = "keyword", ignore = true),
        @Mapping(target = "coreUser", ignore = true)
    })
    AlertEntity create(AlertCreateDto createDto);

    @Override
    @Mappings({
        @Mapping(target = "message", ignore = true),
        @Mapping(target = "keyword", ignore = true),
        @Mapping(target = "coreUser", ignore = true)
    })
    void update(AlertUpdateDto updateDto, @MappingTarget AlertEntity target);
}
