package dev.eliezer.superticket.domain.repository;

import dev.eliezer.superticket.domain.model.Ticket;
import dev.eliezer.superticket.domain.model.TicketAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM tb_tickets t JOIN t.user u WHERE u.id = :userId")
    List<Ticket> findByUserId(Long userId);

    @Query("SELECT t FROM tb_tickets t JOIN t.user u WHERE u.id != :userId")
    List<Ticket> findByNotUserId(Long userId);

    @Query("SELECT t FROM tb_tickets t WHERE t.user IS EMPTY")
    List<Ticket> findTicketsWithoutUser();
}
