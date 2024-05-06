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
    @Query("SELECT t FROM tb_tickets t JOIN t.user u WHERE u.id = :userId AND t.status.type NOT IN (3, 5) ORDER BY t.status.type, t.id" )
    List<Ticket> findByUserIdAndNotStatusTypeClosed(Long userId);
    @Query("SELECT t FROM tb_tickets t JOIN t.user u WHERE CAST(t.id AS string) LIKE CONCAT(:id, '%') AND u.id = :userId AND t.status.type NOT IN (3, 5) ORDER BY t.status.type, t.id")
    List<Ticket> findByIdAndUserIdAndNotStatusTypeClosed(String id, Long userId);
    @Query("SELECT t FROM tb_tickets t JOIN t.user u JOIN t.client c WHERE u.id = :userId AND LOWER(c.razaoSocialName) LIKE LOWER(concat('%', :razaoSocialName, '%')) AND t.status.type NOT IN (3, 5) ORDER BY t.status.type, t.id")
    List<Ticket> findByUserIdAndClientRazaoSocialNameAndNotStatusTypeClosed(Long userId, String razaoSocialName);
    @Query("SELECT t FROM tb_tickets t JOIN t.client c JOIN t.user u WHERE u.id = :userId AND c.cpfCnpj LIKE %:cpfCnpj% AND t.status.type NOT IN (3, 5) ORDER BY t.status.type, t.id")
    List<Ticket> findByUserIdAndCpfCnpjAndNotStatusTypeClosed(Long userId, String cpfCnpj);

    @Query("SELECT t FROM tb_tickets t JOIN t.user u WHERE u.id != :userId AND t.status.type NOT IN (3, 5) ORDER BY t.status.type, t.id")
    List<Ticket> findByNotUserIdAndNotStatusTypeClosed(Long userId);
    @Query("SELECT t FROM tb_tickets t JOIN t.user u WHERE u.id != :userId AND CAST(t.id AS string) LIKE CONCAT(:id, '%') AND t.status.type NOT IN (3, 5) ORDER BY t.status.type, t.id")
    List<Ticket> findByNotUserIdAndIdAndNotStatusTypeClosed(Long userId, String id);
    @Query("SELECT t FROM tb_tickets t JOIN t.user u JOIN t.client c WHERE u.id != :userId AND LOWER(c.razaoSocialName) LIKE LOWER(concat('%', :razaoSocialName, '%')) AND t.status.type NOT IN (3, 5) ORDER BY t.status.type, t.id")
    List<Ticket> findByNotUserIdAndClientRazaoSocialNameAndNotStatusTypeClosed(Long userId, String razaoSocialName);
    @Query("SELECT t FROM tb_tickets t JOIN t.client c JOIN t.user u WHERE u.id != :userId AND c.cpfCnpj LIKE %:cpfCnpj% AND t.status.type NOT IN (3, 5) ORDER BY t.status.type, t.id")
    List<Ticket> findByNotUserIdAndCpfCnpjAndNotStatusTypeClosed(Long userId, String cpfCnpj);


    @Query("SELECT t FROM tb_tickets t WHERE t.user IS EMPTY AND t.status.type NOT IN (3, 5) ORDER BY t.status.type, t.id")
    List<Ticket> findByTicketsWithoutUserAndNotStatusTypeClosed();
    @Query("SELECT t FROM tb_tickets t WHERE CAST(t.id AS string) LIKE CONCAT(:id, '%') AND t.user IS EMPTY AND t.status.type NOT IN (3, 5) ORDER BY t.status.type, t.id ")
    List<Ticket> findByIdAndTicketsWithoutUserAndNotStatusTypeClosed(String id);
    @Query("SELECT t FROM tb_tickets t JOIN t.client c WHERE t.user IS EMPTY AND LOWER(c.razaoSocialName) LIKE LOWER(concat('%', :razaoSocialName, '%')) AND t.status.type NOT IN (3, 5) ORDER BY t.status.type, t.id")
    List<Ticket> findByTicketsWithoutUserAndClientRazaoSocialNameAndNotStatusTypeClosed(String razaoSocialName);
    @Query("SELECT t FROM tb_tickets t JOIN t.client c WHERE t.user is EMPTY AND c.cpfCnpj LIKE %:cpfCnpj% AND t.status.type NOT IN (3, 5) ORDER BY t.status.type, t.id")
    List<Ticket> findByTicketsWithoutUserAndCpfCnpjAndNotStatusTypeClosed(String cpfCnpj);

    @Query("SELECT t FROM tb_tickets t JOIN t.client c WHERE LOWER(c.razaoSocialName) LIKE LOWER(concat('%', :razaoSocialName, '%')) AND t.status.type NOT IN (3, 5) ORDER BY t.status.type, t.id")
    List<Ticket> findByClientRazaoSocialNameAndNotStatusTypeClosed(String razaoSocialName);
    @Query("SELECT t FROM tb_tickets t JOIN t.client c WHERE c.cpfCnpj LIKE %:cpfCnpj% AND t.status.type NOT IN (3, 5) ORDER BY t.status.type, t.id")
    List<Ticket> findByCpfCnpjAndNotStatusTypeClosed(String cpfCnpj);

    @Query("SELECT t FROM tb_tickets t WHERE t.status.type NOT IN (3, 5) ORDER BY t.status.type, t.id")
    List<Ticket> findAllByStatusTypeNotClosed();

    @Query("SELECT t FROM tb_tickets t WHERE CAST(t.id AS string) LIKE CONCAT(:id, '%') AND t.status.type NOT IN (3, 5) ORDER BY t.status.type, t.id")//id funcionando com prefixo na pesquisa **/
    List<Ticket> findByIdStartingWithAndNotStatusTypeClosed(@Param("id") String id);



    @Query("SELECT t FROM tb_tickets t WHERE t.status.type = 2 AND CAST(t.id AS string) LIKE CONCAT(:id, '%') ORDER BY t.id" )
    List<Ticket> findAllCompletedTicketsById(String id);
    @Query("SELECT t FROM tb_tickets t JOIN t.client c WHERE t.status.type = 2 AND LOWER(c.razaoSocialName) LIKE LOWER(concat('%', :razaoSocialName, '%')) ORDER BY t.id")
    List<Ticket> findAllCompletedTicketsByClientRazaoSocialName(String razaoSocialName);
    @Query("SELECT t FROM tb_tickets t JOIN t.client c WHERE t.status.type = 2 AND c.cpfCnpj LIKE %:cpfCnpj% ORDER BY t.id")
    List<Ticket> findAllCompletedTicketsByCpfCnpj(String cpfCnpj);
    @Query("SELECT t FROM tb_tickets t WHERE t.status.type = 2 ORDER BY t.id")
    List<Ticket> findAllCompletedTickets();

    @Query("SELECT t FROM tb_tickets t WHERE t.status.type = 3 AND CAST(t.id AS string) LIKE CONCAT(:id, '%') ORDER BY t.id" )
    List<Ticket> findAllClosedTicketsById(String id);
    @Query("SELECT t FROM tb_tickets t JOIN t.client c WHERE t.status.type = 3 AND LOWER(c.razaoSocialName) LIKE LOWER(concat('%', :razaoSocialName, '%')) ORDER BY t.id")
    List<Ticket> findAllClosedTicketsByClientRazaoSocialName(String razaoSocialName);
    @Query("SELECT t FROM tb_tickets t JOIN t.client c WHERE t.status.type = 3 AND c.cpfCnpj LIKE %:cpfCnpj% ORDER BY t.id")
    List<Ticket> findAllClosedTicketsByCpfCnpj(String cpfCnpj);
    @Query("SELECT t FROM tb_tickets t WHERE t.status.type = 3 ORDER BY t.id")
    List<Ticket> findAllClosedTickets();



}

