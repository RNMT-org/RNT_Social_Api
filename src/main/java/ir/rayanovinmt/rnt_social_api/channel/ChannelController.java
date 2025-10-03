package ir.rayanovinmt.rnt_social_api.channel;

import ir.rayanovinmt.core.response.ApiResponse;
import ir.rayanovinmt.core.security.annotation.HasPermission;
import ir.rayanovinmt.rnt_social_api.channel.dto.*;
import ir.rayanovinmt.rnt_social_api.channel.constant.*;
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
@RequestMapping("/api/channel")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Channel Management", description = "Endpoints for managing Channel entities")
public class ChannelController {
    
    ChannelService channelService;

    @PostMapping
    @Operation(summary = "Create a new Channel")
    @HasPermission({ChannelPermission.CREATE})
    public ResponseEntity<ApiResponse<ChannelLoadDto>> create(
            @Valid @RequestBody ChannelCreateDto createDto) {
        return ResponseEntity.ok(ApiResponse.success(channelService.create(createDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Channel")
    @HasPermission({ChannelPermission.UPDATE})
    public ResponseEntity<ApiResponse<ChannelLoadDto>> update(
            @PathVariable Long id, 
            @Valid @RequestBody ChannelUpdateDto updateDto) {
        return ResponseEntity.ok(ApiResponse.success(channelService.update(id, updateDto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Channel by ID")
    @HasPermission({ChannelPermission.READ})
    public ResponseEntity<ApiResponse<ChannelLoadDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(channelService.findById(id)));
    }

    @GetMapping
    @Operation(summary = "Search Channel entities")
    @HasPermission({ChannelPermission.SEARCH})
    public ResponseEntity<ApiResponse<List<ChannelLoadDto>>> search(
            @SearchParams SearchParamsWrapper params) {
        return ResponseEntity.ok(ApiResponse.success(channelService.search(params)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Channel")
    @HasPermission({ChannelPermission.DELETE})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        channelService.deleteLogical(id);
        return ResponseEntity.noContent().build();
    }
}