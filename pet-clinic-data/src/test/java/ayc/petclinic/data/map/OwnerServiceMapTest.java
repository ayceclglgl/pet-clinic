package ayc.petclinic.data.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ayc.petclinic.model.Owner;
import ayc.petclinic.services.OwnerService;
import ayc.petclinic.services.map.OwnerServiceMap;
import ayc.petclinic.services.map.PetServiceMap;
import ayc.petclinic.services.map.PetTypeServiceMap;


public class OwnerServiceMapTest {

	OwnerService ownerServiceMap;
	final Long id = 1L;
	final String lastName = "LAST";
	
	@BeforeEach
	public void setUp() {
		ownerServiceMap = new OwnerServiceMap(new PetTypeServiceMap(), new PetServiceMap());
		Owner owner = new Owner();
		owner.setId(id);
		owner.setLastName(lastName);
		ownerServiceMap.save(owner);
	}
	
	@Test
	public void saveExistId() {
		Long id = 2L;
		Owner owner = new Owner();
		owner.setId(id);
		ownerServiceMap.save(owner);
		assertEquals(ownerServiceMap.findById(id).getId(), id);
	}
	
	@Test
	public void saveNoId() {
		Owner owner  = ownerServiceMap.save(new Owner());
		assertNotNull(owner);
		assertNotNull(owner.getId());
	}
	
	@Test
	public void findByLastName() {
		Owner owner = ownerServiceMap.findByLastName(lastName);
		assertNotNull(owner);
		assertEquals(id, owner.getId());
	}
	
	@Test
	public void delete() {
		ownerServiceMap.delete(ownerServiceMap.findById(id));
		assertEquals(0, ownerServiceMap.findAll().size());
	}
	
	@Test
	public void deleteById() {
		ownerServiceMap.deleteById(id);
		assertEquals(0, ownerServiceMap.findAll().size());
	}
	
	@Test
	public void findById() {
		Owner owner = ownerServiceMap.findById(id);
		assertNotNull(owner);
		assertEquals(id, owner.getId());
		
	}
	@Test
	public void findAll() {
		assertEquals(1, ownerServiceMap.findAll().size());
	}
	
}
