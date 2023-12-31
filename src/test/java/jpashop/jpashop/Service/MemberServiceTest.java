package jpashop.jpashop.Service;

import jpashop.jpashop.Repository.MemberRepository;
import jpashop.jpashop.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        Member member = new Member();
        member.setName("하준");
        Long join = memberService.join(member);
        Assertions.assertEquals(member,memberRepository.findOne(join));
    }
    @Test
    public void 중복_회원_예외() throws  Exception{
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member1);

        assertThrows(IllegalStateException.class,()->{
            memberService.join(member2);//예외발생
        });
    }

}