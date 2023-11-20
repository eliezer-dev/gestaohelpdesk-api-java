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
        clientValidator(client);
        return clientRepository.save(client);
    }

    @Override
    public Client update(Long id, Client client) {
        clientValidator(client);
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

    private void clientValidator(Client client){
        //checkIfObjectIsNull
        if (checkIfClientIsEmpty(client)) throw new BusinessException("This client data is empty");

        //checkIfPropertiesIsNull
        if (client.getCpfOrCnpj() == null) throw new BusinessException("cpf or CNPJ is not provided");
        if (client.getRazaoSocialOrName() == null) throw new BusinessException("Razão social or name is not provided");
        if (client.getCep() == null) throw new BusinessException("cep is not provided");
        if (client.getAddress() == null) throw new BusinessException("address is not provided");
        if (client.getAddressNumber() == null) throw new BusinessException("address number is not provided");
        if (client.getState() == null) throw new BusinessException("state is not provided");
        if (client.getCity() == null) throw new BusinessException("city is not provided");
    }

    public boolean checkIfClientIsEmpty(Client client) {
        if (client.getCpfOrCnpj() != null) return false;
        if (client.getRazaoSocialOrName() != null) return false;
        if (client.getCep() != null) return false;
        if (client.getCity() != null) return false;
        if (client.getState() != null) return false;
        if (client.getAddress() != null) return false;
        if (client.getAddressNumber() != null) return false;
        return true;
    }

}
