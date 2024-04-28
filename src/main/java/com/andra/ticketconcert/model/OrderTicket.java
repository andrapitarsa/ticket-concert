package com.andra.ticketconcert.model;

import com.andra.ticketconcert.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderTicket extends BaseModel {
    private Integer customerId;
    private Integer ticketId;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private Integer quantity;
}
