package com.sena.ecommerce.controller;

import com.sena.ecommerce.service.CarritoService;
import com.sena.ecommerce.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carrito")
public class CarritoController {

	private final CarritoService carritoService;
	private final ProductoService productoService;

	@GetMapping
	public String ver(Model model) {
		model.addAttribute("items", carritoService.getItems());
		model.addAttribute("total", carritoService.getTotal());
		return "carrito";
	}

	@PostMapping("/agregar")
	public String agregar(@RequestParam Long idProducto,
						   @RequestParam(defaultValue = "1") Integer cantidad,
						   RedirectAttributes redirectAttributes) {
		try {
			carritoService.agregar(productoService.buscarPorId(idProducto), cantidad);
			redirectAttributes.addFlashAttribute("mensaje", "Producto agregado al carrito.");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/productos/" + idProducto;
	}

	@PostMapping("/actualizar")
	public String actualizar(@RequestParam Long idProducto, @RequestParam Integer cantidad) {
		carritoService.actualizarCantidad(idProducto, cantidad);
		return "redirect:/carrito";
	}

	@PostMapping("/eliminar")
	public String eliminar(@RequestParam Long idProducto) {
		carritoService.eliminar(idProducto);
		return "redirect:/carrito";
	}
}
