/*
 * Copyright (C) 2017 Alberto Bausá Cano
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package library;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import library.JIAExtensibleDialog.Type;

/**
 * Clase utilitaria creada con el fin de reducir la complejidad de la librería en alguna de sus clases.
 * <p>
 * Provee funcionalidades comunes y axuliares para varios casos, todo de forma estática y con acceso protegido.
 *
 * @author Alberto Bausá Cano
 */
public class JIAUtils {
    
    /////////////////////// FUNCIONALIDADES FACTORÍA //////////////////////////////////////////////

    /**
    * Logger estándar de Java, con el fin de mostrar mensajes de traza con diferentes niveles de prioridad.
    * <p>
    * Según el nivel establecido, el Logger mostrará unos mensajes u otros por pantalla
    * <p>
    * Los niveles son inclusivos, de tal forma que si está establecido un nivel,
    * se mostrarán los mensajes de ese nivel y los mensajes de todos los niveles inferiores
    * <p>
    * ALL :	Se trazan todos los mensajes, a cualquier nivel<p>
    * FINEST :	Mensajes de información de máximo detalle, útil para localizar errores (depuración nivel 3)
    * <p>
    * FINER :	Mensajes de información más detallada, útil para localizar errores (depuración nivel 2)
    * <p>
    * FINE :	Mensajes de información detallada, útil para localizar errores (depuración nivel 1)
    * <p>
    * CONFIG :	Mensajes de configuración inicial del programa en el arranque<p>
    * INFO :	Mensajes de información, para seguir la ejecución del programa<p>
    * WARNING:	Mensajes de error peligroso, que tienen previsto un mecanismo de recuperación<p>
    * SEVERE :	Mensajes de error catastrófico, que provocan la terminación del programa<p>
    * OFF :	No se genera ninguna traza
    */
   private static final Logger L = Logger.getLogger(JIAFactory.class.getName());

   /**
    * Dimensión por defecto para los botones por defecto de 'Ok' y 'Cancel'.
    */
   private static final Dimension DEFAULT_BUTTONS_SIZE = new Dimension(80, 26);

   /**
    * Dimensión por defecto para los diálogos creados por la factoría, si el programador no proporciona otra.
    */
   protected static final Dimension DEFAULT_DIALOG_SIZE = new Dimension(300, DEFAULT_BUTTONS_SIZE.height + 80);

   /**
    * Botón OK del diálogo principal que crea la factoría en cada llamada.
    */
   private static JButton btnOk;

   /**
    * Botón CANCEL del diálogo principal que crea la factoría en cada llamada.
    */
   private static JButton btnCancel;

   /**
    * Bloque estático de inicialización de algunos parámetros.
    * <p>
    * Se asigna el nivel de proridad de los mensajes de log como 'INFO'.
    */
   static {
       L.setLevel(Level.INFO);
   }

   /**
    * Crea un mensaje de log de nivel 'INFO'.
    *
    * @param message El mensaje de log a mostrar
    */
   protected static void logInfo(String message) {

       L.info(message);
   }
   
    /**
     * Crea y devuelve un diálogo del tipo indicado por el parámetro, proporcionando una implementación trivial para sus métodos.
     *
     * @param type El tipo del diálogo a crear
     * @param parent La ventana del diálogo a crear
     * @param name El nombre del diálogo a crear
     * @param title El título de la ventana del diálogo a crear
     * @return
     */
    protected static JIAExtensibleDialog createWithTrivialImplementation(Type type, JFrame parent, String name, String title) {

        final JIAExtensibleDialog dialog;

        switch (type) {
            case SIMPLE:
                dialog = new JIASimpleDialog() {
                    @Override public boolean validateThis() { return true; }
                    @Override public void saveThis() { }
                    @Override public void cleanThis() { }
                    @Override public void getExternVal(String id, Object value) { } };
                dialog.setContainerName(name);
                addMinimalStructure(dialog, type);
                break;

            case TAB:
                dialog = new JIATabDialog() {
                    @Override public boolean validateThis() { return true; }
                    @Override public void saveThis() { }
                    @Override public void cleanThis() { }
                    @Override public void getExternVal(String id, Object value) { } };
                dialog.setContainerName(name);
                addMinimalStructure(dialog, type);
                break;

            case TREE:
                dialog = new JIATreeViewDialog() {
                    @Override public boolean validateThis() { return true; }
                    @Override public void saveThis() { }
                    @Override public void cleanThis() { }
                    @Override public void getExternVal(String id, Object value) { } };
                dialog.setContainerName(name);
                addMinimalStructure(dialog, type);
                break;

            default:
                dialog = null;
        }

        wrapExtensibleDialog(parent, dialog, title);
        return dialog;
    }

