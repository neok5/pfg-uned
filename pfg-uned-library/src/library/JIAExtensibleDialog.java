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

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JPanel;
import library.interfaces.IMessagesObserver;
import library.interfaces.IRecursiveActions;

/**
 * Diálogo principal, que hereda de JPanel para conectar con Swing, y sirve como base al resto
 * de tipos de diálogos de la librería: JIASimpleDialog, JIATabDialog y JIATreeViewDialog.
 * <p>
 * Se crea como clase abstracta para poder delegar la implementación de alguno de sus métodos
 * a las clases hijas de la jerarquía, así como para también evitar que se pueda instanciar
 * de manera directa sin implementar sus métodos genéricos (que cambian según el tipo de diálogo).
 * 
 * @author Alberto Bausá Cano
 */
public abstract class JIAExtensibleDialog extends JPanel implements IRecursiveActions, IMessagesObserver {
    
    /**
     * Padre del diálogo, establecido al añadirlo como hijo a otro diálogo.
     * Puede ser nulo si se trata del diálogo principal, de más alto nivel.
     */
    private JIAExtensibleDialog ancestor;
    
    /**
     * Nombre usado para mostrar en el encabezado de las pestañas (TabDialog)
     * o bien como nombre de la entrada en la vista de árbol (TreeViewDialog).
     */
    private String caption;
    
    /**
     * Nombre interno de objeto del diálogo, para poder referenciarlo en caso de ser necesario.
     */
    private String containerName;
    
    /**
     * Dimensiones del diálogo. En este caso, se usa el tamaño preferido del diálogo.
     * Se utiliza para el redimensionamiento, y debido a que se trata de un mecanismo
     * interno se le da un acceso protegido para sus métodos de acceso y modificación.
     */
    private Dimension currentSize;
    
    /**
     * Flag usado a la hora de redimensionar, que indica si el diálogo en cuestión ha
     * sido creado por la factoría. Solo será 'true' cuando sea creado por la factoría.
     */
    private boolean factoria;
    
    /**
     * Lista de hijos, siguiendo el patrón de objeto compuesto (patrón Composite).
     */
    private List<JIAExtensibleDialog> listaHijos;
    
    /**
     * Colección de listeners para este diálogo. Todos están asociados con (escuchan a) todos.
     * Esto posibilita una emisión de mensajes broadcast, es decir, desde un nodo a todos.
     */
    private Set<JIAExtensibleDialog> listaListeners;
    
    /**
     * Constructor vacío. Convención JavaBean.
     * <p>
     * Solo inicializamos el flag de la factoría, por defecto falso.
     */
    public JIAExtensibleDialog() { factoria = false; }

    /////////////////////// INTERFAZ PÚBLICA /////////////////////////////////////////////////////////////////////
    
    /**
     * Inicializaciones propias del diálogo reusable.
     *
     * @return Verdadero si el diálogo se ha inicializado correctamente, falso en otro caso
     */
    public boolean setUpDialog() {
        
        setAncestor(ancestor != null ? ancestor : null);
        setCaption(JIAUtils.getProperCaption(this));
        setContainerName(containerName != null ? containerName : this.getClass().getSimpleName());
        setCurrentSize(currentSize != null ? currentSize : this.getPreferredSize());
        listaHijos = new ArrayList<>();
        listaListeners = new HashSet<>();
        
        return ancestor == null && caption != null && containerName != null
            && currentSize != null && listaHijos != null && listaListeners != null;
    }
    
    /**
     * Añade el diálogo pasado como parámetro al diálogo sobre el cuál se invoca.
     * <p>
     * Por un lado, tendrá una parte común a todos los tipos de diálogo: actualizar la lista de hijos,
     * asociar los listeners adecuadamente, y establecer el padre del hijo que se está añadiendo.
     *
     * @param extensibleChild El hijo que se quiere añadir
     * @param caption En el diálogo de tipo Simple no se usa, pero en el caso tanto de Tab como de Tree,
     *                es el título de la pestaña o entrada del árbol, respectivamente, que se va a añadir
     */
    public void addExtensibleChild(final JIAExtensibleDialog extensibleChild, final String caption) {
        
        if(extensibleChild != null) {
            // añadimos el hijo a la lista
            listaHijos.add(extensibleChild);
            // asociamos los listeners de manera adecuada
            linkListeners(extensibleChild);
            // asociamos el padre con su hijo
            extensibleChild.setAncestor(this);
        }
    }
    
