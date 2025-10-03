package ir.rayanovinmt.core.generator.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import ir.rayanovinmt.core.generator.model.BasePackage;
import ir.rayanovinmt.core.util.StringUtils;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RelationModel {
    @JacksonXmlProperty(localName = "relationshipType")
    RelationType type;

    @JacksonXmlProperty(localName = "relatedEntityName")
    String relatedEntityName;

    String relatedEntityPackage;

    @JacksonXmlProperty(localName = "mappedBy")
    String mappedBy;

    @JacksonXmlProperty(localName = "foreignKey")
    String foreignKey;

    @JacksonXmlProperty(localName = "required")
    @Builder.Default
    boolean required = false;

    @JacksonXmlProperty(localName = "name")
    String name;

    @JacksonXmlProperty(localName = "document")
    @Builder.Default
    boolean document = false;

    @JacksonXmlProperty(localName = "coreUser")
    @Builder.Default
    boolean coreUser = false;

    @JacksonXmlProperty(localName = "searchable")
    @Builder.Default
    boolean searchable = false;

    @JacksonXmlProperty(localName = "unique")
    @Builder.Default
    boolean unique = false;

    // Cascade operations
    @JacksonXmlProperty(localName = "cascade")
    @Builder.Default
    String cascade = "ALL"; // ALL, PERSIST, MERGE, REMOVE, REFRESH, DETACH

    @JacksonXmlProperty(localName = "orphanRemoval")
    @Builder.Default
    boolean orphanRemoval = false;

    // Fetch type
    @JacksonXmlProperty(localName = "fetch")
    @Builder.Default
    String fetch = "LAZY"; // LAZY, EAGER

    // Join column options
    @JacksonXmlProperty(localName = "joinColumnName")
    String joinColumnName;

    @JacksonXmlProperty(localName = "referencedColumnName")
    String referencedColumnName;

    @JacksonXmlProperty(localName = "joinTableName")
    String joinTableName;

    @JacksonXmlProperty(localName = "inverseJoinColumnName")
    String inverseJoinColumnName;

    // Optional flag
    @JacksonXmlProperty(localName = "optional")
    @Builder.Default
    boolean optional = true;

    public String getRelationshipName() {
        return (name != null && !name.isEmpty()) ? name : StringUtils.toLowerCamelCase(relatedEntityName);
    }

    public static String generatePackageName(String name) {
        if(name == null){
            return null;
        }
        return BasePackage.getDefaultBasePackage() + "." + name.toLowerCase();
    }

    @com.fasterxml.jackson.annotation.JsonIgnore
    public boolean hasMappedBy() {
        return mappedBy != null && !mappedBy.isEmpty();
    }

    /**
     * Get effective join column name
     */
    @com.fasterxml.jackson.annotation.JsonIgnore
    public String getEffectiveJoinColumnName() {
        if (joinColumnName != null) {
            return joinColumnName;
        }
        return relatedEntityName.toLowerCase() + "_id";
    }

    /**
     * Get effective join table name
     */
    @com.fasterxml.jackson.annotation.JsonIgnore
    public String getEffectiveJoinTableName(String ownerEntityName) {
        if (joinTableName != null) {
            return joinTableName;
        }
        return ownerEntityName.toLowerCase() + "_" + relatedEntityName.toLowerCase();
    }

    /**
     * Get cascade types for JPA annotation
     */
    @com.fasterxml.jackson.annotation.JsonIgnore
    public String getCascadeTypes() {
        if (cascade == null || cascade.isEmpty()) {
            return "CascadeType.ALL";
        }

        String[] types = cascade.split(",");
        if (types.length == 1) {
            return "CascadeType." + types[0].trim();
        }

        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < types.length; i++) {
            sb.append("CascadeType.").append(types[i].trim());
            if (i < types.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Get fetch type for JPA annotation
     */
    @com.fasterxml.jackson.annotation.JsonIgnore
    public String getFetchType() {
        if (fetch == null || fetch.isEmpty()) {
            return "FetchType.LAZY";
        }
        return "FetchType." + fetch.toUpperCase();
    }
}
