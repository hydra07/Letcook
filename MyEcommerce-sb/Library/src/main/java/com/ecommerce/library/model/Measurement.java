package com.ecommerce.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "measurements",uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measurement_id")
    private Long id;

    @Column(columnDefinition = "NVARCHAR(50)")
    private String name;

    private boolean is_deleted;

    public Measurement(String name){
        this.name = name;
        this.is_deleted = false;
    }

}
