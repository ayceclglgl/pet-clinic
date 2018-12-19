package ayc.petclinic.services.springdatajpa;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ayc.petclinic.model.Speciality;
import ayc.petclinic.model.Vet;
import ayc.petclinic.repositories.SpecialityRepository;
import ayc.petclinic.repositories.VetRepository;
import ayc.petclinic.services.VetService;

@Service
@Profile("springdatajpa")
public class VetSDJpaService implements VetService {

	private final SpecialityRepository specialityRepository;
	private final VetRepository vetRepository;
	
	public VetSDJpaService(SpecialityRepository specialityRepository, 
			VetRepository vetRepository) {
		this.specialityRepository = specialityRepository;
		this.vetRepository = vetRepository;
	}
	
	@Override
	public Set<Vet> findAll() {
		Set<Vet> vetSet = new HashSet<>();
		vetRepository.findAll().forEach(vetSet::add);
		return vetSet;
	}

	@Override
	public Vet findById(Long id) {
		return vetRepository.findById(id).orElse(null);
	}

	@Override
	public Vet save(Vet object) {
		if(object.getSpeciality().size() > 0) {
			object.getSpeciality().forEach(s -> {
				if(s.getId() == null) {
					Speciality savedSpeciality = specialityRepository.save(s);
					s.setId(savedSpeciality.getId());
				}
			});
		}else {
			throw new RuntimeException("Specality can not be null");
		}
		return vetRepository.save(object);
	}

	@Override
	public void delete(Vet object) {
		vetRepository.delete(object);		
	}

	@Override
	public void deleteById(Long id) {
		vetRepository.deleteById(id);
	}

}
