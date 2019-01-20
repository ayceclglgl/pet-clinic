package ayc.petclinic.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ayc.petclinic.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Long>{
	Owner findByLastName(String lastName);
	List<Owner> findAllByLastNameLike(String lastName);

}
