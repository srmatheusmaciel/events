package br.com.matheusmaciel.events.repository;

import org.springframework.data.repository.CrudRepository;
import br.com.matheusmaciel.events.model.UserModel;
public interface UserRepository extends CrudRepository<UserModel, Integer> {
    public UserModel findByEmail(String email);  
}