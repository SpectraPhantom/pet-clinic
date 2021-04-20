package spring.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.petclinic.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner,Long> {
    Owner findOwnerByLastName(String lastName);
}
