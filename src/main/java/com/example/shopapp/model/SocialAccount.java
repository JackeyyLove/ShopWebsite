package com.example.shopapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "social_accounts")
@Data // toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider", nullable = false, length = 20)
    private Long provider;

    @Column(name = "provider_id", nullable = false, length = 50)
    private Long providerId;
    @Column(name = "email",  length = 150)
    private String email;
}
