package ir.rayanovinmt.rnt_social_api.person;

import ir.rayanovinmt.rnt_social_api.core.response.ApiResponse;
import ir.rayanovinmt.rnt_social_api.core.security.annotation.HasPermission;
import ir.rayanovinmt.rnt_social_api.person.dto.*;
import ir.rayanovinmt.rnt_social_api.person.constant.*;
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
@RequestMapping("/api/person")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Person Management", description = "Endpoints for managing Person entities")
public class PersonController {
    
    PersonService personService;

    @PostMapping
    @Operation(summary = "Create a new Person")
    @HasPermission({PersonPermission.CREATE})
    public ResponseEntity<ApiResponse<PersonLoadDto>> create(
            @Valid @RequestBody PersonCreateDto createDto) {
        return ResponseEntity.ok(ApiResponse.success(personService.create(createDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Person")
    @HasPermission({PersonPermission.UPDATE})
    public ResponseEntity<ApiResponse<PersonLoadDto>> update(
            @PathVariable Long id, 
            @Valid @RequestBody PersonUpdateDto updateDto) {
        return ResponseEntity.ok(ApiResponse.success(personService.update(id, updateDto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Person by ID")
    @HasPermission({PersonPermission.READ})
    public ResponseEntity<ApiResponse<PersonLoadDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(personService.findById(id)));
    }

    @GetMapping
    @Operation(summary = "Search Person entities")
    @HasPermission({PersonPermission.SEARCH})
    public ResponseEntity<ApiResponse<List<PersonLoadDto>>> search(
            @SearchParams SearchParamsWrapper params) {
        return ResponseEntity.ok(ApiResponse.success(personService.search(params)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Person")
    @HasPermission({PersonPermission.DELETE})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personService.deleteLogical(id);
        return ResponseEntity.noContent().build();
    }
}