package com.sena.ecommerce.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "ordenes")
public class Orden {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idOrden;
	private String numero;
	private LocalDate fechacreacion;
	private LocalDate fecharecibida;
	private Double total;

	@ManyToOne
	@JoinColumn(name = "idUsuario")
	private Usuario usuario;

	@OneToMany(mappedBy = "orden", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DetalleOrden> detalle;

}
