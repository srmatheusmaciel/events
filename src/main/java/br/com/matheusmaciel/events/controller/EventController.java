package br.com.matheusmaciel.events.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.matheusmaciel.events.model.EventModel;
import br.com.matheusmaciel.events.service.EventService;

@RestController
public class EventController {
  
  @Autowired
  private EventService eventService;

  @PostMapping("/events/")
  public EventModel addNewEvent(@RequestBody EventModel newEvent) {
    return eventService.addNewEvent(newEvent);
  }

  @GetMapping("/events")
  public List<EventModel> getAllEvents() {
    return eventService.getAllEvents();
  }

  @GetMapping("/events/{prettyName}")
  public ResponseEntity getEventByPrettyName(@PathVariable String prettyName) {
    
    EventModel event = eventService.getByPrettyName(prettyName);
    if (event != null) {
      return ResponseEntity.ok().body(event);
    } else{
      return ResponseEntity.notFound().build();
    }
    
  }




}
