package pe.todotic.bookstoreapi_s2.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Float total;

    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private User customerId;


    public enum PaymentStatus{
        PENDING, // pagado
        PAID // pagado
    }
}
