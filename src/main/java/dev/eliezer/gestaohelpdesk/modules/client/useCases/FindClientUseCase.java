package dev.eliezer.gestaohelpdesk.modules.client.useCases;

import dev.eliezer.gestaohelpdesk.modules.client.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FindClientUseCase {

    @Autowired
    private ClientRepository clientRepository;

    public Object execute(String search, Long typeSearch) {
        if (search.isEmpty()) {
            return clientRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        }else {
            if (typeSearch == 1) {
                return clientRepository.findByRazaoSocialNameIgnoreCaseContaining(search);
            }
            if (typeSearch == 2) {
                return clientRepository.findByCpfCnpjStartingWith(search);
            }
            if (typeSearch == 3) {
                return clientRepository.findByIdStartingWith(search);
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
            if (typeSearch == 7) {
                return clientRepository.findByBusinessNameIgnoreCaseContaining(search);
            }
        }
        return clientRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

    }
}
