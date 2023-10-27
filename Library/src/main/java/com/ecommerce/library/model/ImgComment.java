package com.ecommerce.library.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "img_comments")
public class ImgComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id" , referencedColumnName = "comment_id")
    private Comment comment;

    @Column(name = "img_url")
    private String imgUrl;

}
