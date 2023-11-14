package jpashop.jpashop.Controller.Member;

import jakarta.validation.Valid;
import jpashop.jpashop.Service.MemberService;
import jpashop.jpashop.domain.Address;
import jpashop.jpashop.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String MemberForm(Model model){
        model.addAttribute("Form",new MemberForm());
        return "members/createMemberForm";
    }
    @PostMapping("/members/new")
    public String CreateMember(@Valid MemberForm form , BindingResult result){
        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(),form.getZipcode());
        Member member = new Member(form.getName(),address);
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String memberList(Model model){
        List<Member> memberList = memberService.findMembers();
        model.addAttribute("memberList",memberList);
        return "/members/memberList";
    }

}
