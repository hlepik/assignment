package com.cgi.dentistapp.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "dentist_visit")
public class DentistVisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String dentistName;
    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private Date visitTime;

    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }

    public String getDentistName()
    {
        return dentistName;
    }
    public void setDentistName(String dentistName)
    {
        this.dentistName = dentistName;
    }
    public Date getVisitTime()
    {
        return visitTime;
    }
    public void setVisitTime(Date visitTime)
    {
        this.visitTime = visitTime;
    }

}
