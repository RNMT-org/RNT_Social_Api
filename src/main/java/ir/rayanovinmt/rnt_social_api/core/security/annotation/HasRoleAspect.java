package ir.rayanovinmt.rnt_social_api.core.security.annotation;

import ir.rayanovinmt.rnt_social_api.core.exception.ExceptionTemplate;
import ir.rayanovinmt.rnt_social_api.core.exception.ExceptionUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Aspect
public class HasRoleAspect {
    @Before("@within(hasRole) || @annotation(hasRole)")
    public void hasRoleMethod(JoinPoint joinPoint, HasRole hasRole) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new SecurityException("Unauthorized: No authentication found.");
        }

        Set<String> rolesRequired = new HashSet<>(Arrays.asList(hasRole.value())).stream()
                .map(role -> "ROLE_" + role)
                .collect(Collectors.toSet());

        Set<String> userRoles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        if (!userRoles.containsAll(rolesRequired)) {
            throw ExceptionUtil.make(ExceptionTemplate.ROLE_ERROR);
        }
    }

    @Before("execution(* *(..)) && @within(hasRole)")
    public void hasRoleClass(JoinPoint joinPoint, HasRole hasRole) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        hasRoleMethod(joinPoint, hasRole);
    }
}
