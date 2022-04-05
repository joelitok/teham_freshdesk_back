package com.ticket.ticket.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CustomFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    
    private String cf_diagnose;
    private String cf_kommentaren;
        
    @JsonIgnore
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCf_diagnose() {
        return cf_diagnose;
    }
    
    public void setCf_diagnose(String cf_diagnose) {
        this.cf_diagnose = cf_diagnose;
    }
    
    public String getCf_kommentaren() {
        return cf_kommentaren;
    }
    
    public void setCf_kommentaren(String cf_kommentaren) {
        this.cf_kommentaren = cf_kommentaren;
    } 

    
}
