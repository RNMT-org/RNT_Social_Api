package ir.rayanovinmt.rnt_social_api.userinchannel;

import ir.rayanovinmt.core.response.ApiResponse;
import ir.rayanovinmt.core.security.annotation.HasPermission;
import ir.rayanovinmt.rnt_social_api.userinchannel.dto.*;
import ir.rayanovinmt.rnt_social_api.userinchannel.constant.*;
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
@RequestMapping("/api/userinchannel")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "UserInChannel Management", description = "Endpoints for managing UserInChannel entities")
public class UserInChannelController {
    
    UserInChannelService userInChannelService;

    @PostMapping
    @Operation(summary = "Create a new UserInChannel")
    @HasPermission({UserInChannelPermission.CREATE})
    public ResponseEntity<ApiResponse<UserInChannelLoadDto>> create(
            @Valid @RequestBody UserInChannelCreateDto createDto) {
        return ResponseEntity.ok(ApiResponse.success(userInChannelService.create(createDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing UserInChannel")
    @HasPermission({UserInChannelPermission.UPDATE})
    public ResponseEntity<ApiResponse<UserInChannelLoadDto>> update(
            @PathVariable Long id, 
            @Valid @RequestBody UserInChannelUpdateDto updateDto) {
        return ResponseEntity.ok(ApiResponse.success(userInChannelService.update(id, updateDto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a UserInChannel by ID")
    @HasPermission({UserInChannelPermission.READ})
    public ResponseEntity<ApiResponse<UserInChannelLoadDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(userInChannelService.findById(id)));
    }

    @GetMapping
    @Operation(summary = "Search UserInChannel entities")
    @HasPermission({UserInChannelPermission.SEARCH})
    public ResponseEntity<ApiResponse<List<UserInChannelLoadDto>>> search(
            @SearchParams SearchParamsWrapper params) {
        return ResponseEntity.ok(ApiResponse.success(userInChannelService.search(params)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a UserInChannel")
    @HasPermission({UserInChannelPermission.DELETE})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userInChannelService.deleteLogical(id);
        return ResponseEntity.noContent().build();
    }
}