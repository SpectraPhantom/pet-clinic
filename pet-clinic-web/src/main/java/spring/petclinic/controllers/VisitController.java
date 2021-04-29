package spring.petclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import spring.petclinic.model.Pet;
import spring.petclinic.model.Visit;
import spring.petclinic.services.PetService;
import spring.petclinic.services.VisitService;

import javax.validation.Valid;

@Controller
public class VisitController {

    private static final String VIEWS_VISIT_CREATE_OR_UPDATE_FORM="pets/createOrUpdateVisitForm";

    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable Long petId, Model model){
        Pet pet=petService.findById(petId);
        model.addAttribute("pet",pet);
        Visit visit=new Visit();
        pet.getVisits().add(visit);
        visit.setPet(pet);
        return visit;
    }

    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String initNewVisitForm(@PathVariable Long petId){
        return VIEWS_VISIT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(@Valid Visit visit, BindingResult result){
        if(result.hasErrors()){
            return VIEWS_VISIT_CREATE_OR_UPDATE_FORM;
        }else{
            Visit savedVisit=visitService.save(visit);
            return "redirect:/owners/{ownerId}";
        }
    }
}
