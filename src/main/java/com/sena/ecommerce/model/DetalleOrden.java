package com.sena.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "detalles")
public class DetalleOrden {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDetalleOrden;
	private String nombre;
	private Integer cantidad;
	private Double precio;
	private Double total;

	@ManyToOne
	@JoinColumn(name = "idOrden")
	private Orden orden;

	@ManyToOne
	@JoinColumn(name = "idProducto")
	private Producto producto;
}
