package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Sponsor;

public interface SponsorRepository extends CrudRepository<Sponsor, Long> {

	public List<Sponsor> findByNome(String nome);
	
}
