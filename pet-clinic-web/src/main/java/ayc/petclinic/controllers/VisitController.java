package ayc.petclinic.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ayc.petclinic.model.Pet;
import ayc.petclinic.model.Visit;
import ayc.petclinic.services.PetService;
import ayc.petclinic.services.VisitService;

@Controller
public class VisitController {
	private final String CREATE_UPDATE_VISIT_FORM_PETS = "pets/createOrUpdateVisitForm";	
	
	private final VisitService visitService;
	private final PetService petService;
	
	public VisitController(VisitService visitService, PetService petService) {
		this.visitService = visitService;
		this.petService = petService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	 
	@ModelAttribute("visit")
	public Visit loadPetWithVisit(@PathVariable("petId") Long petId, Model m) {
		Pet pet = petService.findById(petId);
		Visit visit = new Visit();
		pet.getVisits().add(visit);
		visit.setPet(pet);
		m.addAttribute("pet", pet);
		return visit;
	}
	
	//Spring MVC calls method loadPetWithVisit(....) before newVisit is called.
	@GetMapping("/owners/*/pets/{petId}/visits/new")
	public String newVisit(@PathVariable("petId") Long petId, Model m) {
		return CREATE_UPDATE_VISIT_FORM_PETS;
	}
	
	@PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
	public String processNewVisit(@Valid Visit visit, BindingResult result) {
		if(result.hasErrors()) {
			return CREATE_UPDATE_VISIT_FORM_PETS;
		}else {
			visitService.save(visit);
			return "redirect:/owners/{ownerId}";
		}
		
	}
	

}
