package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Concerto;
import it.uniroma3.siw.spring.model.Canzone;
import it.uniroma3.siw.spring.repository.ConcertoRepository;

@Service
public class ConcertoService {
	
	@Autowired
	private ConcertoRepository concertoRepository; 
	
	@Autowired
	private CanzoneService canzoneService;
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	public CredentialsService getCredentialsService() {
		return credentialsService;
	}

	public CanzoneService getCanzoneService() {
		return canzoneService;
	}

	public SponsorService getSponsorService() {
		return sponsorService;
	}

	@Transactional
	public Concerto inserisci(Concerto concerto) {
		return concertoRepository.save(concerto);
	}
	
	@Transactional
	public List<Concerto> concertoPerNomeAndDescrizione(String nome, String descrizione) {
		return concertoRepository.findByNomeAndDescrizione(nome, descrizione);
	}

	@Transactional
	public List<Concerto> tutti() {
		return (List<Concerto>) concertoRepository.findAll();
	}

	@Transactional
	public Concerto concertoPerId(Long id) {
		Optional<Concerto> optional = concertoRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Concerto collezione) {
		List<Concerto> collezioni = this.concertoRepository.findByNome(collezione.getNome());
		if (collezioni.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public void eliminaConcerto(Concerto c) {
		for(Canzone o:c.getCanzoni()) {
			o.removeConcerto(c);
			canzoneService.inserisci(o);
		}
		concertoRepository.delete(c);
	}
	

}
