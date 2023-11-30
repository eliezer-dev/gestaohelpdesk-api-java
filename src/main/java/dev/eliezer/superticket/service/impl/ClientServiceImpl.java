package dev.eliezer.superticket.service.impl;

import dev.eliezer.superticket.domain.model.Client;
import dev.eliezer.superticket.domain.model.User;
import dev.eliezer.superticket.domain.repository.ClientRepository;
import dev.eliezer.superticket.service.ClientService;
import dev.eliezer.superticket.service.exception.BusinessException;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    private ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }
    @Override
    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public Client insert(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client update(Long id, Client client) {
        Client clientToChange =  clientRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        clientToChange.setCpfOrCnpj(client.getCpfOrCnpj());
        clientToChange.setRazaoSocialOrName(client.getRazaoSocialOrName());
        clientToChange.setCep(client.getCep());
        clientToChange.setState(client.getState());
        clientToChange.setCity(client.getCity());
        return clientRepository.save(clientToChange);
    }

    @Override
    public void delete(Long id) {
        Client clientToDelete = clientRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        clientRepository.delete(clientToDelete);
    }


}
