package ayc.petclinic.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ayc.petclinic.model.Owner;
import ayc.petclinic.services.OwnerService;

@Controller
@RequestMapping(value= "/owners")
public class OwnerController {
	
	private final OwnerService ownerService;
	
	public OwnerController(OwnerService ownerService) {
		this.ownerService = ownerService;
	}
	
	@InitBinder
	public void setAllowedField(WebDataBinder webDataBinder) {
		webDataBinder.setDisallowedFields("id");
	}

	@RequestMapping(value= {"/listAllOwners"})
	public String listOwners(Model model) {
		model.addAttribute("owners", ownerService.findAll());
		return "owners/index";
	}
	
	@GetMapping("/find")
	public String findOwners(Model m) {
		m.addAttribute("owner", new Owner());
		return "owners/findOwners";
	}
	
	@GetMapping("/{ownerId}")
	public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
		ModelAndView mv = new ModelAndView("owners/ownerDetails");
		mv.addObject(ownerService.findById(ownerId));
		return mv;
	}
	
	@GetMapping()//map to root owners
	public String processFindForm(Owner owner, BindingResult result, Model m) {
		if(owner.getLastName() == null) {
			owner.setLastName("");
		}
		List<Owner> results = ownerService.findAllByLastNameLike(owner.getLastName());
		if(results.isEmpty()) {
			result.rejectValue("lastName", "not found", "not found");
			return "owners/findOwners";
		}else if(results.size() == 1) {
			owner = results.iterator().next();
			return "redirect:/owners/" + owner.getId();
		}else {
			m.addAttribute("selections", results);
			return "owners/ownersList";
		}
	}
	
}
