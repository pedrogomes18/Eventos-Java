package br.com.events.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.events.models.*;

public interface ConvidadosRepository extends CrudRepository<Convidados, String> {
	
	Iterable<Convidados> findByEvento(Evento e);
	Convidados findByRg(String rg);
	
}
