package spring.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.petclinic.model.PetType;

public interface PetTypeRepository extends CrudRepository<PetType,Long> {
}
