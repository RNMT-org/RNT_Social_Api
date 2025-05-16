package ir.rayanovinmt.rnt_social_api.post;

import ir.rayanovinmt.rnt_social_api.core.response.ApiResponse;
import ir.rayanovinmt.rnt_social_api.core.security.annotation.HasPermission;
import ir.rayanovinmt.rnt_social_api.post.dto.*;
import ir.rayanovinmt.rnt_social_api.post.constant.*;
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
@RequestMapping("/api/post")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Post Management", description = "Endpoints for managing Post entities")
public class PostController {
    
    PostService postService;

    @PostMapping
    @Operation(summary = "Create a new Post")
    @HasPermission({PostPermission.CREATE})
    public ResponseEntity<ApiResponse<PostLoadDto>> create(
            @Valid @RequestBody PostCreateDto createDto) {
        return ResponseEntity.ok(ApiResponse.success(postService.create(createDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Post")
    @HasPermission({PostPermission.UPDATE})
    public ResponseEntity<ApiResponse<PostLoadDto>> update(
            @PathVariable Long id, 
            @Valid @RequestBody PostUpdateDto updateDto) {
        return ResponseEntity.ok(ApiResponse.success(postService.update(id, updateDto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Post by ID")
    @HasPermission({PostPermission.READ})
    public ResponseEntity<ApiResponse<PostLoadDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(postService.findById(id)));
    }

    @GetMapping
    @Operation(summary = "Search Post entities")
    @HasPermission({PostPermission.SEARCH})
    public ResponseEntity<ApiResponse<List<PostLoadDto>>> search(
            @SearchParams SearchParamsWrapper params) {
        return ResponseEntity.ok(ApiResponse.success(postService.search(params)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Post")
    @HasPermission({PostPermission.DELETE})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.deleteLogical(id);
        return ResponseEntity.noContent().build();
    }
}