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
package app.common;

import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import library.JIAExtensibleDialog;
import library.JIAFactory;

/**
 * Clase utilitaria que engloba varias funcionalidades de forma estática.
 *
 * @author Alberto Bausá Cano
 */
public class Utils {
    
    /////////////////////// UTILIDADES DIÁLOGOS /////////////////////////////////////////////////////////////
    
    // Factoría
    // Se permite que tenga acceso público como caso especial para
    // simular un servicio de factoría desde cualquier ubicación
    public static final JIAFactory FACTORY = JIAFactory.getInstance();
    
    /**
     * Inicializa diálogos creados por el diseñador, controlando posibles errores.
     * 
     * @param dialog El diálogo del diseñador
     * @return El diálogo inicializado
     */
    public static JIAExtensibleDialog initDialog(JIAExtensibleDialog dialog) {
        
        if(!dialog.setUpDialog())
            throw new RuntimeException("El diálogo " + dialog + " no se ha podido inicializar correctamente.");
        return dialog;
    }
    
    /**
     * Crea, inicializa y devuelve un diálogo desde la factoría.
     * Utiliza el 'parent' por defecto de la factoría, es decir, un JFrame predeterminado.
     * 
     * @param type El tipo del diálogo a crear
     * @param name El nombre para el diálogo
     * @param title El título para la ventana que envolverá al diálogo
     * @return  El diálogo creado
     */
    public static JIAExtensibleDialog generateDialog(JIAExtensibleDialog.Type type, String name, String title) {
        return generateDialog(type, name, title, null);
    }
    
    /**
     * Crea, inicializa y devuelve un diálogo desde la factoría.
     * Utiliza el 'parent' diseñado que se le pasa como parámetro.
     * 
     * @param type El tipo del diálogo a crear
     * @param name El nombre para el diálogo
     * @param title El título para la ventana que envolverá al diálogo
     * @param parent El padre deseado como ventana para el diálogo
     * @return  El diálogo creado
     */
    public static JIAExtensibleDialog generateDialog(JIAExtensibleDialog.Type type, String name, String title, JFrame parent) {
        
        JIAExtensibleDialog dialog = FACTORY.createDialog(type, parent, name, title);
        if(!dialog.setUpDialog())
            throw new RuntimeException("El diálogo de tipo " + type + " no se ha podido generar correctamente.");
        return dialog;
    }
    
    /**
     * Permite centrar en pantalla la ventana que envuelve el diálogo.
     * 
     * @param dialog El diálogo
     */
    public static void centrarVentana(JIAExtensibleDialog dialog) {
        ((JFrame) dialog.getTopLevelAncestor()).setLocationRelativeTo(null);
    }

    /**
     * Comprueba si una cadena pasada como parámetro es válida (no es nula y no está vacía).
     *
     * @param cadena La cadena a validad
     * @return Verdadero si la cadena es válida, falso en otro caso
     */
    public static boolean validateString(String cadena) {

        return cadena != null && !cadena.trim().isEmpty();
    }
    
    /**
     * Limpia el diálogo especificado, y recursivamente todos los componentes que contiene.
     *
     * @param dialog El diálogo a resetear
     */
    public static void clearDialog(JIAExtensibleDialog dialog) {
        clearComponent(dialog);
    }
    
    /**
     * Deshabilita el diálogo especificado, y recursivamente todos los componentes que contiene.
     *
     * @param dialog El diálogo a deshabilitar
     */
    public static void disableDialog(JIAExtensibleDialog dialog) {
        disableComponent(dialog);
    }
    
    /////////////////////// UTLIDADES PERSISTENCIA //////////////////////////////////////////////////////////
    
    // Objeto para deserializar ficheros desde disco
    private static ObjectInputStream ois;
    // Objeto para serializar objetos a disco
    private static ObjectOutputStream oos;
    // Fichero auxiliar para obtener la ruta de ejecución actual
    private static final File FILE_AUX = new File("");

