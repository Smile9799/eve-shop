package com.eve_coding.eveshop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Token {

    @Column(unique = true,updatable = false,nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;
    @Column(updatable = false,nullable = false)
    private LocalDateTime expiredAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @Column(unique = true,updatable = false,nullable = false)
    private String tokenStr;
    @ManyToOne
    private User user;

    public Token(User user, String tokenStr){
        this.createdAt = LocalDateTime.now();
        this.expiredAt = LocalDateTime.now().plusDays(3);
        this.tokenStr = tokenStr;
        this.user = user;
    }
}
