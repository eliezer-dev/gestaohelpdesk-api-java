package dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.mappers;

import dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.dtos.TicketAnnotationRequestDTO;
import dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.dtos.TicketAnnotationResponseDTO;
import dev.eliezer.gestaohelpdesk.modules.tickets.dtos.UserForTicketResponseDTO;
import dev.eliezer.gestaohelpdesk.modules.tickets.entities.Ticket;
import dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.entities.TicketAnnotation;
import dev.eliezer.gestaohelpdesk.modules.user.entities.User;
import dev.eliezer.gestaohelpdesk.modules.userAvatar.entities.UserPicture;
import dev.eliezer.gestaohelpdesk.modules.userAvatar.repositories.UserPictureRepository;
import dev.eliezer.gestaohelpdesk.modules.user.repositories.UserRepository;
import dev.eliezer.gestaohelpdesk.providers.ImageUtil;
import dev.eliezer.gestaohelpdesk.service.exception.BusinessException;
import dev.eliezer.gestaohelpdesk.service.exception.NotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Data
@Service
public class TicketAnnotationMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPictureRepository userPictureRepository;

    public TicketAnnotation formatTicketAnnotationRequestDTOForTicketAnnotation (TicketAnnotationRequestDTO ticketAnnotationRequestDTO) {

        TicketAnnotation ticketAnnotation = new TicketAnnotation();

        Ticket ticket = new Ticket();
        ticket.setId(ticketAnnotationRequestDTO.getTicketId());

        User user = new User();
        user.setId(ticketAnnotationRequestDTO.getUserId());

        ticketAnnotation.setTicket(ticket);
        ticketAnnotation.setUser(user);
        ticketAnnotation.setDescription(ticketAnnotationRequestDTO.getDescription());

        return ticketAnnotation;
    }

    public TicketAnnotationResponseDTO formatTicketAnnotationForTicketAnnotationResponseDTO(TicketAnnotation ticketAnnotation) {
        var user = userRepository.findById(ticketAnnotation.getUser().getId()).orElseThrow(() -> new NotFoundException(ticketAnnotation.getId()));
        String avatar = null;
        if (!(user.getIdPicture() == null)) {
            UserPicture userPicture = userPictureRepository.findById(user.getIdPicture())
                    .orElseThrow(() -> new BusinessException("image not found."));

            byte[] image = ImageUtil.decompressImage(userPicture.getImageData());

            avatar = Base64.getEncoder().encodeToString(image);

        }

        UserForTicketResponseDTO userForTicketResponseDTO = new UserForTicketResponseDTO();
        userForTicketResponseDTO.setId(user.getId());
        userForTicketResponseDTO.setName(user.getName());
        userForTicketResponseDTO.setAvatar(avatar);

        TicketAnnotationResponseDTO ticketAnnotationResponseDTO = TicketAnnotationResponseDTO.builder()
                .id(ticketAnnotation.getId())
                .ticketId(ticketAnnotation.getTicket().getId())
                .user(userForTicketResponseDTO)
                .description(ticketAnnotation.getDescription())
                .createAt(ticketAnnotation.getCreateAt())
                .updateAt(ticketAnnotation.getUpdateAt())
                .build();
        return ticketAnnotationResponseDTO;
    }
}
