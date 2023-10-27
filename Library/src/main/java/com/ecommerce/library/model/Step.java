package com.ecommerce.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "steps")
@Entity
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_id")
    private Long id;

    @Column(columnDefinition = "NVARCHAR(500)")
    private String description;

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL)
    private List<ImgStep> images;

    @ManyToOne
    @JoinColumn(name="recipe_id")
    private Recipe recipe;
}
