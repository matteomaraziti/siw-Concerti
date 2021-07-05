package it.uniroma3.siw.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PrenotazioneService {
	@Autowired
	private ConcertoService concertoService;
	@Autowired
	private CanzoneService canzoneService;
	public ConcertoService getConcertoService() {
		return concertoService;
	}

	public void setConcertoService(ConcertoService concertoService) {
		this.concertoService = concertoService;
	}

	public CanzoneService getCanzoneService() {
		return canzoneService;
	}

	public void setCanzoneService(CanzoneService canzoneService) {
		this.canzoneService = canzoneService;
	}
	
}
