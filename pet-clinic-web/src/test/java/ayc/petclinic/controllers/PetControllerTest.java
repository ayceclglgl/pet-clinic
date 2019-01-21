package ayc.petclinic.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ayc.petclinic.model.Owner;
import ayc.petclinic.model.Pet;
import ayc.petclinic.model.PetType;
import ayc.petclinic.services.OwnerService;
import ayc.petclinic.services.PetService;
import ayc.petclinic.services.PetTypeService;

public class PetControllerTest {
	
	@InjectMocks
	PetController controller;
	
	MockMvc mockMvc;
	
	@Mock
	OwnerService ownerService;
	@Mock
	PetTypeService petTypeService;
	@Mock
	PetService petService;
	
	Pet pet1;
	Owner owner1;
	Set<PetType> petTypes;
	
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		pet1 = new Pet();
		pet1.setId(1L);
		
		owner1 = new Owner();
		owner1.setId(1L);
		
		
		PetType dog = new PetType(1L, "dog");
		PetType cat = new PetType(2L, "cat");
		petTypes = new HashSet<PetType>();
		petTypes.add(cat);
		petTypes.add(dog);
	}
	
	@Test
	public void initNewPet() throws Exception {
		when(ownerService.findById(anyLong())).thenReturn(owner1);
		
		mockMvc.perform(get("/owners/1/pets/new"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("owner")) //@ModelAttribute findOwner method
		.andExpect(model().attributeExists("types"))
		.andExpect(model().attributeExists("pet"))
		.andExpect(view().name("pets/createOrUpdatePetForm"));
		
		verifyZeroInteractions(petService);
		verify(ownerService).findById(anyLong()); //it works because of @ModelAttribute findOwner method.
		verify(petTypeService).findAll(); // it works because of @ModelAttribute populatePetTypes method.
	}
	
	@Test
	public void processNewPet() throws Exception {
		when(ownerService.findById(anyLong())).thenReturn(owner1);
		when(petService.save(any())).thenReturn(pet1);
		
		mockMvc.perform(post("/owners/1/pets/new"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/1"))
		.andExpect(model().attributeExists("pet"))
		.andExpect(model().attributeExists("types"))
		.andExpect(model().attributeExists("owner"));
		
		verify(petService).save(any());
	}
	
	@Test
	public void initEditForm() throws Exception {
		when(ownerService.findById(anyLong())).thenReturn(owner1);
		when(petService.findById(anyLong())).thenReturn(pet1);
		
		mockMvc.perform(get("/owners/1/pets/1/edit"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("owner"))
		.andExpect(model().attributeExists("types"))
		.andExpect(model().attributeExists("pet"))
		.andExpect(view().name("pets/createOrUpdatePetForm"));
		
		verify(petService).findById(anyLong());
		verify(ownerService).findById(anyLong());
		
	}
	
	
	@Test
	public void processEditForm() throws Exception {
		when(ownerService.findById(anyLong())).thenReturn(owner1);
		when(petService.save(any())).thenReturn(pet1);
		
		mockMvc.perform(post("/owners/1/pets/1/edit"))
		.andExpect(status().is3xxRedirection())
		.andExpect(model().attributeExists("owner"))
		.andExpect(model().attributeExists("types"))
		.andExpect(view().name("redirect:/owners/1"));
		
		verify(petService).save(any());
	}

}
