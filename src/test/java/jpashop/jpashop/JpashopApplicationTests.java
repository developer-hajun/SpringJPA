package jpashop.jpashop;

import jpashop.jpashop.Repository.MemberRepository;
import jpashop.jpashop.domain.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class JpashopApplicationTests {

	@Autowired
	MemberRepository memberRepository;
	@Test
	void contextLoads() {
	}
}
