package ayc.petclinic.controllers;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.ui.Model;

import ayc.petclinic.services.VetService;

public class VetControllerTest {
	@Mock
	private VetService vetService;
	
	MockMvc mockMvc;
	
	@InjectMocks
	VetController controller;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void testListVets() throws Exception {
		
		mockMvc.perform(get("/vets"))
		.andExpect(view().name("vets/index"))
		.andExpect(model().attributeExists("vets"))
		.andExpect(status().isOk());
		
		verify(vetService).findAll();
		
	}

}
