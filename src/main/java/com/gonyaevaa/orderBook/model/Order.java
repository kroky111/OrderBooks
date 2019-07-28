package com.gonyaevaa.orderBook.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Setter
@Getter
@ToString(exclude = "user")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_ORDER_USER"))
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "orders_books",
            joinColumns = @JoinColumn(name = "order_id",
                    foreignKey = @ForeignKey(name = "FK_ORDER_BOOK_ORDER")),
            inverseJoinColumns = @JoinColumn(name = "book_id",
                    foreignKey = @ForeignKey(name = "FK_ORDER_BOOK_BOOK")))
    private List<Book> books;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public Order() {
        this.status = Status.PENDING;
    }

    public enum Status {
        PENDING, PAID, CANCELLED
    }

    public boolean isPending() {
        return status == Status.PENDING;
    }

    public boolean isCancelled() {
        return status == Status.CANCELLED;
    }
}
