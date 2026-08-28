package com.sena.ecommerce.service;

import com.sena.ecommerce.model.DetalleOrden;
import com.sena.ecommerce.model.Orden;
import com.sena.ecommerce.model.Usuario;
import com.sena.ecommerce.model.dto.ItemCarrito;
import com.sena.ecommerce.repository.OrdenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdenService {

	private final OrdenRepository ordenRepository;
	private final ProductoService productoService;

	@Transactional
	public Orden confirmarPedido(Usuario usuario, List<ItemCarrito> items) {
		if (items.isEmpty()) {
			throw new IllegalStateException("El carrito está vacío.");
		}

		double total = items.stream().mapToDouble(ItemCarrito::getSubtotal).sum();

		Orden orden = new Orden();
		orden.setNumero("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
		orden.setFechacreacion(LocalDate.now());
		orden.setFecharecibida(null);
		orden.setTotal(total);
		orden.setUsuario(usuario);

		List<DetalleOrden> detalles = new ArrayList<>();
		for (ItemCarrito item : items) {
			// Descuenta stock antes de crear el detalle: si no hay suficiente,
			// esto lanza y ninguna otra línea de la orden se llega a guardar
			// gracias a @Transactional.
			productoService.descontarStock(item.getIdProducto(), item.getCantidad());

			DetalleOrden detalle = new DetalleOrden();
			detalle.setNombre(item.getNombre());
			detalle.setCantidad(item.getCantidad());
			detalle.setPrecio(item.getPrecio());
			detalle.setTotal(item.getSubtotal());
			detalle.setOrden(orden);
			detalle.setProducto(productoService.buscarPorId(item.getIdProducto()));
			detalles.add(detalle);
		}
		orden.setDetalle(detalles);

		return ordenRepository.save(orden);
	}

	public List<Orden> listarPorUsuario(Usuario usuario) {
		return ordenRepository.findByUsuarioOrderByFechacreacionDesc(usuario);
	}

	public Orden buscarPorId(Long id) {
		return ordenRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
	}
}
