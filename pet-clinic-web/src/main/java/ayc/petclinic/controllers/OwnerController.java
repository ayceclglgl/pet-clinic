package ayc.petclinic.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ayc.petclinic.model.Owner;
import ayc.petclinic.services.OwnerService;

@Controller
@RequestMapping(value= "/owners")
public class OwnerController {
	private static String CREATE_OR_UPDATE_FORM_VIEW = "owners/createOrUpdateOwnerForm";
	private static String INDEX_VIEW = "owners/index";
	private static String FIND_OWNERS_VIEW = "owners/findOwners";
	private static String OWNERS_LIST_VIEW = "owners/ownersList";
	
	
	
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
		return INDEX_VIEW;
	}
	
	@GetMapping("/find")
	public String findOwners(Model m) {
		m.addAttribute("owner", new Owner());
		return FIND_OWNERS_VIEW;
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
			return FIND_OWNERS_VIEW;
		}else if(results.size() == 1) {
			owner = results.iterator().next();
			return "redirect:/owners/" + owner.getId();
		}else {
			m.addAttribute("selections", results);
			return OWNERS_LIST_VIEW;
		}
	}

	
	@GetMapping("/new")
	public String newOwner(Model m) {
		m.addAttribute("owner", new Owner());
		return CREATE_OR_UPDATE_FORM_VIEW;
	}
	
	@PostMapping("/new")
	public String processNewOwner(@Valid Owner owner, BindingResult result) {
		if(result.hasErrors()) {
			return CREATE_OR_UPDATE_FORM_VIEW;
		}else {
			Owner savedOwner = ownerService.save(owner);
			return "redirect:/owners/" + savedOwner.getId();
		}
	}
	
	
	@GetMapping("/{ownerId}/edit")
	public String editOwner(@PathVariable("ownerId") Long ownerId, Model m) {
		m.addAttribute("owner", ownerService.findById(ownerId));
		return CREATE_OR_UPDATE_FORM_VIEW;
	}
	
	@PostMapping("/{ownerId}/edit")
	public String processEditOwner(@Valid Owner owner, BindingResult result, @PathVariable("ownerId") Long ownerId) {
		if(result.hasErrors()) {
			return CREATE_OR_UPDATE_FORM_VIEW;
		}else {
			owner.setId(ownerId);//Bind the id, cause we explicitly remove it (webDataBinder.setDisallowedFields("id");)
			Owner savedOwner = ownerService.save(owner);
			return "redirect:/owners/" + savedOwner.getId();
		}
	}
}
