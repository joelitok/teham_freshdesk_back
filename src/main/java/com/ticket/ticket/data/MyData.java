package com.ticket.ticket.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MyData {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String data;

@JsonIgnore
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}


public String getData() {
	return data;
}

public void setData(String data) {
	this.data = data;
}

public MyData(Long id, String data) {
	super();
	this.id = id;
	this.data = data;
}

}
