package ir.rayanovinmt.rnt_social_api.core.security.user;

import ir.rayanovinmt.rnt_social_api.core.security.jwt.JwtService;
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

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {
    final PasswordEncoder passwordEncoder;
    final JwtService jwtService;
    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;

    @Value("${security.jwt.exp}")
    Long jwtExp;

    public UserResponseDto register(UserRegisterDto request) {
        User user = User
                .builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User createdUser = userRepository.save(user);
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("authorization",
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
}
