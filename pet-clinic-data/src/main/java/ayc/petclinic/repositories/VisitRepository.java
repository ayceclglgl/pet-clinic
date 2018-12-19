package ayc.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;

import ayc.petclinic.model.Visit;

public interface VisitRepository extends CrudRepository<Visit, Long>{

}
