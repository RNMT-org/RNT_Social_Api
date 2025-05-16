package ir.rayanovinmt.rnt_social_api.core.security.role;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.rayanovinmt.rnt_social_api.core.response.ApiResponse;
import ir.rayanovinmt.rnt_social_api.core.search.SearchParams;
import ir.rayanovinmt.rnt_social_api.core.search.SearchParamsWrapper;
import ir.rayanovinmt.rnt_social_api.core.security.role.dto.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Role Management", description = "Endpoints for managing Role entities")
public class RoleController {
    
    RoleService roleService;

    @PostMapping
    @Operation(summary = "Create a new Role")
    public ResponseEntity<ApiResponse<RoleLoadDto>> create(
            @Valid @RequestBody RoleCreateDto createDto) {
        return ResponseEntity.ok(ApiResponse.success(roleService.create(createDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Role")
    public ResponseEntity<ApiResponse<RoleLoadDto>> update(
            @PathVariable Long id, 
            @Valid @RequestBody RoleUpdateDto updateDto) {
        return ResponseEntity.ok(ApiResponse.success(roleService.update(id, updateDto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Role by ID")
    public ResponseEntity<ApiResponse<RoleLoadDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(roleService.findById(id)));
    }

    @GetMapping
    @Operation(summary = "Search Role entities")
    public ResponseEntity<ApiResponse<List<RoleLoadDto>>> search(
            @SearchParams SearchParamsWrapper params) {
        return ResponseEntity.ok(ApiResponse.success(roleService.search(params)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Role")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.deleteLogical(id);
        return ResponseEntity.noContent().build();
    }
}
