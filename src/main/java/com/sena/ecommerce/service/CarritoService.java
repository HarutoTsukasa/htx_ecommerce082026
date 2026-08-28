package com.sena.ecommerce.service;

import com.sena.ecommerce.model.Producto;
import com.sena.ecommerce.model.dto.ItemCarrito;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Un carrito por sesión HTTP (bean con proxy de sesión). Se pierde si el
 * usuario cierra el navegador o expira la sesión — para un ecommerce real
 * en producción normalmente se persistiría por usuario, pero para el
 * alcance de este proyecto es razonable.
 */
@Service
@SessionScope
public class CarritoService {

	private final List<ItemCarrito> items = new ArrayList<>();

	public List<ItemCarrito> getItems() {
		return items;
	}

	public void agregar(Producto producto, int cantidad) {
		if (cantidad <= 0) {
			throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
		}

		int cantidadYaEnCarrito = items.stream()
				.filter(i -> i.getIdProducto().equals(producto.getIdProducto()))
				.mapToInt(ItemCarrito::getCantidad)
				.findFirst()
				.orElse(0);

		if (cantidadYaEnCarrito + cantidad > producto.getCantidad()) {
			throw new IllegalStateException("Solo hay " + producto.getCantidad() + " unidades disponibles de " + producto.getNombre());
		}

		Optional<ItemCarrito> existente = items.stream()
				.filter(i -> i.getIdProducto().equals(producto.getIdProducto()))
				.findFirst();

		if (existente.isPresent()) {
			existente.get().setCantidad(existente.get().getCantidad() + cantidad);
		} else {
			items.add(new ItemCarrito(producto.getIdProducto(), producto.getNombre(),
					producto.getPrecio(), cantidad, producto.getImagen()));
		}
	}

	public void actualizarCantidad(Long idProducto, int cantidad) {
		items.stream()
				.filter(i -> i.getIdProducto().equals(idProducto))
				.findFirst()
				.ifPresent(i -> {
					if (cantidad <= 0) {
						eliminar(idProducto);
					} else {
						i.setCantidad(cantidad);
					}
				});
	}

	public void eliminar(Long idProducto) {
		items.removeIf(i -> i.getIdProducto().equals(idProducto));
	}

	public Double getTotal() {
		return items.stream().mapToDouble(ItemCarrito::getSubtotal).sum();
	}

	public int getCantidadTotal() {
		return items.stream().mapToInt(ItemCarrito::getCantidad).sum();
	}

	public void vaciar() {
		items.clear();
	}

	public boolean estaVacio() {
		return items.isEmpty();
	}
}
