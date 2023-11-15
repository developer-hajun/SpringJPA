package jpashop.jpashop.api;

import jakarta.validation.Valid;
import jpashop.jpashop.Service.MemberService;
import jpashop.jpashop.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long join = memberService.join(member);
        return new CreateMemberResponse(join);
    }
    //문제점 - 엔티티에 프레젠테이션 계층을 위한 로직 추가
    //      -> 즉 엔티티에 API검증 로직 추가
    //      -> 엔티티는 비즈니스 로직만 들어가야한다 그렇기에 DTO롷 받아얗마
    //      - 엔티티가 변경 -> API 스펙 변경 -> DTO로 파라미터를 받아야함
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest createMemberRequest){
        Member member = new Member();
        member.setName(createMemberRequest.getName());
        Long join = memberService.join(member);
        return new CreateMemberResponse(join);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMember(@PathVariable("id") Long id,@RequestBody @Valid UpdateMemberRequest request){
        Member findMember = memberService.update(id,request.getName());
        return new UpdateMemberResponse(id, findMember.getName());
    }

    @GetMapping ("/api/v1/members")
    public Result findMember(){
        List<Member> members = memberService.findMembers();
        List<MemberDTO> collect = members.stream().map(m -> new MemberDTO(m.getId(), m.getName())).collect(Collectors.toList());
        int size = collect.size();
        return new Result(collect,size);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{ //제네릭 클래스  ->  클래스내의 모든  타입을 T로 처리가능
        private T data;
        private T length;
    }
    @Data
    @AllArgsConstructor
    static class MemberDTO{
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest{
        private String name;
    }
    @Data
    static class UpdateMemberRequest {
        private String name;
    }
    @Data
    static class CreateMemberResponse {
        private Long id;
        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
    @Data
    static class UpdateMemberResponse{
        private Long id;
        private String name;
        public UpdateMemberResponse(Long id,String name){
            this.id = id;
            this.name =name;
        }
    }
}