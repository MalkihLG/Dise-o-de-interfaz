import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * ConversorUI.java
 * Aplicación Swing con CardLayout que contiene:
 * - Inicio, Login, Registro, Recuperar contraseña
 * - Menú de categorías (Longitud, Masa, Temperatura, Energía, Volumen)
 * Fecha de creación: 20/10/2025
 * Diseño de Interfaz: Edgar Nicolás Ibarra Flores
 * Programador: Diana Nicole Uribe Hernández
 */

public class ConversorUI extends JFrame {

    private static final long serialVersionUID = 1L;

    // Control de pantallas     
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Colores según prototipo
    private final Color VERDE_OSCURO = Color.decode("#006b5c"); // botones principales
    private final Color VERDE_MENTA = Color.decode("#c9e0d3"); // fondo general
    private final Color BLANCO_GRIS = Color.decode("#f5fbf6"); // campos
    private final Color VERDE_PANTONE = Color.decode("#007e67");
    private final Color VERDE_PASTEL = Color.decode("#c9e0d3");
    private final Color GRIS_TEXTO = Color.DARK_GRAY;
    
    // Etiqueta global para mostrar resultados
    private JLabel resultadoLabel;

    // Almacenamiento simple de usuarios y contraseñas con ArrayList
    private ArrayList<String> correos = new ArrayList<>();
    private ArrayList<String> contrasenas = new ArrayList<>();

