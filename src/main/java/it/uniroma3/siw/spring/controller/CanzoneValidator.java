package it.uniroma3.siw.spring.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Canzone;
import it.uniroma3.siw.spring.service.CanzoneService;


@Component
public class CanzoneValidator implements Validator {
	@Autowired
	private CanzoneService operaService;
	
    private static final Logger logger = LoggerFactory.getLogger(CanzoneValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titolo", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "testo", "required");
		

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.operaService.alreadyExists((Canzone)o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Canzone.class.equals(aClass);
	}
}
