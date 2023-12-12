package dev.eliezer.superticket.service.impl;

import dev.eliezer.superticket.domain.model.Client;
import dev.eliezer.superticket.domain.model.User;
import dev.eliezer.superticket.domain.repository.ClientRepository;
import dev.eliezer.superticket.service.ClientService;
import dev.eliezer.superticket.service.exception.BusinessException;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

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
        Optional<Client> clientFound = clientRepository.findByCpfCnpj(client.getCpfCnpj());
        if(clientFound.isPresent()) {
            clientFound = null;
            throw new BusinessException("[cpfCnpj] " + client.getCpfCnpj() + " já foi utilizado em outro cadastro.");
        }

        return clientRepository.save(client);
    }

    @Override
    public Client update(Long id, Client client) {
        Client clientToChange =  clientRepository.findById(id).orElseThrow(() -> new NotFoundException(id));

        Optional<Client> clientFound = clientRepository.findByCpfCnpj(client.getCpfCnpj());
        if(clientFound.isPresent() && clientFound.get().getId() == id) {
            clientFound = null;
            throw new BusinessException("[Cpf_Cnpj] " + client.getCpfCnpj() + " já foi utilizado em outro cadastro.");
        }

        clientToChange.setCpfCnpj(client.getCpfCnpj());
        clientToChange.setRazaoSocialName(client.getRazaoSocialName());
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
