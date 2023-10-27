package com.ecommerce.library.model;


import com.ecommerce.library.service.ReactionService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(columnDefinition = "NVARCHAR(1000)")
    private String content;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<ImgComment> images;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Reaction> reactions;

    @ManyToOne
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

}
