package com.github.kjarrio.store.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @OneToOne(cascade=CascadeType.DETACH)
    private User user;

    @ManyToMany(cascade=CascadeType.DETACH, fetch=FetchType.LAZY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(id, purchase.id) &&
                Objects.equals(user, purchase.user) &&
                Objects.equals(products, purchase.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, products);
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", user=" + user +
                ", products=" + products +
                '}';
    }
}