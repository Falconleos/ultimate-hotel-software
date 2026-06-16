package ar.edu.utn.frmdp.ultimate_hotel_software.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", length = 36, unique = true, nullable = false, updatable = false)
    private UUID id;

    @Setter
    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private Instant expiresAt;

    @Builder.Default
    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @Setter
    @Column
    private Instant usedAt;

    @Setter
    @Builder.Default
    @Column(nullable = false)
    private boolean revoked = false;

}
