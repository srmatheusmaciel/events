package br.com.matheusmaciel.events.repository;

import org.springframework.data.repository.CrudRepository;
import br.com.matheusmaciel.events.model.EventModel;
import br.com.matheusmaciel.events.model.SubscriptionModel;
import br.com.matheusmaciel.events.model.UserModel;
public interface SubscriptionRepository extends CrudRepository<SubscriptionModel, Integer> {
    public SubscriptionModel findByEventAndSubscriber(EventModel evt, UserModel user);
    
}