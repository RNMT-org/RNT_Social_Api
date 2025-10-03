package ${basePackage}.${entityName?lower_case};

import ir.rayanovinmt.core.response.ApiResponse;
import ir.rayanovinmt.core.security.annotation.HasPermission;
import ${basePackage}.${entityName?lower_case}.dto.*;
import ${basePackage}.${entityName?lower_case}.constant.*;
import ir.rayanovinmt.core.search.SearchParams;
import ir.rayanovinmt.core.search.SearchParamsWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/${entityName?lower_case}")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "${entityName} Management", description = "Endpoints for managing ${entityName} entities")
public class ${entityName}Controller {
    
    ${entityName}Service ${entityName?uncap_first}Service;

    @PostMapping
    @Operation(summary = "Create a new ${entityName}")
    @HasPermission({${entityName?cap_first}Permission.CREATE})
    public ResponseEntity<ApiResponse<${entityName}LoadDto>> create(
            @Valid @RequestBody ${entityName}CreateDto createDto) {
        return ResponseEntity.ok(ApiResponse.success(${entityName?uncap_first}Service.create(createDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing ${entityName}")
    @HasPermission({${entityName?cap_first}Permission.UPDATE})
    public ResponseEntity<ApiResponse<${entityName}LoadDto>> update(
            @PathVariable Long id, 
            @Valid @RequestBody ${entityName}UpdateDto updateDto) {
        return ResponseEntity.ok(ApiResponse.success(${entityName?uncap_first}Service.update(id, updateDto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a ${entityName} by ID")
    @HasPermission({${entityName?cap_first}Permission.READ})
    public ResponseEntity<ApiResponse<${entityName}LoadDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(${entityName?uncap_first}Service.findById(id)));
    }

    @GetMapping
    @Operation(summary = "Search ${entityName} entities")
    @HasPermission({${entityName?cap_first}Permission.SEARCH})
    public ResponseEntity<ApiResponse<List<${entityName}LoadDto>>> search(
            @SearchParams SearchParamsWrapper params) {
        return ResponseEntity.ok(ApiResponse.success(${entityName?uncap_first}Service.search(params)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a ${entityName}")
    @HasPermission({${entityName?cap_first}Permission.DELETE})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ${entityName?uncap_first}Service.deleteLogical(id);
        return ResponseEntity.noContent().build();
    }
}