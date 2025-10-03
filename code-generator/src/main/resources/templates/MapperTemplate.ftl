package ${basePackage}.${entityName?lower_case};

import ir.rayanovinmt.core.entity.BaseMapper;
import org.mapstruct.*;
import ${basePackage}.${entityName?lower_case}.dto.*;
<#assign imports = []>
<#assign documentIncluded = false>
<#assign coreUserIncluded = false>
<#list relationships as rel>
    <#if rel.document>
        <#if !imports?seq_contains("ir.rayanovinmt.core.document.DocumentMapper")>
            <#assign imports += ["ir.rayanovinmt.core.document.DocumentMapper"]>
        </#if>
        <#if !imports?seq_contains("ir.rayanovinmt.core.document.DocumentDto")>
            <#assign imports += ["ir.rayanovinmt.core.document.DocumentDto"]>
        </#if>
        <#if !imports?seq_contains("ir.rayanovinmt.core.document.Document")>
            <#assign imports += ["ir.rayanovinmt.core.document.Document"]>
        </#if>
    <#elseif rel.coreUser>
        <#if !imports?seq_contains("ir.rayanovinmt.core.security.user.UserMapper")>
            <#assign imports += ["ir.rayanovinmt.core.security.user.UserMapper"]>
        </#if>
        <#if !imports?seq_contains("ir.rayanovinmt.core.security.user.UserDto")>
            <#assign imports += ["ir.rayanovinmt.core.security.user.UserDto"]>
        </#if>
        <#if !imports?seq_contains("ir.rayanovinmt.core.security.user.User")>
            <#assign imports += ["ir.rayanovinmt.core.security.user.User"]>
        </#if>
    <#else>
        <#if !imports?seq_contains("${rel.relatedEntityPackage}.${rel.relatedEntityName}Mapper")>
            <#assign imports += ["${rel.relatedEntityPackage}.${rel.relatedEntityName}Mapper"]>
        </#if>
        <#if !imports?seq_contains("${rel.relatedEntityPackage}.dto.${rel.relatedEntityName}LoadDto")>
            <#assign imports += ["${rel.relatedEntityPackage}.dto.${rel.relatedEntityName}LoadDto"]>
        </#if>
        <#if !imports?seq_contains("${rel.relatedEntityPackage}.${rel.relatedEntityName}Entity")>
            <#assign imports += ["${rel.relatedEntityPackage}.${rel.relatedEntityName}Entity"]>
        </#if>
    </#if>
    <#if !imports?seq_contains("org.mapstruct.factory.Mappers")>
        <#assign imports += ["org.mapstruct.factory.Mappers"]>
    </#if>
</#list>
<#list imports as imp>
import ${imp};
</#list>

@Mapper(componentModel = "spring", uses = {})
public interface ${entityName}Mapper extends BaseMapper<${entityName}Entity, ${entityName}CreateDto, ${entityName}UpdateDto, ${entityName}LoadDto> {

    @Override
    @Mappings({
        <#list relationships as rel>
        @Mapping(target = "${rel.getRelationshipName()}", ignore = true)<#if rel_has_next>,</#if>
        </#list>
    })
    ${entityName}Entity create(${entityName}CreateDto createDto);

    @Override
    @Mappings({
        <#list relationships as rel>
        @Mapping(target = "${rel.getRelationshipName()}", ignore = true)<#if rel_has_next>,</#if>
        </#list>
    })
    ${entityName}Entity entity(${entityName}LoadDto loadDto);

    @Override
    @Mappings({
        <#list relationships as rel>
        @Mapping(target = "${rel.getRelationshipName()}", ignore = true)<#if rel_has_next>,</#if>
        </#list>
    })
    void update(${entityName}UpdateDto updateDto, @MappingTarget ${entityName}Entity target);

<#-- Mapping for List relations -->
<#list relationships as rel>
<#if rel.mappedBy?has_content>

    <#if rel.document>
        <#if documentIncluded == false>
    default DocumentDto DocumentToDocumentDto(Document document) {
        return Mappers.getMapper(DocumentMapper.class).toDto(document);
    }
            <#assign documentIncluded = true>
        </#if>
    <#elseif rel.coreUser>
        <#if coreUserIncluded == false>
    default UserDto userToUserDto(User user) {
        return Mappers.getMapper(UserMapper.class).toDto(user);
    }
            <#assign coreUserIncluded = true>
        </#if>
    <#else>
    default ${rel.relatedEntityName}LoadDto ${rel.relatedEntityName?uncap_first}To${rel.relatedEntityName}Dto(${rel.relatedEntityName}Entity ${rel.relatedEntityName?uncap_first}) {
        return Mappers.getMapper(${rel.relatedEntityName}Mapper.class).load(${rel.relatedEntityName?uncap_first});
    }
    </#if>
</#if>
</#list>
}
