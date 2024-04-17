package ru.practicum.event.model.event;

import lombok.*;
import ru.practicum.category.model.Category;
import ru.practicum.event.model.location.Location;
import ru.practicum.user.model.User;
import ru.practicum.utils.enums.State;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

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

    @Column(name = "title", nullable = false, length = 120)
    private String title;

    @Size(min = 20, max = 7000)
    @Column(name = "description")
    private String description;

    @Column(name = "event_date", nullable = false)
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
    private LocalDateTime publishedOn;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @Column(name = "views")
    private Long views;

}
