package com.sena.ecommerce.service;

import com.sena.ecommerce.model.TipoUsuario;
import com.sena.ecommerce.model.Usuario;
import com.sena.ecommerce.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;

	public Usuario registrar(Usuario usuario) {
		if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
			throw new IllegalArgumentException("El correo es obligatorio.");
		}
		if (usuarioRepository.existsByEmail(usuario.getEmail())) {
			throw new IllegalArgumentException("Ya existe una cuenta con ese correo.");
		}
		if (usuario.getPassword() == null || usuario.getPassword().length() < 6) {
			throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
		}
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		if (usuario.getTipo() == null) {
			usuario.setTipo(TipoUsuario.USER);
		}
		return usuarioRepository.save(usuario);
	}

	public Optional<Usuario> autenticar(String email, String passwordPlano) {
		return usuarioRepository.findByEmail(email)
				.filter(u -> passwordEncoder.matches(passwordPlano, u.getPassword()));
	}

	public Usuario buscarPorId(Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
	}
}
