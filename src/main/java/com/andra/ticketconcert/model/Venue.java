package com.andra.ticketconcert.model;

import com.andra.ticketconcert.constant.VenueType;
import com.andra.ticketconcert.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Venue extends BaseModel {
    private String name;
    private String location;
    @Enumerated(EnumType.STRING)
    private VenueType type;
    private Integer capacity;
    @OneToMany(mappedBy = "artist")
    private Set<Concert> concerts = new HashSet<>();
}
