package spring.petclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import spring.petclinic.model.Owner;
import spring.petclinic.model.Pet;
import spring.petclinic.model.PetType;
import spring.petclinic.services.OwnerService;
import spring.petclinic.services.PetService;
import spring.petclinic.services.PetTypeService;

import javax.validation.Valid;
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

    @GetMapping("/pets/new")
    public String initCreationForm(Owner owner, Model model){
        Pet pet=new Pet();
        owner.getPets().add(pet);
        pet.setOwner(owner);
        model.addAttribute("pet",pet);
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/new")
    public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result,Model model){

        if(StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(),true)!=null){
            result.rejectValue("name","duplicate","already exists");
        }
        owner.getPets().add(pet);
        if(result.hasErrors()){
            model.addAttribute("pet",pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        }
        else {
            this.pets.save(pet);
            return "redirect:/owners/"+owner.getId();
        }
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable Long petId,Model model){
        model.addAttribute("pet",pets.findById(petId));
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(@Valid Pet pet,Owner owner,BindingResult result,Model model){
        if(result.hasErrors()){
            pet.setOwner(owner);
            model.addAttribute("pet",pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        }else{
            owner.getPets().add(pet);
            pets.save(pet);
            return "redirect:/owners/"+owner.getId();
        }
    }
}
