package com.andra.ticketconcert.model;

import com.andra.ticketconcert.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends BaseModel {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String username;
}
