package com.andra.ticketconcert.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqOrderTicketDto {
    private Integer customerId;
    private Integer ticketId;
    private int quantity;
}
