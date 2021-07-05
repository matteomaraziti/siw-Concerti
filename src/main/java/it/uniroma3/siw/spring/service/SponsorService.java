package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Sponsor;
import it.uniroma3.siw.spring.repository.SponsorRepository;

@Service
public class SponsorService{

	@Autowired
	private SponsorRepository curatoreRepository; 
	
	@Transactional
	public List<Sponsor> tutti() {
		return (List<Sponsor>) curatoreRepository.findAll();
	}

	@Transactional
	public Sponsor curatorePerId(Long id) {
		Optional<Sponsor> optional = curatoreRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

}
