package com.booking.entity.dto;

import lombok.Data;
import lombok.Setter;

@Setter
@Data
public class LoanRequestDTO {
    private int idItem;
    private int quantity;
}
