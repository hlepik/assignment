package com.cgi.dentistapp.repository;

import com.cgi.dentistapp.entity.DentistListEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface DentistRepository extends CrudRepository<DentistListEntity, Long> {


}
