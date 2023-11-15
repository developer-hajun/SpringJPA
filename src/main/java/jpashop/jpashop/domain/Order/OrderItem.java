package jpashop.jpashop.domain.Order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jpashop.jpashop.domain.Item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;

    private int count;


    //연관관계 메소드
    public void setOrder(Order order){
        this.order = order;
//        order.getOrderItems().add(this);
    }
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        item.removeStock(count);
        return orderItem;
    }
    public void cancel(){
        this.item.addStock(count);
    }

    public int getTotalPrice(){
        return getOrderPrice()*getCount();
    }
}
