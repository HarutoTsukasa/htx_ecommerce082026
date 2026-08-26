package com.sena.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuario;
	private String nombre;
	private String direccion;
	private String telefono;
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipo; // rol de usuario
	private String email;
	private String password;

}
