package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Concerto;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.repository.ConcertoRepository;

@Service
public class ConcertoService {
	
	@Autowired
	private ConcertoRepository collezioneRepository; 
	
	@Autowired
	private OperaService operaService;
	
	@Autowired
	private CuratoreService curatoreService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	public CredentialsService getCredentialsService() {
		return credentialsService;
	}

	public OperaService getOperaService() {
		return operaService;
	}

	public CuratoreService getCuratoreService() {
		return curatoreService;
	}

	@Transactional
	public Concerto inserisci(Concerto collezione) {
		return collezioneRepository.save(collezione);
	}
	
	@Transactional
	public List<Concerto> collezionePerNomeAndDescrizione(String nome, String descrizione) {
		return collezioneRepository.findByNomeAndDescrizione(nome, descrizione);
	}

	@Transactional
	public List<Concerto> tutti() {
		return (List<Concerto>) collezioneRepository.findAll();
	}

	@Transactional
	public Concerto collezionePerId(Long id) {
		Optional<Concerto> optional = collezioneRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Concerto collezione) {
		List<Concerto> collezioni = this.collezioneRepository.findByNomeAndDescrizione(collezione.getNome(), collezione.getDescrizione());
		if (collezioni.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public void eliminaCollezione(Concerto c) {
		for(Opera o:c.getOpere()) {
			o.setCollezione(null);
			operaService.inserisci(o);
		}
		collezioneRepository.delete(c);
	}
	

}
