package com.haud.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * @author webwerks
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Sim> sims;

    @Column(name = "created_on", updatable = false, nullable = false, columnDefinition = "datetime")
    private Timestamp createdOn;

    @Column(name = "updated_on", nullable = false, columnDefinition = "datetime")
    private Timestamp updatedOn;


    @PrePersist
    public void onCreate() {
        this.createdOn = Timestamp.from(Instant.now());
        this.updatedOn = this.createdOn;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedOn = Timestamp.from(Instant.now());
    }
}
