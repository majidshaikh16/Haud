package com.haud.entity;

import lombok.*;

import javax.persistence.*;
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
}
