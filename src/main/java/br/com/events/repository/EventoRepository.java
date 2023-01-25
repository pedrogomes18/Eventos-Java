package br.com.events.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.events.models.Evento;

public interface EventoRepository extends CrudRepository<Evento, String> {
	Evento findByCodigo(long codigo);
}
