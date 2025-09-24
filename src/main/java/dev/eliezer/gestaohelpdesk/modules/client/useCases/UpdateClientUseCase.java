package dev.eliezer.gestaohelpdesk.modules.client.useCases;

import dev.eliezer.gestaohelpdesk.modules.client.entities.Client;
import dev.eliezer.gestaohelpdesk.modules.client.repositories.ClientRepository;
import dev.eliezer.gestaohelpdesk.service.exception.BusinessException;
import dev.eliezer.gestaohelpdesk.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateClientUseCase {

    @Autowired
    private ClientRepository clientRepository;

    public Client execute(Long id, Client client) {
        Client clientToChange =  clientRepository.findById(id).orElseThrow(() -> new NotFoundException(id));

        Optional<Client> clientFound = clientRepository.findByCpfCnpj(client.getCpfCnpj());
        if(clientFound.isPresent() && clientFound.get().getId() != id) {
            clientFound = null;
            throw new BusinessException("[Cpf_Cnpj] " + client.getCpfCnpj() + " já foi utilizado em outro cadastro.");
        }

        clientToChange.setCpfCnpj(client.getCpfCnpj());
        clientToChange.setRazaoSocialName(client.getRazaoSocialName());
        clientToChange.setBusinessName(client.getBusinessName());
        clientToChange.setCep(client.getCep());
        clientToChange.setAddress(client.getAddress());
        clientToChange.setAddressNumber(client.getAddressNumber());
        clientToChange.setAddressNumber2(client.getAddressNumber2());
        clientToChange.setNeighborhood(client.getNeighborhood());
        clientToChange.setState(client.getState());
        clientToChange.setCity(client.getCity());
        clientToChange.setEmail(client.getEmail());
        clientToChange.setSlaDefault(client.getSlaDefault());
        clientToChange.setSlaUrgency(client.getSlaUrgency());


        return clientRepository.save(clientToChange);
    }
}
