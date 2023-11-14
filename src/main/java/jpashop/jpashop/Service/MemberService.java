package jpashop.jpashop.Service;

import jpashop.jpashop.Repository.MemberRepository;
import jpashop.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final만 생성자로 만들어줌
public class MemberService {
    //@Autowired MemberRepository memberRepository;
    //필드 주입 단점 : memberRepository 변경이 안됨
    
    private final MemberRepository memberRepository;
//    public MemberService(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//    }//생성자 주입 -> @RequiredArgsConstructor로 데체

    //@Autowired 는 자동으로 생성,final 넣어주면 들어갓는지 안들어갓는지 체크 가능


    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member){
        List<Member> byName = memberRepository.findByName(member.getName());
        if(!byName.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }
}
