package ru.practicum.mainservice.event.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.event.model.location.Location;
import ru.practicum.mainservice.user.model.User;
import ru.practicum.mainservice.utils.enums.State;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.mainservice.event.model.event.EventMapper.DATE_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User initiator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Location location;

    @Size(min = 20, max = 2000)
    @Column(name = "annotation", nullable = false)
    private String annotation;

    @Column(name = "title", nullable = false, length = 127)
    private String title;

    @Size(min = 20, max = 7000)
    @Column(name = "description")
    private String description;

    @Column(name = "event_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private LocalDateTime eventDate;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "participant_limit")
    private Long participantLimit;

    @JoinColumn(name = "confirmed_requests")
    private Long confirmedRequests;

    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Column(name = "published_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private LocalDateTime publishedOn;

    @Column(name = "created_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private LocalDateTime createdOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @Column(name = "views")
    private Long views;

}
