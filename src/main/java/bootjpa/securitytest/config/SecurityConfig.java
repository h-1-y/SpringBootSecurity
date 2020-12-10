package bootjpa.securitytest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import bootjpa.securitytest.service.MemberService;
import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity // @Configuration 클래스에 @EnableWebSecurity 어노테이션을 추가하여 Spring Security 설정할 클래스라고 정의
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private MemberService memberService;

    @Bean // Service에서 비밀번호를 암호화할 수 있도록 Bean 으로 등록!
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 객체 !!
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        // static 디렉토리의 하위 파일 목록은 인증 무시하도록 설정!! ( = 항상 통과 )
        web.ignoring().antMatchers("/css/**","/js/**","/img/**","/lib/**");

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                //페이지 권한설정
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/myinfo").hasRole("MEMBER")
                .antMatchers("/**").permitAll()
            .and()
                // 로그인 설정
                .formLogin()
                .loginPage("/user/login") // 로그인 실행 주소
                .defaultSuccessUrl("/user/login/result") // 로그인 성공시 이동 주소
                .permitAll()
            .and()
                //로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 로그아웃을 실행할 주소
                .logoutSuccessUrl("/user/logout/result") // 로그 아웃 성공시 이동 url
                .invalidateHttpSession(true) // 로그 아웃시 인증정보를 지우하고 세션을 무효화
            .and()
                // 403 예외처리 핸들링
                .exceptionHandling().accessDeniedPage("/user/denied");

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());

    }
    
}
