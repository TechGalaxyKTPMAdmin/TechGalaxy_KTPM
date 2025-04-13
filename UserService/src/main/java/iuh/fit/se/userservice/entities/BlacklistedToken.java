package iuh.fit.se.userservice.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blacklisted_tokens")
public class BlacklistedToken {

    @Id
    @UuidGenerator
    private String id;

    @Column(nullable = false, unique = true, columnDefinition = "NVARCHAR(1000)")
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;
}