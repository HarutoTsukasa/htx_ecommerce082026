package com.sena.ecommerce.controller;

import com.sena.ecommerce.model.Orden;
import com.sena.ecommerce.model.Usuario;
import com.sena.ecommerce.service.CarritoService;
import com.sena.ecommerce.service.OrdenService;
import com.sena.ecommerce.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class CheckoutController {

	private final CarritoService carritoService;
	private final OrdenService ordenService;
	private final UsuarioService usuarioService;

	@GetMapping("/checkout")
	public String resumen(Model model) {
		if (carritoService.estaVacio()) {
			return "redirect:/carrito";
		}
		model.addAttribute("items", carritoService.getItems());
		model.addAttribute("total", carritoService.getTotal());
		return "checkout";
	}

	@PostMapping("/checkout/confirmar")
	public String confirmar(HttpSession session, RedirectAttributes redirectAttributes) {
		Long usuarioId = (Long) session.getAttribute("usuarioId");
		Usuario usuario = usuarioService.buscarPorId(usuarioId);
		try {
			Orden orden = ordenService.confirmarPedido(usuario, carritoService.getItems());
			carritoService.vaciar();
			redirectAttributes.addFlashAttribute("mensaje", "Pedido " + orden.getNumero() + " creado con éxito.");
			return "redirect:/pedidos/" + orden.getIdOrden();
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/carrito";
		}
	}
}
