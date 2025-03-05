package iuh.fit.se.userservice.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import iuh.fit.se.userservice.entities.enumeration.Gender;
import iuh.fit.se.userservice.entities.enumeration.SystemUserLevel;
import iuh.fit.se.userservice.entities.enumeration.SystemUserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "System_Users")
public class SystemUser {

    @Id
    @UuidGenerator
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SystemUserStatus systemUserStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SystemUserLevel level;

    @OneToOne
    @JoinColumn(name = "account_id", unique = true, nullable = false)
    @JsonManagedReference(value = "account-systemUser")
    private Account account;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 255)
    private String address;

    @Column(length = 50)
    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 255)
    private String avatar;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
