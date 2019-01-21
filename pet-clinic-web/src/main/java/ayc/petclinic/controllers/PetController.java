package ayc.petclinic.controllers;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ayc.petclinic.model.Owner;
import ayc.petclinic.model.Pet;
import ayc.petclinic.model.PetType;
import ayc.petclinic.services.OwnerService;
import ayc.petclinic.services.PetService;
import ayc.petclinic.services.PetTypeService;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {
	private static String CREATE_OR_UPDATE_PET_FORM_VIEW = "pets/createOrUpdatePetForm";
	
	OwnerService ownerService;
	PetTypeService petTypeService;
	PetService petService;
	
	public PetController(OwnerService ownerService, PetTypeService petTypeService, PetService petService) {
		this.ownerService = ownerService;
		this.petTypeService = petTypeService;
		this.petService = petService;
	}
	
	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable("ownerId") Long ownerId) {
		return ownerService.findById(ownerId);
	}
	
	@ModelAttribute("types")
	public Set<PetType> populatePetTypes(){
		return petTypeService.findAll();
	}
	
	@GetMapping("/pets/new")
	public String newPet(Owner owner, Model m) {
		Pet pet = new Pet();
		pet.setOwner(owner);
		owner.getPets().add(pet);
		m.addAttribute("pet", pet);
		
		return CREATE_OR_UPDATE_PET_FORM_VIEW;
	}
	
	@PostMapping("/pets/new")
	public String processNewPet(Owner owner, @Valid Pet pet, BindingResult result, Model m) {
		if(StringUtils.hasLength(pet.getName()) && pet.isNew() 
				&& owner.getPet(pet.getName(), true) != null) {
			result.rejectValue("name", "duplicate", "alreadyExist");
		}
		owner.getPets().add(pet); //?
		if(result.hasErrors()) {
			m.addAttribute("pet", pet);
			return CREATE_OR_UPDATE_PET_FORM_VIEW;
		}else {
			petService.save(pet); //?
			pet.setOwner(owner); //?
			return "redirect:/owners/" + owner.getId(); //"redirect:/owners/{ownerId}"
			
		}
	}
	
	@GetMapping("/pets/{petId}/edit")
	public String editPet(Owner owner, @PathVariable("petId") Long petId, Model m) {
		m.addAttribute("pet", petService.findById(petId));
		return CREATE_OR_UPDATE_PET_FORM_VIEW;
	}
	
	@PostMapping("/pets/{petId}/edit")
	public String processEditPet(@Valid Pet pet, BindingResult result, Owner owner, Model m) {
		if(result.hasErrors()) {
			pet.setOwner(owner);
			m.addAttribute("pet", pet);
			return CREATE_OR_UPDATE_PET_FORM_VIEW;
		}else {
			owner.getPets().add(pet);
			//pet.setOwner(owner);
			petService.save(pet);
			return "redirect:/owners/" + owner.getId();
		}
	}
}
