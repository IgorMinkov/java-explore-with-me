package ru.practicum.comments.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.event.model.event.Event;
import ru.practicum.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.Utils.DATE_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Event event;

    @Size(min = 20, max = 500)
    @Column(name = "message", nullable = false)
    String message;

    @Column(name = "created")
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime created;

}
