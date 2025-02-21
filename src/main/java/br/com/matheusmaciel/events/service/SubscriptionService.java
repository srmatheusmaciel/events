package br.com.matheusmaciel.events.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.matheusmaciel.events.dto.SubscriptionRankingByUser;
import br.com.matheusmaciel.events.dto.SubscriptionRankingItem;
import br.com.matheusmaciel.events.dto.SubscriptionResponse;
import br.com.matheusmaciel.events.exception.EventNotFoundException;
import br.com.matheusmaciel.events.exception.SubscriptionConflictException;
import br.com.matheusmaciel.events.exception.UserIndicadorNotFoundException;
import br.com.matheusmaciel.events.model.EventModel;
import br.com.matheusmaciel.events.model.SubscriptionModel;
import br.com.matheusmaciel.events.model.UserModel;
import br.com.matheusmaciel.events.repository.EventRepository;
import br.com.matheusmaciel.events.repository.SubscriptionRepository;
import br.com.matheusmaciel.events.repository.UserRepository;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class SubscriptionService {
    
    @Autowired
    private EventRepository evtRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubscriptionRepository subRepository;

    public SubscriptionResponse createNewSubscription(String eventName, UserModel user, Integer userId){
        EventModel evt = evtRepository.findByPrettyName(eventName);
        if (evt == null){
            throw new EventNotFoundException("Evento " + eventName+ " não existe");
        }
        UserModel userRec = userRepository.findByEmail(user.getEmail());
        if(userRec == null){
            userRec = userRepository.save(user);
        }
        UserModel indicador = null;
        if (userId != null) {
            indicador = userRepository.findById(userId).orElse(null);
            if (indicador == null) {
                throw new UserIndicadorNotFoundException("Usuário " + userId + " indicador não existe");
            }
        }
        
        SubscriptionModel subs = new SubscriptionModel();
        subs.setEvent(evt);
        subs.setSubscriber(userRec);
        subs.setIndication(indicador);
        SubscriptionModel tmpSub = subRepository.findByEventAndSubscriber(evt, userRec);
        if(tmpSub != null){
            throw new SubscriptionConflictException("Já existe incrição para o usuário " + userRec.getName() + " no evento " + evt.getTitle());
        }
        SubscriptionModel res = subRepository.save(subs);
        return new SubscriptionResponse(res.getSubscriptionNumber(), "http://codecraft.com/subscription/"+res.getEvent().getPrettyName()+"/"+res.getSubscriber().getId());
    }

    public List<SubscriptionRankingItem> getCompleteRanking(String prettyName){
        EventModel evt = evtRepository.findByPrettyName(prettyName);
        if (evt ==null){
            throw new EventNotFoundException("Ranking do evento " + prettyName + " não existe");
        }
        return subRepository.generateRanking(evt.getEventId());
    }

    public SubscriptionRankingByUser getRankingByUser(String prettyName, Integer userId){
        List<SubscriptionRankingItem> ranking = getCompleteRanking(prettyName);
        SubscriptionRankingItem item = ranking.stream().filter(i->i.userId().equals(userId)).findFirst().orElse(null);
        if(item == null){
            throw new UserIndicadorNotFoundException("Não há inscrições com indicação do usuário "+userId);
        }
        Integer posicao = IntStream.range(0, ranking.size())
                          .filter(pos -> ranking.get(pos).userId().equals(userId))
                          .findFirst().getAsInt();
        return new SubscriptionRankingByUser(item, posicao+1);
    }
}