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
	private Long id;
	private String nombre;
	private Integer cantidad;
	private Double precio;
	private Double total;

	@ManyToOne
	private Orden orden;

	@ManyToOne
	private Producto producto;
}
