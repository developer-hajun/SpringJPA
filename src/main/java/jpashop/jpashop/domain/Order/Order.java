package jpashop.jpashop.domain.Order;

import jakarta.persistence.*;
import jpashop.jpashop.domain.Delivery.Delivery;
import jpashop.jpashop.domain.Delivery.DeliveryStatus;
import jpashop.jpashop.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    //즉시 로딩의 경우 만약 오더 Entity가 100개가 존재한다면
    //order를 가져오는 쿼리가 1번인데
    //각 order마다 다른 member를 가지고 있다면 member를 가져오는 쿼리가
    //order개수만큼 나가는 n+1문제 발생
    //그래서 즉시로딩이 필요한 경우는 fetch join으로 해결
    //지연 로딩은 transaction밖에서 에러 발생
    //-> transaction을 빨리 가져오거나 
    //-> command와 query를 분리

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //연관관계 메서드
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    public void cancel(){
        if(this.delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송 완료 된 상품입니다");
        }
        this.setStatus(OrderStatus.CENCEL); //더티 채킹으로 인해 SQL에 반영됨
        for( OrderItem orderitem : orderItems ){
            orderitem.cancel();
        }
    }

    public int getTotalPrice(){
        int total=0;
        for(OrderItem orderitem : orderItems){
            total += orderitem.getTotalPrice();
        }
        return total;
    }


}
