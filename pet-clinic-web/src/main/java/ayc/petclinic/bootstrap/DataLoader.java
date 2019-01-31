package ayc.petclinic.bootstrap;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ayc.petclinic.model.Owner;
import ayc.petclinic.model.Pet;
import ayc.petclinic.model.PetType;
import ayc.petclinic.model.Speciality;
import ayc.petclinic.model.Vet;
import ayc.petclinic.model.Visit;
import ayc.petclinic.services.OwnerService;
import ayc.petclinic.services.PetTypeService;
import ayc.petclinic.services.SpecialityService;
import ayc.petclinic.services.VetService;
import ayc.petclinic.services.VisitService;

@Component
public class DataLoader implements CommandLineRunner {

	private final OwnerService ownerService;
	private final VetService vetService;
	private final PetTypeService petTypeService;
//	private final PetService petService;
	private final SpecialityService specialityService;
	private final VisitService visitService;
	
	public DataLoader(OwnerService ownerService, VetService vetService,
			PetTypeService petTypeService,SpecialityService specialityService,
			 VisitService visitService) {
		this.ownerService = ownerService;
		this.vetService = vetService;
		this.petTypeService = petTypeService;
//		this.petService = petService;
		this.specialityService = specialityService;
		this.visitService = visitService;
	}
	
	@Override
	public void run(String... args) throws Exception {
		if(ownerService.findAll().size() == 0) {
			loadData();
		}
	}

	private void loadData() {
		PetType dog = new PetType();
		dog.setName("dog");
		PetType savedDogPetType = petTypeService.save(dog);
		
		PetType cat = new PetType();
		cat.setName("cat");
		PetType savedCatPetType = petTypeService.save(cat);
		
		
		Speciality radiology = new Speciality();
		radiology.setDescription("Radiology");
		Speciality savedRadiology = specialityService.save(radiology);
		Speciality dentist = new Speciality();
		dentist.setDescription("Dentist");
		Speciality savedDentist = specialityService.save(dentist);
		Speciality surgery = new Speciality();
		surgery.setDescription("Surgery");
		Speciality savedSurgery = specialityService.save(surgery);
		
		Owner owner1 = new Owner();
		owner1.setFirstName("Micheal");
		owner1.setLastName("Konskson");
		owner1.setAddress("123, TW9");
		owner1.setCity("London");
		owner1.setTelephone("12334566");
		
		Pet michealPets = new Pet();
		michealPets.setBirthDate(LocalDate.of(1998, Month.JULY, 3));
		michealPets.setName("sugar");
		michealPets.setOwner(owner1);
		michealPets.setPetType(savedDogPetType);
//		petService.save(michealPets);
		owner1.getPets().add(michealPets);
		ownerService.save(owner1);
		
		Owner owner2 = new Owner();
		owner2.setFirstName("Mike");
		owner2.setLastName("Brown");
		owner2.setAddress("123, 1UR");
		owner2.setCity("London");
		owner2.setTelephone("34566000");
		
		Pet mikesPets = new Pet();
		mikesPets.setBirthDate(LocalDate.of(200, Month.APRIL, 10));
		mikesPets.setName("milk");
		mikesPets.setOwner(owner2);
		mikesPets.setPetType(savedCatPetType);
//		petService.save(mikesPets);
		owner2.getPets().add(mikesPets);
		ownerService.save(owner2);
		
		Visit catVisit = new Visit();
		catVisit.setDate(LocalDate.now());
		catVisit.setDescription("Cat Visit");
		catVisit.setPet(mikesPets);
		visitService.save(catVisit);
		
		Visit dogVisit = new Visit();
		dogVisit.setDate(LocalDate.now());
		dogVisit.setDescription("Dog Visit");
		dogVisit.setPet(michealPets);
		visitService.save(dogVisit);
		
		Vet vet1 = new Vet();
		vet1.setFirstName("Jonh");
		vet1.setLastName("Brown");
		vet1.getSpeciality().add(savedDentist);
		vetService.save(vet1);
		
		Vet vet2 = new Vet();
		vet2.setFirstName("Asmen");
		vet2.setLastName("Coco");
		vet2.getSpeciality().add(savedSurgery);
		vetService.save(vet2);

		System.out.println("Datas are loaded..");
	}

}
