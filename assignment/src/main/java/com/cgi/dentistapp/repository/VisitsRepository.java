package com.cgi.dentistapp.repository;

import com.cgi.dentistapp.entity.DentistListEntity;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VisitsRepository extends CrudRepository<DentistVisitEntity, Long> {

    List<DentistVisitEntity> findByDentistName(String dentistName);
    List<DentistVisitEntity> findAllByVisitTimeAfterOrderByVisitTime(Date now);
    List<DentistVisitEntity> findAllByDentistNameContainingIgnoreCaseAndVisitTimeIsAfterOrderByVisitTime(String input, Date now);
}
