package com.ecommerce.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private long id;
    @Column(columnDefinition = "NVARCHAR(50)")
    private String name;
    private boolean is_deleted;
    private boolean is_activated;

    public Category(String name){
        this.name = name;
        this.is_deleted = true;
        this.is_activated = false;
    }

}
