package ayc.petclinic.services.map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ayc.petclinic.model.Owner;
import ayc.petclinic.model.Pet;
import ayc.petclinic.model.PetType;
import ayc.petclinic.services.OwnerService;
import ayc.petclinic.services.PetService;
import ayc.petclinic.services.PetTypeService;

@Service
@Profile({"default", "map"})
public class OwnerServiceMap extends AbstractMapService<Owner, Long>
implements OwnerService{
	
	private PetTypeService petTypeService;
	private PetService petService;
	
	public OwnerServiceMap(PetTypeService petTypeService, PetService petService) {
		this.petTypeService = petTypeService;
		this.petService = petService;
	}
	

	@Override
	public Owner save(Owner object) {
		if(object != null) {
			if (object.getPets() != null) {
				object.getPets().forEach(pet -> {
					if (pet.getId() == null) {
						// pet is not saved to db
						Pet savedPet = petService.save(pet);
						pet.setId(savedPet.getId());
					}

					if (pet.getPetType() != null) {
						if (pet.getPetType().getId() == null) {
							// pet type is not saved to db
							PetType savedPetType = petTypeService.save(pet.getPetType());
							pet.getPetType().setId(savedPetType.getId());
						}
					}else {
						throw new RuntimeException("PetType can not be null");
					}
				});
			}
			
			return super.save(object);
		}else {
			return null;
		}
	}

	@Override
	public Owner findByLastName(String lastName) {
		return super.findAll()
				.stream()
				.filter(ln -> ln.getLastName().equals(lastName))
				.findAny()
				.orElse(null);
	}

	

}
