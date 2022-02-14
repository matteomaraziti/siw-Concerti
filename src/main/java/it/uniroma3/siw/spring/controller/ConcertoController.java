package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.model.Concerto;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Canzone;
import it.uniroma3.siw.spring.service.ConcertoService;
import it.uniroma3.siw.spring.service.SponsorService;
import it.uniroma3.siw.spring.service.CanzoneService;

@Controller
public class ConcertoController {
	
	@Autowired
	private ConcertoService concertoService;
	
	
    @Autowired
    private ConcertoValidator concertoValidator;
    
   
    

    @RequestMapping(value="/admin/addConcerto", method = RequestMethod.GET)
    public String addCollezione(Model model) {
    	
    	model.addAttribute("concerto", new Concerto());
    	model.addAttribute("sponsors",concertoService.getSponsorService().tutti());
        return "concertoForm.html";
    }

    @RequestMapping(value = "/concerto/{id}", method = RequestMethod.GET)
    public String getCollezione(@PathVariable("id") Long id, Model model) {
    	Concerto c = this.concertoService.concertoPerId(id);
    	model.addAttribute("concerto", c);
    	model.addAttribute("canzone", new Canzone());
    	model.addAttribute("canzoni",concertoService.getCanzoneService().getCanzoniFiltered(c));
    	model.addAttribute("canzoniConcerto",c.getCanzoni());
    	
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.concertoService.getCredentialsService().getCredentials(userDetails.getUsername());
    	
    	model.addAttribute("credentials", credentials);
    	model.addAttribute("iscritto",c.getIscritti().contains(credentials.getUser()));
    	model.addAttribute("numero",c.getCanzoni().size());
    	return "concerto.html";
    }

    @RequestMapping(value = "/concerto", method = RequestMethod.GET)
    public String getCollezioni(Model model) {
    		model.addAttribute("concerti", this.concertoService.tutti());
    		return "concerti.html";
    }
    
    @RequestMapping(value = "/admin/concerto", method = RequestMethod.POST)
    public String newCollezione(@ModelAttribute("concerto") Concerto concerto, 
    									Model model, BindingResult bindingResult) {
    	this.concertoValidator.validate(concerto, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.concertoService.inserisci(concerto);
            model.addAttribute("concerti", this.concertoService.tutti());
            return "concerti.html";
        }
        model.addAttribute("concerto", new Concerto());
    	model.addAttribute("sponsors",concertoService.getSponsorService().tutti());
        return "concertoForm.html";
    }
    
    @RequestMapping(value = "/admin/addCanzoneAConcerto/{id}", method = RequestMethod.POST)
    public String aggiungiOpera(@RequestParam("canzone") Long idCanzone, 
    									Model model, @PathVariable("id") Long idConcerto) {
    	
    	Canzone o=concertoService.getCanzoneService().canzonePerId(idCanzone);
    	Concerto c = this.concertoService.concertoPerId(idConcerto);
    	if(!o.getConcerti().contains(c)) {
    			o.addConcerto(c);
    			concertoService.getCanzoneService().inserisci(o);
    	}
    	model.addAttribute("concerto", this.concertoService.concertoPerId(idConcerto));
    	model.addAttribute("canzoni",concertoService.getCanzoneService().getCanzoniFiltered(c));
    	model.addAttribute("canzoniConcerto",c.getCanzoni());
    	
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.concertoService.getCredentialsService().getCredentials(userDetails.getUsername());
    	
    	model.addAttribute("credentials", credentials);
    	model.addAttribute("iscritto",c.getIscritti().contains(credentials.getUser()));
    	return "concerto.html";
    }
    
    @RequestMapping(value = "/admin/rimuoviCanzone/{id}", method = RequestMethod.POST)
    public String rimuoviOpera(@RequestParam("canzone") Long idCanzone, 
    									Model model, @PathVariable("id") Long idConcerto) {
    	Concerto c = this.concertoService.concertoPerId(idConcerto);
    	Canzone o=concertoService.getCanzoneService().canzonePerId(idCanzone);
    	if(o.getConcerti().contains(c)) {
    		
    		o.removeConcerto(c);
    		concertoService.getCanzoneService().inserisci(o);
    	}
    	model.addAttribute("concerto", c);
    	model.addAttribute("canzoni",concertoService.getCanzoneService().getCanzoniFiltered(c));
    	model.addAttribute("canzoniConcerto",c.getCanzoni());
    	
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.concertoService.getCredentialsService().getCredentials(userDetails.getUsername());
    	
    	model.addAttribute("credentials", credentials);
    	model.addAttribute("iscritto",c.getIscritti().contains(credentials.getUser()));
    	return "concerto.html";
    }
    
    @RequestMapping(value = "/admin/eliminaConcerto/{id}", method = RequestMethod.POST)
    public String eliminaCollezione(Model model, @PathVariable("id") Long idConcerto) {
    		
    		Concerto c=concertoService.concertoPerId(idConcerto);
    		concertoService.eliminaConcerto(c);
    		model.addAttribute("concerti", this.concertoService.tutti());
    		return "concerti.html";
    }
    
}