   /**
    * Lógica que establece la disposición más adecuada para el diálogo que se está creando.
    * <p>
    * Coloca los botones 'Ok' y 'Cancel' en la segunda (Tab y TreeView) o tercera (Simple) fila de un GridBagLayout,
    * dejando la primera (Tab y TreeView) o las dos primeras (Simple) filas disponibles para colocar los posibles
    * componentes que puedan contener los distintos tipos de diálogos.
    * <p>
    * En el caso del SimpleDialog podrá contener como máximo otros 2 ExtensibleDialog, en el caso del TabDialog
    * contendrá un tabbedPane interno donde irán sus componentes, y en el caso del TreeViewDialog contendrá un
    * splitPane, donde el componente izquierdo será JTree y el derecho será JPanel con distribución CardLayout.
    *
    * @param dialog Diálogo para el cuál se va a construir la estructura mínima
    * @param type Tipo del diálogo al que se le va a añadir la estructura mínima
    */
    private static void addMinimalStructure(JIAExtensibleDialog dialog, JIAExtensibleDialog.Type type) {

       GridBagConstraints constraints;
       int nFilas = type.equals(JIAExtensibleDialog.Type.SIMPLE) ? 3 : 2;

       resetButtons(dialog);
       dialog.setFactoria(true);
       dialog.setLayout(new GridBagLayout());

       switch(nFilas) {

           // caso de TabDialog o TreeViewDialog, se añade el componente y continúan en case 3 para los botones
           case 2:
               constraints = new GridBagConstraints();
               constraints.gridx = 0;
               constraints.gridy = 0;
               constraints.gridwidth = 2;
               constraints.weightx = 1;
               constraints.weighty = 1;
               constraints.fill = GridBagConstraints.BOTH;

               if(dialog instanceof JIATabDialog)
                   generateTabbedPane((JIATabDialog) dialog, constraints);
               else if(dialog instanceof  JIATreeViewDialog)
                   generateSplitPane((JIATreeViewDialog) dialog, constraints);

           // caso del SimpleDialog, solo se añaden botones; es la parte común a los 3 diálogos
           case 3:
               constraints = new GridBagConstraints();
               constraints.gridx = 0;
               constraints.gridy = nFilas-1;
               constraints.weightx = 1;
               constraints.weighty = 0;
               constraints.insets = new Insets(20, 20, 20, 20);
               constraints.anchor = GridBagConstraints.LAST_LINE_END;
               dialog.add(btnOk, constraints);

               constraints = new GridBagConstraints();
               constraints.gridx = 1;
               constraints.gridy = nFilas-1;
               constraints.weightx = 1;
               constraints.weighty = 0;
               constraints.insets = new Insets(20, 20, 20, 20);
               constraints.anchor = GridBagConstraints.LAST_LINE_START;
               dialog.add(btnCancel, constraints);

               setPreferredSizeByType(dialog, type);
       }
   }

   /**
    * Recibe un diálogo, y según su tipo, le asigna un tamaño preferido por defecto.
    *
    * @param dialog El diálogo
    * @param type El tipo del diálogo
    */
   private static void setPreferredSizeByType(JIAExtensibleDialog dialog, JIAExtensibleDialog.Type type) {

       dialog.setPreferredSize(type.equals(JIAExtensibleDialog.Type.TREE) ?
               new Dimension(DEFAULT_DIALOG_SIZE.width+100, DEFAULT_DIALOG_SIZE.height+200)
               : type.equals(JIAExtensibleDialog.Type.TAB) ? new Dimension(DEFAULT_DIALOG_SIZE.width+50, DEFAULT_DIALOG_SIZE.height+150) : DEFAULT_DIALOG_SIZE);
   }

