package com.example.cards.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter @ToString
public class BaseEntity {

    @Column(updatable = false)
   private LocalDateTime createdAt;
    // This field is set to not be updated after creation
    @Column(updatable = false)
   private String createdBy;

    @Column(insertable = false)
   private LocalDateTime updatedAt;

    @Column(insertable = false)
   private String updatedBy;

}
