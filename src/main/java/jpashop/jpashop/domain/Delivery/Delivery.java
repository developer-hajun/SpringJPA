package jpashop.jpashop.domain.Delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jpashop.jpashop.domain.Address;
import jpashop.jpashop.domain.Order.Order;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "delivery")
    @JsonIgnore
    private Order order;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
