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
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.ConcertoService;
import it.uniroma3.siw.spring.service.CuratoreService;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class ConcertoController {
	
	@Autowired
	private ConcertoService concertoService;
	
	
    @Autowired
    private ConcertoValidator concertoValidator;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    

    @RequestMapping(value="/admin/addConcerto", method = RequestMethod.GET)
    public String addCollezione(Model model) {
    	
    	model.addAttribute("concerto", new Concerto());
    	model.addAttribute("curatori",concertoService.getCuratoreService().tutti());
        return "concertoForm.html";
    }

    @RequestMapping(value = "/concerto/{id}", method = RequestMethod.GET)
    public String getCollezione(@PathVariable("id") Long id, Model model) {
    	Concerto c = this.concertoService.collezionePerId(id);
    	model.addAttribute("concerto", c);
    	model.addAttribute("opera", new Opera());
    	model.addAttribute("opere",concertoService.getOperaService().getOpereFiltered());
    	model.addAttribute("opereConcerto",c.getOpere());
   
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.concertoService.getCredentialsService().getCredentials(userDetails.getUsername());
    	model.addAttribute("credentials", credentials);
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
        return "concertoForm.html";
    }
    
    @RequestMapping(value = "/admin/addOperaAConcerto/{id}", method = RequestMethod.POST)
    public String aggiungiOpera(@RequestParam("opera") Long idOpera, 
    									Model model, @PathVariable("id") Long idConcerto) {
    	
    	Opera o=concertoService.getOperaService().operaPerId(idOpera);
    	Concerto c = this.concertoService.collezionePerId(idConcerto);
    	o.setCollezione(c);
    	concertoService.getOperaService().inserisci(o);
    	model.addAttribute("concerto", this.concertoService.collezionePerId(idConcerto));
    	model.addAttribute("opere",concertoService.getOperaService().getOpereFiltered());
    	model.addAttribute("opereConcerto",c.getOpere());
    	
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.concertoService.getCredentialsService().getCredentials(userDetails.getUsername());
    	model.addAttribute("credentials", credentials);
    	return "concerto.html";
    }
    
    @RequestMapping(value = "/admin/rimuoviOpera/{id}", method = RequestMethod.POST)
    public String rimuoviOpera(@RequestParam("opera") Long idOpera, 
    									Model model, @PathVariable("id") Long idConcerto) {
    	Concerto c = this.concertoService.collezionePerId(idConcerto);
    	Opera o=concertoService.getOperaService().operaPerId(idOpera);
    	o.setCollezione(null);
    	concertoService.getOperaService().inserisci(o);
    	model.addAttribute("concerto", c);
    	model.addAttribute("opere",concertoService.getOperaService().getOpereFiltered());
    	model.addAttribute("opereConcerto",c.getOpere());
    	
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.concertoService.getCredentialsService().getCredentials(userDetails.getUsername());
    	model.addAttribute("credentials", credentials);
    	return "concerto.html";
    }
    
    @RequestMapping(value = "/admin/eliminaConcerto/{id}", method = RequestMethod.POST)
    public String eliminaCollezione(Model model, @PathVariable("id") Long idConcerto) {
    		
    		Concerto c=concertoService.collezionePerId(idConcerto);
    		concertoService.eliminaCollezione(c);
    		model.addAttribute("concerti", this.concertoService.tutti());
    		return "concerti.html";
    }
    
}
