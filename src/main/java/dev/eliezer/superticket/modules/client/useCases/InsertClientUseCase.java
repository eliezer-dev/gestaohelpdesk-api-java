package dev.eliezer.superticket.modules.client.useCases;

import dev.eliezer.superticket.modules.client.entities.Client;
import dev.eliezer.superticket.modules.client.repositories.ClientRepository;
import dev.eliezer.superticket.service.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InsertClientUseCase {

    @Autowired
    private ClientRepository clientRepository;

    public Client execute(Client client) {
        Optional<Client> clientFound = clientRepository.findByCpfCnpj(client.getCpfCnpj());
        if(clientFound.isPresent()) {
            throw new BusinessException("[cpfCnpj] " + client.getCpfCnpj() + " já foi utilizado em outro cadastro.");
        }
        return clientRepository.save(client);
    }
}
