package br.com.mercado_souto.model.acess;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.mercado_souto.model.client.Client;
import br.com.mercado_souto.model.client.ClientRepository;
import br.com.mercado_souto.model.client.ClientService;
import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    @Lazy
    private ClientService clientService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository,
            PasswordEncoder passwordEncoder, RoleRepository roleRepository, ClientRepository clientRepository) {

        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User authenticate(String username, String password) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        return repository.findByUsername(username).orElseThrow();
    }

    @Transactional
    public User findByUsername(String username) {

        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public boolean exists(String username) {
        return repository.existsByUsername(username);
    }

    @Transactional
    public Client findOrCreateOAuthClient(String email, String name) {

        Optional<User> existingUserOpt = repository.findByUsername(email);

        if (existingUserOpt.isPresent()) {

            User user = existingUserOpt.get();
            Optional<Client> existingClientOpt = clientRepository.findByUser(user);

            if (existingClientOpt.isPresent()) {
                return existingClientOpt.get();
            } else {

                Client newClient = new Client();
                newClient.setUser(user);
                newClient.setName(name);
                newClient.setEmail(email);
                newClient.setActive(Boolean.TRUE);

                return clientService.createOAuthClient(newClient);
            }

        } else {

            User newUser = new User();
            newUser.setUsername(email);
            newUser.setActive(Boolean.TRUE);

            String randomPassword = "OAUTH_LOGIN_" + System.currentTimeMillis();
            newUser.setPassword(passwordEncoder.encode(randomPassword));

            Role clientRole = roleRepository.findByName(Role.ROLE_CLIENT);
            newUser.setRoles(List.of(clientRole));

            User savedUser = repository.save(newUser);

            Client newClient = new Client();
            newClient.setUser(savedUser);
            newClient.setName(name);
            newClient.setEmail(email);
            newClient.setActive(Boolean.TRUE);

            return clientService.createOAuthClient(newClient);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Transactional
    public User save(User user) {

        if (user.getId() == null) {

            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            if (!user.getPassword().startsWith("$2a$") && !user.getPassword().startsWith("$2b$")) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }

        user.setActive(Boolean.TRUE);
        return repository.save(user);
    }

}
