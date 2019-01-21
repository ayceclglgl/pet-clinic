package ayc.petclinic.model;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "owners")
public class Owner extends Person {
	@Column(name="pets")
	@OneToMany(cascade = CascadeType.ALL, mappedBy="owner") //Deleting the owner deletes the pets too, it cascades down from parent to its childs.
	private Set<Pet> pets = new HashSet<>();
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "telephone")
	private String telephone;

	
	public Pet getPet(String name) {
		return getPet(name, false);
	}
	
	public Pet getPet(String name, boolean ignoreNew) {
		String lName = name.toLowerCase();
		Optional<Pet> optionalPet = this.getPets()
				.stream()
				.filter(pet -> {
					if(pet.getName() != null && pet.getName().toLowerCase().equals(lName)) {
						return true;
					}
					return false;
					})
				.findFirst();
		return optionalPet.orElse(null);
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Set<Pet> getPets() {
		return pets;
	}

	public void setPets(Set<Pet> pets) {
		this.pets = pets;
	}
	
	

}
