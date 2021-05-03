package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.DentistListDTO;
import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.entity.DentistListEntity;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import com.cgi.dentistapp.service.DentistVisitService;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@EnableAutoConfiguration
public class DentistAppController extends WebMvcConfigurerAdapter {

    @Autowired
    private DentistVisitService dentistVisitService;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }


    @GetMapping("/")
    public String showRegisterForm(DentistVisitDTO dentistVisitDTO, @Valid Model model, DentistListDTO dentistListDTO) {
        DentistListEntity first = dentistVisitService.getListById(1L);
        if(first == null){
            List<String> dentists = new ArrayList<>();
            dentists.add("Dr. Mari Murakas");
            dentists.add("Dr. Mati Jalakas");
            dentists.add("Dr. Kati Kask");
            dentists.add("Dr. Madis Mets");
            dentists.add("Dr. Kaspar Karu");
            for (String dentist : dentists) {
                dentistVisitService.addDentist(dentist);
            }
        }
        Iterable<DentistListEntity> dentists = dentistVisitService.getAllDentists();
        model.addAttribute("dentists", dentists);
        return "form";
    }

    @PostMapping("/")
    public String postRegisterForm(@Valid DentistVisitDTO dentistVisitDTO, BindingResult bindingResult, @Valid Model model) {

        if(!dentistVisitDTO.getSearchValue().equals("")){
            Iterable<DentistVisitEntity> visits = dentistVisitService.getAllSearchElements(dentistVisitDTO.getSearchValue());
            if(visits == null){
                var error = "Otsingule ei leitud ühtegi tulemust!";
                model.addAttribute("error", error);
                return "form";
            }
            model.addAttribute("visits",visits);

            return "registered";

        }

        Date date = new Date();

        if(dentistVisitDTO.getVisitTime().before(date)){
            var error = "Kuupäev või kellaaeg on möödas!";
            model.addAttribute("error", error);
            Iterable<DentistListEntity> dentists = dentistVisitService.getAllDentists();
            model.addAttribute("dentists", dentists);
            return "form";
        }
        if (bindingResult.hasErrors()) {
            Iterable<DentistListEntity> dentists = dentistVisitService.getAllDentists();
            model.addAttribute("dentists", dentists);
            return "form";
        }
        boolean hasVisit = dentistVisitService.checkIfAvailable(dentistVisitDTO);
        if(!hasVisit){
            var error = "Kellaaeg pole saadaval. Palun valige uus aeg!";
            model.addAttribute("error", error);
            Iterable<DentistListEntity> dentists = dentistVisitService.getAllDentists();
            model.addAttribute("dentists", dentists);
            return "form";
        }

        dentistVisitService.addVisit(dentistVisitDTO.getDentistName(), dentistVisitDTO.getVisitTime());
        return "redirect:/results";
    }
    @PostMapping("/edit/{id}")
    public String updateForm(@PathVariable("id") long id, @Valid DentistVisitDTO dentistVisitDTO, BindingResult bindingResult, @Valid Model model) {

        if (bindingResult.hasErrors()) {
            Iterable<DentistListEntity> dentists = dentistVisitService.getAllDentists();
            model.addAttribute("dentists", dentists);
            DentistVisitEntity entity = dentistVisitService.getVisitById(id);
            model.addAttribute("entity", entity);
            return "edit";
        }
        Date date = new Date();
        if(dentistVisitDTO.getVisitTime().before(date)){
            var error = "Kuupäev või kellaaeg on möödas!";
            model.addAttribute("error", error);
            Iterable<DentistListEntity> dentists = dentistVisitService.getAllDentists();
            model.addAttribute("dentists", dentists);
            return "edit";
        }
        boolean hasVisit = dentistVisitService.checkIfAvailable(dentistVisitDTO);
        if(!hasVisit){
            var error = "Kellaaeg pole saadaval. Palun valige uus aeg!";
            model.addAttribute("error", error);
            Iterable<DentistListEntity> dentists = dentistVisitService.getAllDentists();
            model.addAttribute("dentists", dentists);
            DentistVisitEntity entity = dentistVisitService.getVisitById(id);
            model.addAttribute("entity", entity);
            return "edit";
        }
        dentistVisitService.update(id, dentistVisitDTO.getDentistName(), dentistVisitDTO.getVisitTime());
        return "redirect:/results";
    }


    @RequestMapping(value = "/registered", method = RequestMethod.GET)
    public String registered(@Valid Model model) {
        Iterable<DentistVisitEntity> visits = dentistVisitService.getAllDateAfter();
        model.addAttribute("visits",visits);
        return "registered";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String updateVisit(@PathVariable("id") long id,
                             @Valid Model model, DentistVisitDTO dentistVisitDTO) {
        Iterable<DentistListEntity> dentists = dentistVisitService.getAllDentists();
        model.addAttribute("dentists", dentists);
        DentistVisitEntity entity = dentistVisitService.getVisitById(id);
        model.addAttribute("entity", entity);
        model.addAttribute("editId", id);
        return "edit";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {

        dentistVisitService.deleteRegistration(id);
        return "redirect:/registered";
    }
}
