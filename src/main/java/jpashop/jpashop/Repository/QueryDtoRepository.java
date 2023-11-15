package jpashop.jpashop.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class QueryDtoRepository {
    public final EntityManager em;
    public List<OrderSimpleQueryDto> findQueryDTO(){
        return em.createQuery("select new jpashop.jpashop.Repository.OrderSimpleQueryDto(o.id,m.name,o.orderDate,o.status,d.address)" +
                " from Order o join o.member m join o.delivery d", OrderSimpleQueryDto.class).getResultList();
    }
    // DTO에 맞춰서 가져온것이기 때문에 재사용 어려움 , 논리적 계층 깨짐 -> 성능 최적화 잘됨 ( 패치조인과 트레이드 오프 BUT 패치조인과 성능차이가 별로 안남) -> 트래픽이 매우 많으면 고민해서 사용
    // -> 이때 사용할려면 성능 최적화된 리포지토리 새로 만들어서 사용 ex) OrderSimpleQueryRepository
    //fetch join을 사용하는 이유는 엔티티 상태에서 엔티티 그래프를 참조하기 위해서 사용한다.
    //DTO는 Entity가 아니기 떄문에 연관관계가  존재하지X 그렇기에 실제 필요한 컬럼들만 조회해야 할때에는 일반적인 조인

}
