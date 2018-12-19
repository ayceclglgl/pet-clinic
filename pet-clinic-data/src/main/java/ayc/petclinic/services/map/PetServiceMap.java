package ayc.petclinic.services.map;

import org.springframework.stereotype.Service;

import ayc.petclinic.model.Pet;
import ayc.petclinic.services.PetService;

@Service
public class PetServiceMap extends AbstractMapService<Pet, Long> 
implements PetService{

	@Override
	public Pet save(Pet object) {
		return super.save(object);
	}



}
