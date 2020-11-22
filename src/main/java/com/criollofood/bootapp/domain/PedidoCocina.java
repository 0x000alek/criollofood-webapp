package com.criollofood.bootapp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PedidoCocina implements Serializable {
    private Long pedidoRecetaId;
    private Long atencionId;
    private Long pedidoId;
    private String codigoAtencion;
    private Long numeroMesa;
    private String comentario;
    private LocalDateTime fechaIngreso;
    private String garzon;
    private Long recetaId;
    private String nombreReceta;
    private String descripcionReceta;
    private String imagen;
    private String ingredientes;
    private Integer tiempo;
    private boolean atrasado;
}
