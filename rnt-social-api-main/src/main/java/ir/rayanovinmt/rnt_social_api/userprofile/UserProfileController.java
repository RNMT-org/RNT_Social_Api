package ir.rayanovinmt.rnt_social_api.userprofile;

import ir.rayanovinmt.core.response.ApiResponse;
import ir.rayanovinmt.core.security.annotation.HasPermission;
import ir.rayanovinmt.rnt_social_api.userprofile.dto.*;
import ir.rayanovinmt.rnt_social_api.userprofile.constant.*;
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
@RequestMapping("/api/userprofile")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "UserProfile Management", description = "Endpoints for managing UserProfile entities")
public class UserProfileController {
    
    UserProfileService userProfileService;

    @PostMapping
    @Operation(summary = "Create a new UserProfile")
    @HasPermission({UserProfilePermission.CREATE})
    public ResponseEntity<ApiResponse<UserProfileLoadDto>> create(
            @Valid @RequestBody UserProfileCreateDto createDto) {
        return ResponseEntity.ok(ApiResponse.success(userProfileService.create(createDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing UserProfile")
    @HasPermission({UserProfilePermission.UPDATE})
    public ResponseEntity<ApiResponse<UserProfileLoadDto>> update(
            @PathVariable Long id, 
            @Valid @RequestBody UserProfileUpdateDto updateDto) {
        return ResponseEntity.ok(ApiResponse.success(userProfileService.update(id, updateDto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a UserProfile by ID")
    @HasPermission({UserProfilePermission.READ})
    public ResponseEntity<ApiResponse<UserProfileLoadDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(userProfileService.findById(id)));
    }

    @GetMapping
    @Operation(summary = "Search UserProfile entities")
    @HasPermission({UserProfilePermission.SEARCH})
    public ResponseEntity<ApiResponse<List<UserProfileLoadDto>>> search(
            @SearchParams SearchParamsWrapper params) {
        return ResponseEntity.ok(ApiResponse.success(userProfileService.search(params)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a UserProfile")
    @HasPermission({UserProfilePermission.DELETE})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userProfileService.deleteLogical(id);
        return ResponseEntity.noContent().build();
    }
}