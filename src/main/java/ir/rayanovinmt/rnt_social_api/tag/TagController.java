package ir.rayanovinmt.rnt_social_api.tag;

import ir.rayanovinmt.rnt_social_api.core.response.ApiResponse;
import ir.rayanovinmt.rnt_social_api.core.security.annotation.HasPermission;
import ir.rayanovinmt.rnt_social_api.tag.dto.*;
import ir.rayanovinmt.rnt_social_api.tag.constant.*;
import ir.rayanovinmt.rnt_social_api.core.search.SearchParams;
import ir.rayanovinmt.rnt_social_api.core.search.SearchParamsWrapper;
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
@RequestMapping("/api/tag")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Tag Management", description = "Endpoints for managing Tag entities")
public class TagController {
    
    TagService tagService;

    @PostMapping
    @Operation(summary = "Create a new Tag")
    @HasPermission({TagPermission.CREATE})
    public ResponseEntity<ApiResponse<TagLoadDto>> create(
            @Valid @RequestBody TagCreateDto createDto) {
        return ResponseEntity.ok(ApiResponse.success(tagService.create(createDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Tag")
    @HasPermission({TagPermission.UPDATE})
    public ResponseEntity<ApiResponse<TagLoadDto>> update(
            @PathVariable Long id, 
            @Valid @RequestBody TagUpdateDto updateDto) {
        return ResponseEntity.ok(ApiResponse.success(tagService.update(id, updateDto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Tag by ID")
    @HasPermission({TagPermission.READ})
    public ResponseEntity<ApiResponse<TagLoadDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(tagService.findById(id)));
    }

    @GetMapping
    @Operation(summary = "Search Tag entities")
    @HasPermission({TagPermission.SEARCH})
    public ResponseEntity<ApiResponse<List<TagLoadDto>>> search(
            @SearchParams SearchParamsWrapper params) {
        return ResponseEntity.ok(ApiResponse.success(tagService.search(params)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Tag")
    @HasPermission({TagPermission.DELETE})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.deleteLogical(id);
        return ResponseEntity.noContent().build();
    }
}