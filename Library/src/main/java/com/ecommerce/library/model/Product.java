package com.ecommerce.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @Column(columnDefinition = "NVARCHAR(50)")
    private String name;
    @Column(columnDefinition = "NVARCHAR(500)")
    private String description;
    private int currentQuantity;
    private double costPrice;
    private double salePrice;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<ImgProduct> imgProducts;
    //    String image;s
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;
    private boolean is_activated;
    private boolean is_deleted;
    //so luong moi lan ban
    @Column(nullable = true) // This column can be nullable
    private double amountToSell;
    //so luong trung binh vi du 1 qua ca chua la 120g
    private double averageWeight;
    //don vi de ban
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "measurement_id", referencedColumnName = "measurement_id")
    private Measurement measurement;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @JoinTable(name = "product_nutrition", //Tạo ra một join Table tên là "address_person"
//            joinColumns = @JoinColumn(name = "product_id"),
//            inverseJoinColumns = @JoinColumn(name = "nutrition_id")
//    )
//    private Collection<Nutrition> nutritions;
    private double calories;
    private double sugar;
    private double fat;
    private double sodium;
    private double carbs;
    private double fiber;
    @Column(columnDefinition = "NVARCHAR(200)")
    private String tags;


}