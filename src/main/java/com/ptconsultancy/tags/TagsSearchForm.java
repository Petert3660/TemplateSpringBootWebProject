package com.ptconsultancy.tags;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class TagsSearchForm {

    @NotEmpty(message="{error.updates.emptyerror}")
    @Pattern(regexp = "^[a-zA-Z\\s#,]*$", message="{error.updates.tagspatternerror}")
    private String tags;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
