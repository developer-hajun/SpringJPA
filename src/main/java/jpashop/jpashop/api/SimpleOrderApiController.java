package jpashop.jpashop.api;

import jpashop.jpashop.Repository.OrderRepository;
import jpashop.jpashop.Repository.OrderSimpleQueryDto;
import jpashop.jpashop.Repository.QueryDtoRepository;
import jpashop.jpashop.Service.OrderService;
import jpashop.jpashop.domain.Address;
import jpashop.jpashop.domain.Member;
import jpashop.jpashop.domain.Order.Order;
import jpashop.jpashop.domain.Order.OrderItem;
import jpashop.jpashop.domain.Order.OrderSearch;
import jpashop.jpashop.domain.Order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SimpleOrderApiController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final QueryDtoRepository queryDtoRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<OrderDTO> findOrdersV1(){
        List<Order> orders = orderService.findOrders(new OrderSearch());
        //orders에서 그냥 member를 뽑아서 출력한다면
        //member의 프록시 객체를 json으로 어떻게 생성할지모름 -> hibernate로 지연로딩 or DTO로 변환 or hibernate로 초기화된것만 출력
        List<OrderDTO> collect = orders.stream().map(OrderDTO::new).collect(Collectors.toList());
        return collect;
        //order 1번 member 2번 delivery 2번 1+n+n 문제
    }
    @GetMapping("/api/v2/simple-orders")
    public List<OrderDTO> findOrdersV2() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        //orders에서 그냥 member를 뽑아서 출력한다면
        //member의 프록시 객체를 json으로 어떻게 생성할지모름 -> hibernate로 지연로딩 or DTO로 변환 or hibernate로 초기화된것만 출력
        List<OrderDTO> collect = orders.stream().map(OrderDTO::new).collect(Collectors.toList());
        return collect;
    }
    @GetMapping("/api/v3/simple-orders")
    public List<OrderSimpleQueryDto> findOrdersV3(){
        return queryDtoRepository.findQueryDTO();
    }

    @GetMapping("/api/v3.1/simple-orders")
    public List<OrderDTO> findOrdersV3_page(@RequestParam(value = "offset",defaultValue = "0") int offset,@RequestParam(value = "limit",defaultValue = "100") int limit) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset,limit);
        //orders에서 그냥 member를 뽑아서 출력한다면
        //member의 프록시 객체를 json으로 어떻게 생성할지모름 -> hibernate로 지연로딩 or DTO로 변환 or hibernate로 초기화된것만 출력
        return orders.stream().map(OrderDTO::new).collect(Collectors.toList());
    }
    @GetMapping("/api/v4/simple-orders")
    public List<OrderDTO> findOrdersV4() {
        List<Order> orders = orderRepository.findAll();
        //orders에서 그냥 member를 뽑아서 출력한다면
        //member의 프록시 객체를 json으로 어떻게 생성할지모름 -> hibernate로 지연로딩 or DTO로 변환 or hibernate로 초기화된것만 출력
        List<OrderDTO> collect = orders.stream().map(OrderDTO::new).collect(Collectors.toList());
        return collect;
    }
    @Data
    static class OrderDTO{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate; //주문시간
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItem> orderItems;
        public OrderDTO(Order order){
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
            this.orderItems = order.getOrderItems();
        }
    }

}
