package ru.practicum.mainservice.event.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.event.model.event.Event;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByInitiatorId(Long initiatorId, PageRequest pageRequest);

    List<Event> findByCategoryId(Long categoryId);

    Event findByIdAndInitiatorId(Long eventId, Long initiatorId);

}
