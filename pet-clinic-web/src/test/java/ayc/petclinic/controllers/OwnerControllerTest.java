package ayc.petclinic.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ayc.petclinic.model.Owner;
import ayc.petclinic.services.OwnerService;

@ExtendWith(MockitoExtension.class)
public class OwnerControllerTest {

	@Mock
	OwnerService ownerService;
	
	@InjectMocks
	OwnerController controller;
	
	MockMvc mockMvc;
	Set<Owner> owners;
	Owner owner1;
	Owner owner2;
	
	
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		owner1 = new Owner();
		owner1.setId(1L);
		owner2 = new Owner();
		owner2.setId(2L);
		
		owners = new HashSet<>();
        owners.add(owner1);
        owners.add(owner2);
     }
	
	@Test
	public void listOwners() throws Exception {
		mockMvc.perform(get("/owners/listAllOwners"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/index"))
			.andExpect(model().attribute("owners", Matchers.emptyIterable()));
		
		verify(ownerService).findAll();
	}
	
	@Test
	public void showOwner() throws Exception {
		when(ownerService.findById(anyLong())).thenReturn(owner1);
		
		mockMvc.perform(get("/owners/1"))
		.andExpect(status().isOk())
		.andExpect(view().name("owners/ownerDetails"))
		.andExpect(model().attributeExists("owner"))
		.andExpect(model().attribute("owner", hasProperty("id", is(1L))));

	
		verify(ownerService).findById(anyLong());
	}
	
	@Test
	public void findOwners() throws Exception {
		mockMvc.perform(get("/owners/find"))
		.andExpect(status().isOk())
		.andExpect(view().name("owners/findOwners"))
		.andExpect(model().attributeExists("owner"));
		
		verifyZeroInteractions(ownerService);
	}
	
	@Test
	public void processFindFormReturnMany() throws Exception {
		when(ownerService.findAllByLastNameLike(anyString())).thenReturn(new ArrayList(owners));
		
		mockMvc.perform(get("/owners"))
		.andExpect(status().isOk())
		.andExpect(view().name("owners/ownersList"))
		.andExpect(model().attributeExists("selections"));
		
	}
	
	@Test
	public void processFindFormReturnOne() throws Exception {
		List<Owner> list = new ArrayList<>();
		list.add(owner1);
		when(ownerService.findAllByLastNameLike(anyString())).thenReturn(list);
		
		mockMvc.perform(get("/owners"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/1"));
	
	}
	
	@Test
	public void initNewOwner() throws Exception {
		mockMvc.perform(get("/owners/new"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("owner"))
		.andExpect(view().name("owners/createOrUpdateOwnerForm"));
		
		verifyZeroInteractions(ownerService);
	}
	
	@Test
	public void processNewOwner() throws Exception {
		when(ownerService.save(any())).thenReturn(owner1);
		
		mockMvc.perform(post("/owners/new"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/1"))
		.andExpect(model().attributeExists("owner"));
		
		verify(ownerService).save(any());
	}
	
	@Test
	public void initEditForm() throws Exception {
		when(ownerService.findById(anyLong())).thenReturn(owner1);
		
		mockMvc.perform(get("/owners/1/edit"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("owner"))
		.andExpect(view().name("owners/createOrUpdateOwnerForm"));
		
		verify(ownerService).findById(anyLong());
	}
	
	
	@Test
	public void processEditForm() throws Exception {
		when(ownerService.save(any())).thenReturn(owner1);
		
		mockMvc.perform(post("/owners/1/edit"))
		.andExpect(status().is3xxRedirection())
		.andExpect(model().attributeExists("owner"))
		.andExpect(view().name("redirect:/owners/1"));
		
		verify(ownerService).save(any());
	}
}
