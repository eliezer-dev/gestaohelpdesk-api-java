package dev.eliezer.superticket.service.impl;

import dev.eliezer.superticket.domain.model.Client;
import dev.eliezer.superticket.domain.repository.ClientRepository;
import dev.eliezer.superticket.service.ClientService;
import dev.eliezer.superticket.service.exception.BusinessException;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Object index(String search, Long typeSearch) {
        if (search.isEmpty()) {
            return clientRepository.findAll();

        }else {
            if (typeSearch == 1) {
                return clientRepository.findByRazaoSocialNameIgnoreCaseContaining(search);
            }
            if (typeSearch == 2) {
                return clientRepository.findByBusinessNameIgnoreCaseContaining(search);
            }
            if (typeSearch == 3) {
                List clientList = new ArrayList();
                Optional<Client> client = clientRepository.findByCpfCnpj(search);
                if (client.isPresent()) {
                    clientList.add(client);
                }

                return clientList;
            }
            if (typeSearch == 4) {
                return clientRepository.findByEmailIgnoreCaseContaining(search);
            }
            if (typeSearch == 5) {
                return clientRepository.findByCityIgnoreCaseContaining(search);
            }
            if (typeSearch == 6) {
                return clientRepository.findByAddressIgnoreCaseContaining(search);
            }
        }
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
        if(clientFound.isPresent() && clientFound.get().getId() != id) {
            clientFound = null;
            throw new BusinessException("[Cpf_Cnpj] " + client.getCpfCnpj() + " já foi utilizado em outro cadastro.");
        }

        clientToChange.setCpfCnpj(client.getCpfCnpj());
        clientToChange.setRazaoSocialName(client.getRazaoSocialName());
        clientToChange.setCep(client.getCep());
        clientToChange.setState(client.getState());
        clientToChange.setCity(client.getCity());
        clientToChange.setBusinessName(client.getBusinessName());
        return clientRepository.save(clientToChange);
    }

    @Override
    public void delete(Long id) {
        Client clientToDelete = clientRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        clientRepository.delete(clientToDelete);
    }


}
