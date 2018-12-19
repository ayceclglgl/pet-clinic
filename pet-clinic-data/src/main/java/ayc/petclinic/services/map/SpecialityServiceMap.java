package ayc.petclinic.services.map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ayc.petclinic.model.Speciality;
import ayc.petclinic.services.SpecialityService;

@Service
@Profile({"default", "map"})
public class SpecialityServiceMap extends AbstractMapService<Speciality, Long> 
implements SpecialityService {

	@Override
	public Speciality save(Speciality object) {
		return super.save(object);
	}
}
