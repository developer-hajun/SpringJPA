package jpashop.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jpashop.jpashop.domain.Order.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    //엔티티는 타입으로 구분 but 테이블은 타입이X -> 이름으로 구분 
    //->@Column을 통해 이름 지정 -> 구분 
    private String name;
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();
    //member가 연관 관계 주인 -> member에 있는 Order가 빠지면 member가 아니라 order가 업데이트 됨
    //order의 외래키값이 변경 -> 별도의 업데이트 쿼리발생(성능 저하),유지보수성 저하

    public Member(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}
