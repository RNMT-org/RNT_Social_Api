package ir.rayanovinmt.rnt_social_api.core.security.permission;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.rayanovinmt.rnt_social_api.core.response.ApiResponse;
import ir.rayanovinmt.rnt_social_api.core.search.SearchParams;
import ir.rayanovinmt.rnt_social_api.core.search.SearchParamsWrapper;
import ir.rayanovinmt.rnt_social_api.core.security.annotation.HasPermission;
import ir.rayanovinmt.rnt_social_api.core.security.permission.dto.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Permission Management", description = "Endpoints for managing Permission entities")
public class PermissionController {

    PermissionService permissionService;

    @PostMapping
    @Operation(summary = "Create a new Permission")
    @HasPermission("permission:create")
    public ResponseEntity<ApiResponse<PermissionLoadDto>> create(
            @Valid @RequestBody PermissionCreateDto createDto) {
        return ResponseEntity.ok(ApiResponse.success(permissionService.create(createDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Permission")
    @HasPermission("permission:update")
    public ResponseEntity<ApiResponse<PermissionLoadDto>> update(
            @PathVariable Long id,
            @Valid @RequestBody PermissionUpdateDto updateDto) {
        return ResponseEntity.ok(ApiResponse.success(permissionService.update(id, updateDto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Permission by ID")
    @HasPermission("permission:read")
    public ResponseEntity<ApiResponse<PermissionLoadDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(permissionService.findById(id)));
    }

    @GetMapping
    @Operation(summary = "Search Permission entities")
    @HasPermission("permission:search")
    public ResponseEntity<ApiResponse<List<PermissionLoadDto>>> search(
            @SearchParams SearchParamsWrapper params) {
        return ResponseEntity.ok(ApiResponse.success(permissionService.search(params)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Permission")
    @HasPermission("permission:delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        permissionService.deleteLogical(id);
        return ResponseEntity.noContent().build();
    }
}
