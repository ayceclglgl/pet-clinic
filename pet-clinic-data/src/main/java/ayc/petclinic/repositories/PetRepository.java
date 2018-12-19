package ayc.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;

import ayc.petclinic.model.Pet;

public interface PetRepository extends CrudRepository<Pet, Long> {

}
