package dev.eliezer.gestaohelpdesk.modules.client.useCases;

import dev.eliezer.gestaohelpdesk.modules.client.entities.Client;
import dev.eliezer.gestaohelpdesk.modules.client.repositories.ClientRepository;
import dev.eliezer.gestaohelpdesk.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindClientByIdUseCase {

    @Autowired
    private ClientRepository clientRepository;

    public Client execute(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
}
