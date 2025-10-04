package ir.rayanovinmt.rnt_social_api.alert;

import ir.rayanovinmt.core.response.ApiResponse;
import ir.rayanovinmt.core.security.annotation.HasPermission;
import ir.rayanovinmt.rnt_social_api.alert.dto.*;
import ir.rayanovinmt.rnt_social_api.alert.constant.*;
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
@RequestMapping("/api/alert")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Alert Management", description = "Endpoints for managing Alert entities")
public class AlertController {
    
    AlertService alertService;

    @PostMapping
    @Operation(summary = "Create a new Alert")
    @HasPermission({AlertPermission.CREATE})
    public ResponseEntity<ApiResponse<AlertLoadDto>> create(
            @Valid @RequestBody AlertCreateDto createDto) {
        return ResponseEntity.ok(ApiResponse.success(alertService.create(createDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Alert")
    @HasPermission({AlertPermission.UPDATE})
    public ResponseEntity<ApiResponse<AlertLoadDto>> update(
            @PathVariable Long id, 
            @Valid @RequestBody AlertUpdateDto updateDto) {
        return ResponseEntity.ok(ApiResponse.success(alertService.update(id, updateDto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Alert by ID")
    @HasPermission({AlertPermission.READ})
    public ResponseEntity<ApiResponse<AlertLoadDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(alertService.findById(id)));
    }

    @GetMapping
    @Operation(summary = "Search Alert entities")
    @HasPermission({AlertPermission.SEARCH})
    public ResponseEntity<ApiResponse<List<AlertLoadDto>>> search(
            @SearchParams SearchParamsWrapper params) {
        return ResponseEntity.ok(ApiResponse.success(alertService.search(params)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Alert")
    @HasPermission({AlertPermission.DELETE})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alertService.deleteLogical(id);
        return ResponseEntity.noContent().build();
    }
}