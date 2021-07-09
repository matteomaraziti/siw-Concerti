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

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Canzone;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.CanzoneService;

@Controller
public class CanzoneController {
	
	@Autowired
	private CanzoneService canzoneService;
	
    @Autowired
    private CanzoneValidator canzoneValidator;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    

    @RequestMapping(value="/admin/addCanzone", method = RequestMethod.GET)
    public String addOpera(Model model) {
    	
    	model.addAttribute("canzone", new Canzone());
    	model.addAttribute("artisti", canzoneService.getArtistaService().tutti());
        return "canzoneForm.html";
    }

    @RequestMapping(value = "/canzone/{id}", method = RequestMethod.GET)
    public String getOpera(@PathVariable("id") Long id, Model model) {
    	Canzone o=canzoneService.canzonePerId(id);
    	model.addAttribute("canzone", o);
    	model.addAttribute("artista",o.getArtista());
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.canzoneService.getCredentialsService().getCredentials(userDetails.getUsername());
    	model.addAttribute("credentials", credentials);
    	model.addAttribute("concerti",o.getConcerti());
    	return "canzone.html";
    }

    @RequestMapping(value = "/canzoni", method = RequestMethod.GET)
    public String getOpere(Model model) {
    		model.addAttribute("canzoni", this.canzoneService.tutti());
    		return "canzoni.html";
    }
    
    @RequestMapping(value = "/admin/canzone", method = RequestMethod.POST)
    public String newCanzone(@ModelAttribute("canzone") Canzone canzone, 
    									Model model, BindingResult bindingResult) {
    	this.canzoneValidator.validate(canzone, bindingResult);
        if (!bindingResult.hasErrors()) {
        	
        	this.canzoneService.inserisci(canzone);
            model.addAttribute("canzoni", this.canzoneService.tutti());
            return "canzoni.html";
        }
        model.addAttribute("canzone", new Canzone());
        model.addAttribute("artisti", canzoneService.getArtistaService().tutti());
        return "canzoneForm.html";
    }
    
    @RequestMapping(value = "/admin/eliminaCanzone/{id}", method = RequestMethod.POST)
    public String eliminaOpera(Model model, @PathVariable("id") Long idCanzone) {
    		
    		Canzone o=canzoneService.canzonePerId(idCanzone);
    		canzoneService.eliminaCanzone(o);
    		model.addAttribute("canzoni", this.canzoneService.tutti());
    		return "canzoni.html";
    }
}
