package br.com.matheusmaciel.events.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.matheusmaciel.events.model.EventModel;
import br.com.matheusmaciel.events.repository.EventRepository;

@Service
public class EventService {
  
  @Autowired
  private EventRepository eventRepository;

  public EventModel addNewEvent(EventModel event) {
    //generate prettyname
    event.setPrettyName(event.getTitle().toLowerCase().replace(" ", "-"));

    return eventRepository.save(event);
  }

  public List<EventModel> getAllEvents() {
    return (List<EventModel>)eventRepository.findAll();
  }

  public EventModel getByPrettyName(String prettyName) {
    return eventRepository.findByPrettyName(prettyName);
  }

}