    // Constructor: configura ventana y agrega pantallas
    public ConversorUI() {
        setTitle("Conversor de Unidades Básicas");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setUndecorated(true); // Quita los botones y la barra superior
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre en pantalla completa
        setResizable(true);
        setMinimumSize(new Dimension(900, 600));

        configurarEstiloGlobal();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Agregar pantallas al CardLayout
        mainPanel.add(crearPantallaInicio(), "Inicio");
        mainPanel.add(crearPantallaLogin(), "Login");
        mainPanel.add(crearPantallaRegistro(), "Registro");
        mainPanel.add(crearPantallaRecuperar(), "Recuperar");
        mainPanel.add(crearPantallaMenu(), "Menu");
        mainPanel.add(crearPantallaConversion("Longitud"), "Longitud");
        mainPanel.add(crearPantallaConversion("Masa"), "Masa");
        mainPanel.add(crearPantallaConversion("Temperatura"), "Temperatura");
        mainPanel.add(crearPantallaConversion("Energia"), "Energia");
        mainPanel.add(crearPantallaConversion("Volumen"), "Volumen");
        mainPanel.add(crearPantallaResultado(), "Resultado");

        add(mainPanel);

      // --- BOTÓN "X" FLOTANTE ---
JButton cerrarBtn = new JButton("X");
cerrarBtn.setForeground(Color.WHITE);
cerrarBtn.setBackground(Color.RED.darker());
cerrarBtn.setFocusPainted(false);
cerrarBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
cerrarBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
cerrarBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

// Añadimos ActionListener
cerrarBtn.addActionListener(e -> {
    int opcion = JOptionPane.showOptionDialog(
        null,
        "¿Realmente quieres salir?",
        "Confirmar salida",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        new Object[]{"Sí", "No"},
        "No" // valor por defecto
    );

    if (opcion == JOptionPane.YES_OPTION) {
        System.exit(0);
    }
});

        getLayeredPane().add(cerrarBtn, JLayeredPane.PALETTE_LAYER);
        cerrarBtn.setBounds(getWidth() - 60, 10, 50, 30);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                cerrarBtn.setBounds(getWidth() - 60, 10, 50, 30);
            }
        });

        setVisible(true);
        mostrarPantalla("Inicio");

        // Usuario de ejemplo
        correos.add("admin");
        contrasenas.add("1234");
    }

    /**
     * Ajustes visuales globales (fuentes, colores base para componentes).
     */
    private void configurarEstiloGlobal() {
        UIManager.put("Panel.background", VERDE_MENTA);
        UIManager.put("Label.font", new Font("SansSerif", Font.PLAIN, 14));
        UIManager.put("Label.foreground", GRIS_TEXTO);
        UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 15));
        UIManager.put("TextField.font", new Font("SansSerif", Font.PLAIN, 14));
    }

    // -------------------- PANTALLAS --------------------
    private JPanel crearPantallaInicio() {
        JPanel panel = crearPanelBase("Selecciona una opción para iniciar o salir del programa.");
        JPanel centro = (JPanel) panel.getClientProperty("centro");

        ImageIcon logo = new ImageIcon("logo_conalep.png");
        Image img = logo.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(img));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centro.add(logoLabel);
        centro.add(Box.createVerticalStrut(15));
        
        JLabel titulo = crearTitulo("CONVERSOR DE UNIDADES BÁSICAS");
        JButton iniciar = crearBotonPrincipal("INICIAR", e -> mostrarPantalla("Login"));
        iniciar.setToolTipText("Haz clic para iniciar sesión o crear cuenta");

        centro.add(Box.createVerticalGlue());
        centro.add(titulo);
        centro.add(Box.createVerticalStrut(20));
        centro.add(iniciar);
        centro.add(Box.createVerticalGlue());
        habilitarNavegacionPorTeclado(panel);
        return panel;
    }
    // 2. Pantalla de Login
    // Fecha: 21/10/2025 - Responsable: Diana Nicole Uribe Hernández
    private JPanel crearPantallaLogin() {
        JPanel panel = crearPanelBase("Ingresa tu usuario y contraseña.");
        JPanel centro = (JPanel) panel.getClientProperty("centro");

        JLabel titulo = crearTitulo("INICIO DE SESIÓN");
        centro.add(titulo);
        centro.add(Box.createVerticalStrut(20));

        centro.add(crearEtiqueta("Usuario o correo:"));
        JTextField usuarioField = crearCampoTexto();
        usuarioField.setToolTipText("Ingresa tu usuario o correo registrado");
        centro.add(usuarioField);
        centro.add(Box.createVerticalStrut(15));

        centro.add(crearEtiqueta("Contraseña:"));
        JPasswordField passField = crearCampoPassword();
        passField.setToolTipText("Ingresa tu contraseña");
        centro.add(passField);
        centro.add(Box.createVerticalStrut(10));

        JButton enviar = crearBotonPrincipal("ENVIAR", e -> {
            String usuario = usuarioField.getText().trim();
            String pass = new String(passField.getPassword());
            boolean encontrado = false;

            for (int i = 0; i < correos.size(); i++) {
                if (correos.get(i).equals(usuario) && contrasenas.get(i).equals(pass)) {
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión correcto.");
                mostrarPantalla("Menu");
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        enviar.setToolTipText("Haz clic para iniciar sesión");
        centro.add(enviar);
        centro.add(Box.createVerticalStrut(8));

        JButton crearCuenta = crearBotonSecundario("CREAR NUEVA CUENTA", e -> mostrarPantalla("Registro"));
        crearCuenta.setToolTipText("Haz clic para crear una nueva cuenta");
        centro.add(crearCuenta);
        centro.add(Box.createVerticalStrut(6));

        JButton olvide = crearBotonTexto("Olvidé mi contraseña", e -> mostrarPantalla("Recuperar"));
        olvide.setToolTipText("Recupera tu contraseña olvidada");
        centro.add(olvide);
        centro.add(Box.createVerticalStrut(6));

        JButton atras = crearBotonPrincipal("ATRÁS", e -> mostrarPantalla("Inicio"));
        atras.setToolTipText("Regresa a la pantalla de inicio");
        centro.add(atras);
        habilitarNavegacionPorTeclado(panel);
        return panel;
    }
     // 3. Pantalla de Registro
    // Fecha: 21/10/2025 - Responsable: Diana Nicole Uribe Hernández
    private JPanel crearPantallaRegistro() {
        JPanel panel = crearPanelBase("Completa los campos para registrar una cuenta.");
        JPanel centro = (JPanel) panel.getClientProperty("centro");

        JLabel titulo = crearTitulo("CREAR CUENTA");
        centro.add(titulo);
        centro.add(Box.createVerticalStrut(20));

        centro.add(crearEtiqueta("Correo o usuario:"));
        JTextField email = crearCampoTexto();
        centro.add(email);
        centro.add(Box.createVerticalStrut(10));

        centro.add(crearEtiqueta("Contraseña:"));
        JPasswordField pass = crearCampoPassword();
        centro.add(pass);
        centro.add(Box.createVerticalStrut(10));

        centro.add(crearEtiqueta("Confirmar contraseña:"));
        JPasswordField confirmPass = crearCampoPassword();
        centro.add(confirmPass);
        centro.add(Box.createVerticalStrut(20));

        JButton crear = crearBotonPrincipal("CREAR", e -> {
            String correo = email.getText().trim();
            String contra = new String(pass.getPassword());
            String confirm = new String(confirmPass.getPassword());

            if (correo.isEmpty() || contra.isEmpty() || confirm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!contra.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (correos.contains(correo)) {
                JOptionPane.showMessageDialog(this, "Ese usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            correos.add(correo);
            contrasenas.add(contra);
            JOptionPane.showMessageDialog(this, "Cuenta creada correctamente.", "Registro", JOptionPane.INFORMATION_MESSAGE);
            mostrarPantalla("Login");
        });
        crear.setToolTipText("Crea tu nueva cuenta");
        centro.add(crear);
        
        centro.add(Box.createVerticalStrut(15));
        JButton volver = crearBotonPrincipal("VOLVER", e -> mostrarPantalla("Login"));
        centro.add(volver);
        habilitarNavegacionPorTeclado(panel);
        return panel;
    }

    private JPanel crearPantallaRecuperar() {
        JPanel panel = crearPanelBase("Recupera el acceso a tu cuenta.");
        JPanel centro = (JPanel) panel.getClientProperty("centro");

        JLabel titulo = crearTitulo("RECUPERAR CONTRASEÑA");
        centro.add(titulo);
        centro.add(Box.createVerticalStrut(20));

        centro.add(crearEtiqueta("Correo electrónico o usuario:"));
        JTextField usuario = crearCampoTexto();
        centro.add(usuario);
        centro.add(Box.createVerticalStrut(10));

        centro.add(crearEtiqueta("Nueva contraseña:"));
        JPasswordField nueva = crearCampoPassword();
        centro.add(nueva);
        centro.add(Box.createVerticalStrut(10));

        centro.add(crearEtiqueta("Confirmar nueva contraseña:"));
        JPasswordField confirmar = crearCampoPassword();
        centro.add(confirmar);
        centro.add(Box.createVerticalStrut(20));

        JButton actualizar = crearBotonPrincipal("ACTUALIZAR", e -> {
            String user = usuario.getText().trim();
            String pass1 = new String(nueva.getPassword());
            String pass2 = new String(confirmar.getPassword());

            if (user.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!pass1.equals(pass2)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int index = correos.indexOf(user);
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "El usuario no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            contrasenas.set(index, pass1);
            JOptionPane.showMessageDialog(this, "Contraseña actualizada correctamente.");
            mostrarPantalla("Login");
        });
        actualizar.setToolTipText("Actualiza tu contraseña olvidada");
        centro.add(actualizar);

        centro.add(Box.createVerticalStrut(8));
        JButton volver = crearBotonPrincipal("VOLVER", e -> mostrarPantalla("Login"));
        centro.add(volver);
        habilitarNavegacionPorTeclado(panel);
        return panel;
    }

    private JPanel crearPantallaMenu() {
    JPanel panel = crearPanelBase("Selecciona la categoría que deseas convertir.");
    JPanel centro = (JPanel) panel.getClientProperty("centro");

    JLabel titulo = crearTitulo("¿QUÉ UNIDAD QUIERES CONVERTIR?");
    centro.add(titulo);
    centro.add(Box.createVerticalStrut(15));

    JButton energia = crearBotonPrincipal("ENERGÍA", e -> mostrarPantalla("Energía"));
    energia.setToolTipText("Convertir unidades de energía");
    centro.add(energia);
    centro.add(Box.createVerticalStrut(10));

    JButton longitud = crearBotonPrincipal("LONGITUD", e -> mostrarPantalla("Longitud"));
    longitud.setToolTipText("Convertir unidades de longitud");
    centro.add(longitud);
    centro.add(Box.createVerticalStrut(10));

    JButton masa = crearBotonPrincipal("MASA", e -> mostrarPantalla("Masa"));
    masa.setToolTipText("Convertir unidades de masa");
    centro.add(masa);
    centro.add(Box.createVerticalStrut(10));

    JButton temperatura = crearBotonPrincipal("TEMPERATURA", e -> mostrarPantalla("Temperatura"));
    temperatura.setToolTipText("Convertir unidades de temperatura");
    centro.add(temperatura);
    centro.add(Box.createVerticalStrut(10));

    JButton volumen = crearBotonPrincipal("VOLUMEN", e -> mostrarPantalla("Volumen"));
    volumen.setToolTipText("Convertir unidades de volumen");
    centro.add(volumen);
    centro.add(Box.createVerticalStrut(20));

    JButton atras = crearBotonPrincipal("ATRÁS", e -> mostrarPantalla("Login"));
    atras.setToolTipText("Regresar al inicio");
    centro.add(atras);

         // Botón pequeño "Acerca de" (esquina inferior derecha)
    JButton acercaDe = new JButton(" ℹ️ ");
    acercaDe.setPreferredSize(new Dimension(30, 25));
    acercaDe.setBackground(Color.decode("#007e67")); // Verde Pantone
    acercaDe.setForeground(Color.WHITE);
    acercaDe.setFocusPainted(false);
    acercaDe.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
    acercaDe.setFont(new Font("SansSerif", Font.BOLD, 12));
    acercaDe.setToolTipText("Información sobre el programa");

    acercaDe.addActionListener(e -> {
    JOptionPane.showMessageDialog(this,
        "<html><center>"
         + "<div style='text-align: center; font-family: SansSerif; padding: 50px;'>"
        + "<b>Conversor de Unidades Básicas</b><br>"
        + "Versión 1.0<br><br>"
        + "Desarrollado con fines educativos.<br>"
        + "Este programa permite convertir entre diferentes unidades de medida<br>"
        + "como energía, masa, longitud, temperatura y volumen.<br><br>"
        + "<b>Creado por:</b> SOFTEDGE<br><br>"
        + "<b>Equipo de desarrollo:</b><br>"
        + "Líder: <b>José Alberto Hernández Cruz</b><br>"
        + "Analista: <b>Edgar Nicolás Ibarra Flores</b><br>"
        + "Diseñador: <b>Ernesto Antonio Corona Cortés</b><br>"
        + "Programador: <b>Diana Nicole Uribe Hernández</b><br>"
        + "Tester: <b>Malkih López González</b><br><br>"
        + "<i>Proyecto académico para CONALEP</i><br>"
        + "<small>© 2025 Todos los derechos reservados</small>"
        + "</center></html>",
        "Acerca de", JOptionPane.INFORMATION_MESSAGE);
});

    // Panel para colocarlo abajo a la derecha
    JPanel esquinaInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
    esquinaInferior.setOpaque(false);
    esquinaInferior.add(acercaDe);

    panel.add(esquinaInferior, BorderLayout.SOUTH);


habilitarNavegacionPorTeclado(panel);
return panel;

}

    // Pantalla genérica de conversión (usa tipo para cambiar las unidades)
    private JPanel crearPantallaConversion(String tipo) {
        JPanel panel = crearPanelBase(textoNotaPorTipo(tipo));
        JPanel centro = (JPanel) panel.getClientProperty("centro");

        JLabel titulo = crearTitulo(tipo.toUpperCase());
        centro.add(titulo);
        centro.add(Box.createVerticalStrut(10));

        centro.add(crearEtiqueta("Valor a convertir:"));
        JTextField campoValor = crearCampoTexto();
        campoValor.setToolTipText("Ingresa el valor numérico que deseas convertir");
        centro.add(campoValor);
        centro.add(Box.createVerticalStrut(10));

        centro.add(crearEtiqueta("Unidad a convertir:"));
        JComboBox<String> origenCombo = crearComboUnidades(tipo);
        origenCombo.setToolTipText("Selecciona la unidad de origen");
        centro.add(origenCombo);
        centro.add(Box.createVerticalStrut(10));

        centro.add(crearEtiqueta("Unidad final:"));
        JComboBox<String> destinoCombo = crearComboUnidades(tipo);
        destinoCombo.setToolTipText("Selecciona la unidad final");
        centro.add(destinoCombo);
        centro.add(Box.createVerticalStrut(20));

        // Botón convertir: hace la conversión y muestra pantalla resultado
        JButton convertirBtn = crearBotonPrincipal("CONVERTIR", e -> {
    try {
        // Obtener valores del usuario
        double valor = Double.parseDouble(campoValor.getText().replace(",", ".").trim());
        String origen = (String) origenCombo.getSelectedItem();
        String destino = (String) destinoCombo.getSelectedItem();

        // Realizar conversión según tipo
        double resultado = convertir(valor, origen, destino, tipo);

        // Formatear resultado: máximo 3 decimales, sin ceros finales, sin notación científica
        DecimalFormat df = new DecimalFormat("0.###");
        df.setMaximumFractionDigits(3);
        df.setGroupingUsed(false);
        String resultadoFormateado = df.format(resultado);

        // Mostrar el resultado en la pantalla de resultado
        resultadoLabel.setText("<html><div style='text-align:center;'>"
                + "<span style='font-size:16px;'>" + valor + " " + origen + " equivalen a:</span><br>"
                + "<span style='color:black; font-size:20px; font-weight:bold;'>"
                + resultadoFormateado + " " + destino + "</span></div></html>");

        // Mostrar la pantalla de resultado
        mostrarPantalla("Resultado");

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Valor inválido. Usa números (ej: 12.5).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

        convertirBtn.setToolTipText("Haz clic para realizar la conversión");
        centro.add(convertirBtn);

        centro.add(Box.createVerticalStrut(10));
        JButton volver = crearBotonPrincipal("VOLVER", e -> mostrarPantalla("Menu"));
        volver.setToolTipText("Regresa al menú principal");
        centro.add(volver);
        habilitarNavegacionPorTeclado(panel);
        return panel;
    }

    // Pantalla para mostrar el resultado de la conversión
// Pantalla para mostrar el resultado de la conversión
// ======================= PANTALLA DE RESULTADO =======================
private JPanel crearPantallaResultado() {
    JPanel panel = crearPanelBase("Resultado de la conversión.");
    JPanel centro = (JPanel) panel.getClientProperty("centro");
    centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
    centro.setBackground(VERDE_PASTEL);

    // --- Tarjeta blanca centrada ---
    JPanel tarjeta = new JPanel();
    tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
    tarjeta.setBackground(Color.WHITE);
    tarjeta.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
        BorderFactory.createEmptyBorder(30, 45, 30, 45)
    ));
    tarjeta.setAlignmentX(Component.CENTER_ALIGNMENT);
    tarjeta.setMaximumSize(new Dimension(420, 260));
    tarjeta.setOpaque(true);

    // --- Título ---
    JLabel titulo = new JLabel("RESULTADO:", SwingConstants.CENTER);
    titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
    titulo.setForeground(VERDE_PANTONE);
    titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
    tarjeta.add(titulo);
    tarjeta.add(Box.createVerticalStrut(20));

    // --- Cuadro del resultado ---
    JPanel resultadoBox = new JPanel(new BorderLayout());
    resultadoBox.setBackground(Color.WHITE);
    resultadoBox.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(210, 210, 210), 1, true),
        BorderFactory.createEmptyBorder(12, 18, 12, 18)
    ));
    resultadoBox.setMaximumSize(new Dimension(320, 50));
    resultadoBox.setAlignmentX(Component.CENTER_ALIGNMENT);
    resultadoBox.setOpaque(true);

    resultadoLabel = new JLabel("", SwingConstants.CENTER);
    resultadoLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
    resultadoLabel.setForeground(Color.DARK_GRAY);
    resultadoBox.add(resultadoLabel, BorderLayout.CENTER);

    tarjeta.add(resultadoBox);
    tarjeta.add(Box.createVerticalStrut(25));

    // --- Pregunta ---
    JLabel preguntaLabel = new JLabel("¿Quieres realizar otra conversión?", SwingConstants.CENTER);
    preguntaLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
    preguntaLabel.setForeground(Color.BLACK);
    preguntaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    tarjeta.add(preguntaLabel);
    tarjeta.add(Box.createVerticalStrut(20));

    // --- Botones en horizontal ---
    JPanel botonesPanel = new JPanel();
    botonesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
    botonesPanel.setOpaque(false);

    JButton siBtn = crearBotonPrincipal("SÍ", e -> mostrarPantalla("Menu"));
    siBtn.setPreferredSize(new Dimension(100, 38));

    JButton noBtn = crearBotonPrincipal("NO", e -> mostrarPantalla("Inicio"));
    noBtn.setPreferredSize(new Dimension(100, 38));

    botonesPanel.add(siBtn);
    botonesPanel.add(noBtn);
    tarjeta.add(botonesPanel);

    // --- Añadir tarjeta al centro ---
    centro.add(Box.createVerticalGlue());
    centro.add(tarjeta);
    centro.add(Box.createVerticalGlue());
    habilitarNavegacionPorTeclado(panel);
    return panel;
}

    // -------------------- MÉTODOS AUXILIARES DE INTERFAZ --------------------

    /**
     * Crea el panel base con la columna de información a la izquierda
     * y un centro para agregar componentes en columna (como en tu prototipo).
     * No altera posiciones originales, solo devuelve una estructura reutilizable.
     */
    private JPanel crearPanelBase(String textoNota) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(VERDE_MENTA);

        JPanel lateral = crearInfo(textoNota);
        panel.add(lateral, BorderLayout.WEST);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(VERDE_MENTA);
        centro.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 60));
        centro.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(centro, BorderLayout.CENTER);

        panel.putClientProperty("centro", centro);
        habilitarNavegacionPorTeclado(panel);
        return panel;
    }

    // Panel lateral con nota/información 
    private JPanel crearInfo(String texto) {
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(BLANCO_GRIS);
        panelInfo.setPreferredSize(new Dimension(220, 0));
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, VERDE_OSCURO),
                BorderFactory.createEmptyBorder(20, 15, 20, 15)
        ));
        JLabel info = new JLabel("<html><div style='text-align:justify;'><b>Nota:</b><br><br>" + texto.replace("\n", "<br>") + "</div></html>");
        info.setFont(new Font("SansSerif", Font.PLAIN, 12));
        info.setForeground(GRIS_TEXTO);
        panelInfo.add(info);
        return panelInfo;
    }

    // Crea un título grande centrado
    private JLabel crearTitulo(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 22));
        label.setForeground(VERDE_OSCURO);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    // Etiqueta secundaria (texto explicativo sobre campos)
    private JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label.setForeground(GRIS_TEXTO);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    // Campo de texto estilizado
    private JTextField crearCampoTexto() {
        JTextField field = new JTextField(20);
        field.setMaximumSize(new Dimension(280, 35));
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        field.setBackground(BLANCO_GRIS);
        field.setHorizontalAlignment(SwingConstants.CENTER);
        return field;
    }

    // Campo de contraseña estilizado
    private JPasswordField crearCampoPassword() {
        JPasswordField field = new JPasswordField(20);
        field.setMaximumSize(new Dimension(280, 35));
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        field.setBackground(BLANCO_GRIS);
        field.setHorizontalAlignment(SwingConstants.CENTER);
        return field;
    }

    // Botón principal (fondo verde oscuro y texto blanco)
    private JButton crearBotonPrincipal(String texto, ActionListener al) {
        JButton btn = new JButton(texto);
        btn.setBackground(VERDE_OSCURO);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setMaximumSize(new Dimension(220, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(al);
        return btn;
    }

    // Botón secundario (texto en color rojo intenso en tu versión previa, lo dejamos similar)
    private JButton crearBotonSecundario(String texto, ActionListener al) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setForeground(Color.decode("#bd1214")); // rojo intenso (igual que antes)
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addActionListener(al);
        return btn;
    }

    // Botón estilo texto (para "Olvidé mi contraseña") - apariencia de enlace
    private JButton crearBotonTexto(String texto, ActionListener al) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btn.setForeground(VERDE_OSCURO);
        btn.setBackground(VERDE_MENTA);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addActionListener(al);
        return btn;
    }

    // Mostrar una pantalla por nombre
    private void mostrarPantalla(String nombre) {
        cardLayout.show(mainPanel, nombre);
    }

    // -------------------- LÓGICA DE CONVERSIÓN --------------------

    // Determina qué unidades están disponibles según el tipo de conversión
    private JComboBox<String> crearComboUnidades(String tipo) {
        String[] unidades;
        switch (tipo.toUpperCase()) {
            case "LONGITUD":
                unidades = new String[]{"Kilómetros (km)", "Metros (m)", "Centímetros (cm)", "Milímetros (mm)", "Millas (mi)", "Yardas", "Pulgadas (in)"};
                break;
            case "MASA":
                unidades = new String[]{"Kilogramos (kg)", "Gramos (g)", "Libras (lb)", "Onzas (oz)", "Toneladas (t)"};
                break;
            case "TEMPERATURA":
                unidades = new String[]{"Celsius (°C)", "Kelvin (K)", "Fahrenheit (°F)", "Rankine (°R)"}; 
                break;
            case "ENERGIA":
                unidades = new String[]{"Joules (J)", "Kilojoules (kJ)", "Calorías (cal)", "Kilocalorías (kcal)", "Watt-horas (Wh)", "Kilowatt-horas (kWh)"};
                break;
            case "VOLUMEN":
                unidades = new String[]{"Litros (L)", "Mililitros (mL)", "Hectolitros (hL)", "Metros cúbicos (m³)"};
                break;
            default:
                unidades = new String[]{"Selecciona una unidad"};
        }
        JComboBox<String> combo = new JComboBox<>(unidades);
        combo.setMaximumSize(new Dimension(280, 35));
        combo.setBackground(BLANCO_GRIS);
        combo.setAlignmentX(Component.CENTER_ALIGNMENT);
        return combo;
    }

    // Enrutador de conversiones según tipo
    private double convertir(double valor, String origen, String destino, String tipo) {
        switch (tipo.toUpperCase()) {
            case "LONGITUD":
                return convertirLongitud(valor, origen, destino);
            case "MASA":
                return convertirMasa(valor, origen, destino);
            case "TEMPERATURA":
                return convertirTemperatura(valor, origen, destino);
            case "ENERGIA":
                return convertirEnergia(valor, origen, destino);
            case "VOLUMEN":
                return convertirVolumen(valor, origen, destino);
            default:
                return valor;
        }
    }

    // Conversiónes de longitud: convertimos todo a metros como paso intermedio
    private double convertirLongitud(double valor, String origen, String destino) {
        double valorM = 0;
        switch (origen) {
            case "Kilómetros (km)": valorM = valor * 1000; break;
            case "Metros (m)": valorM = valor; break;
            case "Centímetros (cm)": valorM = valor / 100.0; break;
            case "Milímetros (mm)": valorM = valor / 1000.0; break;
            case "Millas (mi)": valorM = valor * 1609.34; break;
            case "Yardas": valorM = valor * 0.9144; break;
            case "Pulgadas (in)": valorM = valor * 0.0254; break;
            default: valorM = valor; break;
        }
        switch (destino) {
            case "Kilómetros (km)": return valorM / 1000.0;
            case "Metros (m)": return valorM;
            case "Centímetros (cm)": return valorM * 100.0;
            case "Milímetros (mm)": return valorM * 1000.0;
            case "Millas (mi)": return valorM / 1609.34;
            case "Yardas": return valorM / 0.9144;
            case "Pulgadas (in)": return valorM / 0.0254;
            default: return valor;
        }
    }

    // Conversiónes de masa: todo a kilogramos como base
    private double convertirMasa(double valor, String origen, String destino) {
        double valorKg = 0;
        switch (origen) {
            case "Kilogramos (kg)": valorKg = valor; break;
            case "Gramos (g)": valorKg = valor / 1000.0; break;
            case "Libras (lb)": valorKg = valor * 0.453592; break;
            case "Onzas (oz)": valorKg = valor * 0.0283495; break;
            case "Toneladas (t)": valorKg = valor * 1000.0; break;
            default: valorKg = valor; break;
        }
        switch (destino) {
            case "Kilogramos (kg)": return valorKg;
            case "Gramos (g)": return valorKg * 1000.0;
            case "Libras (lb)": return valorKg / 0.453592;
            case "Onzas (oz)": return valorKg / 0.0283495;
            case "Toneladas (t)": return valorKg / 1000.0;
            default: return valor;
        }
    }

    // Conversión de temperatura (convertimos todo a Celsius como base)
    private double convertirTemperatura(double valor, String origen, String destino) {
        double celsius = 0;
        switch (origen) {
            case "Celsius (°C)": celsius = valor; break;
            case "Kelvin (K)": celsius = valor - 273.15; break;
            case "Fahrenheit (°F)": celsius = (valor - 32) * 5.0 / 9.0; break;
            case "Rankine (°R)": celsius = (valor - 491.67) * 5.0 / 9.0; break;
            default: celsius = valor; break;
        }
        switch (destino) {
            case "Celsius (°C)": return celsius;
            case "Kelvin (K)": return celsius + 273.15;
            case "Fahrenheit (°F)": return celsius * 9.0 / 5.0 + 32;
            case "Rankine (°R)": return (celsius + 273.15) * 9.0 / 5.0;
            default: return valor;
        }
    }

    // Conversión de energía: base Joules
    private double convertirEnergia(double valor, String origen, String destino) {
        double joule = 0;
        switch (origen) {
            case "Joules (J)": joule = valor; break;
            case "Kilojoules (kJ)": joule = valor * 1000.0; break;
            case "Calorías (cal)": joule = valor * 4.184; break;
            case "Kilocalorías (kcal)": joule = valor * 4184.0; break;
            case "Watt-horas (Wh)": joule = valor * 3600.0; break;
            case "Kilowatt-horas (kWh)": joule = valor * 3600000.0; break;
            default: joule = valor; break;
        }
        switch (destino) {
            case "Joules (J)": return joule;
            case "Kilojoules (kJ)": return joule / 1000.0;
            case "Calorías (cal)": return joule / 4.184;
            case "Kilocalorías (kcal)": return joule / 4184.0;
            case "Watt-horas (Wh)": return joule / 3600.0;
            case "Kilowatt-horas (kWh)": return joule / 3600000.0;
            default: return valor;
        }
    }

    // Conversión de volumen: base Litros
    private double convertirVolumen(double valor, String origen, String destino) {
        double litro = 0;
        switch (origen) {
            case "Litros (L)": litro = valor; break;
            case "Mililitros (mL)": litro = valor / 1000.0; break;
            case "Hectolitros (hL)": litro = valor * 100.0; break;
            case "Metros cúbicos (m³)": litro = valor * 1000.0; break;
            default: litro = valor; break;
        }
        switch (destino) {
            case "Litros (L)": return litro;
            case "Mililitros (mL)": return litro * 1000.0;
            case "Hectolitros (hL)": return litro / 100.0;
            case "Metros cúbicos (m³)": return litro / 1000.0;
            default: return valor;
        }
    }

    // Mensajes de nota según tipo (usados en la columna lateral)
    private String textoNotaPorTipo(String tipo) {
        switch (tipo.toUpperCase()) {
            case "LONGITUD": return "Convierte entre km, m, cm, mm, millas, yardas y pulgadas.";
            case "MASA": return "Convierte entre kg, g, lb, oz y toneladas.";
            case "TEMPERATURA": return "Convierte entre °C, K, °F y °R.";
            case "ENERGIA": return "Convierte entre J, kJ, cal, kcal, Wh y kWh.";
            case "VOLUMEN": return "Convierte entre L, mL, hL y m³.";
            default: return "";
        }
    }

    // -------------------- MAIN --------------------

   public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConversorUI());
    }
