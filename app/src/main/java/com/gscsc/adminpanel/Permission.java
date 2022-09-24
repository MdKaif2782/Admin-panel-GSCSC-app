package com.gscsc.adminpanel;

public class Permission {
    private String email;
    private boolean permission;

    public Permission() {
    }

    public Permission(String email, boolean permission) {
        this.email = email;
        this.permission = permission;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }
}
