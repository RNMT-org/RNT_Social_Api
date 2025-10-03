package ir.rayanovinmt.rnt_social_api.alert;

import ir.rayanovinmt.core.entity.BaseMapper;
import org.mapstruct.*;
import ir.rayanovinmt.rnt_social_api.alert.dto.*;
import ir.rayanovinmt.rnt_social_api.message.MessageMapper;
import ir.rayanovinmt.rnt_social_api.message.dto.MessageLoadDto;
import ir.rayanovinmt.rnt_social_api.message.MessageEntity;
import org.mapstruct.factory.Mappers;
import ir.rayanovinmt.rnt_social_api.keyword.KeywordMapper;
import ir.rayanovinmt.rnt_social_api.keyword.dto.KeywordLoadDto;
import ir.rayanovinmt.rnt_social_api.keyword.KeywordEntity;
import ir.rayanovinmt.rnt_social_api.userprofile.UserProfileMapper;
import ir.rayanovinmt.rnt_social_api.userprofile.dto.UserProfileLoadDto;
import ir.rayanovinmt.rnt_social_api.userprofile.UserProfileEntity;

@Mapper(componentModel = "spring", uses = {})
public interface AlertMapper extends BaseMapper<AlertEntity, AlertCreateDto, AlertUpdateDto, AlertLoadDto> {

    @Override
    @Mappings({
        @Mapping(target = "message", ignore = true),
        @Mapping(target = "keyword", ignore = true),
        @Mapping(target = "recipient", ignore = true)
    })
    AlertEntity create(AlertCreateDto createDto);

    @Override
    @Mappings({
        @Mapping(target = "message", ignore = true),
        @Mapping(target = "keyword", ignore = true),
        @Mapping(target = "recipient", ignore = true)
    })
    AlertEntity entity(AlertLoadDto loadDto);

    @Override
    @Mappings({
        @Mapping(target = "message", ignore = true),
        @Mapping(target = "keyword", ignore = true),
        @Mapping(target = "recipient", ignore = true)
    })
    void update(AlertUpdateDto updateDto, @MappingTarget AlertEntity target);

}
