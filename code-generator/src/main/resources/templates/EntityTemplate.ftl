package ${basePackage}.${entityName?lower_case};

import ir.rayanovinmt.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
<#assign imports = []>
<#assign entityImports = []>
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
    <#if rel.type.type == "OneToMany" || rel.type.type == "ManyToMany">
        <#if !imports?seq_contains("java.util.List")>
            <#assign imports += ["java.util.List"]>
        </#if>
    </#if>
    <#if rel.type.type == "ManyToMany">
        <#if !imports?seq_contains("java.util.Set")>
            <#assign imports += ["java.util.Set"]>
        </#if>
        <#if !imports?seq_contains("java.util.HashSet")>
            <#assign imports += ["java.util.HashSet"]>
        </#if>
    </#if>
    <#if rel.document>
        <#if !imports?seq_contains("ir.rayanovinmt.core.document.Document")>
            <#assign imports += ["ir.rayanovinmt.core.document.Document"]>
        </#if>
    <#elseif rel.coreUser>
        <#if !imports?seq_contains("ir.rayanovinmt.core.security.user.User")>
            <#assign imports += ["ir.rayanovinmt.core.security.user.User"]>
        </#if>
    <#else>
        <#if rel.relatedEntityName != entityName>
            <#if !entityImports?seq_contains("${rel.relatedEntityPackage}.${rel.relatedEntityName}Entity")>
                <#assign entityImports += ["${rel.relatedEntityPackage}.${rel.relatedEntityName}Entity"]>
            </#if>
        </#if>
    </#if>
