package ir.rayanovinmt.rnt_social_api.core.security.user;

import ir.rayanovinmt.rnt_social_api.core.exception.ExceptionTemplate;
import ir.rayanovinmt.rnt_social_api.core.exception.ExceptionUtil;
import ir.rayanovinmt.rnt_social_api.core.security.jwt.JwtService;
import ir.rayanovinmt.rnt_social_api.core.security.role.RoleEntity;
import ir.rayanovinmt.rnt_social_api.core.security.role.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {
    final PasswordEncoder passwordEncoder;
    final JwtService jwtService;
    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;
    final RoleRepository roleRepository;

    @Value("${security.jwt.exp}")
    Long jwtExp;

    @Transactional
    public UserResponseDto register(UserRegisterDto request) {
        User user = User
                .builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        Optional<User> foundedUser = userRepository.findByUsername(user.getUsername());

        if (foundedUser.isPresent()){
           throw ExceptionUtil.make(ExceptionTemplate.USER_DUPLICATED_ERROR);
        }

        User createdUser = userRepository.save(user);
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("authorization",createdUser.getRoles() == null ? null :
                createdUser.getRoles().stream()
                        .map(roleEntity -> UserRoleDto.builder()
                                .name(roleEntity.getName())
                                .permissions(roleEntity.getPermission().stream()
                                        .map(permissionEntity -> UserPermissionDto.builder()
                                                .name(permissionEntity.getName())
                                                .showName(permissionEntity.getShowName())
                                                .build())
                                        .toList())
                                .build()).toList()
        );

        String jwtToken = jwtService.generateToken(
                extraClaims,
                user.getUsername(),
                jwtExp
        );

        return UserMapper.INSTANCE.toResponseDto(createdUser, jwtToken);
    }

    public UserResponseDto login(UserLoginDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("authorization",
                user.getRoles().stream()
                        .map(roleEntity -> UserRoleDto.builder()
                                .name(roleEntity.getName())
                                .permissions(roleEntity.getPermission().stream()
                                        .map(permissionEntity -> UserPermissionDto.builder()
                                                .name(permissionEntity.getName())
                                                .showName(permissionEntity.getShowName())
                                                .build())
                                        .toList())
                                .build()).toList()
        );

        String jwtToken = jwtService.generateToken(
                extraClaims,
                user.getUsername(),
                jwtExp
        );


        return UserMapper.INSTANCE.toResponseDto(user, jwtToken);
    }

    public UserDto get() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return UserMapper.INSTANCE.toDto(user);
    }

    @Transactional
    public UserDto update(UserUpdateDto updateDto) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        // Update name
        if (updateDto.getName() != null && !updateDto.getName().isBlank()) {
            user.setName(updateDto.getName());
        }

        // Update password if provided
        if (updateDto.getPassword() != null && !updateDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updateDto.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toDto(updatedUser);
    }

    @Transactional
    public UserDto assignRoles(UserRoleAssignDto assignDto) {
        User user = userRepository.findById(assignDto.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        // Load roles from database
        Set<RoleEntity> roles = new HashSet<>();
        for (Long roleId : assignDto.getRoleIds()) {
            RoleEntity role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new IllegalArgumentException("Role with ID " + roleId + " not found"));
            roles.add(role);
        }

        // Replace user's roles with new set
        user.setRoles(roles);

        User updatedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toDto(updatedUser);
    }

    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        return UserMapper.INSTANCE.toDto(user);
    }

    /**
     * Get complete authorization context for frontend
     * Returns user info with roles and permissions structured for easy access control
     */
    public UserAuthorizationDto getAuthorizationContext() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Build roles with permissions
        List<RoleAuthDto> roles = user.getRoles().stream()
                .map(role -> RoleAuthDto.builder()
                        .roleId(role.getId())
                        .roleName(role.getName())
                        .permissions(role.getPermission().stream()
                                .map(permission -> PermissionAuthDto.builder()
                                        .permissionId(permission.getId())
                                        .name(permission.getName())
                                        .showName(permission.getShowName())
                                        .build())
                                .collect(Collectors.toSet()))
                        .build())
                .toList();

        // Flatten all permissions for easy checking
        Set<String> allPermissions = user.getRoles().stream()
                .flatMap(role -> role.getPermission().stream())
                .map(permission -> permission.getName())
                .collect(Collectors.toSet());

        return UserAuthorizationDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .roles(roles)
                .allPermissions(allPermissions)
                .build();
    }
}
