package com.bookstore.admin.domain.security;

import com.bookstore.admin.domain.User;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.StringJoiner;

@Entity
public class PasswordResetToken {

    private static final int EXPIRATION_TIME_IN_MINUTES = 60*24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    private Date expiryTime;

    public PasswordResetToken() {
    }

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryTime = calculateExpiryDate(getExpirationTimeInMinutes());
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void updatePasswordResetTokenTime(String token){
        this.token = token;
        this.expiryTime = calculateExpiryDate(getExpirationTimeInMinutes());
    }

    private static int getExpirationTimeInMinutes() {
        return EXPIRATION_TIME_IN_MINUTES;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PasswordResetToken.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("token='" + token + "'")
                .add("user=" + user)
                .add("expiryTime=" + expiryTime)
                .toString();
    }
}
