package com.example.store.enums;

public enum Role {
    ADMIN(new String[]{}), // ADMIN không có quyền con
    MANAGER(new String[] {"VIEW_DASHBOARD", "MANAGE_ORDERS", "VIEW_CUSTOMERS", "EDIT_CUSTOMERS"}),
    STAFF(new String[] {"VIEW_DASHBOARD", "MANAGE_ORDERS", "VIEW_ORDERS"}),
    CUSTOMER(new String[] {"VIEW_PRODUCTS", "MAKE_ORDER", "VIEW_ORDER_STATUS"});

    private final String[] permissions;

    // Constructor for roles with permissions
    Role(String[] permissions) {
        this.permissions = permissions;
    }

    // Getter to retrieve the permissions for a role
    public String[] getPermissions() {
        return permissions;
    }

    // You can add more methods if needed (e.g., to check if a role has a specific permission)
    public boolean hasPermission(String permission) {
        for (String p : permissions) {
            if (p.equals(permission)) {
                return true;
            }
        }
        return false;
    }
}