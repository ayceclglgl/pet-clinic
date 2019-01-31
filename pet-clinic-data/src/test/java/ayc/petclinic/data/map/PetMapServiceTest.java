package ayc.petclinic.data.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ayc.petclinic.model.Owner;
import ayc.petclinic.model.Pet;
import ayc.petclinic.services.PetService;
import ayc.petclinic.services.map.PetServiceMap;

public class PetMapServiceTest {
	
	PetService petService;
	Pet pet;
	final Long id = 1L;
	
	@BeforeEach
	public void setUp() {
		petService = new PetServiceMap();
		
		pet = new Pet();
		pet.setId(id);
		petService.save(pet);
	}
	
	@Test
	public void testSaveExistId() {
		Pet savedPet = petService.save(pet);
		assertNotNull(savedPet);
		assertEquals(pet.getId(), savedPet.getId());
	}
	
	@Test
	public void testSaveNoId() {
		Pet pet = petService.save(new Pet());
		assertNotNull(pet);
		assertNotNull(pet.getId());
	}
	
	@Test
	public void delete() {
		petService.delete(petService.findById(id));
		assertEquals(0, petService.findAll().size());
	}
	
	@Test
	public void deleteById() {
		petService.deleteById(id);
		assertEquals(0, petService.findAll().size());
	}
	
	@Test
	public void findById() {
		Pet pet = petService.findById(id);
		assertNotNull(pet);
		assertEquals(id, pet.getId());
		
	}
	@Test
	public void findAll() {
		assertEquals(1, petService.findAll().size());
	}
}
