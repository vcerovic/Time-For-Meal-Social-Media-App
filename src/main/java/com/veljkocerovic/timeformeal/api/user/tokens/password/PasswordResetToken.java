package com.veljkocerovic.timeformeal.api.user.tokens.password;

import com.veljkocerovic.timeformeal.api.user.appuser.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {
    private static final int EXPIRATION_TIME = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Integer id;
    private String token;

    @Column(name = "expiration_time")
    private Date expirationTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_PASSWORD_RESET_TOKEN"))
    private AppUser appUser;

    public PasswordResetToken(AppUser appUser, String token) {
        super();
        this.token = token;
        this.appUser = appUser;
        this.expirationTime = calculateExpirationDate();
    }

    private Date calculateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
}
