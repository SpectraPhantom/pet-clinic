package spring.petclinic.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.petclinic.model.Owner;
import spring.petclinic.services.OwnerService;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM="owners/createOrUpdateOwnerForm";

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/find")
    public String findOwners(Model model){
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result,Model model){
        if (owner.getLastName()==null){
            owner.setLastName("");
        }
        List<Owner> results=ownerService.findAllByLastNameLike("%"+owner.getLastName()+"%");

        if(results.isEmpty()){
            result.rejectValue("lastName","not found","not found");
            return "owners/findOwners";
        }
        else if(results.size()==1){
            owner=results.get(0);
            return "redirect:/owners/"+owner.getId();

        }
        else{
            model.addAttribute("selections",results);
            return "owners/ownerList";
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") Long id){
        ModelAndView mav=new ModelAndView("/owners/ownerDetails");
        mav.addObject(ownerService.findById(id));
        return mav;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model){
        model.addAttribute("owner",Owner.builder().build());
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Owner owner, BindingResult result){
        if (result.hasErrors()){
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        }
        Owner savedOwner=ownerService.save(owner);
        return "redirect:/owners/"+savedOwner.getId();
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateForm(@PathVariable Long ownerId, Model model){
        model.addAttribute("owner",ownerService.findById(ownerId));
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateForm(@Valid Owner owner,BindingResult result,@PathVariable Long ownerId){
        if (result.hasErrors()){
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        }
        owner.setId(ownerId);
        Owner savedOwner=ownerService.save(owner);

        return "redirect:/owners/"+savedOwner.getId();
    }
}
