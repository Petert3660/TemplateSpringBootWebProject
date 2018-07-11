package com.ptconsultancy.admin;

import com.ptconsultancy.users.Role;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AddUserForm {

    @NotEmpty(message="{error.updates.emptyerror}")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message="{error.admin.usernameerror}")
    private String username;
    @NotEmpty(message="{error.updates.emptyerror}")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message="{error.admin.usernameerror}")
    private String password;
    @NotNull(message="{error.updates.nothingselectederror}")
    private Role role;
    @NotEmpty(message="{error.updates.emptyerror}")
    @Pattern(regexp = "^[a-zA-Z0-9-]*$", message="{error.admin.nameerror}")
    private String firstname;
    @NotEmpty(message="{error.updates.emptyerror}")
    @Pattern(regexp = "^[a-zA-Z0-9-]*$", message="{error.admin.nameerror}")
    private String lastname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
