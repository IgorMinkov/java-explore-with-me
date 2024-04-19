package ru.practicum.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static ru.practicum.Utils.DATE_FORMAT;

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

    @Column(name = "ip", nullable = false, length = 63)
    @EqualsAndHashCode.Exclude
    private String ip;

    @Column(name = "time_stamp", nullable = false)
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime timestamp;

}
