package jpashop.jpashop.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jdk.jfr.Enabled;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;
}

//값 타입은 변경 불가능하게 설계
// -> setter와 기본생성자를 막아둔다
// @NoArgsConstructor(access = AccessLevel.PROTECTED)
// @AllArgsConstructor(access = AccessLevel.PUBLIC)
// 위 2줄 추가
// 빈 생성자가 무조건 필요한 이유 -> JPA 구현 라이브러리가 객체 생성시
// 리플랙션과 같은기술을 사용할수 있도록지원