    /**
     * Permite recuperar la base de datos en disco situada en el archivo "dataBase.dat".
     * 
     * @return un objeto del tipo ServicioDatosInterface, leído de disco
     */
    public static DataBase readDB() {
        DataBase dataBase = null;

        try {
            String path = FILE_AUX.getCanonicalPath() + "\\src\\database\\dataBase.dat";
            ois = new ObjectInputStream(new FileInputStream(path));
            dataBase = (DataBase) ois.readObject();
            return dataBase; }
        catch(FileNotFoundException fnfe) {
            return null; }
        catch(ClassNotFoundException | IOException e) { }
        
        return dataBase;
    }

    /**
     * Permite serializar la base de datos en disco en un archivo llamado "dataBase.dat".
     * 
     * @param dataBase el objeto de la base de datos para escribirlo en disco
     */
    public static void writeDB(DataBase dataBase) {
        try {
            String path = FILE_AUX.getCanonicalPath() + "\\src\\database\\dataBase.dat";
            oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(dataBase); }
        catch(IOException ioe) { }
    }

    /////////////////////// FUNCIONALIDADES PRIVADAS ////////////////////////////////////////////////////////
    
    private static void clearComponent(Component c) {

        if(c instanceof JTextField)
            // texto vacío
            ((JTextField) c).setText("");
        else if(c instanceof JTextArea)
            // texto vacío
            ((JTextArea) c).setText("");
        else if(c instanceof JComboBox)
            // seleccionado primer elemento
            ((JComboBox) c).setSelectedIndex(0);
        else if(c instanceof JCheckBox)
            // casilla desmarcada
            ((JCheckBox) c).setSelected(false);
        else if(c instanceof JSlider)
            // valor establecido a mitad del rango
            ((JSlider) c).setValue(Math.floorDiv(
                ((JSlider) c).getMaximum() + ((JSlider) c).getMinimum(), 2));
        else if(c instanceof JSpinner)
            // valor establecido a cero
            ((JSpinner) c).setValue(0);
        else if(c instanceof Container)
            // por defecto, se realiza una llamada recursiva para limpiar un contenedor,
            // es decir, en caso de no ser uno de los componentes anteriores, se comprueba
            // si es un Container para recorrer recursivamente sus elementos de nuevo
            clearContainer((Container) c);
    }

    private static void disableComponent(Component c) {

        if(c instanceof JTextField
            || c instanceof JTextArea
                || c instanceof JComboBox
                    || c instanceof JCheckBox
                        || c instanceof JSlider
                            || c instanceof JSpinner)
            c.setEnabled(false);

        else if(c instanceof Container)
            // por defecto, se realiza una llamada recursiva para deshabilitar un contenedor,
            // es decir, en caso de no ser uno de los componentes anteriores, se comprueba
            // si es un Container para recorrer recursivamente sus elementos de nuevo
            disableContainer((Container) c);
    }

    private static void clearContainer(Container c) {
        List<Component> componentList;

        if(c instanceof JPanel || c instanceof JTabbedPane || c instanceof JSplitPane) {
            componentList = Arrays.asList(c.getComponents());

            componentList.forEach((comp) -> {
                clearComponent(comp); });
        }
        else if(c instanceof JScrollPane) {
            componentList = Arrays.asList(((JScrollPane) c).getViewport().getComponents());

            componentList.forEach((comp) -> {
                clearComponent(comp); });
        }
    }

    private static void disableContainer(Container c) {
        List<Component> componentList;

        if(c instanceof JPanel || c instanceof JTabbedPane || c instanceof JSplitPane) {
            componentList = Arrays.asList(c.getComponents());

            componentList.forEach((comp) -> {
                disableComponent(comp); });
        }
        else if(c instanceof JScrollPane) {
            componentList = Arrays.asList(((JScrollPane) c).getViewport().getComponents());

            componentList.forEach((comp) -> {
                disableComponent(comp); });
        }
    }
}