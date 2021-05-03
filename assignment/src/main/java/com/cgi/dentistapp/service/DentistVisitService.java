package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.entity.DentistListEntity;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cgi.dentistapp.repository.DentistRepository;
import com.cgi.dentistapp.repository.VisitsRepository;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class DentistVisitService {
    @Autowired
    DentistRepository repo;
    @Autowired
    VisitsRepository visitRepo;


    public DentistVisitService(DentistRepository repo, VisitsRepository visitRepo) {
        this.repo = repo;
        this.visitRepo = visitRepo;
    }

    public void addVisit(String dentistName, Date visitTime) {

        DentistVisitEntity entity = new DentistVisitEntity();
        entity.setDentistName(dentistName);
        entity.setVisitTime(visitTime);
        visitRepo.save(entity);
    }

    public void addDentist(String dentist) {
        DentistListEntity entity = new DentistListEntity();
        entity.setName(dentist);
        repo.save(entity);
    }
    public DentistListEntity getListById(Long id)
    {
        return repo.findOne(id);
    }

    public Iterable<DentistListEntity> getAllDentists(){

        return repo.findAll();
    }
    public Iterable<DentistVisitEntity> getAllVisits(){

        return visitRepo.findAll();
    }
    public void deleteRegistration(Long id){

        visitRepo.delete(id);
    }
    public DentistVisitEntity getVisitById(Long id)
    {

        return visitRepo.findOne(id);
    }
    public Iterable<DentistVisitEntity> getAllDateAfter(){

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();

        return visitRepo.findAllByVisitTimeAfterOrderByVisitTime(yesterday);
    }

    public boolean checkIfAvailable(DentistVisitDTO dto){

        List<DentistVisitEntity> entities = visitRepo.findByDentistName(dto.getDentistName());
        for (DentistVisitEntity entity : entities) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            String strDate= formatter.format(entity.getVisitTime());
            String compareDate = formatter.format(dto.getVisitTime());
            if(strDate.equals(compareDate)){
                return false;
            }
        }
        return true;
    }
    public Iterable<DentistVisitEntity> getAllSearchElements(String input){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();

        return visitRepo.findAllByDentistNameContainingIgnoreCaseAndVisitTimeIsAfterOrderByVisitTime(input, yesterday);

    }
    public void update(Long id, String dentistName, Date visitTime){
        DentistVisitEntity visitFromDb = visitRepo.findOne(id);
        visitFromDb.setVisitTime(visitTime);
        visitFromDb.setDentistName(dentistName);
        visitRepo.save(visitFromDb);
    }


}
