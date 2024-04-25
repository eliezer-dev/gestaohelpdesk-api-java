package dev.eliezer.superticket.domain.repository;

import dev.eliezer.superticket.domain.model.Ticket;
import dev.eliezer.superticket.domain.model.TicketAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM tb_tickets t JOIN t.user u WHERE u.id = :userId")
    List<Ticket> findByUserId(Long userId);
    @Query("SELECT t FROM tb_tickets t JOIN t.user u WHERE t.id = :id AND u.id = :userId")
    Optional<Ticket> findByIdAndUserId(Long id, Long userId);
    @Query("SELECT t FROM tb_tickets t JOIN t.user u JOIN t.client c WHERE u.id = :userId AND LOWER(c.razaoSocialName) LIKE LOWER(concat('%', :razaoSocialName, '%'))")
    List<Ticket> findByUserIdAndClientRazaoSocialName(Long userId, String razaoSocialName);
    @Query("SELECT t FROM tb_tickets t JOIN t.client c JOIN t.user u WHERE u.id = :userId AND c.cpfCnpj LIKE %:cpfCnpj%")
    List<Ticket> findByUserIdAndCpfCnpj(Long userId, String cpfCnpj);

    @Query("SELECT t FROM tb_tickets t JOIN t.user u WHERE u.id != :userId")
    List<Ticket> findByNotUserId(Long userId);
    @Query("SELECT t FROM tb_tickets t JOIN t.user u WHERE u.id != :userId AND t.id = :id")
    Optional<Ticket> findByNotUserIdAndId(Long userId, Long id);
    @Query("SELECT t FROM tb_tickets t JOIN t.user u JOIN t.client c WHERE u.id != :userId AND LOWER(c.razaoSocialName) LIKE LOWER(concat('%', :razaoSocialName, '%'))")
    List<Ticket> findByNotUserIdAndClientRazaoSocialName(Long userId, String razaoSocialName);
    @Query("SELECT t FROM tb_tickets t JOIN t.client c JOIN t.user u WHERE u.id != :userId AND c.cpfCnpj LIKE %:cpfCnpj%")
    List<Ticket> findByNotUserIdAndCpfCnpj(Long userId, String cpfCnpj);


    @Query("SELECT t FROM tb_tickets t WHERE t.user IS EMPTY")
    List<Ticket> findByTicketsWithoutUser();
    @Query("SELECT t FROM tb_tickets t WHERE t.id = :id AND t.user IS EMPTY")
    Optional<Ticket> findByIdAndTicketsWithoutUser(Long id);
    @Query("SELECT t FROM tb_tickets t JOIN t.client c WHERE t.user IS EMPTY AND LOWER(c.razaoSocialName) LIKE LOWER(concat('%', :razaoSocialName, '%'))")
    List<Ticket> findByTicketsWithoutUserAndClientRazaoSocialName(String razaoSocialName);
    @Query("SELECT t FROM tb_tickets t JOIN t.client c WHERE t.user is EMPTY AND c.cpfCnpj LIKE %:cpfCnpj%")
    List<Ticket> findByTicketsWithoutUserAndCpfCnpj(String cpfCnpj);

    @Query("SELECT t FROM tb_tickets t JOIN t.client c WHERE LOWER(c.razaoSocialName) LIKE LOWER(concat('%', :razaoSocialName, '%'))")
    List<Ticket> findByClientRazaoSocialName(String razaoSocialName);
    @Query("SELECT t FROM tb_tickets t JOIN t.client c WHERE c.cpfCnpj LIKE %:cpfCnpj%")
    List<Ticket> findByCpfCnpj(String cpfCnpj);
}
