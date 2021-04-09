package spring.petclinic.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import spring.petclinic.model.Owner;
import spring.petclinic.model.Vet;
import spring.petclinic.services.OwnerService;
import spring.petclinic.services.VetService;
import spring.petclinic.services.map.OwnerServiceMap;
import spring.petclinic.services.map.VetServiceMap;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader() {
        ownerService=new OwnerServiceMap();
        vetService=new VetServiceMap();

    }

    @Override
    public void run(String... args) throws Exception {

        Owner owner1=new Owner();
        owner1.setId(1L);
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");

        ownerService.save(owner1);

        Owner owner2=new Owner();
        owner2.setId(2L);
        owner2.setFirstName("Fiona");
        owner2.setLastName("Johnson");

        ownerService.save(owner2);

        System.out.println("Loaded owners..");

        Vet vet1=new Vet();
        vet1.setId(1L);
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");

        vetService.save(vet1);

        Vet vet2=new Vet();
        vet2.setId(2L);
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");

        vetService.save(vet2);

        System.out.println("Loaded vets..");

    }
}
