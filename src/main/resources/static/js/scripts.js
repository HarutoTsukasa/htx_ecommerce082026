// datatables
$(document).ready(function() {
    $("#myTable").DataTable({
        language: {
            url: "//cdn.datatables.net/plug-ins/2.3.8/i18n/es-ES.json",
        },
        pageLength: 5,
        lengthMenu: [
            [5, 10, 25, 50, 100],
            [5, 10, 25, 50, 100],
        ],
    });
});
// Botón Volver Arriba
const backToTopBtn = document.querySelector('.back-to-top');

window.addEventListener('scroll', function() {
    if (window.pageYOffset > 300) {
        backToTopBtn.classList.add('show');
    } else {
        backToTopBtn.classList.remove('show');
    }
});

backToTopBtn.addEventListener('click', function(e) {
    e.preventDefault();
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    });
});
document.addEventListener('DOMContentLoaded', function() {
    // Inicializar tooltips
    const tooltip = new bootstrap.Tooltip(document.querySelector('.whatsapp-btn'));

    // Mantener centrado vertical durante el scroll
    window.addEventListener('scroll', function() {
        const btn = document.querySelector('.whatsapp-btn');
        btn.style.top = '50%';
    });
});
// ========== MODO OSCURO ==========
// Función para cambiar el tema
function setTheme(theme) {
    const htmlElement = document.documentElement; // <html>
    htmlElement.setAttribute('data-bs-theme', theme);

    // Guardar preferencia
    localStorage.setItem('theme', theme);

    // Actualizar botón: icono y tooltip
    const button = document.getElementById('darkModeToggle');
    if (button) {
        const icon = button.querySelector('i');
        if (theme === 'dark') {
            icon.classList.remove('fa-moon');
            icon.classList.add('fa-sun');
            button.setAttribute('title', 'Modo Claro');
        } else {
            icon.classList.remove('fa-sun');
            icon.classList.add('fa-moon');
            button.setAttribute('title', 'Modo Oscuro');
        }
        // Si usas Bootstrap Tooltip, actualiza el tooltip
        if (typeof bootstrap !== 'undefined' && bootstrap.Tooltip) {
            const tooltip = bootstrap.Tooltip.getInstance(button);
            if (tooltip) tooltip.dispose();
            new bootstrap.Tooltip(button, { placement: 'bottom' });
        }
    }
}

// Alternar entre light y dark
function toggleTheme() {
    const currentTheme = document.documentElement.getAttribute('data-bs-theme');
    const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
    setTheme(newTheme);
}

// Al cargar la página: leer localStorage y aplicar
document.addEventListener('DOMContentLoaded', () => {
    const savedTheme = localStorage.getItem('theme');
    // Si existe preferencia guardada, la usamos; si no, detectar sistema o default 'light'
    if (savedTheme) {
        setTheme(savedTheme);
    } else {
        // Opcional: detectar preferencia del sistema operativo
        const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
        setTheme(prefersDark ? 'dark' : 'light');
    }

    // Asignar evento click al botón
    const toggleBtn = document.getElementById('darkModeToggle');
    if (toggleBtn) {
        toggleBtn.addEventListener('click', toggleTheme);
    }
});
// ========== tooltip ==============
document.addEventListener('DOMContentLoaded', function() {
    // Función para inicializar tooltip en un elemento, eliminando uno previo si existe
    function initTooltip(element) {
        if (!element) return;
        // Si ya tiene un tooltip activo, lo destruimos para evitar duplicados
        let existingTooltip = bootstrap.Tooltip.getInstance(element);
        if (existingTooltip) existingTooltip.dispose();
        // Inicializamos nuevo tooltip
        return new bootstrap.Tooltip(element, {
            placement: element.getAttribute('data-bs-placement') || 'bottom',
            title: element.getAttribute('title') || ''
        });
    }

    // Inicializar tooltip para el botón Carrito (#cartToggle)
    const cartBtn = document.getElementById('cartToggle');
    if (cartBtn) {
        initTooltip(cartBtn);
        // Aseguramos que el tooltip no se duplique si el botón es recreado dinámicamente
    }

    // Inicializar tooltip para el botón Login (#loginToggle)
    const loginBtn = document.getElementById('loginToggle');
    if (loginBtn) {
        // Como loginBtn tiene data-bs-toggle="modal", Bootstrap puede interferir.
        // Forzamos el tooltip manualmente y configuramos para que no cierre al hacer clic (opcional)
        const tooltipLogin = initTooltip(loginBtn);
        // Opcional: evitar que el tooltip se cierre al abrir el modal (mejor experiencia)
        if (tooltipLogin) {
            loginBtn.addEventListener('click', function() {
                tooltipLogin.hide();
            });
        }
    }
});