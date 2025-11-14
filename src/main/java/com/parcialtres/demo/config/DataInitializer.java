package com.parcialtres.demo.config;

import com.parcialtres.demo.entity.Comentario;
import com.parcialtres.demo.entity.Producto;
import com.parcialtres.demo.entity.Usuario;
import com.parcialtres.demo.repository.ComentarioRepository;
import com.parcialtres.demo.repository.ProductoRepository;
import com.parcialtres.demo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final ComentarioRepository comentarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            log.info("Iniciando carga de datos iniciales...");

            cargarUsuarios();
            cargarProductos();
            cargarUsuariosAdicionales();
            cargarComentarios();

            log.info("Carga de datos completada exitosamente!");
            log.info("Total Usuarios: {}", usuarioRepository.count());
            log.info("Total Productos: {}", productoRepository.count());
            log.info("Total Comentarios: {}", comentarioRepository.count());
        } else {
            log.info("Los datos ya existen en la base de datos.");
        }
    }

    private void cargarUsuarios() {
        log.info("Cargando usuarios principales...");

        // Usuario 1: Juan Pérez
        Usuario juan = new Usuario();
        juan.setUsername("juan.perez");
        juan.setPassword(passwordEncoder.encode("Qwerty123"));
        juan.setEmail("juan.perez@email.com");
        juan.setRol("USER");
        juan.setDireccion("Carrera 45 #10-20");
        juan.setMetodoPago("Tarjeta de crédito");
        juan.setActivo(true);
        usuarioRepository.save(juan);

        // Usuario 2: Ana Gómez
        Usuario ana = new Usuario();
        ana.setUsername("ana.gomez");
        ana.setPassword(passwordEncoder.encode("Pass456"));
        ana.setEmail("ana.gomez@email.com");
        ana.setRol("USER");
        ana.setDireccion("Calle 21 #35-50");
        ana.setMetodoPago("PayPal");
        ana.setActivo(true);
        usuarioRepository.save(ana);

        // Usuario 3: Carlos Ruiz
        Usuario carlos = new Usuario();
        carlos.setUsername("carlos.ruiz");
        carlos.setPassword(passwordEncoder.encode("Segura789"));
        carlos.setEmail("carlos.ruiz@email.com");
        carlos.setRol("USER");
        carlos.setDireccion("Avenida Principal #100");
        carlos.setMetodoPago("Transferencia bancaria");
        carlos.setActivo(true);
        usuarioRepository.save(carlos);

        // Usuario 4: Sofía Martínez
        Usuario sofia = new Usuario();
        sofia.setUsername("sofia.martinez");
        sofia.setPassword(passwordEncoder.encode("Clave987"));
        sofia.setEmail("sofia.martinez@email.com");
        sofia.setRol("USER");
        sofia.setDireccion("Calle 8 #20-30");
        sofia.setMetodoPago("Efectivo");
        sofia.setActivo(true);
        usuarioRepository.save(sofia);

        // Usuario 5: Diego Fernández
        Usuario diego = new Usuario();
        diego.setUsername("diego.fernandez");
        diego.setPassword(passwordEncoder.encode("Contra654"));
        diego.setEmail("diego.fernandez@email.com");
        diego.setRol("ADMIN");
        diego.setDireccion("Carrera 77 #40-60");
        diego.setMetodoPago("Tarjeta débito");
        diego.setActivo(true);
        usuarioRepository.save(diego);

        log.info("5 usuarios principales cargados");
    }

    private void cargarProductos() {
        log.info("Cargando 50 productos...");

        Object[][] productosData = {
                { 1L, "Laptop", "Portátil con pantalla Full HD y SSD de 512GB", 89999.0, 10 },
                { 2L, "Smartphone", "Teléfono con cámara de 108MP y carga rápida", 49950.0, 20 },
                { 3L, "Tablet", "Dispositivo con pantalla táctil de 10 pulgadas", 29999.0, 15 },
                { 4L, "Auriculares", "Audífonos inalámbricos con cancelación de ruido", 12999.0, 25 },
                { 5L, "Teclado", "Teclado mecánico con iluminación RGB", 8999.0, 30 },
                { 6L, "Mouse", "Ratón inalámbrico con sensor óptico de alta precisión", 5999.0, 50 },
                { 7L, "Monitor", "Pantalla LED 4K de 27 pulgadas", 49900.0, 12 },
                { 8L, "Impresora", "Láser multifuncional con Wi-Fi", 17999.0, 18 },
                { 9L, "Cámara", "Cámara digital con lente profesional", 79999.0, 8 },
                { 10L, "Smartwatch", "Reloj inteligente con GPS y monitoreo cardíaco", 19999.0, 22 },
                { 11L, "Silla Gamer", "Silla ergonómica ajustable con soporte lumbar", 29999.0, 14 },
                { 12L, "Microondas", "Horno microondas con múltiples funciones", 12999.0, 40 },
                { 13L, "Refrigerador", "Frigorífico doble puerta con sistema No Frost", 119999.0, 5 },
                { 14L, "Lavadora", "Lavadora automática con capacidad de 10kg", 59999.0, 7 },
                { 15L, "Cafetera", "Cafetera express con vaporizador de leche", 14999.0, 35 },
                { 16L, "Drone", "Drone con cámara 4K y estabilizador", 69999.0, 9 },
                { 17L, "Bocina Bluetooth", "Altavoz portátil con sonido envolvente", 8999.0, 33 },
                { 18L, "Videocámara", "Videocámara profesional con grabación en 4K", 99999.0, 6 },
                { 19L, "TV LED", "Televisor inteligente de 55 pulgadas con HDR", 74999.0, 11 },
                { 20L, "Batería Externa", "Batería de 20000mAh con carga rápida", 3999.0, 45 },
                { 21L, "Disco Duro", "Disco duro externo de 2TB", 12999.0, 28 },
                { 22L, "Memoria USB", "Pendrive de 128GB", 2999.0, 60 },
                { 23L, "Router", "Router Wi-Fi 6 de alta velocidad", 19999.0, 16 },
                { 24L, "Joystick", "Control inalámbrico para videojuegos", 7999.0, 20 },
                { 25L, "Fuente de Poder", "Fuente de alimentación para PC de 750W", 8999.0, 17 },
                { 26L, "SSD", "Unidad de almacenamiento SSD de 1TB", 14999.0, 32 },
                { 27L, "Altavoces", "Par de bocinas estéreo con subwoofer", 13999.0, 23 },
                { 28L, "Webcam", "Cámara web Full HD con micrófono integrado", 6999.0, 37 },
                { 29L, "Procesador", "CPU Intel i7 de última generación", 34999.0, 9 },
                { 30L, "Motherboard", "Placa base compatible con procesadores modernos", 19999.0, 13 },
                { 31L, "Memoria RAM", "Módulo de RAM DDR4 de 16GB", 7999.0, 41 },
                { 32L, "Fuente Solar", "Panel solar portátil con batería integrada", 24999.0, 4 },
                { 33L, "Control Remoto", "Mando universal para TV y dispositivos", 2499.0, 50 },
                { 34L, "Termostato", "Termostato digital programable", 9999.0, 22 },
                { 35L, "Smart Lock", "Cerradura electrónica con huella digital", 19999.0, 6 },
                { 36L, "Proyector", "Proyector LED con resolución Full HD", 29999.0, 12 },
                { 37L, "Switch Ethernet", "Switch de red de 8 puertos", 5999.0, 38 },
                { 38L, "Reloj Digital", "Reloj inteligente con pantalla AMOLED", 8999.0, 26 },
                { 39L, "Luces LED", "Tiras LED RGB con control remoto", 3999.0, 55 },
                { 40L, "Estabilizador", "Estabilizador de voltaje para dispositivos electrónicos", 15999.0, 10 },
                { 41L, "Cargador Inalámbrico", "Base de carga inalámbrica rápida", 4999.0, 30 },
                { 42L, "HDD Externo", "Disco duro portátil de 4TB", 17999.0, 15 },
                { 43L, "Micrófono", "Micrófono profesional para grabación", 14999.0, 7 },
                { 44L, "Altavoz Inteligente", "Asistente de voz con altavoz integrado", 12999.0, 20 },
                { 45L, "Antena Wi-Fi", "Amplificador de señal inalámbrico", 7999.0, 33 },
                { 46L, "Climatizador", "Aire acondicionado portátil con control remoto", 29999.0, 5 },
                { 47L, "Raspberry Pi", "Kit de desarrollo con Raspberry Pi 4", 12999.0, 19 },
                { 48L, "Capturadora", "Placa de captura de video en alta resolución", 19999.0, 8 },
                { 49L, "Smart Plug", "Enchufe inteligente compatible con asistentes virtuales", 3999.0, 42 },
                { 50L, "Timbre Inteligente", "Timbre con cámara y conexión a Wi-Fi", 14999.0, 10 }
        };

        for (Object[] data : productosData) {
            Producto producto = new Producto();
            producto.setNombre((String) data[1]);
            producto.setDescripcion((String) data[2]);
            producto.setPrecio(BigDecimal.valueOf((Double) data[3]));
            producto.setStock((Integer) data[4]);
            producto.setActivo(true);
            productoRepository.save(producto);
        }

        log.info("50 productos cargados");
    }

    private void cargarUsuariosAdicionales() {
        log.info("Cargando usuarios adicionales para comentarios...");

        String[][] usuariosExtra = {
                { "lucia.rodriguez", "lucia.rodriguez@email.com", "Pass123" },
                { "andres.ramirez", "andres.ramirez@email.com", "Pass123" },
                { "maria.garcia", "maria.garcia@email.com", "Pass123" },
                { "javier.martinez", "javier.martinez@email.com", "Pass123" },
                { "carolina.lopez", "carolina.lopez@email.com", "Pass123" },
                { "daniel.castro", "daniel.castro@email.com", "Pass123" },
                { "paola.herrera", "paola.herrera@email.com", "Pass123" },
                { "esteban.rojas", "esteban.rojas@email.com", "Pass123" },
                { "fernanda.sanchez", "fernanda.sanchez@email.com", "Pass123" },
                { "camilo.torres", "camilo.torres@email.com", "Pass123" },
                { "gabriela.suarez", "gabriela.suarez@email.com", "Pass123" },
                { "raul.espinosa", "raul.espinosa@email.com", "Pass123" },
                { "veronica.mendoza", "veronica.mendoza@email.com", "Pass123" },
                { "fabio.jimenez", "fabio.jimenez@email.com", "Pass123" },
                { "ricardo.vargas", "ricardo.vargas@email.com", "Pass123" },
                { "silvia.gomez", "silvia.gomez@email.com", "Pass123" },
                { "martin.aguilar", "martin.aguilar@email.com", "Pass123" },
                { "valentina.perez", "valentina.perez@email.com", "Pass123" },
                { "jose.ramirez", "jose.ramirez@email.com", "Pass123" },
                { "natalia.correa", "natalia.correa@email.com", "Pass123" },
                { "julio.fernandez", "julio.fernandez@email.com", "Pass123" },
                { "amanda.castro", "amanda.castro@email.com", "Pass123" },
                { "pedro.duarte", "pedro.duarte@email.com", "Pass123" },
                { "isabela.medina", "isabela.medina@email.com", "Pass123" },
                { "oscar.rodriguez", "oscar.rodriguez@email.com", "Pass123" },
                { "cristina.vargas", "cristina.vargas@email.com", "Pass123" },
                { "mario.hernandez", "mario.hernandez@email.com", "Pass123" },
                { "sofia.ramirez", "sofia.ramirez@email.com", "Pass123" },
                { "andrea.gutierrez", "andrea.gutierrez@email.com", "Pass123" },
                { "pablo.medina", "pablo.medina@email.com", "Pass123" },
                { "patricia.lopez", "patricia.lopez@email.com", "Pass123" },
                { "gonzalo.espinoza", "gonzalo.espinoza@email.com", "Pass123" },
                { "elena.herrera", "elena.herrera@email.com", "Pass123" },
                { "diego.soto", "diego.soto@email.com", "Pass123" },
                { "miguel.rojas", "miguel.rojas@email.com", "Pass123" },
                { "estefania.carrillo", "estefania.carrillo@email.com", "Pass123" },
                { "manuel.vargas", "manuel.vargas@email.com", "Pass123" },
                { "luisa.mejia", "luisa.mejia@email.com", "Pass123" },
                { "victoria.torres", "victoria.torres@email.com", "Pass123" },
                { "federico.montoya", "federico.montoya@email.com", "Pass123" }
        };

        for (String[] userData : usuariosExtra) {
            Usuario usuario = new Usuario();
            usuario.setUsername(userData[0]);
            usuario.setPassword(passwordEncoder.encode(userData[2]));
            usuario.setEmail(userData[1]);
            usuario.setRol("USER");
            usuario.setActivo(true);
            usuarioRepository.save(usuario);
        }

        log.info("{} usuarios adicionales cargados", usuariosExtra.length);
    }

    private void cargarComentarios() {
        log.info("Cargando 50 comentarios...");

        Object[][] comentariosData = {
                { 1L, "juan.perez", "Excelente rendimiento; muy rápida. ¡Me encanta!", "2025-05-01" },
                { 2L, "ana.gomez", "Buena cámara pero la batería dura poco.", "2025-05-03" },
                { 3L, "carlos.ruiz", "No me gustó; pantalla de baja calidad.", "2025-05-05" },
                { 4L, "sofia.martinez", "Sonido aceptable pero el material parece frágil.", "2025-05-06" },
                { 5L, "diego.fernandez", "Muy buen teclado mecánico; excelente respuesta.", "2025-05-08" },
                { 6L, "ana.gomez", "El sensor no es tan preciso como esperaba.", "2025-05-10" },
                { 7L, "carlos.ruiz", "Colores vibrantes y buena resolución. Muy satisfecho.", "2025-05-12" },
                { 8L, "juan.perez", "Tarda mucho en imprimir; no me convence.", "2025-05-13" },
                { 9L, "sofia.martinez", "Increíble calidad de imagen; fotos súper nítidas.", "2025-05-15" },
                { 10L, "diego.fernandez", "Buena batería; pero la pantalla no es muy brillante.", "2025-05-18" },
                { 11L, "lucia.rodriguez", "Comodidad espectacular; perfecto para largas sesiones de juego.",
                        "2025-05-20" },
                { 12L, "andres.ramirez", "Calienta bien pero hace mucho ruido.", "2025-05-22" },
                { 13L, "maria.garcia", "Espacioso y enfría rápido; muy recomendado.", "2025-05-24" },
                { 14L, "javier.martinez", "Lava bien pero el ciclo es muy largo.", "2025-05-26" },
                { 15L, "carolina.lopez", "Hace café delicioso; fácil de usar.", "2025-05-28" },
                { 16L, "daniel.castro", "Muy divertido pero la batería dura poco.", "2025-05-30" },
                { 17L, "paola.herrera", "Sonido potente y buena conexión Bluetooth.", "2025-06-01" },
                { 18L, "esteban.rojas", "Perfecta para grabaciones profesionales.", "2025-06-03" },
                { 19L, "fernanda.sanchez", "Imagen excelente pero el sonido podría mejorar.", "2025-06-05" },
                { 20L, "camilo.torres", "Carga bien pero es un poco pesada.", "2025-06-07" },
                { 21L, "gabriela.suarez", "Gran capacidad de almacenamiento; funciona rápido.", "2025-06-09" },
                { 22L, "raul.espinosa", "Buen tamaño pero la velocidad de transferencia es baja.", "2025-06-11" },
                { 23L, "veronica.mendoza", "Señal potente; cubre toda la casa.", "2025-06-13" },
                { 24L, "fabio.jimenez", "Comodo y resistente; ideal para gaming.", "2025-06-15" },
                { 25L, "ricardo.vargas", "Funciona bien pero los cables son muy cortos.", "2025-06-17" },
                { 26L, "silvia.gomez", "Velocidad increíble; mi PC va mucho más rápido ahora.", "2025-06-19" },
                { 27L, "martin.aguilar", "Sonido envolvente; muy buena compra.", "2025-06-21" },
                { 28L, "valentina.perez", "Imagen clara pero el micrófono es deficiente.", "2025-06-23" },
                { 29L, "jose.ramirez", "Rendimiento impecable; ideal para gaming y diseño.", "2025-06-25" },
                { 30L, "natalia.correa", "Buenas prestaciones pero la instalación fue complicada.", "2025-06-27" },
                { 31L, "julio.fernandez", "Expande muy bien el rendimiento del sistema.", "2025-06-29" },
                { 32L, "amanda.castro", "Energía confiable pero la batería es pequeña.", "2025-07-01" },
                { 33L, "pedro.duarte", "Fácil de usar; reconoce muchos dispositivos.", "2025-07-03" },
                { 34L, "isabela.medina", "Regula bien la temperatura; intuitivo de usar.", "2025-07-05" },
                { 35L, "oscar.rodriguez", "Seguridad y tecnología en un solo dispositivo.", "2025-07-07" },
                { 36L, "cristina.vargas", "Imagen nítida pero requiere una sala oscura.", "2025-07-09" },
                { 37L, "mario.hernandez", "Buena velocidad de conexión; estable.", "2025-07-11" },
                { 38L, "sofia.ramirez", "Pantalla atractiva pero la batería dura poco.", "2025-07-13" },
                { 39L, "andrea.gutierrez", "Buenas opciones de colores; buen diseño.", "2025-07-15" },
                { 40L, "pablo.medina", "Protege bien contra variaciones de voltaje.", "2025-07-17" },
                { 41L, "patricia.lopez", "Carga rápido pero requiere posicionamiento preciso.", "2025-07-19" },
                { 42L, "gonzalo.espinoza", "Mucho espacio; resistente y confiable.", "2025-07-21" },
                { 43L, "elena.herrera", "Calidad de sonido profesional; ideal para podcast.", "2025-07-23" },
                { 44L, "diego.soto", "Responde bien a comandos de voz; útil en casa.", "2025-07-25" },
                { 45L, "miguel.rojas", "Amplifica bien la señal pero el rango es limitado.", "2025-07-27" },
                { 46L, "estefania.carrillo", "Enfría rápido pero es algo ruidoso.", "2025-07-29" },
                { 47L, "manuel.vargas", "Perfecto para proyectos electrónicos y programación.", "2025-07-31" },
                { 48L, "luisa.mejia", "Ideal para streaming y grabaciones en alta calidad.", "2025-08-02" },
                { 49L, "victoria.torres", "Muy práctico; fácil de conectar y configurar.", "2025-08-04" },
                { 50L, "federico.montoya", "Buena cámara; útil para seguridad.", "2025-08-06" }
        };

        for (Object[] data : comentariosData) {
            Long productoId = (Long) data[0];
            String username = (String) data[1];
            String contenido = (String) data[2];
            // String fecha = (String) data[3]; // Fecha no utilizada por ahora

            Producto producto = productoRepository.findById(productoId).orElse(null);
            Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);

            if (producto != null && usuario != null) {
                Comentario comentario = new Comentario();
                comentario.setProducto(producto);
                comentario.setUsuario(usuario);
                comentario.setContenido(contenido);
                comentarioRepository.save(comentario);
            }
        }

        log.info("50 comentarios cargados");
    }
}