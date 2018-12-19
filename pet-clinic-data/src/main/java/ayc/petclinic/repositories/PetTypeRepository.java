package ayc.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;

import ayc.petclinic.model.PetType;

public interface PetTypeRepository extends CrudRepository<PetType, Long>{

}
