package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Canzone;

public interface CanzoneRepository extends CrudRepository<Canzone, Long> {
	
	public List<Canzone> findByTitolo(String titolo);
}
