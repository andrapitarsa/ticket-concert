package com.andra.ticketconcert.model;

import com.andra.ticketconcert.model.base.BaseModel;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Concert extends BaseModel {
    private Integer id;
    private String name;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @OneToMany(mappedBy = "concert")
    private Set<Ticket> tickets = new HashSet<>();

    @OneToMany(mappedBy = "concert")
    private Set<TicketCategory> ticketCategories = new HashSet<>();

    @Builder
    public Concert(Integer id, LocalDateTime createAt, LocalDateTime modifyAt, Boolean isDeleted,
                   String name) {
        super(id, createAt, modifyAt, isDeleted);
        this.name = name;
    }
}
