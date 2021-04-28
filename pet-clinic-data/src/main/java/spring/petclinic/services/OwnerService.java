package spring.petclinic.services;

import org.springframework.stereotype.Service;
import spring.petclinic.model.Owner;

import java.util.List;


@Service
public interface OwnerService extends CrudService<Owner,Long> {

    Owner findByLastName(String lastName);
    List<Owner> findAllByLastNameLike(String lastName);
}
