package ayc.petclinic.services.map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ayc.petclinic.model.PetType;
import ayc.petclinic.services.PetTypeService;

@Service
@Profile({"default", "map"})
public class PetTypeServiceMap extends AbstractMapService<PetType, Long> 
implements PetTypeService{

	@Override
	public PetType save(PetType object) {
		return super.save(object);
	}



}
