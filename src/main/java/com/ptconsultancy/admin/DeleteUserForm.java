package com.ptconsultancy.admin;

import javax.validation.constraints.NotNull;

public class DeleteUserForm {

    @NotNull(message="{error.updates.nothingselectederror}")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
