package ayc.petclinic.services.springdatajpa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ayc.petclinic.model.Owner;
import ayc.petclinic.model.Pet;
import ayc.petclinic.model.PetType;
import ayc.petclinic.repositories.OwnerRepository;
import ayc.petclinic.repositories.PetRepository;
import ayc.petclinic.repositories.PetTypeRepository;
import ayc.petclinic.services.OwnerService;

@Service
@Profile("springdatajpa")
public class OwnerSDJpaService implements OwnerService {
	
	private final OwnerRepository ownerRepository;
	private final PetTypeRepository petTypeRepository;
	private final PetRepository petRepository;

	
	public OwnerSDJpaService(OwnerRepository ownerRepository, PetTypeRepository petTypeRepository,
			PetRepository petRepository) {
		this.ownerRepository = ownerRepository;
		this.petTypeRepository = petTypeRepository;
		this.petRepository = petRepository;
	}
	
	
	@Override
	public Set<Owner> findAll() {
		Set<Owner> ownerSet = new HashSet<>();
		ownerRepository.findAll().forEach(ownerSet::add);
		return ownerSet;
	}

	@Override
	public Owner findById(Long id) {
		return ownerRepository.findById(id).orElse(null);
	}

	@Override
	public Owner save(Owner object) {
		if (object != null) {
			object.getPets().forEach(p -> {
				if (p.getId() == null) {
					Pet savedPet = petRepository.save(p);
					p.setId(savedPet.getId());
				}
				if (p.getPetType() != null) {
					if (p.getPetType().getId() == null) {
						PetType savedPetType = petTypeRepository.save(p.getPetType());
						p.getPetType().setId(savedPetType.getId());
					}
				} else {
					throw new RuntimeException("PetType can not be null");
				}
			});
			return ownerRepository.save(object);
		}else {
			return null;
		}

	}

	@Override
	public void delete(Owner object) {
		ownerRepository.delete(object);
	}

	@Override
	public void deleteById(Long id) {
		ownerRepository.deleteById(id);
	}

	@Override
	public Owner findByLastName(String lastName) {
		return ownerRepository.findByLastName(lastName);
	}

	@Override
	public List<Owner> findAllByLastNameLike(String lastName) {
		return ownerRepository.findAllByLastNameLike(lastName);
	}
}
