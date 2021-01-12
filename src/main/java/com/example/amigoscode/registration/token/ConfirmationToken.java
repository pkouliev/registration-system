package com.example.amigoscode.registration.token;

import com.example.amigoscode.appuser.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;

    private String confirmationToken;

    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    private LocalDateTime confirmedAt;


    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id")
    private AppUser appUser;

    public ConfirmationToken(String confirmationToken,
                             LocalDateTime createdAt,
                             LocalDateTime expiredAt,
                             AppUser appUser) {
        this.confirmationToken = confirmationToken;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.appUser = appUser;
    }
}
