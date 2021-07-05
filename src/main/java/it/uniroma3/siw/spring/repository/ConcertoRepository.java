package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Concerto;

public interface ConcertoRepository extends CrudRepository<Concerto,Long>{
	
	public List<Concerto> findByNome(String nome);

	public List<Concerto> findByNomeAndDescrizione(String nome, String descrizione);

	public List<Concerto> findByNomeOrDescrizione(String nome, String descrizione);

}
