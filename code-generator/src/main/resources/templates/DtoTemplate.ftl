package ${basePackage}.${entityName?lower_case}.dto;

import ir.rayanovinmt.core.entity.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
<#if dtoType != "Load">
import jakarta.validation.constraints.*;
</#if>
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
<#assign imports = []>
<#list fields as field>
    <#if field.type.type == "Enum">
        <#if !imports?seq_contains("${basePackage}.${entityName?lower_case}.constant.${entityName?cap_first}${field.name?cap_first}Enum")>
            <#assign imports += ["${basePackage}.${entityName?lower_case}.constant.${entityName?cap_first}${field.name?cap_first}Enum"]>
        </#if>
    <#else>
        <#if field.type.requiresImport()>
            <#if !imports?seq_contains(field.type.importPath)>
                <#assign imports += [field.type.importPath]>
            </#if>
        </#if>
    </#if>
</#list>
<#list relationships as rel>
    <#if rel.document>
        <#assign imports += ["ir.rayanovinmt.core.document.DocumentDto"]>
    <#elseif rel.coreUser>
        <#assign imports += ["ir.rayanovinmt.core.security.user.UserDto"]>
    <#else>
        <#if !imports?seq_contains("${rel.relatedEntityPackage}.dto.${rel.relatedEntityName}LoadDto")>
            <#assign imports += ["${rel.relatedEntityPackage}.dto.${rel.relatedEntityName}LoadDto"]>
        </#if>
    </#if>
    <#if rel.type.type == "OneToMany" || rel.type.type == "ManyToMany">
        <#if !imports?seq_contains("java.util.List")>
            <#assign imports += ["java.util.List"]>
        </#if>
    </#if>
</#list>
<#list imports as imp>
import ${imp};
</#list>

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ${entityName}${dtoType}Dto extends BaseDto {
<#list fields as field>
<#-- Add validation annotations for Create/Update DTOs -->
<#if dtoType != "Load">
    <#if field.required>
    @NotNull(message = "${field.name} is required")
    </#if>
    <#if field.notBlank>
    @NotBlank(message = "${field.name} cannot be blank")
    </#if>
    <#if field.notEmpty>
    @NotEmpty(message = "${field.name} cannot be empty")
    </#if>
    <#if field.email>
    @Email(message = "${field.name} must be a valid email")
    </#if>
    <#if field.min??>
    @Min(${field.min})
    </#if>
    <#if field.max??>
    @Max(${field.max})
    </#if>
    <#if field.minLength??>
    @Size(min = ${field.minLength?c}<#if field.maxLength??>, max = ${field.maxLength?c}</#if>)
    <#elseif field.maxLength??>
    @Size(max = ${field.maxLength?c})
    </#if>
    <#if field.pattern??>
    @Pattern(regexp = "${field.pattern}", message = "${field.name} does not match the required pattern")
    </#if>
    <#if field.positive>
    @Positive(message = "${field.name} must be positive")
    </#if>
    <#if field.negative>
    @Negative(message = "${field.name} must be negative")
    </#if>
    <#if field.positiveOrZero>
    @PositiveOrZero(message = "${field.name} must be positive or zero")
    </#if>
    <#if field.negativeOrZero>
    @NegativeOrZero(message = "${field.name} must be negative or zero")
    </#if>
    <#if field.future>
    @Future(message = "${field.name} must be a future date")
    </#if>
    <#if field.past>
    @Past(message = "${field.name} must be a past date")
    </#if>
</#if>
    <#if field.type.type == "Enum">
    ${entityName?cap_first}${field.name?cap_first}Enum ${field.name};
    <#else>
    ${field.type.type} ${field.name};
    </#if>

</#list>
<#list relationships as rel>
<#-- For LoadDto: include full related entities if mappedBy is present -->
<#if dtoType == "Load" && rel.mappedBy?has_content>
    <#if rel.type.type == "OneToOne" || rel.type.type == "ManyToOne">
        <#if rel.document>
    DocumentDto ${rel.relationshipName};
        <#elseif rel.coreUser>
    UserDto ${rel.relationshipName};
        <#else>
    ${rel.relatedEntityName}LoadDto ${rel.relationshipName};
        </#if>
    </#if>
    <#if rel.type.type == "OneToMany" || rel.type.type == "ManyToMany">
        <#if rel.document>
    List<DocumentDto> ${rel.relationshipName};
        <#elseif rel.coreUser>
    List<UserDto> ${rel.relationshipName};
        <#else>
    List<${rel.relatedEntityName}LoadDto> ${rel.relationshipName};
        </#if>
    </#if>
</#if>
<#-- For Create/Update DTOs: include ID fields for required relationships -->
<#if (dtoType == "Create" || dtoType == "Update") && !rel.mappedBy?has_content>
    <#if rel.type.type == "ManyToOne" || rel.type.type == "OneToOne">
        <#if rel.required && dtoType == "Create">
    @NotNull(message = "${rel.relationshipName}Id is required")
        </#if>
        <#if rel.coreUser>
    Long coreUserId;
        <#else>
    Long ${rel.relationshipName}Id;
        </#if>
    </#if>
    <#if rel.type.type == "ManyToMany">
        <#if rel.required && dtoType == "Create">
    @NotEmpty(message = "${rel.relationshipName}Ids cannot be empty")
        </#if>
    List<Long> ${rel.relationshipName}Ids;
    </#if>
</#if>
</#list>
}
