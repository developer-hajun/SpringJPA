package jpashop.jpashop.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpashop.jpashop.Repository.OrderRepository;
import jpashop.jpashop.domain.Address;
import jpashop.jpashop.domain.Item.Book;
import jpashop.jpashop.domain.Item.Item;
import jpashop.jpashop.domain.Member;
import jpashop.jpashop.domain.Order.Order;
import jpashop.jpashop.domain.Order.OrderItem;
import jpashop.jpashop.domain.Order.OrderStatus;
import jpashop.jpashop.exception.NotEnoughStockException;
import org.assertj.core.api.NotThrownAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {
    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //GIVEN
        Member member = CreateMember("하준", new Address("부산광역시", "사하구", "낙동남로1357번길"));
        Item item = CreateBook("시골 JPA", 10000, 10);
        int orderCount = 2;
        //WHEN
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        //THEN
        Order one = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, one.getStatus(), "주문상태");
        assertEquals(one.getOrderItems().size(), 1, "상품 종류 수");
        assertEquals(one.getTotalPrice(), 10000 * 2, "가격");
        assertEquals(item.getStockQuantity(), 8, "재고수량");
    }
    @Test
    public void 재고수량_초과() throws Exception{
        //GIVEN
        Member member = CreateMember("하준", new Address("부산광역시", "사하구", "낙동남로1357번길"));
        Item item = CreateBook("시골 JPA", 10000, 10);
        int orderCount=11;
        //THEN
        assertThrows(NotEnoughStockException.class,()->{
            System.out.println("재고수량을 초과했습니다");
            Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        });
    }
    @Test
    public void 주문취소() throws Exception{
        //GIVEN
        Member member = CreateMember("하준", new Address("부산광역시", "사하구", "낙동남로1357번길"));
        Item item = CreateBook("시골 JPA", 10000, 10);
        int orderCount=5;
        //WHEN
        Long order = orderService.order(member.getId(), item.getId(), orderCount);
        Order findOrder = em.find(Order.class, order);
        assertEquals(findOrder.getStatus(),OrderStatus.ORDER);
        assertEquals(5,item.getStockQuantity());
        orderService.cancelOrder(order);
        //THEN
        Order findOrder2 = em.find(Order.class, order);
        assertEquals(findOrder2.getStatus(),OrderStatus.CENCEL);
        assertEquals(10,item.getStockQuantity());
    }

    public Member CreateMember(String name,Address address){
        Member member = new Member();
        member.setName("하준");
        member.setAddress(address);
        em.persist(member);
        return member;
    }
    public Book CreateBook(String name,int price,int stockQuantity){
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
}