   /**
    * Crea un panel de pestañas por defecto, estableciendo la colocación de las pestañas en la parte superior,
    * y la política de disposición de pestañas como SCROLL_TAB_LAYOUT, lo que quiere decir, que en el caso de
    * que el número de pestañas sea tan grande que no sea posible mostrarlas todas horizontalmente, se mostrarán
    * solo las primeras y se habilitarán unos botones de dirección para desplazarse a izquierda y derecha.
    *
    * @param tabDialog El diálogo para el cuál se está generando el panel
    * @param constraints Las restricciones que se aplican al añadir el componente al diálogo
    */
   private static void generateTabbedPane(JIATabDialog tabDialog, GridBagConstraints constraints) {

       JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
       tabDialog.add(tabbedPane, constraints);
       tabDialog.setTabbedPane(tabbedPane);
   }

   /**
    * Crea un panel divisor por defecto, con una vista de árbol a su izquierda, y un panel de tarjetas a su derecha.
    * Ambos componentes, a izquierda y derechá, se envolverán en un JScrollPane, con el fin de mantenerlos visibles,
    * sin necesidad de que ocupen más espacio. La vista de árbol estará vacía por defecto y su raíz no será visible.
    *
    * @param treeViewDialog El diálogo para el cuál se está generando el panel
    * @param constraints Las restricciones que se aplican al añadir el componente al diálogo
    */
   private static void generateSplitPane(JIATreeViewDialog treeViewDialog, GridBagConstraints constraints) {

       // inicializaciones propias del panel de contenido
       JPanel content = new JPanel(new CardLayout());
       content.setMinimumSize(new Dimension(100, 0));

       // inicializaciones propias de la vista de árbol
       JTree treeView = new JTree(new DefaultMutableTreeNode("(default)"));
       treeView.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
       treeView.setRowHeight(23);
       treeView.addTreeSelectionListener((TreeSelectionEvent e) -> {
           Object[] nodesPath = e.getPath().getPath();
           ((CardLayout) content.getLayout()).show(content, nodesPath[nodesPath.length-1].toString());
       });

       // inicializaciones propias de los paneles de desplazamiento
       JScrollPane treeViewScroll = new JScrollPane(treeView);
       treeViewScroll.setMinimumSize(new Dimension(100, 0));

       // inicializaciones propias del panel divisor
       JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, treeViewScroll, content);
       splitPane.setDividerLocation(150);
       splitPane.setDividerSize(7);

       treeViewDialog.add(splitPane, constraints);

