package ayc.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;

import ayc.petclinic.model.Vet;

public interface VetRepository extends CrudRepository<Vet, Long>{

}
