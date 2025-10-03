package ir.rayanovinmt.rnt_social_api.message;

import ir.rayanovinmt.core.response.ApiResponse;
import ir.rayanovinmt.core.security.annotation.HasPermission;
import ir.rayanovinmt.rnt_social_api.message.dto.*;
import ir.rayanovinmt.rnt_social_api.message.constant.*;
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
@RequestMapping("/api/message")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Message Management", description = "Endpoints for managing Message entities")
public class MessageController {
    
    MessageService messageService;

    @PostMapping
    @Operation(summary = "Create a new Message")
    @HasPermission({MessagePermission.CREATE})
    public ResponseEntity<ApiResponse<MessageLoadDto>> create(
            @Valid @RequestBody MessageCreateDto createDto) {
        return ResponseEntity.ok(ApiResponse.success(messageService.create(createDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Message")
    @HasPermission({MessagePermission.UPDATE})
    public ResponseEntity<ApiResponse<MessageLoadDto>> update(
            @PathVariable Long id, 
            @Valid @RequestBody MessageUpdateDto updateDto) {
        return ResponseEntity.ok(ApiResponse.success(messageService.update(id, updateDto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Message by ID")
    @HasPermission({MessagePermission.READ})
    public ResponseEntity<ApiResponse<MessageLoadDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(messageService.findById(id)));
    }

    @GetMapping
    @Operation(summary = "Search Message entities")
    @HasPermission({MessagePermission.SEARCH})
    public ResponseEntity<ApiResponse<List<MessageLoadDto>>> search(
            @SearchParams SearchParamsWrapper params) {
        return ResponseEntity.ok(ApiResponse.success(messageService.search(params)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Message")
    @HasPermission({MessagePermission.DELETE})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        messageService.deleteLogical(id);
        return ResponseEntity.noContent().build();
    }
}