       // asignación de los componentes a variables internas del diálogo
       treeViewDialog.setTreeView(treeView);
       treeViewDialog.setCardPane(content);
       treeViewDialog.setSplitPane(splitPane);
   }

   /**
    * Reseteamos los botones para cada nuevo diálogo que se vaya a crear.
    *
    * @param dialog El diálogo para el cual resetear los botones
    */
   private static void resetButtons(JIAExtensibleDialog dialog) {

       // creamos los botones principales de la aplicación, y les damos un tamaño fijo
       btnOk = new JButton("<html><font color=#1F7225>Aceptar</font></html>");
       btnOk.setMinimumSize(DEFAULT_BUTTONS_SIZE); btnOk.setPreferredSize(DEFAULT_BUTTONS_SIZE);
       btnCancel = new JButton("<html><font color=#984646>Cancelar</font></html>");
       btnCancel.setMinimumSize(DEFAULT_BUTTONS_SIZE); btnCancel.setPreferredSize(DEFAULT_BUTTONS_SIZE);

       // añadimos los listeners de los botones de la ventana principal

       // botón 'Ok'
       btnOk.addActionListener((ActionEvent e) -> {
           logInfo("Botón 'Ok' pulsado.\n");
           
           if (dialog.validar()) {
               logInfo("La validación tiene éxito, la aplicación continúa, persistiendo los datos.\n");
               dialog.salvar();
               dialog.limpiar();
               ((JFrame) (dialog.getTopLevelAncestor())).dispose();
            }
            else {
               logInfo("La validación fracasa, la aplicación continúa sin persistir los datos.\n");
               JOptionPane.showMessageDialog(dialog, "No se han podido validar los datos introducidos,"
                            + " por favor, vuelva a intentarlo.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
       });

       // botón 'Cancel'
       btnCancel.addActionListener((ActionEvent e) -> {
           logInfo("Botón 'Cancel' pulsado.\n");
           dialog.limpiar();
           ((JFrame) (dialog.getTopLevelAncestor())).dispose();
       });
   }

   /**
    * Crea o utiliza una envoltura (JFrame) para el JIAExtensibleDialog creado.
    * <p>
    * El tipo del 'parent' es JFrame con el fin de permitir el uso de más opciones.
    * De esta forma, se pueden utilizar ventanas como JFrame, u otras derivadas,
    * permitiendo ventanas personalizadas que hereden directa o indirectamente.
    *
    * @param parent Ventana que actúa como envoltura, puede ser null
    * @param dialog Diálogo a envolver por la ventana, no puede ser null
    * @param title Título de la ventana principal, puede ser null
    */
   private static void wrapExtensibleDialog(JFrame parent, JIAExtensibleDialog dialog, String title) {

       // si 'parent' no es nulo, se envuelve usando la ventana pasada como parámetro
       // si no se proporciona ninguna ventana, se crea una predeterminada como envoltura
       JFrame wrapper = parent != null ? parent : new JFrame();

       // se añade un listener para poder registrar la traza de ejecución
       wrapper.addWindowListener(new WindowAdapter() { @Override
           public void windowClosing(WindowEvent e) {
               logInfo("Ventana cerrándose.\n"); } });
       // operación por defecto al pulsar en cerrar: DISPOSE_ON_CLOSE, lo que provoca que esta ventana se
       // cierre y libere sus recursos, y si es la última ventana del programa, éste finaliza por completo
       wrapper.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       // tamaño mínimo por defecto para la ventana
       wrapper.setMinimumSize(dialog.getPreferredSize());
       // se asigna el título de la ventana, mirando primero el parámetro 'title', a
       // continuación el title del JFrame, y por último, el nombre de clase del diálogo
       wrapper.setTitle(validateString(title) ? title
               : validateString(wrapper.getTitle()) ? wrapper.getTitle() : dialog.getClass().getSimpleName());
       // foco por defecto de la ventana en el botón 'Aceptar'
       wrapper.getRootPane().setDefaultButton((JButton) dialog.getComponent(dialog.getComponentCount() - 2));
       // se añade el diálogo principal creado a la ventana que lo envuelve
       wrapper.add(dialog);
       // se hace visible la ventana, y con ella, el diálogo principal
       wrapper.setVisible(true);
   }
    
    /////////////////////// FUNCIONALIDADES DIÁLOGOS EXTENSIBLES //////////////////////////////////////////////
    
    /**
     * Devuelve el tamaño más grande (ancho y alto) de todos los tamaños de una lista de diálogos.
     *
     * @param extensibleDialogs La lista de diálogos
     * @return El tamaño más grande de todos ellos, es decir, el formado por la mayor anchura y la mayor altura
     */
    protected static Dimension getLargerSize(final List<JIAExtensibleDialog> extensibleDialogs) {

        // se empieza estableciendo el tamaño no negativo más pequeño posible
        final Dimension largerSize = new Dimension(0, 0);

        extensibleDialogs.stream().map((dialog) -> {
            largerSize.width = Math.max(dialog.getCurrentSize().width, largerSize.width); return dialog; })
                .forEachOrdered((dialog) -> { largerSize.height = Math.max(dialog.getCurrentSize().height, largerSize.height); });

        return largerSize;
    }

    /**
     * Comprueba si una cadena pasada como parámetro es válida (no es nula y no está vacía).
     *
     * @param cadena La cadena a validad
     * @return Verdadero si la cadena es válida, falso en otro caso
     */
    protected static boolean validateString(String cadena) {

        return cadena != null && !cadena.trim().isEmpty();
    }

    /**
     * Permite obtener el caption para un diálogo. Busca primero en su propiedad caption, y si
     * ésta es nula o está vacía, entonces establece como caption el nombre de clase del diálogo.
     *
     * @param dialog El diálogo del cuál se va a obtener el caption
     * @return Una cadena de caracteres que representa el caption adecuado
     */
    protected static String getProperCaption(JIAExtensibleDialog dialog) {

        return validateString(dialog.getCaption()) ?
            dialog.getCaption() : dialog.getClass().getSimpleName();
    }
}