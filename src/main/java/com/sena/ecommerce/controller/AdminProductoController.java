package com.sena.ecommerce.controller;

import com.sena.ecommerce.model.Producto;
import com.sena.ecommerce.service.ProductoService;
import com.sena.ecommerce.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/productos")
public class AdminProductoController {

	private final ProductoService productoService;
	private final UsuarioService usuarioService;

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("productos", productoService.listarTodos());
		return "admin/productos";
	}

	@GetMapping("/nuevo")
	public String formularioNuevo(Model model) {
		model.addAttribute("producto", new Producto());
		return "admin/producto-form";
	}

	@GetMapping("/{id}/editar")
	public String formularioEditar(@PathVariable Long id, Model model) {
		model.addAttribute("producto", productoService.buscarPorId(id));
		return "admin/producto-form";
	}

	@PostMapping("/guardar")
	public String guardar(@ModelAttribute Producto producto, HttpSession session,
						   RedirectAttributes redirectAttributes, @RequestParam("img") MultipartFile file) {
		Long usuarioId = (Long) session.getAttribute("usuarioId");
		producto.setUsuario(usuarioService.buscarPorId(usuarioId));
		productoService.guardar(producto);
		redirectAttributes.addFlashAttribute("mensaje", "Producto guardado.");
		return "redirect:/admin/productos";
	}

	@PostMapping("/{id}/eliminar")
	public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		productoService.eliminar(id);
		redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado.");
		return "redirect:/admin/productos";
	}
}
