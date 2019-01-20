package ayc.petclinic.services;

import java.util.List;

import ayc.petclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {
	Owner findByLastName(String lastName);
	List<Owner> findAllByLastNameLike(String lastName);
}
