package ru.adel.tasktracker.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.adel.tasktracker.model.enums.Permission.ADMIN_PERM;
import static ru.adel.tasktracker.model.enums.Permission.USER_PERM;

@RequiredArgsConstructor
public enum Role {
    USER(Set.of(USER_PERM)),
    ADMIN(Set.of(USER_PERM,ADMIN_PERM));

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
