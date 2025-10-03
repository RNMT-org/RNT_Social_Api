package ir.rayanovinmt.rnt_social_api.city;

import ir.rayanovinmt.core.response.ApiResponse;
import ir.rayanovinmt.core.security.annotation.HasPermission;
import ir.rayanovinmt.rnt_social_api.city.dto.*;
import ir.rayanovinmt.rnt_social_api.city.constant.*;
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
@RequestMapping("/api/city")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "City Management", description = "Endpoints for managing City entities")
public class CityController {
    
    CityService cityService;

    @PostMapping
    @Operation(summary = "Create a new City")
    @HasPermission({CityPermission.CREATE})
    public ResponseEntity<ApiResponse<CityLoadDto>> create(
            @Valid @RequestBody CityCreateDto createDto) {
        return ResponseEntity.ok(ApiResponse.success(cityService.create(createDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing City")
    @HasPermission({CityPermission.UPDATE})
    public ResponseEntity<ApiResponse<CityLoadDto>> update(
            @PathVariable Long id, 
            @Valid @RequestBody CityUpdateDto updateDto) {
        return ResponseEntity.ok(ApiResponse.success(cityService.update(id, updateDto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a City by ID")
    @HasPermission({CityPermission.READ})
    public ResponseEntity<ApiResponse<CityLoadDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(cityService.findById(id)));
    }

    @GetMapping
    @Operation(summary = "Search City entities")
    @HasPermission({CityPermission.SEARCH})
    public ResponseEntity<ApiResponse<List<CityLoadDto>>> search(
            @SearchParams SearchParamsWrapper params) {
        return ResponseEntity.ok(ApiResponse.success(cityService.search(params)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a City")
    @HasPermission({CityPermission.DELETE})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cityService.deleteLogical(id);
        return ResponseEntity.noContent().build();
    }
}