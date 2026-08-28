package com.sena.ecommerce.service;

import com.sena.ecommerce.model.Producto;
import com.sena.ecommerce.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

	private final ProductoRepository productoRepository;

	public List<Producto> listarTodos() {
		return productoRepository.findAll();
	}

	public List<Producto> buscar(String nombre) {
		if (nombre == null || nombre.isBlank()) {
			return listarTodos();
		}
		return productoRepository.findByNombreContainingIgnoreCase(nombre);
	}

	public Producto buscarPorId(Long id) {
		return productoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
	}

	public Producto guardar(Producto producto) {
		return productoRepository.save(producto);
	}

	public void eliminar(Long id) {
		productoRepository.deleteById(id);
	}

	public void descontarStock(Long idProducto, int cantidad) {
		Producto producto = buscarPorId(idProducto);
		int nuevaCantidad = producto.getCantidad() - cantidad;
		if (nuevaCantidad < 0) {
			throw new IllegalStateException("No hay suficiente stock de " + producto.getNombre());
		}
		producto.setCantidad(nuevaCantidad);
		productoRepository.save(producto);
	}
}
