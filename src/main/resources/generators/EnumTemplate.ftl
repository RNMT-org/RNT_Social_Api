package ${basePackage}.${entityName?lower_case}.constant;

import lombok.Getter;

@Getter
public enum ${entityName?cap_first}${enumName?cap_first}Enum {
    <#list enums as enum>
    ${enum.name?upper_case}("${enum.value}")<#if enum_has_next>,<#else>;</#if>
    </#list>

    private final String value;

    ${entityName?cap_first}${enumName?cap_first}Enum(String value) {
        this.value = value;
    }

    public static ${entityName?cap_first}${enumName?cap_first}Enum fromValue(String value) {
        for (${entityName?cap_first}${enumName?cap_first}Enum field : ${entityName?cap_first}${enumName?cap_first}Enum.values()) {
            if (field.getValue().equalsIgnoreCase(value)) {
                return field;
            }
        }
        throw new IllegalArgumentException("No enum constant with value " + value);
    }
}
