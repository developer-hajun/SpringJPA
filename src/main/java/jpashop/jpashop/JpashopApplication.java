package jpashop.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.hibernate.Hibernate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}
	@Bean
	Hibernate5JakartaModule hibernate5Module() {
//		return new Hibernate5JakartaModule(); //초기화된 프록시 객체만 노출
		Hibernate5JakartaModule hibernate5Module = new Hibernate5JakartaModule();
		hibernate5Module.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING, true);
		//강제 지연 로딩 설정 -> 양방향 연관관계를 계속 로딩 -> JsonIgnore로 끊어줘야함
		//-> 이것보다 좋은 방법은 DTO로 변환해서 사용하는것이 더 좋다
		//-> 지연로딩을 피하기위해 즉시 로딩은 금지
		return hibernate5Module;
	}
}
