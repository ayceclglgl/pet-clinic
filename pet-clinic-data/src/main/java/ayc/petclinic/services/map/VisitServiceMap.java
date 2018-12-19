package ayc.petclinic.services.map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ayc.petclinic.model.Visit;
import ayc.petclinic.services.VisitService;

@Service
@Profile({"default", "map"})
public class VisitServiceMap extends AbstractMapService<Visit, Long> 
implements VisitService{

	@Override
	public Visit save(Visit visit) {
		if(visit.getPet() == null || visit.getPet().getId() ==null || 
				visit.getPet().getOwner() == null  ||
				visit.getPet().getOwner().getId() == null ) {
			throw new RuntimeException("Invalid visit");
		}
		return super.save(visit);
	}
}
