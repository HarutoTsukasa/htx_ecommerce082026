package com.sena.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "productos")
public class Producto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProducto;
	private String nombre;
	@Lob
	private String descripcion;
	private String imagen;
	private Double precio;
	private Integer cantidad;

	@ManyToOne
	@JoinColumn(name = "idUsuario")
	private Usuario usuario;

}
