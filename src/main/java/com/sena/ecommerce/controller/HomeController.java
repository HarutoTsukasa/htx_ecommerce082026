package com.sena.ecommerce.controller;

import com.sena.ecommerce.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final ProductoService productoService;

	@GetMapping("/")
	public String index(@RequestParam(required = false) String buscar, Model model) {
		model.addAttribute("productos", productoService.buscar(buscar));
		model.addAttribute("buscar", buscar);
		return "index";
	}

	@GetMapping("/productos/{id}")
	public String detalle(@PathVariable Long id, Model model) {
		model.addAttribute("producto", productoService.buscarPorId(id));
		return "producto-detalle";
	}
}
