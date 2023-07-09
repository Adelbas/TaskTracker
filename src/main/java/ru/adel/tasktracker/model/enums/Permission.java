package ru.adel.tasktracker.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    USER_PERM("user:permission"),
    ADMIN_PERM("admin:permission");

    @Getter
    private final String permission;
}
