package bootjpa.securitytest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bootjpa.securitytest.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    // Email을 Where 조건절로 하여, 데이터를 가져올 수 있도록 findByEmail() 메서드를 정의 !!
    
}
