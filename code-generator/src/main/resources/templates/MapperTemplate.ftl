package ${basePackage}.${entityName?lower_case};

import ir.rayanovinmt.core.entity.BaseMapper;
import org.mapstruct.*;
import ${basePackage}.${entityName?lower_case}.dto.*;
<#assign imports = []>
<#assign documentIncluded = false>
<#assign coreUserIncluded = false>
<#list relationships as rel>
    <#if !rel.mappedBy?has_content>
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
            <#if !imports?seq_contains("ir.rayanovinmt.core.security.user.UserLoadDto")>
                <#assign imports += ["ir.rayanovinmt.core.security.user.UserLoadDto"]>
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
    </#if>
</#list>
<#if imports?has_content>
    <#if !imports?seq_contains("org.mapstruct.factory.Mappers")>
        <#assign imports += ["org.mapstruct.factory.Mappers"]>
    </#if>
</#if>
<#list imports as imp>
import ${imp};
</#list>

<#-- No mapper dependencies needed since LoadDto has no relationships -->
@Mapper(componentModel = "spring")
public interface ${entityName}Mapper extends BaseMapper<${entityName}Entity, ${entityName}CreateDto, ${entityName}UpdateDto, ${entityName}LoadDto> {

    <#-- For load(): MapStruct auto-maps only matching fields (LoadDto has NO relationships) -->
    @Override
    ${entityName}LoadDto load(${entityName}Entity entity);

    <#-- For create(): ignore ALL relationships (handled in service) -->
    @Override
    <#if relationships?has_content>
    @Mappings({
        <#list relationships as rel>
        @Mapping(target = "${rel.relationshipName}", ignore = true)<#if rel_has_next>,</#if>
        </#list>
    })
    </#if>
    ${entityName}Entity create(${entityName}CreateDto createDto);

    <#-- For update(): ignore ALL relationships (handled in service) -->
    @Override
    <#if relationships?has_content>
    @Mappings({
        <#list relationships as rel>
        @Mapping(target = "${rel.relationshipName}", ignore = true)<#if rel_has_next>,</#if>
        </#list>
    })
    </#if>
    void update(${entityName}UpdateDto updateDto, @MappingTarget ${entityName}Entity target);
}
