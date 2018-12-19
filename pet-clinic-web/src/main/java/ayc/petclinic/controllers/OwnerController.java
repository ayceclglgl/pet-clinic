package ayc.petclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ayc.petclinic.services.OwnerService;

@Controller
@RequestMapping(value= "/owners")
public class OwnerController {
	
	private final OwnerService ownerService;
	
	public OwnerController(OwnerService ownerService) {
		this.ownerService = ownerService;
	}

	@RequestMapping(value= {"", "/index", "/index.html"})
	public String listOwner(Model model) {
		model.addAttribute("owners", ownerService.findAll());
		return "owners/index";
	}
}
