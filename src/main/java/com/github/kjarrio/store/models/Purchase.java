package com.github.kjarrio.store.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "purchases")
@ApiModel("Purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @ApiModelProperty(hidden = true)
    private Integer id;

    @OneToOne(cascade=CascadeType.DETACH)
    @ApiModelProperty(name = "User")
    private User user;

    @ManyToMany(cascade=CascadeType.DETACH, fetch=FetchType.LAZY)
    @ApiModelProperty(name = "Products")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Product> products;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        if (this.products == null) this.products = new ArrayList<>();
        this.products.add(product);
    }

    public BigDecimal getTotal() {
        if (this.products == null) return BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        for (Product product : products) total = total.add(product.getPrice());
        return total;
    }

}