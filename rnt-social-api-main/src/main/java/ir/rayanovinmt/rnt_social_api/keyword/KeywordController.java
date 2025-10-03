package ir.rayanovinmt.rnt_social_api.keyword;

import ir.rayanovinmt.core.response.ApiResponse;
import ir.rayanovinmt.core.security.annotation.HasPermission;
import ir.rayanovinmt.rnt_social_api.keyword.dto.*;
import ir.rayanovinmt.rnt_social_api.keyword.constant.*;
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
@RequestMapping("/api/keyword")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Keyword Management", description = "Endpoints for managing Keyword entities")
public class KeywordController {
    
    KeywordService keywordService;

    @PostMapping
    @Operation(summary = "Create a new Keyword")
    @HasPermission({KeywordPermission.CREATE})
    public ResponseEntity<ApiResponse<KeywordLoadDto>> create(
            @Valid @RequestBody KeywordCreateDto createDto) {
        return ResponseEntity.ok(ApiResponse.success(keywordService.create(createDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Keyword")
    @HasPermission({KeywordPermission.UPDATE})
    public ResponseEntity<ApiResponse<KeywordLoadDto>> update(
            @PathVariable Long id, 
            @Valid @RequestBody KeywordUpdateDto updateDto) {
        return ResponseEntity.ok(ApiResponse.success(keywordService.update(id, updateDto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Keyword by ID")
    @HasPermission({KeywordPermission.READ})
    public ResponseEntity<ApiResponse<KeywordLoadDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(keywordService.findById(id)));
    }

    @GetMapping
    @Operation(summary = "Search Keyword entities")
    @HasPermission({KeywordPermission.SEARCH})
    public ResponseEntity<ApiResponse<List<KeywordLoadDto>>> search(
            @SearchParams SearchParamsWrapper params) {
        return ResponseEntity.ok(ApiResponse.success(keywordService.search(params)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Keyword")
    @HasPermission({KeywordPermission.DELETE})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        keywordService.deleteLogical(id);
        return ResponseEntity.noContent().build();
    }
}