package spring.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.petclinic.model.Vet;

public interface VetRepository extends CrudRepository<Vet,Long> {
}
