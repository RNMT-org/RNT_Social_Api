package ${basePackage}.${entityName?lower_case};

import ir.rayanovinmt.core.entity.BaseMapper;
import ir.rayanovinmt.core.entity.BaseRepository;
import ir.rayanovinmt.core.entity.BaseService;
import ${basePackage}.${entityName?lower_case}.dto.${entityName}CreateDto;
import ${basePackage}.${entityName?lower_case}.dto.${entityName}LoadDto;
import ${basePackage}.${entityName?lower_case}.dto.${entityName}UpdateDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
<#assign hasRelationships = false>
<#list relationships as rel>
    <#if !rel.mappedBy?has_content && (rel.type.type == "ManyToOne" || rel.type.type == "OneToOne")>
        <#assign hasRelationships = true>
    </#if>
</#list>
<#if hasRelationships>
import jakarta.transaction.Transactional;
</#if>
<#list relationships as rel>
    <#if !rel.mappedBy?has_content && (rel.type.type == "ManyToOne" || rel.type.type == "OneToOne")>
        <#if rel.coreUser>
import ir.rayanovinmt.core.security.user.UserRepository;
        <#else>
import ${rel.relatedEntityPackage}.${rel.relatedEntityName}Repository;
        </#if>
    </#if>
</#list>

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ${entityName}Service extends BaseService<${entityName}Entity , ${entityName}CreateDto, ${entityName}UpdateDto, ${entityName}LoadDto> {
    ${entityName}Repository repository;
    ${entityName}Mapper mapper = Mappers.getMapper(${entityName}Mapper.class);
<#list relationships as rel>
    <#if !rel.mappedBy?has_content && (rel.type.type == "ManyToOne" || rel.type.type == "OneToOne")>
        <#if rel.coreUser>
    UserRepository userRepository;
        <#else>
    ${rel.relatedEntityName}Repository ${rel.relationshipName}Repository;
        </#if>
    </#if>
</#list>

    @Override
    protected BaseRepository<${entityName}Entity> getRepository() {
        return repository;
    }

    @Override
    protected BaseMapper<${entityName}Entity , ${entityName}CreateDto, ${entityName}UpdateDto, ${entityName}LoadDto> getMapper() {
        return mapper;
    }

    <#if searchableFields?has_content>
    @Override
    protected List<String> getSearchableFields() {
        return Arrays.asList(
        <#list searchableFields as field>
            "${field}"<#sep>, </#sep>
        </#list>
        );
    }
    </#if>

<#if hasRelationships>
    @Override
    @Transactional
    public ${entityName}LoadDto create(${entityName}CreateDto createDto) {
        ${entityName}Entity entity = mapper.create(createDto);

        // Set relationships
<#list relationships as rel>
    <#if !rel.mappedBy?has_content && (rel.type.type == "ManyToOne" || rel.type.type == "OneToOne")>
        <#if rel.coreUser>
        if (createDto.getCoreUserId() != null) {
            entity.setCoreUser(userRepository.findById(createDto.getCoreUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + createDto.getCoreUserId())));
        }
        <#else>
        if (createDto.get${rel.relationshipName?cap_first}Id() != null) {
            entity.set${rel.relationshipName?cap_first}(${rel.relationshipName}Repository.findById(createDto.get${rel.relationshipName?cap_first}Id())
                .orElseThrow(() -> new RuntimeException("${rel.relatedEntityName} not found with id: " + createDto.get${rel.relationshipName?cap_first}Id())));
        }
        </#if>
    </#if>
</#list>

        ${entityName}Entity savedEntity = repository.save(entity);
        return mapper.load(savedEntity);
    }

    @Override
    @Transactional
    public ${entityName}LoadDto update(Long id, ${entityName}UpdateDto updateDto) {
        ${entityName}Entity entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("${entityName} not found with id: " + id));

        mapper.update(updateDto, entity);

        // Update relationships
<#list relationships as rel>
    <#if !rel.mappedBy?has_content && (rel.type.type == "ManyToOne" || rel.type.type == "OneToOne")>
        <#if rel.coreUser>
        if (updateDto.getCoreUserId() != null) {
            entity.setCoreUser(userRepository.findById(updateDto.getCoreUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + updateDto.getCoreUserId())));
        }
        <#else>
        if (updateDto.get${rel.relationshipName?cap_first}Id() != null) {
            entity.set${rel.relationshipName?cap_first}(${rel.relationshipName}Repository.findById(updateDto.get${rel.relationshipName?cap_first}Id())
                .orElseThrow(() -> new RuntimeException("${rel.relatedEntityName} not found with id: " + updateDto.get${rel.relationshipName?cap_first}Id())));
        }
        </#if>
    </#if>
</#list>

        ${entityName}Entity updatedEntity = repository.save(entity);
        return mapper.load(updatedEntity);
    }
</#if>
}