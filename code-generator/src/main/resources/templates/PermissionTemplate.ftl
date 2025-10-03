package ${basePackage}.${entityName?lower_case}.constant;

public class ${entityName?cap_first}Permission {
    public final static String ROOT = "${entityName?lower_case}:root";
    public final static String READ = "${entityName?lower_case}:read";
    public final static String CREATE = "${entityName?lower_case}:create";
    public final static String UPDATE = "${entityName?lower_case}:update";
    public final static String SEARCH = "${entityName?lower_case}:search";
    public final static String DELETE = "${entityName?lower_case}:delete";
}
