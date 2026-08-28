package com.sena.ecommerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa una línea del carrito de compras mientras el usuario todavía
 * está decidiendo qué llevar. Deliberadamente NO es la entidad DetalleOrden:
 * un carrito no debería vivir en la base de datos hasta que se confirma el
 * pedido, así que se mapea a DetalleOrden solo en el checkout.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarrito {
	private Long idProducto;
	private String nombre;
	private Double precio;
	private Integer cantidad;
	private String imagen;

	public Double getSubtotal() {
		if (precio == null || cantidad == null) {
			return 0.0;
		}
		return precio * cantidad;
	}
}
