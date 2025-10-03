package ir.rayanovinmt.rnt_social_api.bot;

import ir.rayanovinmt.core.response.ApiResponse;
import ir.rayanovinmt.core.security.annotation.HasPermission;
import ir.rayanovinmt.rnt_social_api.bot.dto.*;
import ir.rayanovinmt.rnt_social_api.bot.constant.*;
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
@RequestMapping("/api/bot")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Bot Management", description = "Endpoints for managing Bot entities")
public class BotController {
    
    BotService botService;

    @PostMapping
    @Operation(summary = "Create a new Bot")
    @HasPermission({BotPermission.CREATE})
    public ResponseEntity<ApiResponse<BotLoadDto>> create(
            @Valid @RequestBody BotCreateDto createDto) {
        return ResponseEntity.ok(ApiResponse.success(botService.create(createDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Bot")
    @HasPermission({BotPermission.UPDATE})
    public ResponseEntity<ApiResponse<BotLoadDto>> update(
            @PathVariable Long id, 
            @Valid @RequestBody BotUpdateDto updateDto) {
        return ResponseEntity.ok(ApiResponse.success(botService.update(id, updateDto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Bot by ID")
    @HasPermission({BotPermission.READ})
    public ResponseEntity<ApiResponse<BotLoadDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(botService.findById(id)));
    }

    @GetMapping
    @Operation(summary = "Search Bot entities")
    @HasPermission({BotPermission.SEARCH})
    public ResponseEntity<ApiResponse<List<BotLoadDto>>> search(
            @SearchParams SearchParamsWrapper params) {
        return ResponseEntity.ok(ApiResponse.success(botService.search(params)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Bot")
    @HasPermission({BotPermission.DELETE})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        botService.deleteLogical(id);
        return ResponseEntity.noContent().build();
    }
}