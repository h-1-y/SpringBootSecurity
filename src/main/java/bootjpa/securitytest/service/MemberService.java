package bootjpa.securitytest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bootjpa.securitytest.domain.Role;
import bootjpa.securitytest.dto.MemberDto;
import bootjpa.securitytest.entity.Member;
import bootjpa.securitytest.repository.MemberRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private MemberRepository memberRepository;

    @Transactional // 회원가입을 처리하는 메서드!! 비밀번호를 암호화하여 저장!
    public Long joinUser(MemberDto memberDto) { 

        // 비밀번호 암호화 작업!
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        return memberRepository.save(memberDto.toEntity()).getId();

    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Member> userEntityWrapper = memberRepository.findByEmail(email);
        Member member = userEntityWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (("admin@example.com").equals(email)) {

            // authorities.add(new SimpleGrantedAuthority());
            // Role = 권한을 부여하는 코드!! 
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));

        } else {

            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));

        }

        // return은 SpringSecurity에서 제공하는 UserDetails를 구현한 User를 반환해야한다!! (import org.springframework.security.core.userdetails.User)
        return new User(member.getEmail(), member.getPassword(), authorities);
        
    }
    
}
