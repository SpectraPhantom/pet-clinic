package spring.petclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.petclinic.model.Owner;
import spring.petclinic.model.PetType;
import spring.petclinic.services.OwnerService;
import spring.petclinic.services.PetService;
import spring.petclinic.services.PetTypeService;

import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM="pets/createOrUpdatePetForm";
    private final PetService pets;
    private final OwnerService owners;
    private final PetTypeService petTypeService;

    public PetController(PetService pets, OwnerService owners, PetTypeService petTypeService) {
        this.pets = pets;
        this.owners = owners;
        this.petTypeService = petTypeService;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes(){
        return this.petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId){
        return owners.findById(ownerId);
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }
}
