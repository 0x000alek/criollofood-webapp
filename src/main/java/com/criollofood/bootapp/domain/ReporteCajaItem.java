package com.criollofood.bootapp.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReporteCajaItem {
    private Integer numeroCaja;
    private LocalDateTime fecha;
    private Integer total;
}
