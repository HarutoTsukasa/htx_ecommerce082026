package com.sena.ecommerce.controller;

import com.sena.ecommerce.model.Usuario;
import com.sena.ecommerce.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AuthController {

	private final UsuarioService usuarioService;

	@GetMapping("/registro")
	public String formularioRegistro(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "registro";
	}

	@PostMapping("/registro")
	public String registrar(@ModelAttribute Usuario usuario, Model model) {
		try {
			usuarioService.registrar(usuario);
			return "redirect:/login?registrado";
		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("usuario", usuario);
			return "registro";
		}
	}

	@GetMapping("/login")
	public String formularioLogin() {
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String password,
						 @RequestParam(required = false) String redirect,
						 HttpSession session, Model model) {
		Optional<Usuario> usuario = usuarioService.autenticar(email, password);
		if (usuario.isEmpty()) {
			model.addAttribute("error", "Correo o contraseña incorrectos.");
			return "login";
		}
		session.setAttribute("usuarioId", usuario.get().getIdUsuario());
		session.setAttribute("usuarioNombre", usuario.get().getNombre());
		session.setAttribute("usuarioTipo", usuario.get().getTipo());
		return "redirect:" + (redirect != null && !redirect.isBlank() ? redirect : "/");
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
