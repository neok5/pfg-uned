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
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * Clase que actúa como diálogo de tipo Tab, y hereda de JIAExtensibleDialog, la raíz de la jerarquía.
 * Cada diálogo que contiene se sitúa en una pestaña diferente, como máximo uno por pestaña.
 *
 * @author Alberto Bausá Cano
 */
public class JIATabDialog extends JIAExtensibleDialog {
    
    /**
     * Panel de pestañas interno. En la factoría se crea uno y se asigna al TabDialog que se está construyendo.
     * En el caso de que no sea creado por la factoría, se debe inicializar esta propiedad correctamente, siendo
     * el panel que se asigna al TabDialog el mismo que se le añade como componente a la hora de mostrarlo.
     */
    private JTabbedPane tabbedPane;
    
    /**
     * Alto de la pestaña del JTabbedPane. Se tiene en cuenta
     * a la hora de redimensionar, para sumar al tamaño final.
     */
    private static final int SWING_TAB_HEIGHT = 52;
    
    /**
     * Constructor vacío, por defecto. Convención JavaBean.
     */
    public JIATabDialog() { }
    
    /////////////////////// INTERFAZ PÚBLICA /////////////////////////////////////////////////////////////////////
    
   /**
     * {@inheritDoc}
     * <p>
     * En el caso de TabDialog, además de las inicializaciones comunes, comprueba que el tabbedPane no sea nulo.
     */
    @Override
    public final boolean setUpDialog() {
        return super.setUpDialog() && tabbedPane != null;
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * Por otro lado, para el caso de TabDialog, crea una nueva pestaña por cada JIAExtensibleDialog que se vaya
     * a añadir, estableciendo como título de la pestaña el caption pasado como parámetro. Además, llama a un
     * método de redimensionamiento, que, en caso de ser necesario, redimensionará los diálogos adecuadamente.
     */
    @Override
    public final void addExtensibleChild(JIAExtensibleDialog extensibleChild, String caption) {
        
        if(extensibleChild != null) {
            // parte común a todos los tipos
            super.addExtensibleChild(extensibleChild, caption);
            
            // parte específica del TabDialog
            String dialogCaption = JIAUtils.validateString(caption) ?
                    caption : JIAUtils.getProperCaption(extensibleChild);
            tabbedPane.addTab(dialogCaption, extensibleChild);

            resizeThis(extensibleChild.getCurrentSize());
        }
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * Por otro lado, para el caso de TabDialog, en primer lugar se hace la parte común para cada diálogo de la lista
     * (añadir a la lista de hijos, enlazar listeners, establecer padre). A continuación, se crea una nueva pestaña
     * en el diálogo al que se están añadiendo los hijos, y en esa pestaña se añade como componente principal un
     * nuevo TabbedPane interno, que contendrá una pestaña anidada por cada diálogo de la lista pasada como parámetro.
     * En caso de que algunos de los hijos tenga a su vez hijos, se llama de forma recursiva al método para crear
     * la estructura interna de ese hijo (con sus hijos) antes de añadirlo de forma efectiva al diálogo padre.
     * Por último, se obtiene un tamaño formado por la altura y anchura máximas de entre todos los diálogos de la lista,
     * y se utiliza para invocar el redimensionamiento que se ocupará de hacer los cambios necesarios en la jerarquía.
     */
    @Override
    public final void addExtensibleChildrenList(List<JIAExtensibleDialog> extensibleChildrenList, String caption) {
        
        if(extensibleChildrenList != null && !extensibleChildrenList.isEmpty()) {
            // parte común a todos los tipos
            extensibleChildrenList.forEach((dialog) -> { super.addExtensibleChild(dialog, ""); });
            
            // parte específica del TabDialog
            tabbedPane.addTab(caption, generateInternalPane(extensibleChildrenList));
            
            resizeThis(JIAUtils.getLargerSize(extensibleChildrenList));
        }
    }
    
    /////////////////////// INTERFAZ NO PÚBLICA /////////////////////////////////////////////////////////////////////

    /**
     * Método recursivo que, dada una lista de diálogos, los inserta en un panel de pestañas y devuelve dicho panel.
     * En caso de que alguno de los diálogos de la lista tenga hijos, se realiza una llamada recursive para construir
     * su panel de pestañas correspondiente, y así sucesivamente a lo largo de la jerarquía de parentesco de la lista.
     *
     * @param extensibleDialogs La lista de hijos relacionada
     * @return El panel de pestañas interno que contiene los diálogos de esa lista
     */
    private JTabbedPane generateInternalPane(List<JIAExtensibleDialog> extensibleDialogs) {

        JTabbedPane internalPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        
        extensibleDialogs.forEach((dialog) -> {
            // tiene hijos
            if(!dialog.getListaHijos().isEmpty())
                internalPane.addTab(JIAUtils.getProperCaption(dialog),
                            generateInternalPane(dialog.getListaHijos()));
            // no tiene hijos
            else
                internalPane.addTab(JIAUtils.getProperCaption(dialog), dialog);
        });
        
        return internalPane;
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * En el caso del TabDialog, si es llamado desde #addExtensibleChildrenList, se le pasa como Dimension
     * para el diálogo hijo la Dimension más grande obtenida a partir de la lista de todos los diálogos hijos.
     * <p>
     * El proceso consiste básicamente en el descrito en los comentarios del código.
     */
    @Override
    protected final void resizeThis(Dimension childSize) {
        
        final Dimension parentSize = this.getCurrentSize();
        // si es el diálogo principal (creado por la factoría), no podremos comparar
        // directamente las alturas de padre e hijo, sino que al padre tendremos que
        // restarle la altura de los botones (DEFAULT_DIALOG_SIZE.height), y ese será
        // el espacio del que dispondrá el hijo para encajar dentro del diálogo padre
        final int parentHeight = parentSize.height
                - (this.isFactoria() ? JIAUtils.DEFAULT_DIALOG_SIZE.height : 0);
        // inicialmente, cogemos el tamaño del padre como el final
        final Dimension finalSize = new Dimension(parentSize.width, parentHeight);
        
        // a continuación, modificamos el tamaño final asignándole el mayor ancho y
        // alto de los tamaños pasados como parámetros al método (el padre y su hijo)
        finalSize.width = Math.max(parentSize.width, childSize.width);
        finalSize.height = Math.max(parentHeight, childSize.height);
        
        // aún hay diálogos de más alto nivel en la jerarquía, por tanto no puede ser el principal
        if(this.getAncestor() != null) {
            // si el tamaño final no es igual que el del padre, es porque el
            // padre no era lo suficientemente grande para contener al hijo
            if(!finalSize.equals(parentSize)) {
                // por lo tanto, redimensionamos el padre (sumamos el tamaño de la pestaña de Swing)
                this.setCurrentSize(new Dimension(finalSize.width, finalSize.height+SWING_TAB_HEIGHT));
                this.setPreferredSize(new Dimension(finalSize.width, finalSize.height+SWING_TAB_HEIGHT));
                // y debemos llamar recursivamente al redimensionamiento del abuelo,
                // pasándole el nuevo tamaño del padre (su hijo) para compararlos
                this.getAncestor().resizeThis(this.getCurrentSize());
            }
            else;
            // en caso de que el padre sea suficientemente grande, el tamaño final seguirá siendo igual que
            // el del padre, por lo que no será necesario redimensionar el padre, y el proceso termina aquí
        }
        // se ha llegado al diálogo de nivel superior de la jerarquía
        else {
            // si el tamaño final no es igual que el del padre, es porque el
            // padre no era lo suficientemente grande para contener al hijo;
            // las anchuras se comparan directamente, pero para comparar las
            // alturas, deberemos, una vez más, restarle el alto de los botones
            // al padre en caso de que sea el diálogo principal de la factoría
            if(finalSize.width != parentSize.width || finalSize.height != parentHeight) {
                // es un diálogo intermedio, pero el superior de esta jerarquía
                if(!this.isFactoria()) {
                    // asignamos el tamaño y finalizamos (sumamos el tamaño de la pestaña de Swing)
                    this.setCurrentSize(new Dimension(finalSize.width, finalSize.height+SWING_TAB_HEIGHT));
                    this.setPreferredSize(new Dimension(finalSize.width, finalSize.height+SWING_TAB_HEIGHT));
                }
                // es el diálogo principal, el cual siempre es el superior de cualquier jerarquía
                else {
                    Dimension finalSizeWithButtons = new Dimension(finalSize.width,
                            finalSize.height + JIAUtils.DEFAULT_DIALOG_SIZE.height);
                    // asignamos el tamaño, sumando al alto el tamaño por defecto de los botones,
                    // tanto para el diálogo principal, como para la ventana que lo envuelve
                    // (sumamos el tamaño de la pestaña de Swing)
                    this.setCurrentSize(new Dimension(finalSizeWithButtons.width,
                                                        finalSizeWithButtons.height+SWING_TAB_HEIGHT));
                    this.setPreferredSize(new Dimension(finalSizeWithButtons.width,
                                                        finalSizeWithButtons.height+SWING_TAB_HEIGHT));
                    this.getTopLevelAncestor().setMinimumSize(new Dimension(finalSizeWithButtons.width,
                                                        finalSizeWithButtons.height+SWING_TAB_HEIGHT));
                    
                    if(this.getTopLevelAncestor() instanceof JFrame)
                        ((JFrame) this.getTopLevelAncestor()).pack();
                }
            }
            else;
            // en caso de que el padre sea suficientemente grande, el tamaño final seguirá siendo igual que
            // el del padre, por lo que no será necesario redimensionar el padre, y el proceso termina aquí
        }
    }
    
    /////////////////////// GETTERS & SETTERS /////////////////////////////////////////////////////////////////////

    /**
     * Devuelve el panel de pestañas de este diálogo.
     *
     * @return El panel de pestañas
     */
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    /**
     * Asigna al diálogo el panel de pestañas pasado como parámetro.
     *
     * @param tabbedPane El panel a asignar
     */
    public void setTabbedPane(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }
}