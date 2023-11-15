package jpashop.jpashop.Repository;

import jakarta.persistence.Entity;
import jpashop.jpashop.domain.Address;
import jpashop.jpashop.domain.Order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderSimpleQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate; //주문시간
    private OrderStatus orderStatus;
    private Address address;
}
