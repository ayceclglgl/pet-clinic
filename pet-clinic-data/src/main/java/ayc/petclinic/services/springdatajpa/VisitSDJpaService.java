package ayc.petclinic.services.springdatajpa;

import java.util.HashSet;
import java.util.Set;

import ayc.petclinic.model.Visit;
import ayc.petclinic.repositories.VisitRepository;
import ayc.petclinic.services.VisitService;

public class VisitSDJpaService implements VisitService{

	private final VisitRepository visitRepository;
	
	public VisitSDJpaService(VisitRepository visitRepository) {
		this.visitRepository = visitRepository;
	}
	
	@Override
	public Set<Visit> findAll() {
		Set<Visit> visitSet = new HashSet<>();
		visitRepository.findAll().forEach(visitSet::add);
		return visitSet;
	}

	@Override
	public Visit findById(Long id) {
		return visitRepository.findById(id).orElse(null);
	}

	@Override
	public Visit save(Visit object) {
		return visitRepository.save(object);
	}

	@Override
	public void delete(Visit object) {
		visitRepository.delete(object);
	}

	@Override
	public void deleteById(Long id) {
		visitRepository.deleteById(id);
	}

}
