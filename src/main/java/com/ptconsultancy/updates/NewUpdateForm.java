package com.ptconsultancy.updates;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class NewUpdateForm {

    @NotEmpty(message="{error.updates.emptyerror}")
    @Pattern(regexp = "^[a-zA-Z\\s@']*$", message="{error.updates.titlepatternerror}")
    private String title;
    @NotEmpty(message="{error.updates.emptyerror}")
    @Pattern(regexp = "^[a-zA-Z0-9\\s!\"£$%^&*()@'#:;,.?\\[\\]{}]*$", message="{error.updates.detailspatternerror}")
    private String details;
    @NotEmpty(message="{error.updates.emptyerror}")
    @Pattern(regexp = "^[a-zA-Z\\s#,]*$", message="{error.updates.tagspatternerror}")
    private String tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
