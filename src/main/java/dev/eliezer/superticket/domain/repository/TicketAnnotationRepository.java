package dev.eliezer.superticket.domain.repository;

import dev.eliezer.superticket.domain.model.Ticket;
import dev.eliezer.superticket.domain.model.TicketAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketAnnotationRepository extends JpaRepository<TicketAnnotation, Long> {
    List<TicketAnnotation> findByTicketId(Long id);

}
