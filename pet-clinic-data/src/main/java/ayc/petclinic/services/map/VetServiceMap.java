package ayc.petclinic.services.map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ayc.petclinic.model.Speciality;
import ayc.petclinic.model.Vet;
import ayc.petclinic.services.SpecialityService;
import ayc.petclinic.services.VetService;

@Service
@Profile({"default", "map"})
public class VetServiceMap extends AbstractMapService<Vet, Long>
implements VetService{
	
	private final SpecialityService specialityService;
	
	public VetServiceMap(SpecialityService specialityService) {
		this.specialityService = specialityService;
	}

	@Override
	public Vet save(Vet object) {
		if(object.getSpeciality().size() > 0 ) {
			object.getSpeciality().forEach(speciality -> {
				if(speciality.getId() == null) {
					Speciality saved = specialityService.save(speciality);
					speciality.setId(saved.getId());
				}
			});
			
		}else {
			throw new RuntimeException("Specality can not be null");
		}
		return super.save(object);
	}

}
