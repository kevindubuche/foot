package com.foot.team_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Base class for auditable entities providing createdAt and updatedAt timestamps.
 * <p>
 * This class is annotated with {@code @MappedSuperclass} to indicate that its fields should
 * be mapped to the fields of entities that inherit from it. It uses Lombok's {@code @Data} annotation
 * to generate getters, setters, toString, equals, and hashCode methods.
 * </p>
 */
@Data
@MappedSuperclass
public abstract class Auditable {

    /**
     * Date and time when the entity was created.
     * <p>
     * This field is annotated with {@code @CreationTimestamp} to automatically set its value
     * to the current date and time when a new entity is persisted. It is also annotated with
     * {@code @Column(updatable = false)} to prevent updates after creation.
     * </p>
     */
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * Date and time when the entity was last updated.
     * <p>
     * This field is annotated with {@code @UpdateTimestamp} to automatically set its value
     * to the current date and time whenever the entity is updated.
     * </p>
     */
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}