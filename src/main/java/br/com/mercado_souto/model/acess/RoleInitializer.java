package br.com.mercado_souto.model.acess;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

public class RoleInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        createRoleIfNotExists(Role.ROLE_CLIENT);
        createRoleIfNotExists(Role.ROLE_SELLER);

    }

    private void createRoleIfNotExists(String roleName) {
        try {
            if (roleRepository.existsByName(roleName)) {
                return;
            }

            Role role = Role.builder()
                    .name(roleName)
                    .build();

            role.setActive(Boolean.TRUE);

            roleRepository.save(role);

        } catch (Exception ignored) {

        }
    }
}
