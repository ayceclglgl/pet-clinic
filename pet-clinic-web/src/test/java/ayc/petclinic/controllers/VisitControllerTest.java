package ayc.petclinic.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ayc.petclinic.model.Pet;
import ayc.petclinic.services.PetService;
import ayc.petclinic.services.VisitService;

public class VisitControllerTest {

	@InjectMocks
	VisitController controller;
	MockMvc mockMvc;
	
	@Mock
	private VisitService visitService;
	@Mock
	private PetService petService;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void testNewVisit() throws Exception {
		Pet pet = new Pet();
		pet.setId(1L);
		
		when(petService.findById(anyLong())).thenReturn(pet);
		
		mockMvc.perform(get("/owners/*/pets/1/visits/new"))
		.andExpect(view().name("pets/createOrUpdateVisitForm"))
		.andExpect(model().attributeExists("pet"))
		.andExpect(status().isOk());
		
		verify(petService).findById(anyLong());
	}
	
	@Test
	public void testProcessNewVisit() throws Exception {
		Pet pet = new Pet();
		pet.setId(1L);
		
		when(petService.findById(anyLong())).thenReturn(pet);
		
		mockMvc.perform(post("/owners/1/pets/1/visits/new"))
		.andExpect(view().name("redirect:/owners/{ownerId}"))
		.andExpect(model().attributeExists("pet"))
		.andExpect(status().is3xxRedirection());
		
		verify(petService).findById(anyLong());
		verify(visitService).save(any());
	}
	
	
}
