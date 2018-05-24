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

import javax.swing.JFrame;
import library.JIAExtensibleDialog.Type;

/**
 * Factoría de la librería. Permite crear y obtener diálogos de estructura mínima, es decir,
 * botones, un contenedor y una vista de árbol para navegación en el caso de JIATreeViewDialog.
 * <p>
 * La sigla JIA viene simplemente de 'Java Inteligencia Artificial', en alusión al departamento.
 * Por ello, todas las clases principales de la librería tendrán dicho prefijo.
 * <p>
 * Se hace uso del patrón Singleton para la factoría, asegurando una instancia única.
 *
 * @author Alberto Bausá Cano
 */
public final class JIAFactory {
    
    /////////////////////// INTERFAZ PÚBLICA /////////////////////////////////////////////////////////////////////
    
    /**
     * Crea y devuelve un diálogo del tipo indicado por el primer parámetro.
     *
     * @param type El tipo de diálogo que se quiere crear
     * @param parent La ventana principal (padre) que envuelve el diálogo principal creado
     * @param name El nombre del objeto interno del diálogo que se le quiera asignar (containerName)
     * @param title El título de la ventana principal de la aplicación
     * @return El diálogo creado, o null en caso de que no se haya creado correctamente
     */
    public final JIAExtensibleDialog createDialog(Type type, JFrame parent, String name, String title) {
        
        JIAUtils.logInfo("Creando diálogo de tipo " + type + "...\n");
        final JIAExtensibleDialog mainDialog =
                JIAUtils.createWithTrivialImplementation(type, parent, name, title);
        JIAUtils.logInfo("Diálogo de tipo " + type + " creado con éxito.\n");

        return mainDialog;
    }
    
    /**
     * Crea, en caso de no existir, una instancia de la factoría, y la devuelve.
     *
     * @return El singleton existente (o creado) para la factoría
     */
    public static final JIAFactory getInstance() {
        
        if(INSTANCIA == null)
            createInstance();
        
        return INSTANCIA;
    }
    
    /**
     * Se sobreescribe el método clone() de la clase Object, el cual lanza una excepción
     * con el fin de indicar que la instancia del Singleton de JIAFactory no es clonable.
     * 
     * @return nada
     * @throws CloneNotSupportedException En el caso de que se intente clonar la instancia
     */
    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public final JIAFactory clone() throws CloneNotSupportedException {
        
        throw new CloneNotSupportedException("La clase JIAFactory está"
                + " implementada usando el patrón Singleton, y por tanto, no es clonable.");
    }
    
    /////////////////////// INTERFAZ NO PÚBLICA //////////////////////////////////////////////////////////////////
    
    /**
     * Instancia única de la clase (Singleton).
     */
    private static JIAFactory INSTANCIA = null;
    
    /**
     * Constructor privado, para evitar que se pueda instanciar.
     */
    private JIAFactory() { }
    
    /**
     * Si no existe una instancia de la Factoría, la crea. Se trata de un método con acceso interno
     * sincronizado con el fin de evitar problemas en el caso de ejecución concurrente o multi-hilo.
     */
    private static void createInstance() {
        
        synchronized(JIAFactory.class) {
            if(INSTANCIA == null)
                INSTANCIA = new JIAFactory();
        }
    }
}