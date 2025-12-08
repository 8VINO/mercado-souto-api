package br.com.mercado_souto.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.mercado_souto.model.acess.User;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(Long itemId) {
        super(String.format("CardItem not found with product id %s", itemId));
    }
    public EntityNotFoundException(String entity, Long id) {
        super(String.format("Entity %s not found with id %s", entity, id));
    }
    public EntityNotFoundException(String entity, User user) {
        super(String.format("Entity %s not found with the user id: %s", entity, user.getId()));
    }
}