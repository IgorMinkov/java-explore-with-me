package ru.practicum.server.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hits")
public class Hit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app", nullable = false)
    @EqualsAndHashCode.Exclude
    private String app;

    @Column(name = "uri", nullable = false)
    @EqualsAndHashCode.Exclude
    private String uri;

    @Column(name = "ip", nullable = false)
    @EqualsAndHashCode.Exclude
    private String ip;

    @Column(name = "time_stamp", nullable = false)
    private LocalDateTime timestamp;

}