</#list>
<#list imports as imp>
import ${imp};
</#list>
<#list entityImports as imp>
import ${imp};
</#list>

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "${tableName}"<#if fields?has_content && fields?filter(f -> f.unique)?size gt 0>
    , uniqueConstraints = {
<#list fields?filter(f -> f.unique) as field>
        @UniqueConstraint(name = "${tableName}_${field.effectiveColumnName}_unique", columnNames = "${field.effectiveColumnName}")<#sep>,</#sep>
</#list>
    }</#if><#if fields?has_content && fields?filter(f -> f.indexed)?size gt 0>
    , indexes = {
<#list fields?filter(f -> f.indexed) as field>
        @Index(name = "${field.indexName!tableName + '_' + field.effectiveColumnName + '_idx'}", columnList = "${field.effectiveColumnName}")<#sep>,</#sep>
</#list>
    }</#if>
)
public class ${entityName}Entity extends BaseEntity {

<#list fields as field>
    <#-- Column annotation with all options -->
    @Column(<#if field.effectiveColumnName != field.name>name = "${field.effectiveColumnName}"<#if field.required || field.hasColumnCustomization()>, </#if></#if><#if field.required>nullable = false<#if field.hasColumnCustomization()>, </#if></#if><#if field.unique>unique = true<#if field.length?? || field.precision?? || field.defaultValue??>, </#if></#if><#if field.length??>length = ${field.length?c}<#if field.precision?? || field.defaultValue??>, </#if></#if><#if field.precision??>precision = ${field.precision?c}<#if field.scale??>, scale = ${field.scale?c}</#if><#if field.defaultValue??>, </#if></#if><#if field.defaultValue??>columnDefinition = "${field.type.type} default '${field.defaultValue}'"</#if>)
    <#if field.type.type == "Enum">
    @Enumerated(EnumType.STRING)
    ${entityName?cap_first}${field.name?cap_first}Enum ${field.name};
    <#else>
    ${field.type.type} ${field.name};
    </#if>

</#list>
<#list relationships as rel>
    <#-- One-to-One Relationship -->
    <#if rel.type.type == "OneToOne">
        <#if rel.hasMappedBy()>
    @OneToOne(mappedBy = "${rel.mappedBy}"<#if rel.cascade??>, cascade = ${rel.cascadeTypes}</#if><#if rel.orphanRemoval>, orphanRemoval = true</#if><#if rel.fetch??>, fetch = ${rel.fetchType}</#if><#if !rel.optional>, optional = false</#if>)
        <#else>
    @OneToOne(<#if rel.cascade??>cascade = ${rel.cascadeTypes}<#if rel.fetch??>, </#if></#if><#if rel.fetch??>fetch = ${rel.fetchType}<#if !rel.optional>, </#if></#if><#if !rel.optional>optional = false</#if>)
    @JoinColumn(name = "${rel.effectiveJoinColumnName}"<#if rel.unique>, unique = true</#if><#if rel.required>, nullable = false</#if><#if rel.referencedColumnName??>, referencedColumnName = "${rel.referencedColumnName}"</#if>)
        </#if>
        <#if rel.document>
    Document ${rel.relationshipName};
        <#elseif rel.coreUser>
    User ${rel.relationshipName};
        <#else>
    ${rel.relatedEntityName}Entity ${rel.relationshipName};
        </#if>

    </#if>
    <#-- One-to-Many Relationship -->
    <#if rel.type.type == "OneToMany">
        <#if rel.hasMappedBy()>
    @OneToMany(mappedBy = "${rel.mappedBy}"<#if rel.cascade??>, cascade = ${rel.cascadeTypes}</#if><#if rel.orphanRemoval>, orphanRemoval = true</#if><#if rel.fetch??>, fetch = ${rel.fetchType}</#if>)
        <#else>
    @OneToMany(<#if rel.cascade??>cascade = ${rel.cascadeTypes}<#if rel.fetch??>, </#if></#if><#if rel.fetch??>fetch = ${rel.fetchType}</#if>)
    @JoinColumn(name = "${rel.effectiveJoinColumnName}")
        </#if>
        <#if rel.document>
    List<Document> ${rel.relationshipName};
        <#elseif rel.coreUser>
    List<User> ${rel.relationshipName};
        <#else>
    List<${rel.relatedEntityName}Entity> ${rel.relationshipName};
        </#if>

    </#if>
    <#-- Many-to-One Relationship -->
    <#if rel.type.type == "ManyToOne">
    @ManyToOne(<#if rel.cascade??>cascade = ${rel.cascadeTypes}<#if rel.fetch??>, </#if></#if><#if rel.fetch??>fetch = ${rel.fetchType}<#if !rel.optional>, </#if></#if><#if !rel.optional>optional = false</#if>)
    @JoinColumn(name = "${rel.effectiveJoinColumnName}"<#if rel.unique>, unique = true</#if><#if rel.required>, nullable = false</#if><#if rel.referencedColumnName??>, referencedColumnName = "${rel.referencedColumnName}"</#if>)
    <#if rel.document>
    Document ${rel.relationshipName};
    <#elseif rel.coreUser>
    User ${rel.relationshipName};
    <#else>
    ${rel.relatedEntityName}Entity ${rel.relationshipName};
    </#if>

    </#if>
    <#-- Many-to-Many Relationship -->
    <#if rel.type.type == "ManyToMany">
        <#if rel.hasMappedBy()>
    @ManyToMany(mappedBy = "${rel.mappedBy}"<#if rel.cascade??>, cascade = ${rel.cascadeTypes}</#if><#if rel.fetch??>, fetch = ${rel.fetchType}</#if>)
        <#else>
    @ManyToMany(<#if rel.cascade??>cascade = ${rel.cascadeTypes}<#if rel.fetch??>, </#if></#if><#if rel.fetch??>fetch = ${rel.fetchType}</#if>)
    @JoinTable(
        name = "${rel.getEffectiveJoinTableName(entityName)}",
        joinColumns = @JoinColumn(name = "${entityName?lower_case}_id"),
        inverseJoinColumns = @JoinColumn(name = "${rel.inverseJoinColumnName!rel.relatedEntityName?lower_case + '_id'}")
    )
        </#if>
    @Builder.Default
    <#if rel.coreUser>
    Set<User> ${rel.relationshipName} = new HashSet<>();
    <#else>
    Set<${rel.relatedEntityName}Entity> ${rel.relationshipName} = new HashSet<>();
    </#if>

    </#if>
</#list>
}