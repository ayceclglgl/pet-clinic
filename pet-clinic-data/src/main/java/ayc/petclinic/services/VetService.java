package ayc.petclinic.services;

import java.util.Set;

import ayc.petclinic.model.Vet;

public interface VetService {
	Vet findById(Long id);
	Vet save(Vet vet);
	Set<Vet> findAll();

}
