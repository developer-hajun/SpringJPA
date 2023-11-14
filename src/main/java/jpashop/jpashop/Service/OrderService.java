package jpashop.jpashop.Service;

import jpashop.jpashop.Repository.ItemRepository;
import jpashop.jpashop.Repository.MemberRepository;
import jpashop.jpashop.Repository.OrderRepository;
import jpashop.jpashop.domain.Delivery.Delivery;
import jpashop.jpashop.domain.Delivery.DeliveryStatus;
import jpashop.jpashop.domain.Item.Item;
import jpashop.jpashop.domain.Member;
import jpashop.jpashop.domain.Order.Order;
import jpashop.jpashop.domain.Order.OrderItem;
import jpashop.jpashop.domain.Order.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId,Long itemId,int count){
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);
        //저장 시킬 item과 member저장
        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.READY);
        delivery.setAddress(member.getAddress());
        // 이때는 Order만 delivery를 사용하기 때문에 cascade 옵션을 통해 함꼐 가져왓지 다른 곳에서도 delivery를 사용한다면 deliveryRepository를 만들어 사용
        //배달 주소 생성
        OrderItem orderItem =  OrderItem.createOrderItem(item, item.getPrice(),count);
        //주문 item 생성
        Order order = Order.createOrder(member,delivery,orderItem);
        //주문 정보 생성
        orderRepository.save(order);
        //주문 저장
        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByCriteria(orderSearch);
    }
}
