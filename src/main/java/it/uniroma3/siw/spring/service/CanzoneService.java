package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Canzone;
import it.uniroma3.siw.spring.repository.CanzoneRepository;
@Service
public class CanzoneService {
	
	
	@Autowired
	private CanzoneRepository canzoneRepository; 
	
	@Autowired
	private ArtistaService artistaService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	public ArtistaService getArtistaService() {
		return artistaService;
	}

	@Transactional
	public Canzone inserisci(Canzone canzone) {
		return canzoneRepository.save(canzone);
	}
	
	@Transactional
	public List<Canzone> canzonePerTitolo(String titolo) {
		return canzoneRepository.findByTitolo(titolo);
	}

	@Transactional
	public List<Canzone> tutti() {
		return (List<Canzone>) canzoneRepository.findAll();
	}

	@Transactional
	public Canzone canzonePerId(Long id) {
		Optional<Canzone> optional = canzoneRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Canzone canzone) {
		List<Canzone> opere = this.canzoneRepository.findByTitolo(canzone.getTitolo());
		if (opere.size() > 0)
			return true;
		else 
			return false;
	}
	
	
	@Transactional
	public List<Canzone> filtraLista(List<Canzone> lista) {
		List<Canzone> opere=this.tutti();
		for(Canzone o:lista) {	//rimuovo opere che appartengono già alla collezione
			opere.remove(o);
		}
		return opere;
	}
	
	@Transactional
	public void eliminaCanzone(Canzone o) {
		canzoneRepository.delete(o);
	}
	
	@Transactional
	public List<Canzone> getCanzoniFiltered(){
		List<Canzone> filtrato=new ArrayList<>();
		for(Canzone o: this.tutti()) {
			if(o.getConcerto()==null)
				filtrato.add(o);
		}
		return filtrato;
	}

	public CredentialsService getCredentialsService() {
		return credentialsService;
	}

	public void setCredentialsService(CredentialsService credentialsService) {
		this.credentialsService = credentialsService;
	}

	public void setArtistaService(ArtistaService artistaService) {
		this.artistaService = artistaService;
	}


	

}
