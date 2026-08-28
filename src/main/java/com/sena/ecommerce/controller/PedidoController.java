package com.sena.ecommerce.controller;

import com.sena.ecommerce.model.Orden;
import com.sena.ecommerce.model.TipoUsuario;
import com.sena.ecommerce.model.Usuario;
import com.sena.ecommerce.service.OrdenService;
import com.sena.ecommerce.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PedidoController {

	private final OrdenService ordenService;
	private final UsuarioService usuarioService;

	@GetMapping("/pedidos")
	public String listar(HttpSession session, Model model) {
		Usuario usuario = usuarioService.buscarPorId((Long) session.getAttribute("usuarioId"));
		model.addAttribute("pedidos", ordenService.listarPorUsuario(usuario));
		return "pedidos";
	}

	@GetMapping("/pedidos/{id}")
	public String detalle(@PathVariable Long id, HttpSession session, Model model) {
		Orden orden = ordenService.buscarPorId(id);
		Long usuarioId = (Long) session.getAttribute("usuarioId");
		TipoUsuario tipo = (TipoUsuario) session.getAttribute("usuarioTipo");

		boolean esDueno = orden.getUsuario() != null && orden.getUsuario().getIdUsuario().equals(usuarioId);
		if (!esDueno && tipo != TipoUsuario.ADMIN) {
			throw new IllegalArgumentException("Pedido no encontrado");
		}

		model.addAttribute("pedido", orden);
		return "pedido-detalle";
	}
}
