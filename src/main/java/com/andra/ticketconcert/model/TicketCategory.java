package com.andra.ticketconcert.model;

import com.andra.ticketconcert.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TicketCategory extends BaseModel {
    private String description;
    private String area;
    private BigDecimal price;
    private Integer quantity;
    private LocalDateTime bookingStartDate;
    private LocalDateTime bookingEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private Concert concert;

    @OneToMany(mappedBy = "ticketCategory")
    private Set<Ticket> tickets = new HashSet<>();


    @Override
    public void prePersist() {
        super.prePersist();
        if (this.quantity < 0) {
            quantity = 0;
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (this.quantity < 0) {
            this.quantity = 0;
        }
    }
}
