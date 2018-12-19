package ayc.petclinic.services.springdatajpa;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ayc.petclinic.model.Speciality;
import ayc.petclinic.repositories.SpecialityRepository;
import ayc.petclinic.services.SpecialityService;

@Service
@Profile("springdatajpa")
public class SpecialitySDJpaService implements SpecialityService{

	private final SpecialityRepository specialityRepository;
	
	public SpecialitySDJpaService(SpecialityRepository specialityRepository) {
		this.specialityRepository = specialityRepository;
	}
	
	@Override
	public Set<Speciality> findAll() {
		Set<Speciality> specialitySet = new HashSet<>();
		specialityRepository.findAll().forEach(specialitySet::add);
		return specialitySet;
	}

	@Override
	public Speciality findById(Long id) {
		return specialityRepository.findById(id).orElse(null);
	}

	@Override
	public Speciality save(Speciality object) {
		return specialityRepository.save(object);
	}

	@Override
	public void delete(Speciality object) {
		specialityRepository.delete(object);
		
	}

	@Override
	public void deleteById(Long id) {
		specialityRepository.deleteById(id);
	}

}
