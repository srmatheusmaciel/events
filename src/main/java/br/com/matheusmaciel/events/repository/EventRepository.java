package br.com.matheusmaciel.events.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.matheusmaciel.events.model.EventModel;

public interface EventRepository extends CrudRepository<EventModel, Integer> {
  public EventModel findByPrettyName(String prettyName);
}
