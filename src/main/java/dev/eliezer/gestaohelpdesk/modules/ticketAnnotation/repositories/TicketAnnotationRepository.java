package dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.repositories;

import dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.entities.TicketAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketAnnotationRepository extends JpaRepository<TicketAnnotation, Long> {
    List<TicketAnnotation> findByTicketId(Long id);

}
