import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConversorUI extends JFrame
{   // REQUERIMIENTO FUNCIONAL: Ventana principal del sistema de conversión de unidades.
   // Administra todas las pantallas (inicio,registro, menú, conversiones y resultados).

    private static final long serialVersionUID = 1L;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Colores institucionales y estéticos (REQUERIMIENTO DE USABILIDAD)
    private final Color VERDE_PANTONE = Color.decode("#007e67");
    private final Color VERDE_PASTEL = Color.decode("#c9e0d3");
    private final Color ROJO_INTENSO = Color.decode("#bd1214");
    private final Color VERDE_MENTA = Color.decode("#ecfbee");
    private final Color VERDE_APAGADO = Color.decode("#729c8e");
    private final Color VERDE_GRIS = Color.decode("#9bb5a8");
    private final Color BLANCO_GRIS = Color.decode("#f5fbf6");

    private JLabel resultadoLabel;

    public ConversorUI() {
    
        // REQUERIMIENTO FUNCIONAL: Interfaz gráfica inicial del sistema
        setTitle("Conversor de Unidades");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 520);
        setLocationRelativeTo(null);
        setResizable(false);

        configurarEstiloGlobal();

        // Uso de CardLayout para cambiar entre pantallas
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // REQUERIMIENTO FUNCIONAL: Carga de todas las pantallas del sistema
        mainPanel.add(crearPantallaInicio(), "Inicio");
        mainPanel.add(crearPantallaLogin(), "Login");
        mainPanel.add(crearPantallaRegistro(), "Registro");
        mainPanel.add(crearPantallaMenu(), "Menu");
        mainPanel.add(crearPantallaConversion("Longitud"), "Longitud");
        mainPanel.add(crearPantallaConversion("Masa"), "Masa");
        mainPanel.add(crearPantallaConversion("Temperatura"), "Temperatura");
        mainPanel.add(crearPantallaConversion("Energia"), "Energia");
        mainPanel.add(crearPantallaConversion("Volumen"), "Volumen");
        mainPanel.add(crearPantallaResultado(), "Resultado");

        add(mainPanel);
        setVisible(true);
        mostrarPantalla("Inicio");
    }

    private void configurarEstiloGlobal() {
        // REQUERIMIENTO FUNCIONAL: Diseño uniforme para todos los elementos de la interfaz
        UIManager.put("Panel.background", VERDE_PASTEL);
        UIManager.put("Label.font", new Font("SansSerif", Font.PLAIN, 14));
        UIManager.put("Label.foreground", Color.DARK_GRAY);
        UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 15));
        UIManager.put("TextField.font", new Font("SansSerif", Font.PLAIN, 14));
    }

    
    // -------------------- PANTALLAS --------------------
    private JPanel crearPantallaInicio() {
        // REQUERIMIENTO FUNCIONAL: Permitir iniciar sesión o salir del sistema
        JPanel panel = crearPanelBase("Selecciona una opción para iniciar o salir del programa.");
        JPanel centro = (JPanel) panel.getClientProperty("centro");

        JLabel titulo = crearTitulo("CONVERSOR DE UNIDADES BÁSICAS");
        JButton iniciar = crearBotonRedondeado("INICIAR", VERDE_PANTONE, VERDE_APAGADO, 40, e -> mostrarPantalla("Login")); // Acción: ir al login

        iniciar.setToolTipText("Haz clic para iniciar sesión");

        centro.add(Box.createVerticalGlue());
        centro.add(titulo);
        centro.add(Box.createVerticalStrut(20));
        centro.add(iniciar);
        centro.add(Box.createVerticalGlue());
        return panel;
    }

    private JPanel crearPantallaLogin() {
        // REQUERIMIENTO FUNCIONAL: Inicio de sesión de usuario
        JPanel panel = crearPanelBase("Ingresa tus credenciales o crea una nueva cuenta.");
        JPanel centro = (JPanel) panel.getClientProperty("centro");

        JLabel titulo = crearTitulo("INICIO DE SESIÓN");
        centro.add(titulo);
        centro.add(Box.createVerticalStrut(20));

         // Campos de usuario y contraseña
        centro.add(crearEtiquetaSecundaria("Usuario o matrícula:"));
        JTextField usuarioField = crearCampoTexto();
        usuarioField.setToolTipText("Ingresa tu usuario o matrícula");
        centro.add(usuarioField);
        centro.add(Box.createVerticalStrut(15));

        centro.add(crearEtiquetaSecundaria("Contraseña:"));
        JPasswordField passField = crearCampoPassword();
        passField.setToolTipText("Ingresa tu contraseña");
        centro.add(passField);
        centro.add(Box.createVerticalStrut(10));

       // Botón de envío que lleva al menú principal (simula autenticación)
        JButton enviar = crearBotonRedondeado("ENVIAR", VERDE_PANTONE, VERDE_APAGADO, 40, e -> mostrarPantalla("Menu")); // REQUERIMIENTO FUNCIONAL: Acceso a funciones
        enviar.setToolTipText("Haz clic para iniciar sesión");
        centro.add(enviar);
        centro.add(Box.createVerticalStrut(8));

         // Botón para crear nueva cuenta
        JButton crearCuenta = crearBotonSecundario("CREAR NUEVA CUENTA", e -> mostrarPantalla("Registro"));
        crearCuenta.setToolTipText("Haz clic para crear una nueva cuenta");
        centro.add(crearCuenta);
        centro.add(Box.createVerticalStrut(6));

        // Botón "Olvidé mi contraseña" (REQUERIMIENTO FUNCIONAL)
        JButton olvido = new JButton("OLVIDÉ MI CONTRASEÑA");
        olvido.setFont(new Font("SansSerif", Font.PLAIN, 12));
        olvido.setBorderPainted(false);
        olvido.setContentAreaFilled(false);
        olvido.setForeground(VERDE_PANTONE);
        olvido.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        olvido.setAlignmentX(Component.CENTER_ALIGNMENT);
        olvido.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Por favor contacta al administrador para recuperar tu contraseña.",
                "Recuperar contraseña", JOptionPane.INFORMATION_MESSAGE));
        olvido.setToolTipText("Haz clic si olvidaste tu contraseña");
        centro.add(olvido);

        centro.add(Box.createVerticalStrut(6));
        
        // Botón atrás
        JButton atras = crearBotonRedondeado("ATRÁS", VERDE_PANTONE, VERDE_APAGADO, 40, e -> mostrarPantalla("Inicio"));
        atras.setToolTipText("Regresar a la pantalla de inicio");
        centro.add(atras);
        return panel;
    }

    private JPanel crearPantallaRegistro() {
       
        // REQUERIMIENTO FUNCIONAL: Permitir registro de nuevos usuarios
        JPanel panel = crearPanelBase("Completa los campos para registrar una cuenta.");
        JPanel centro = (JPanel) panel.getClientProperty("centro");

        JLabel titulo = crearTitulo("CREAR CUENTA");
        centro.add(titulo);
        centro.add(Box.createVerticalStrut(20));
        
        // Campos de registro
        centro.add(crearEtiquetaSecundaria("Correo electrónico:"));
        JTextField email = crearCampoTexto();
        email.setToolTipText("Ingresa tu correo electrónico");
        centro.add(email);
        centro.add(Box.createVerticalStrut(10));

        centro.add(crearEtiquetaSecundaria("Contraseña:"));
        JPasswordField pass = crearCampoPassword();
        pass.setToolTipText("Ingresa tu contraseña");
        centro.add(pass);
        centro.add(Box.createVerticalStrut(10));

        centro.add(crearEtiquetaSecundaria("Confirmar contraseña:"));
        JPasswordField confirmPass = crearCampoPassword();
        confirmPass.setToolTipText("Confirma tu contraseña");
        centro.add(confirmPass);
        centro.add(Box.createVerticalStrut(20));

        // Mensaje de confirmación
        JButton crear = crearBotonRedondeado("CREAR", VERDE_PANTONE, VERDE_APAGADO, 40, e -> {
            JOptionPane.showMessageDialog(this, "Cuenta creada correctamente.", "Registro", JOptionPane.INFORMATION_MESSAGE);
            mostrarPantalla("Login");
        });
        crear.setToolTipText("Crear cuenta");
        centro.add(crear);

        // Volver al login
        JButton volver = crearBotonRedondeado("VOLVER", VERDE_PANTONE, VERDE_APAGADO, 40, e -> mostrarPantalla("Login"));
        volver.setToolTipText("Regresar a inicio de sesión");
        centro.add(volver);
        return panel;
    }

    private JPanel crearPantallaMenu() {

        // REQUERIMIENTO FUNCIONAL: Menú principal del sistema
        JPanel panel = crearPanelBase("Selecciona la categoría que deseas convertir.");
        JPanel centro = (JPanel) panel.getClientProperty("centro");

        JLabel titulo = crearTitulo("¿QUÉ UNIDAD QUIERES CONVERTIR?");
        centro.add(titulo);
        centro.add(Box.createVerticalStrut(15));

        // Botones de acceso a cada módulo de conversión
        JButton energia = crearBotonRedondeado("ENERGÍAS", VERDE_PANTONE, VERDE_APAGADO, 40, e -> mostrarPantalla("Energia"));
        energia.setToolTipText("Convertir unidades de energía");
        centro.add(energia);
        centro.add(Box.createVerticalStrut(10));

        JButton longitud = crearBotonRedondeado("LONGITUDES", VERDE_PANTONE, VERDE_APAGADO, 40, e -> mostrarPantalla("Longitud"));
        longitud.setToolTipText("Convertir unidades de longitud");
        centro.add(longitud);
        centro.add(Box.createVerticalStrut(10));

        JButton masa = crearBotonRedondeado("MASAS", VERDE_PANTONE, VERDE_APAGADO, 40, e -> mostrarPantalla("Masa"));
        masa.setToolTipText("Convertir unidades de masa");
        centro.add(masa);
        centro.add(Box.createVerticalStrut(10));

        JButton temperatura = crearBotonRedondeado("TEMPERATURAS", VERDE_PANTONE, VERDE_APAGADO, 40, e -> mostrarPantalla("Temperatura"));
        temperatura.setToolTipText("Convertir unidades de temperatura");
        centro.add(temperatura);
        centro.add(Box.createVerticalStrut(10));

        JButton volumen = crearBotonRedondeado("VOLUMENES", VERDE_PANTONE, VERDE_APAGADO, 40, e -> mostrarPantalla("Volumen"));
        volumen.setToolTipText("Convertir unidades de volumen");
         // Agregar al panel
        centro.add(volumen);
        centro.add(Box.createVerticalStrut(20));

         // Botón de regreso al login
        JButton atras = crearBotonRedondeado("ATRÁS", VERDE_PANTONE, VERDE_APAGADO, 40, e -> mostrarPantalla("Login"));
        atras.setToolTipText("Regresar al login");
        centro.add(atras);
        return panel;
    }

    private JPanel crearPantallaConversion(String tipo) {

        // REQUERIMIENTO FUNCIONAL: Conversiones entre distintas unidades
        JPanel panel = crearPanelBase(textoNotaPorTipo(tipo));
        JPanel centro = (JPanel) panel.getClientProperty("centro");

        JLabel titulo = crearTitulo(tipo.toUpperCase());
        JLabel subtitulo = crearEtiquetaSecundaria(subtituloPorTipo(tipo));

       
        centro.add(titulo);
        centro.add(Box.createVerticalStrut(10));
        centro.add(subtitulo);
        centro.add(Box.createVerticalStrut(10));
        centro.add(crearEtiquetaSecundaria("Valor a convertir:"));
       
        // Campos para ingresar valor y elegir unidades
        JTextField campoValor = crearCampoTexto();
        campoValor.setToolTipText("Ingresa el valor que deseas convertir");
        centro.add(campoValor);
        centro.add(Box.createVerticalStrut(10));

        centro.add(crearEtiquetaSecundaria("Unidad a convertir:"));
        JComboBox<String> origenCombo = crearComboUnidades(tipo);
        origenCombo.setToolTipText("Selecciona la unidad original");
        centro.add(origenCombo);
        centro.add(Box.createVerticalStrut(10));

        centro.add(crearEtiquetaSecundaria("Unidad final:"));
        JComboBox<String> destinoCombo = crearComboUnidades(tipo);
        destinoCombo.setToolTipText("Selecciona la unidad a convertir");
        centro.add(destinoCombo);
        centro.add(Box.createVerticalStrut(20));

        // Botón que realiza la conversión
        JButton convertirBtn = crearBotonRedondeado("CONVERTIR", VERDE_PANTONE, VERDE_APAGADO, 40, e -> {
            try { 

                // REQUERIMIENTO FUNCIONAL: Validar entrada de datos
                String texto = campoValor.getText().trim();
                if(texto.isEmpty()) throw new NumberFormatException();
                double valor = Double.parseDouble(texto.replace(",", "."));
                String origenUnidad = (String) origenCombo.getSelectedItem();
                String destinoUnidad = (String) destinoCombo.getSelectedItem();
                
                // Realizar la conversión
                double resultado = convertir(valor, origenUnidad, destinoUnidad, tipo);

                // Mostrar resultado final
                resultadoLabel.setText("<html><div style='text-align:center;'>"
                        + "<span style='font-size:16px;'>" 
                        + formatoNumero(valor) + " " + origenUnidad + " equivalen a:</span><br>"
                        + "<span style='color:#007e67; font-size:20px; font-weight:bold;'>" 
                        + formatoNumero(resultado) + " " + destinoUnidad + "</span>"
                        + "</div></html>");

                mostrarPantalla("Resultado");
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "<html><b>ERROR</b><br>Se ha ingresado un valor erróneo en el Valor a convertir.<br>Ingresa un número válido.</html>",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        convertirBtn.setToolTipText("Realizar la conversión");
        centro.add(convertirBtn);

        centro.add(Box.createVerticalStrut(10));
        
        // Botón para regresar al menú
        JButton volver = crearBotonRedondeado("VOLVER", VERDE_PANTONE, VERDE_APAGADO, 40, e -> mostrarPantalla("Menu"));
        volver.setToolTipText("Regresar al menú principal");
        centro.add(volver);
        return panel;
    }

    private JPanel crearPantallaResultado() {
        // REQUERIMIENTO FUNCIONAL: Mostrar el resultado final de la conversión
        JPanel panel = crearPanelBase("Resultado de la conversión.");
        JPanel centro = (JPanel) panel.getClientProperty("centro");

        JLabel titulo = crearTitulo("RESULTADO");
        resultadoLabel = new JLabel("", SwingConstants.CENTER);
        resultadoLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        resultadoLabel.setForeground(VERDE_PANTONE);
        resultadoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centro.add(titulo);
        centro.add(Box.createVerticalStrut(10));
        centro.add(resultadoLabel);
        centro.add(Box.createVerticalStrut(20));
        centro.add(crearEtiquetaSecundaria("¿Quieres realizar otra conversión?"));
        centro.add(Box.createVerticalStrut(10));

        // REQUERIMIENTO FUNCIONAL: Repetir o finalizar
        JButton siBtn = crearBotonRedondeado("SÍ", VERDE_PANTONE, VERDE_APAGADO, 40, e -> mostrarPantalla("Menu"));
        siBtn.setToolTipText("Realizar otra conversión");
        centro.add(siBtn);
        centro.add(Box.createVerticalStrut(5));

        JButton noBtn = crearBotonRedondeado("NO", VERDE_PANTONE, VERDE_APAGADO, 40, e -> mostrarPantalla("Inicio"));
        noBtn.setToolTipText("Finalizar y regresar a inicio");
        centro.add(noBtn);

        return panel;
    }

    // -------------------- COMPONENTES --------------------
    private JPanel crearPanelBase(String textoNota) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(VERDE_PASTEL);

        JPanel lateral = crearInfo(textoNota);
        panel.add(lateral, BorderLayout.WEST);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(VERDE_PASTEL);
        centro.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 60));
        centro.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(centro, BorderLayout.CENTER);

        panel.putClientProperty("centro", centro);
        return panel;
    }

    private JPanel crearInfo(String texto) {
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(VERDE_MENTA);
        panelInfo.setPreferredSize(new Dimension(200, 0));
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, VERDE_GRIS),
                BorderFactory.createEmptyBorder(20, 15, 20, 15)
        ));

        JLabel info = new JLabel("<html><div style='text-align:justify;'><b>ℹ Nota:</b><br><br>" + texto.replace("\n","<br>") + "</div></html>");
        info.setFont(new Font("SansSerif", Font.PLAIN, 11));
        info.setForeground(Color.DARK_GRAY);
        info.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelInfo.add(info);
        return panelInfo;
    }

    private JLabel crearTitulo(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 22));
        label.setForeground(VERDE_PANTONE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JLabel crearEtiquetaSecundaria(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label.setForeground(Color.DARK_GRAY);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JTextField crearCampoTexto() {
        JTextField field = new JTextField(20);
        field.setMaximumSize(new Dimension(250, 35));
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        field.setBackground(BLANCO_GRIS);
        field.setHorizontalAlignment(SwingConstants.CENTER);
        return field;
    }

    private JPasswordField crearCampoPassword() {
        JPasswordField field = new JPasswordField(20);
        field.setMaximumSize(new Dimension(250, 35));
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        field.setBackground(BLANCO_GRIS);
        field.setHorizontalAlignment(SwingConstants.CENTER);
        return field;
    }

    private JComboBox<String> crearComboUnidades(String tipo) {
        String[] unidades;
        switch (tipo.toUpperCase()) {
            case "LONGITUD": unidades = new String[]{
                    "Kilómetros (km)", "Metros (m)", "Centímetros (cm)", "Milímetros (mm)", 
                    "Millas (mi)", " yardas (yd)", "Pulgadas (in)"}; break;
            case "MASA": unidades = new String[]{
                    "Kilogramos (kg)", "Gramos (g)", "Libras (lb)", "Onzas (oz)", "Toneladas (t)"}; break;
            case "TEMPERATURA": unidades = new String[]{
                    "Celsius (°C)", "Kelvin (K)", "Fahrenheit (°F)", "Rankine (°R)"}; break;
            case "ENERGIA": unidades = new String[]{
                    "Joules (J)", "Kilojoules (kJ)", "Calorías (cal)", "Kilocalorías (kcal)", 
                    "Watt-horas (Wh)", "Kilowatt-horas (kWh)"}; break;
            case "VOLUMEN": unidades = new String[]{
                    "Litros (L)", "Mililitros (mL)", "Hectolitros (hL)", "Metros cúbicos (m³)"}; break;
            default: unidades = new String[]{"Selecciona una unidad"};
        }
        JComboBox<String> combo = new JComboBox<>(unidades);
        combo.setMaximumSize(new Dimension(250, 35));
        combo.setBackground(BLANCO_GRIS);
        combo.setAlignmentX(Component.CENTER_ALIGNMENT);
        return combo;
    }

    private JButton crearBotonRedondeado(String texto, Color colorFondo, Color colorHover, int alto, ActionListener al) {
        JButton btn = new JButton(texto);
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setMaximumSize(new Dimension(220, alto));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(al);
        return btn;
    }

    private JButton crearBotonSecundario(String texto, ActionListener al) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setForeground(ROJO_INTENSO);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addActionListener(al);
        return btn;
    }

    private void mostrarPantalla(String nombre) {
        cardLayout.show(mainPanel, nombre);
    }

    private String textoNotaPorTipo(String tipo) {
        switch(tipo.toUpperCase()) {
            case "LONGITUD": return "Convierte entre diferentes unidades de longitud, desde metros hasta millas.";
            case "MASA": return "Convierte entre diferentes unidades de masa, incluyendo kg, lb y oz.";
            case "TEMPERATURA": return "Convierte entre diferentes escalas de temperatura como Celsius y Fahrenheit.";
            case "ENERGIA": return "Convierte entre diferentes unidades de energía como Joules, calorías y kWh.";
            case "VOLUMEN": return "Convierte entre diferentes unidades de volumen como litros y metros cúbicos.";
            default: return "";
        }
    }

    private String subtituloPorTipo(String tipo) {
        switch(tipo.toUpperCase()) {
            case "LONGITUD": return "Ej: metros a kilómetros";
            case "MASA": return "Ej: gramos a libras";
            case "TEMPERATURA": return "Ej: Celsius a Fahrenheit";
            case "ENERGIA": return "Ej: Joules a Kilocalorías";
            case "VOLUMEN": return "Ej: Litros a Mililitros";
            default: return "";
        }
    }

    private String formatoNumero(double valor) {
        return String.format("%.4f", valor);
    }

    // -------------------- CONVERSIÓN --------------------
    private double convertir(double valor, String origen, String destino, String tipo) {
        switch(tipo.toUpperCase()) {
            case "LONGITUD":
                double valorMetros = 0;
                if (origen.equals("Kilómetros (km)")) valorMetros = valor * 1000;
                else if (origen.equals("Metros (m)")) valorMetros = valor;
                else if (origen.equals("Centímetros (cm)")) valorMetros = valor * 0.01;
                else if (origen.equals("Milímetros (mm)")) valorMetros = valor * 0.001;
                else if (origen.equals("Millas (mi)")) valorMetros = valor * 1609.34;
                else if (origen.equals(" yardas (yd)")) valorMetros = valor * 0.9144;
                else if (origen.equals("Pulgadas (in)")) valorMetros = valor * 0.0254;

                if (destino.equals("Kilómetros (km)")) return valorMetros / 1000;
                else if (destino.equals("Metros (m)")) return valorMetros;
                else if (destino.equals("Centímetros (cm)")) return valorMetros / 0.01;
                else if (destino.equals("Milímetros (mm)")) return valorMetros / 0.001;
                else if (destino.equals("Millas (mi)")) return valorMetros / 1609.34;
                else if (destino.equals(" yardas (yd)")) return valorMetros / 0.9144;
                else if (destino.equals("Pulgadas (in)")) return valorMetros / 0.0254;
                break;

            case "MASA":
                double valorKg = 0;
                if (origen.equals("Kilogramos (kg)")) valorKg = valor;
                else if (origen.equals("Gramos (g)")) valorKg = valor * 0.001;
                else if (origen.equals("Libras (lb)")) valorKg = valor * 0.453592;
                else if (origen.equals("Onzas (oz)")) valorKg = valor * 0.0283495;
                else if (origen.equals("Toneladas (t)")) valorKg = valor * 1000;

                if (destino.equals("Kilogramos (kg)")) return valorKg;
                else if (destino.equals("Gramos (g)")) return valorKg / 0.001;
                else if (destino.equals("Libras (lb)")) return valorKg / 0.453592;
                else if (destino.equals("Onzas (oz)")) return valorKg / 0.0283495;
                else if (destino.equals("Toneladas (t)")) return valorKg / 1000;
                break;

            case "VOLUMEN":
                double valorM3 = 0;
                if (origen.equals("Litros (L)")) valorM3 = valor * 0.001;
                else if (origen.equals("Mililitros (mL)")) valorM3 = valor * 0.000001;
                else if (origen.equals("Hectolitros (hL)")) valorM3 = valor * 0.1;
                else if (origen.equals("Metros cúbicos (m³)")) valorM3 = valor;

                if (destino.equals("Litros (L)")) return valorM3 / 0.001;
                else if (destino.equals("Mililitros (mL)")) return valorM3 / 0.000001;
                else if (destino.equals("Hectolitros (hL)")) return valorM3 / 0.1;
                else if (destino.equals("Metros cúbicos (m³)")) return valorM3;
                break;

            case "ENERGIA":
                double valorJ = 0;
                if (origen.equals("Joules (J)")) valorJ = valor;
                else if (origen.equals("Kilojoules (kJ)")) valorJ = valor * 1000;
                else if (origen.equals("Calorías (cal)")) valorJ = valor * 4.184;
                else if (origen.equals("Kilocalorías (kcal)")) valorJ = valor * 4184;
                else if (origen.equals("Watt-horas (Wh)")) valorJ = valor * 3600;
                else if (origen.equals("Kilowatt-horas (kWh)")) valorJ = valor * 3600000;

                if (destino.equals("Joules (J)")) return valorJ;
                else if (destino.equals("Kilojoules (kJ)")) return valorJ / 1000;
                else if (destino.equals("Calorías (cal)")) return valorJ / 4.184;
                else if (destino.equals("Kilocalorías (kcal)")) return valorJ / 4184;
                else if (destino.equals("Watt-horas (Wh)")) return valorJ / 3600;
                else if (destino.equals("Kilowatt-horas (kWh)")) return valorJ / 3600000;
                break;

            case "TEMPERATURA":
                double celsius = 0;
                if (origen.equals("Celsius (°C)")) celsius = valor;
                else if (origen.equals("Kelvin (K)")) celsius = valor - 273.15;
                else if (origen.equals("Fahrenheit (°F)")) celsius = (valor - 32) * 5 / 9;
                else if (origen.equals("Rankine (°R)")) celsius = (valor - 491.67) * 5 / 9;

                if (destino.equals("Celsius (°C)")) return celsius;
                else if (destino.equals("Kelvin (K)")) return celsius + 273.15;
                else if (destino.equals("Fahrenheit (°F)")) return celsius * 9 / 5 + 32;
                else if (destino.equals("Rankine (°R)")) return (celsius + 273.15) * 9 / 5;
                break;
        }
        return valor;
    }

    public static void main(String[] args) {
        // REQUERIMIENTO FUNCIONAL: Inicia la aplicación
        SwingUtilities.invokeLater(ConversorUI::new);
    }
}