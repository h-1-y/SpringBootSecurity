package bootjpa.securitytest.dto;

import java.time.LocalDateTime;

import bootjpa.securitytest.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {

    private Long id;
    private String email;
    private String password;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public Member toEntity(){

        return Member.builder()
                     .id(id)
                     .email(email)
                     .password(password)
                     .build();

    }

    @Builder
    public MemberDto(Long id, String email, String password) {

        this.id = id;
        this.email = email;
        this.password = password;

    }
    
}
