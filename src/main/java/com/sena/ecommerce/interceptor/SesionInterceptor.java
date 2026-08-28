package com.sena.ecommerce.interceptor;

import com.sena.ecommerce.model.TipoUsuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Sustituto liviano de Spring Security: exige sesión iniciada para
 * carrito/checkout/pedidos, y rol ADMIN para /admin/**. No reemplaza
 * protección CSRF, límites de intentos de login, ni gestión declarativa
 * de roles — para eso haría falta spring-boot-starter-security de verdad.
 */
@Component
public class SesionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		Long usuarioId = (Long) session.getAttribute("usuarioId");
		String uri = request.getRequestURI();

		if (usuarioId == null) {
			response.sendRedirect(request.getContextPath() + "/login?redirect=" + uri);
			return false;
		}

		if (uri.startsWith(request.getContextPath() + "/admin")) {
			TipoUsuario tipo = (TipoUsuario) session.getAttribute("usuarioTipo");
			if (tipo != TipoUsuario.ADMIN) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tienes permisos de administrador.");
				return false;
			}
		}
		return true;
	}
}
