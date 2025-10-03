package ir.rayanovinmt.rnt_social_api.core.security.user;

import ir.rayanovinmt.rnt_social_api.core.i18n.I18nUtil;
import ir.rayanovinmt.rnt_social_api.core.response.ApiResponse;
import ir.rayanovinmt.rnt_social_api.core.security.annotation.HasPermission;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;
    I18nUtil i18nUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> register(


             @RequestBody UserRegisterDto request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(userService.register(request))
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(
            @RequestBody UserLoginDto request
    ) {
        try {
            return ResponseEntity.ok(
                    ApiResponse.success(userService.login(request))
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    ApiResponse.error(i18nUtil.getMessage("user.credential.error"), HttpStatus.NOT_FOUND)
            );
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<UserDto>> get() {
        return ResponseEntity.ok(
                ApiResponse.success(userService.get())
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<UserDto>> update(
            @Valid @RequestBody UserUpdateDto updateDto
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(userService.update(updateDto))
        );
    }

    @PostMapping("/assign-roles")
    @HasPermission("user:assign_roles")
    public ResponseEntity<ApiResponse<UserDto>> assignRoles(
            @Valid @RequestBody UserRoleAssignDto assignDto
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(userService.assignRoles(assignDto))
        );
    }

    @GetMapping("/{userId}")
    @HasPermission("user:read")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(userService.getUserById(userId))
        );
    }

    @GetMapping("/authorization-context")
    public ResponseEntity<ApiResponse<UserAuthorizationDto>> getAuthorizationContext() {
        return ResponseEntity.ok(
                ApiResponse.success(userService.getAuthorizationContext())
        );
    }
}