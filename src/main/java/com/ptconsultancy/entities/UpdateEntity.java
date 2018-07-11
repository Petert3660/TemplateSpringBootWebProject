package com.ptconsultancy.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UpdateEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String tags;
    private String title;
    private String username;
    private String details;
    private LocalDateTime createdAt;

    public UpdateEntity(){
    }

    public UpdateEntity(String tags, String title, String username, String details, LocalDateTime createdAt) {
        this.tags = tags;
        this.title = title;
        this.username = username;
        this.details = details;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAtAsString() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return createdAt.format(formatter);
    }

    public String getCreatedAtTimeAsString() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        return createdAt.format(formatter);
    }
}