    /**
     * Añade la lista de diálogos pasados como parámetro al diálogo sobre el cuál se invoca.
     * <p>
     * Por un lado, tendrá una parte común a todos los tipos de diálogo: actualizar la lista de hijos,
     * asociar los listeners adecuadamente, y establecer el padre del hijo que se está añadiendo.
     * Es decir, lo mismo que el método individual, pero para cada uno de los diálogos de la lista.
     * 
     * @see #addExtensibleChild
     *
     * @param extensibleChildrenList La lista de hijos relacionados que se quieren añadir
     * @param caption En el diálogo de tipo Simple no se usa, pero en el caso tanto de Tab como de Tree,
     *                es el título de la pestaña o entrada del árbol 'superior', respectivamente, donde
     *                se anidarán todos los hijos relacionados de la lista pasada como parámetro
     */
    public void addExtensibleChildrenList(final List<JIAExtensibleDialog> extensibleChildrenList, final String caption) {

        extensibleChildrenList.forEach((dialog) -> { addExtensibleChild(dialog, ""); });
    }
    
    @Override
    public String toString() {
        return this.getCaption();
    }

    /////////////////////// INTERFAZ NO PÚBLICA /////////////////////////////////////////////////////////////////////
    
    /**
     * Vincula los diferentes listeners entre sí, de cara a construir la estructura para el paso de mensajes.
     * <p>
     * El algoritmo es el siguiente: en primer lugar, se construye una 'lista superior'
     * de listeners, formada por el padre más todos sus listeners, y en segundo lugar se
     * construye una 'lista inferior' de listeners, formada por el hijo más todos sus listeners.
     * <p>
     * A continuación, se recorre la lista inferior, y para cada uno de sus elementos se añade la lista
     * superior completa. Después, se recorre la lista superior, y para cada uno de sus elementos se
     * añade la lista inferior completa. De esta manera, quedan todos enlazados, formando un grafo completo.
     *
     * @param listener El listener (diálogo - IMessagesObserver) a añadir
     */
    protected void linkListeners(final JIAExtensibleDialog listener) {
        
        Set<JIAExtensibleDialog> listaSuperior = new HashSet<>(listaListeners);
        Set<JIAExtensibleDialog> listaInferior = new HashSet<>(listener.getListaListeners());
        
        listaSuperior.add(this);
        listaInferior.add(listener);
        
        listaInferior.forEach((elemInf) -> { elemInf.getListaListeners().addAll(listaSuperior); });
        listaSuperior.forEach((elemSup) -> { elemSup.getListaListeners().addAll(listaInferior); });
    }
    
    /**
     * Dispara el proceso de validación recursiva para todos los diálogos de la jerarquía.
     *
     * @return Verdadero si la validación es existosa, falso en otro caso
     */
    protected final boolean validar() {
        
        boolean correcto = true;
        Iterator<JIAExtensibleDialog> it = this.listaHijos.iterator();
        
        while(it.hasNext() && correcto)
            correcto = it.next().validar();
        
        return correcto ? this.validateThis() : false;
    }
    
    /**
     * Dispara el proceso de guardado de cambios realizados para todos los diálogos de la jerarquía.
     */
    protected final void salvar() {
        
        this.listaHijos.forEach((dialog) -> { dialog.salvar(); });
        
        this.saveThis();
    }
    
    /**
     * Dispara el proceso de cierre y finalización para todos los diálogos de la jerarquía.
     */
    protected final void limpiar() {
        
        this.listaHijos.forEach((dialog) -> { dialog.limpiar(); });
        
        this.cleanThis();
    }
    
