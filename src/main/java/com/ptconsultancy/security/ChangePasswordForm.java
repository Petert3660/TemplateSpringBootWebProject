package com.ptconsultancy.security;

import javax.validation.constraints.NotEmpty;

public class ChangePasswordForm {

    @NotEmpty(message="{error.updates.emptyerror}")
    private String newPassword;
    @NotEmpty(message="{error.updates.emptyerror}")
    private String retypeNewPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRetypeNewPassword() {
        return retypeNewPassword;
    }

    public void setRetypeNewPassword(String retypeNewPassword) {
        this.retypeNewPassword = retypeNewPassword;
    }
}
