package dev.eliezer.superticket.modules.client.useCases;

import dev.eliezer.superticket.modules.client.entities.Client;
import dev.eliezer.superticket.modules.client.repositories.ClientRepository;
import dev.eliezer.superticket.service.exception.BusinessException;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteClientUseCase {

    @Autowired
    private ClientRepository clientRepository;

    public void execute(Long id) {
        Client clientToDelete = clientRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        clientRepository.delete(clientToDelete);
    }

}