    /**
     * Habilita el envío de un mensaje a otro diálogo externo.
     * <p>
     * Simplemente se encarga de recorrer la lista de listeners de un diálogo,
     * llamando para cada uno, a su método de recepción de mensajes externos.
     * 
     * @see #getExternVal
     *
     * @param id Identificador del mensaje enviado
     * @param value Valor enviado de forma externa
     */
    protected void cambiaVal(final String id, final Object value) {
        
        if(getListaListeners() != null)
            getListaListeners().forEach((listener) -> { listener.getExternVal(id, value); });
    }
    
    /**
     * Calcula el nuevo tamaño para el diálogo padre, si es necesario, en función del tamaño del diálogo hijo.
     *
     * @param childSize El tamaño del hijo que se quiere añadir
     */
    protected abstract void resizeThis(Dimension childSize);
    
    /////////////////////// GETTERS & SETTERS /////////////////////////////////////////////////////////////////////
    
    /**
     * Devuelve el padre del diálogo.
     *
     * @return El padre
     */
    protected JIAExtensibleDialog getAncestor() {
        return ancestor;
    }

    /**
     * Asigna el padre pasado como parámetro al diálogo.
     *
     * @param ancestor El padre que se quiere asignar
     */
    protected void setAncestor(final JIAExtensibleDialog ancestor) {
        this.ancestor = ancestor;
    }

    /**
     * Devuelve el caption del diálogo.
     *
     * @return El caption del diálogo
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Asigna el caption pasado como parámetro al diálogo.
     *
     * @param caption El caption que se quiere asignar
     */
    public void setCaption(final String caption) {
        this.caption = caption;
    }
    
    /**
     * Devuelve el containerName del diálogo.
     * 
     * @return El containerName del diálogo
     */
    public String getContainerName() {
        return containerName != null ? containerName : "";
    }

    /**
     * Asigna el containerName pasado como parámetro al diálogo.
     *
     * @param containerName El containerName que se quiere asignar
     */
    public void setContainerName(final String containerName) {
        this.containerName = containerName;
    }

    /**
     * Devuelve el tamaño del diálogo. Usado para el redimensionamiento.
     * 
     * @return El tamaño como un objeto Dimension
     */

    protected Dimension getCurrentSize() {
        return currentSize;
    }

    /**
     * Asigna el tamaño pasado como parámetro. Usado para el redimensionamiento.
     * 
     * @param currentSize El tamaño que se quiere asignar
     */
    protected void setCurrentSize(final Dimension currentSize) {
        this.currentSize = currentSize;
    }

    /**
     * Devuelve el flag que indica que un diálogo ha sido creado por la factoría.
     *
     * @return Verdadero si ha sido creado por la factoría, falso en otro caso
     */
    protected boolean isFactoria() {
        return factoria;
    }

    /**
     * Asigna el valor pasado como parámetro al flag de creado por la factoría.
     *
     * @param factoria El valor que se quiere asignar
     */
    protected void setFactoria(boolean factoria) {
        this.factoria = factoria;
    }
    
    /**
     * Devuelve la lista de hijos del diálogo.
     *
     * @return La lista
     */
    protected List<JIAExtensibleDialog> getListaHijos() {
        return listaHijos;
    }

    /**
     * Devuelve la lista de observadores del diálogo.
     *
     * @return La lista
     */
    protected Set<JIAExtensibleDialog> getListaListeners() {
        return listaListeners;
    }
    
    /**
     * Enumerado para distinguir los tipos de diálogo existentes en la librería.
     * <p>
     * Se le da un campo interno de tipo String únicamente con fines de log.
     */
    public enum Type {
        
        SIMPLE("Simple"), TAB("Pestañas"), TREE("Árbol");

        /**
         * Representa en forma de cadena el tipo de diálogo.
         */
        private final String value;

        /**
         * Constructor privado, no instanciable desde fuera.
         *
         * @param value
         */
        private Type(final String value) {
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return value;
        }
    }
}