package com.cgi.dentistapp.dto;

import java.util.Date;

public class DentistListDTO {

    String name;
    public DentistListDTO(String name) {
        this.name = name;

    }
    public DentistListDTO() {

    }

    public String getDentistName() {
        return name;
    }

    public void setDentistName(String name) {
        this.name = name;
    }
}
