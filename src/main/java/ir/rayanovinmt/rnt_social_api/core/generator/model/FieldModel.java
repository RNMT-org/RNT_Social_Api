package ir.rayanovinmt.rnt_social_api.core.generator.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldModel {
    @JacksonXmlProperty(localName = "name")
    String name;

    @JacksonXmlProperty(localName = "type")
    FieldType type;

    @JacksonXmlProperty(localName = "required")
    @Builder.Default
    boolean required = false;

    @JacksonXmlElementWrapper(localName = "enums")
    @JacksonXmlProperty(localName = "enum")
    @Builder.Default
    List<EnumModel> enums = new ArrayList<>();

    @JacksonXmlProperty(localName = "searchable")
    @Builder.Default
    boolean searchable = false;

    // Column properties
    @JacksonXmlProperty(localName = "unique")
    @Builder.Default
    boolean unique = false;

    @JacksonXmlProperty(localName = "length")
    Integer length;

    @JacksonXmlProperty(localName = "precision")
    Integer precision;

    @JacksonXmlProperty(localName = "scale")
    Integer scale;

    @JacksonXmlProperty(localName = "columnName")
    String columnName;

    @JacksonXmlProperty(localName = "defaultValue")
    String defaultValue;

    // Validation properties
    @JacksonXmlProperty(localName = "min")
    Long min;

    @JacksonXmlProperty(localName = "max")
    Long max;

    @JacksonXmlProperty(localName = "minLength")
    Integer minLength;

    @JacksonXmlProperty(localName = "maxLength")
    Integer maxLength;

    @JacksonXmlProperty(localName = "pattern")
    String pattern;

    @JacksonXmlProperty(localName = "email")
    @Builder.Default
    boolean email = false;

    @JacksonXmlProperty(localName = "notBlank")
    @Builder.Default
    boolean notBlank = false;

    @JacksonXmlProperty(localName = "notEmpty")
    @Builder.Default
    boolean notEmpty = false;

    @JacksonXmlProperty(localName = "positive")
    @Builder.Default
    boolean positive = false;

    @JacksonXmlProperty(localName = "negative")
    @Builder.Default
    boolean negative = false;

    @JacksonXmlProperty(localName = "positiveOrZero")
    @Builder.Default
    boolean positiveOrZero = false;

    @JacksonXmlProperty(localName = "negativeOrZero")
    @Builder.Default
    boolean negativeOrZero = false;

    @JacksonXmlProperty(localName = "future")
    @Builder.Default
    boolean future = false;

    @JacksonXmlProperty(localName = "past")
    @Builder.Default
    boolean past = false;

    // Index support
    @JacksonXmlProperty(localName = "indexed")
    @Builder.Default
    boolean indexed = false;

    @JacksonXmlProperty(localName = "indexName")
    String indexName;

    /**
     * Check if field has any validation annotations
     */
    @com.fasterxml.jackson.annotation.JsonIgnore
    public boolean hasValidation() {
        return required || notBlank || notEmpty || email || min != null || max != null
                || minLength != null || maxLength != null || pattern != null
                || positive || negative || positiveOrZero || negativeOrZero
                || future || past;
    }

    /**
     * Check if field has column customization
     */
    @com.fasterxml.jackson.annotation.JsonIgnore
    public boolean hasColumnCustomization() {
        return unique || length != null || precision != null || scale != null
                || columnName != null || defaultValue != null;
    }

    /**
     * Get effective column name
     */
    @com.fasterxml.jackson.annotation.JsonIgnore
    public String getEffectiveColumnName() {
        return columnName != null ? columnName : name;
    }
}