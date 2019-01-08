package ayc.petclinic.data.sdjpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ayc.petclinic.model.Owner;
import ayc.petclinic.repositories.OwnerRepository;
import ayc.petclinic.repositories.PetRepository;
import ayc.petclinic.repositories.PetTypeRepository;
import ayc.petclinic.services.springdatajpa.OwnerSDJpaService;



@ExtendWith(MockitoExtension.class)
public class OwnerSDJpaServiceTest {
	

	@Mock
	OwnerRepository ownerRepository;
	@Mock
	PetTypeRepository petTypeRepository;
	@Mock
	PetRepository petRepository;
	
	@InjectMocks
	OwnerSDJpaService ownerSDJpaService;
	
	final String lastName = "LAST";
	final Long id = 1L;
	
	Owner owner;
	
	@BeforeEach
	public void setUp() {
		owner = new Owner();
		owner.setId(id);
		owner.setLastName(lastName);
	}
	
	
	@Test
	public void findAll() {
		Set<Owner> ownerSet = new HashSet<Owner>();
		ownerSet.add(owner);
		when(ownerRepository.findAll()).thenReturn(ownerSet);
		
		Set<Owner> returnedSet =  ownerSDJpaService.findAll();
		assertEquals(returnedSet.size(), 1);
		//verify(ownerRepository, times(1)).findAll(); // default times value is 1
		verify(ownerRepository).findAll();
	}
	
	
	@Test
	public void findById() {
		when(ownerRepository.findById(id)).thenReturn(Optional.of(owner));
		Owner returned = ownerSDJpaService.findById(id); //demek ki mock lu repolar değer tutumuyor beybisi
		assertEquals(id, returned.getId());
	}
	
	@Test
	public void findByIdNotFound() {
		when(ownerRepository.findById(id)).thenReturn(Optional.empty());
		Owner returned = ownerSDJpaService.findById(id); //demek ki mock lu repolar değer tutumuyor beybisi
		assertNull(returned);
	}
	
	
	@Test
	public void save() {
		when(ownerRepository.save(any())).thenReturn(owner);
		Owner returnedOwner = ownerSDJpaService.save(owner);
		assertNotNull(returnedOwner);
		assertEquals(lastName, returnedOwner.getLastName());
		verify(ownerRepository).save(owner);
	}
	
	
	@Test
	public void delete() {
		ownerSDJpaService.delete(owner);
		verify(ownerRepository).delete(owner);
	}
	
	
	
	@Test
	public void deleteById() {
		ownerSDJpaService.deleteById(id);
		verify(ownerRepository).deleteById(id);
	}
	
	
	
	@Test
	public void findByLastName() {
		when(ownerRepository.findByLastName(any())).thenReturn(owner);
		Owner returnedOwner = ownerSDJpaService.findByLastName(lastName);
		assertNotNull(returnedOwner);
		assertEquals(lastName, returnedOwner.getLastName());
		verify(ownerRepository).findByLastName(lastName);
	}
}
