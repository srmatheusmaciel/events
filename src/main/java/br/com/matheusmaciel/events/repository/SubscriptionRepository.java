package br.com.matheusmaciel.events.repository;



import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.matheusmaciel.events.model.EventModel;
import br.com.matheusmaciel.events.model.SubscriptionModel;
import br.com.matheusmaciel.events.model.UserModel;
import br.com.matheusmaciel.events.dto.SubscriptionRankingItem;
public interface SubscriptionRepository extends CrudRepository<SubscriptionModel, Integer> {
    public SubscriptionModel findByEventAndSubscriber(EventModel evt, UserModel user);

     @Query(value = "select count(subscription_number) as quantidade, indication_user_id, user_name" + 
                " from tbl_subscription inner join tbl_user" + 
                " on tbl_subscription.indication_user_id = tbl_user.user_id " + 
                " where indication_user_id is not null" + 
                "    and event_id = :eventId" + 
                " group by indication_user_id" + 
                " order by quantidade desc", nativeQuery = true)
    public List<SubscriptionRankingItem> generateRanking(@Param("eventId") Integer eventId);
    
}