package ir.rayanovinmt.rnt_social_api.messagingplatform;

import ir.rayanovinmt.core.response.ApiResponse;
import ir.rayanovinmt.core.security.annotation.HasPermission;
import ir.rayanovinmt.rnt_social_api.messagingplatform.dto.*;
import ir.rayanovinmt.rnt_social_api.messagingplatform.constant.*;
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
@RequestMapping("/api/messagingplatform")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "MessagingPlatform Management", description = "Endpoints for managing MessagingPlatform entities")
public class MessagingPlatformController {
    
    MessagingPlatformService messagingPlatformService;

    @PostMapping
    @Operation(summary = "Create a new MessagingPlatform")
    @HasPermission({MessagingPlatformPermission.CREATE})
    public ResponseEntity<ApiResponse<MessagingPlatformLoadDto>> create(
            @Valid @RequestBody MessagingPlatformCreateDto createDto) {
        return ResponseEntity.ok(ApiResponse.success(messagingPlatformService.create(createDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing MessagingPlatform")
    @HasPermission({MessagingPlatformPermission.UPDATE})
    public ResponseEntity<ApiResponse<MessagingPlatformLoadDto>> update(
            @PathVariable Long id, 
            @Valid @RequestBody MessagingPlatformUpdateDto updateDto) {
        return ResponseEntity.ok(ApiResponse.success(messagingPlatformService.update(id, updateDto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a MessagingPlatform by ID")
    @HasPermission({MessagingPlatformPermission.READ})
    public ResponseEntity<ApiResponse<MessagingPlatformLoadDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(messagingPlatformService.findById(id)));
    }

    @GetMapping
    @Operation(summary = "Search MessagingPlatform entities")
    @HasPermission({MessagingPlatformPermission.SEARCH})
    public ResponseEntity<ApiResponse<List<MessagingPlatformLoadDto>>> search(
            @SearchParams SearchParamsWrapper params) {
        return ResponseEntity.ok(ApiResponse.success(messagingPlatformService.search(params)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a MessagingPlatform")
    @HasPermission({MessagingPlatformPermission.DELETE})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        messagingPlatformService.deleteLogical(id);
        return ResponseEntity.noContent().build();
    }
}