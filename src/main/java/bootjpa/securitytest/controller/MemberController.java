package bootjpa.securitytest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import bootjpa.securitytest.dto.MemberDto;
import bootjpa.securitytest.service.MemberService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MemberController {

    private MemberService memberService;

    // 메인페이지
    @GetMapping("/")
    public String index() {
        return "/index";
    }

    // 회원가입페이지
    @GetMapping("/user/signup")
    public String signUp(){
        return "/signup";
    }

    // 회원가입 처리
    @PostMapping("/user/signup")
    public String singUpImpl(MemberDto memberDto) {

        memberService.joinUser(memberDto);

        return "redirect:/user/login";

    }

    // 로그인 페이지
    @GetMapping("/user/login")
    public String login(){
        return "/login";
    }

    // 로그인 결과 페이지
    @GetMapping("/user/login/result")
    public String loginResult() {
        return "/loginSuccess";
    }

    // 로그아웃 결과 페이지
    @GetMapping("/user/logout/result")
    public String logout(){
        return "/logout";
    }

    // 접근 거부 페이지
    @GetMapping("/user/denied")
    public String denide(){
        return "/denide";
    }

    // 내 정보 페이지
    @GetMapping("/user/info")
    public String myInfo(){
        return "/myinfo";
    }

    // 관리자 페이지
    @GetMapping("/admin")
    public String admin(){
        return "/admin";
    }
    
}
