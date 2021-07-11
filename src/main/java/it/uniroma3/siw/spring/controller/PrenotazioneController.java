package it.uniroma3.siw.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Concerto;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.service.PrenotazioneService;

@Controller
public class PrenotazioneController {
	@Autowired
	private PrenotazioneService prenotazioneService;

	@RequestMapping(value="/aggiungiPrenotazione/{id}", method = RequestMethod.POST)
	public String addPrenotazione(@PathVariable("id") Long idConcerto, Model model) {
		Concerto c=this.prenotazioneService.getConcertoService().concertoPerId(idConcerto);
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.prenotazioneService.getConcertoService().getCredentialsService().getCredentials(userDetails.getUsername());
    	c.addIscritto(credentials.getUser());
    	this.prenotazioneService.getConcertoService().inserisci(c);
    	model.addAttribute("concerto", this.prenotazioneService.getConcertoService().concertoPerId(idConcerto));
    	model.addAttribute("canzoni",this.prenotazioneService.getCanzoneService().getCanzoniFiltered(c));
    	model.addAttribute("canzoniConcerto",c.getCanzoni());
    	model.addAttribute("credentials",credentials);
    	model.addAttribute("iscritto",c.getIscritti().contains(credentials.getUser()));
		return "concerto";	
	}
	
	@RequestMapping(value="/eliminaPrenotazione/{id}", method = RequestMethod.POST)
	public String removePrenotazione(@PathVariable("id") Long idConcerto, Model model) {
		Concerto c=this.prenotazioneService.getConcertoService().concertoPerId(idConcerto);
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.prenotazioneService.getConcertoService().getCredentialsService().getCredentials(userDetails.getUsername());
		c.removeIscritto(credentials.getUser());
		this.prenotazioneService.getConcertoService().inserisci(c);
		model.addAttribute("concerto", this.prenotazioneService.getConcertoService().concertoPerId(idConcerto));
    	model.addAttribute("canzoni",this.prenotazioneService.getCanzoneService().getCanzoniFiltered(c));
    	model.addAttribute("canzoniConcerto",c.getCanzoni());
    	model.addAttribute("credentials",credentials);
    	model.addAttribute("iscritto",c.getIscritti().contains(credentials.getUser()));
		
		return "concerto";
	}
	
	@RequestMapping(value="/iscrizioni", method = RequestMethod.GET)
	public String getIscrizioni(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.prenotazioneService.getConcertoService().getCredentialsService().getCredentials(userDetails.getUsername());
    	List<Concerto> concerti= credentials.getUser().getConcerti();
    	model.addAttribute("concerti",concerti);
    	return "prenotazioni.html";
	}
}
