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
import org.springframework.web.multipart.MultipartFile;


import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.service.ArtistaService;

@Controller
public class ArtistaController {
	
	@Autowired
	private ArtistaService artistaService;
	
    @Autowired
    private ArtistaValidator artistaValidator;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    

    @RequestMapping(value="/admin/addArtista", method = RequestMethod.GET)
    public String addArtista(Model model) {
    	logger.debug("addArtista");
    	model.addAttribute("artista", new Artista());
        return "artistaForm.html";
    }

    @RequestMapping(value = "/artista/{id}", method = RequestMethod.GET)
    public String getArtista(@PathVariable("id") Long id, Model model) {
    	Artista a=this.artistaService.artistaPerId(id);
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.artistaService.getCredentialsService().getCredentials(userDetails.getUsername());
    	model.addAttribute("credentials",credentials);
    	model.addAttribute("artista", a);
    	model.addAttribute("canzoniArtista",a.getCanzoni());
    	return "artista.html";
    }

    @RequestMapping(value = "/artista", method = RequestMethod.GET)
    public String getArtisti(Model model) {
    		model.addAttribute("artisti", this.artistaService.tutti());
    		return "artisti.html";
    }
    
    @RequestMapping(value = "/admin/artista", method = RequestMethod.POST)
    public String newArtista( @ModelAttribute("artista") Artista artista, 
    									Model model, BindingResult bindingResult  ) {
  
    	this.artistaValidator.validate(artista, bindingResult);
        if (!bindingResult.hasErrors()) {
        	
        	
        	this.artistaService.inserisci(artista);
            model.addAttribute("artisti", this.artistaService.tutti());
            return "artisti.html";
        }
        model.addAttribute("artista", new Artista());
        return "artistaForm.html";
    }
    
    @RequestMapping(value = "/admin/addImage/{id}", method = RequestMethod.POST)
    public String newArtista( @PathVariable("id")Long idArt, @RequestParam("immagine")MultipartFile file, 
    									Model model   ) {
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.artistaService.getCredentialsService().getCredentials(userDetails.getUsername());
    	model.addAttribute("credentials",credentials);
    	Artista a=this.artistaService.artistaPerId(idArt);
    	String nome=file.getOriginalFilename();
    	a.setImmagine(nome);
    	this.artistaService.inserisci(a);
    	
    	model.addAttribute("artista", a);
    	model.addAttribute("canzoniArtista",a.getCanzoni());
    	return "artista";
    	
    }
    
}

