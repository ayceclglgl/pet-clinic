package ayc.petclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="vets")
public class Vet extends Person {

	@ManyToMany(fetch=FetchType.EAGER)//default is lazy, load everything at once
	@JoinTable(name="vet_speacialities", joinColumns = @JoinColumn(name="vet_id"),
	inverseJoinColumns = @JoinColumn(name = "speciality_id"))
	private Set<Speciality> speciality = new HashSet<>();

	public Set<Speciality> getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Set<Speciality> speciality) {
		this.speciality = speciality;
	}
	
}