// ======================= ACCESIBILIDAD GLOBAL =======================
private void habilitarNavegacionPorTeclado(JComponent contenedor) {
    Component[] componentes = contenedor.getComponents();

    for (int i = 0; i < componentes.length; i++) {
        Component comp = componentes[i];
        final int indice = i; // necesario para usar dentro de la clase anónima

        if (comp instanceof JButton boton) {
            boton.setFocusable(true); // permite TAB
            boton.getInputMap(JComponent.WHEN_FOCUSED)
                 .put(KeyStroke.getKeyStroke("ENTER"), "pressEnter");
            boton.getActionMap().put("pressEnter", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boton.doClick();
                }
            });

        } else if (comp instanceof JComboBox<?> combo) {
            combo.setFocusable(true); // permite TAB
            combo.getInputMap(JComponent.WHEN_FOCUSED)
                 .put(KeyStroke.getKeyStroke("ENTER"), "selectItem");
            combo.getActionMap().put("selectItem", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (combo.isPopupVisible()) {
                        combo.setSelectedIndex(combo.getSelectedIndex());
                        combo.hidePopup();
                    } else {
                        combo.showPopup();
                    }
                }
            });

        } else if (comp instanceof JTextField textField) {
            textField.setFocusable(true);
            textField.getInputMap(JComponent.WHEN_FOCUSED)
                     .put(KeyStroke.getKeyStroke("ENTER"), "focusNext");
            textField.getActionMap().put("focusNext", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Mover foco al siguiente componente “focusable”
                    Component next = null;
                    for (int j = indice + 1; j < componentes.length; j++) {
                        if (componentes[j].isFocusable()) {
                            next = componentes[j];
                            break;
                        }
                    }
                    if (next != null) next.requestFocus();
                }
            });

        } else if (comp instanceof JComponent subContenedor) {
            // Aplicar recursivamente a subcomponentes
            habilitarNavegacionPorTeclado(subContenedor);
        }
    }
}
  
}





