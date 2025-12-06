package br.com.mercado_souto.model.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mercado_souto.model.acess.Role;
import br.com.mercado_souto.model.acess.RoleRepository;
import br.com.mercado_souto.model.acess.User;
import br.com.mercado_souto.model.acess.UserService;
import br.com.mercado_souto.util.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public Client create(Client client) {

        Role clientRole= roleRepository.findByName(Role.ROLE_CLIENT);
        client.getUser().getRoles().add(clientRole);
        
        userService.save(client.getUser());

        client.setActive(Boolean.TRUE);

        return clientRepository.save(client);
    }

    public List<Client> findAll() {

        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client", id));

        return client;
    }

    public Client findByUser(User user) {
        Client client = clientRepository.findByUser(user)
                        .orElseThrow(() -> new EntityNotFoundException("Client", user));
              
        return client;
    }

    @Transactional
    public Client update(Long id, Client modifiedClient) {
        Client client = findById(id);
        client.setName(modifiedClient.getName());
        client.setEmail(modifiedClient.getEmail());
        client.setPassword(modifiedClient.getPassword());
        client.setCpf(modifiedClient.getCpf());
        client.setPhone(modifiedClient.getPhone());

        return clientRepository.save(client);
    }

    @Transactional
    public void delete(Long id) {
        Client client = findById(id);
        if (client.getSeller() != null) {
            client.getSeller().setActive(Boolean.FALSE);
        }
        client.setActive(Boolean.FALSE);

        clientRepository.save(client);
    }
}
