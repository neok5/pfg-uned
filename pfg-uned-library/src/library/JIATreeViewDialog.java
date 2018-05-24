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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Clase que actúa como diálogo de tipo TreeView, y hereda de JIAExtensibleDialog, raíz de la jerarquía.
 * Cada diálogo que contiene se sitúa en una entrada diferente de la vista de árbol, con la posibilidad
 * de que exista anidamiendo de entradas con un número arbitrario de niveles. Un diálogo por entrada.
 *
 * @author Alberto Bausá Cano
 */
public class JIATreeViewDialog extends JIAExtensibleDialog {
    
    /**
     * Nombre de la raíz de la vista de árbol de la parte izquierda del diálogo.
     */
    private String treeRootName;
    
    /** 
     * Vista de árbol del diálogo. Contiene el listado de elementos del diálogo dispuestos jerárquicamente.
     */
    private JTree treeView;
    
    /**
     * Panel divisor del diálogo. A la izquierda contendrá la vista de árbol, y a la derecha el panel apilado.
     */
    private JSplitPane splitPane;
    
    /**
     * Panel de tarjetas del diálogo. Contiene una colección con todos los diálogos hijos del diálogo principal,
     * y permite mostrarlos de forma selectiva e individual, acorde al nodo seleccionado en la vista de árbol.
     */
    private JPanel cardPane;
    
    /**
     * Conjunto de nombres de los componentes añadidos al panel de contenido del diálogo.
     */
    private Set<String> componentNames;
    
    /**
     * Constructor vacío, por defecto. Convención JavaBean.
     */
    public JIATreeViewDialog() { }
    
    /////////////////////// INTERFAZ PÚBLICA /////////////////////////////////////////////////////////////////////
    
    /**
     * {@inheritDoc}
     * <p>
     * En el caso de TreeViewDialog, además de las inicializaciones comunes, comprueba que el splitPane,
     * treeView y cardPane no sean nulos. En caso de que el nombre para la raíz del árbol sea nulo o vacío,
     * se le asigna un nombre a la raíz basado en el nombre de la clase del diálogo.
     */
    @Override
    public final boolean setUpDialog() {
        
        setTreeRootName(JIAUtils.validateString(treeRootName) ?
                treeRootName : this.getClass().getSimpleName());
        componentNames = new HashSet<>();
        return super.setUpDialog() && splitPane != null && treeView != null && cardPane != null;
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * Por otro lado, para el caso de TreeViewDialog, crea una nueva entrada en el árbol (un nuevo nodo)
     * para el diálogo que se va a añadir, estableciendo como nombre de la entrada el caption pasado como
     * parámetro (o en su defecto, obteniendo el caption que pueda tener el propio diálogo, y en último caso,
     * el nombre de clase del diálogo). También añade el diálogo, junto con el mismo caption utilizado para
     * la entrada en el árbol, al CardPanel del panel de contenido de la derecha. Además, llama a un método
     * de redimensionamiento, que, en caso de ser necesario, redimensionará los diálogos adecuadamente.
     */
    @Override
    public final void addExtensibleChild(JIAExtensibleDialog extensibleChild, String caption) {
        
        if(extensibleChild != null) {
            // parte común a todos los tipos
            super.addExtensibleChild(extensibleChild, caption);

            // parte específica del TreeViewDialog
            String dialogCaption = JIAUtils.validateString(caption) ?
                    caption : JIAUtils.getProperCaption(extensibleChild);
            ((DefaultMutableTreeNode) treeView.getModel().getRoot())
                    .add(new DefaultMutableTreeNode(dialogCaption));
            cardPane.add(extensibleChild, dialogCaption);

            resizeThis(extensibleChild.getCurrentSize());
        }
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * Por otro lado, para el caso de TreeViewDialog, en primer lugar se hace la parte común para cada diálogo de la
     * lista (añadir a la lista de hijos, enlazar listeners, establecer padre). A continuación, crea una nueva entrada
     * (nodo) en el árbol, el cuál es "rellenado" con la lista de diálogos hijos pasada como parámetro. Para ello, se
     * invoca un método auxiliar. Una vez generado el nodo correctamente, se añade al nodo raíz de la vista de árbol.
     * Por último, se obtiene un tamaño formado por la altura y anchura máximas de entre todos los diálogos de la lista,
     * y se utiliza para invocar el redimensionamiento que se ocupará de hacer los cambios necesarios en la jerarquía.
     */
    @Override
    public final void addExtensibleChildrenList(List<JIAExtensibleDialog> extensibleChildrenList, String caption) {
        
        if(extensibleChildrenList != null && !extensibleChildrenList.isEmpty()) {
            // parte común a todos los tipos
            extensibleChildrenList.forEach((dialog) -> { super.addExtensibleChild(dialog, ""); });
            
            // parte específica del TreeViewDialog
            ((DefaultMutableTreeNode) treeView.getModel().getRoot())
                    .add(generateInternalNode(extensibleChildrenList, caption));
            addDialogsToContent(extensibleChildrenList);
            
            resizeThis(JIAUtils.getLargerSize(extensibleChildrenList));
        }
    }
    
    /////////////////////// INTERFAZ NO PÚBLICA /////////////////////////////////////////////////////////////////////

    /**
     * Se encarga de construir la estructura interna del nodo que actúa como nueva entrada en el árbol. Para ello,
     * utiliza la lista de hijos, y las listas de hijos de dichos hijos (en caso de que alguno de ellos tenga), para
     * construir recursivamente la jerarquía de nodos, teniendo como nodo raíz el de la nueva entrada en el árbol.
     *
     * @param extensibleDialogs La lista de diálogos relacionados
     * @param caption El caption para la entrada del árbol que engloba la lista
     * @return El nodo raíz para la entrada del árbol
     */
    private DefaultMutableTreeNode generateInternalNode(List<JIAExtensibleDialog> extensibleDialogs, String caption) {
        
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(caption);
        
        extensibleDialogs.forEach((dialog) -> {
            // tiene hijos
            if(!dialog.getListaHijos().isEmpty())
                rootNode.add(generateInternalNode(dialog.getListaHijos(), JIAUtils.getProperCaption(dialog)));
            // no tiene hijos
            else
                rootNode.add(new DefaultMutableTreeNode(JIAUtils.getProperCaption(dialog)));
        });
        
        return rootNode;
    }

    /**
     * Se encarga de añadir la lista de diálogos al panel de contenido del árbol.
     *
     * @param extensibleChildrenList
     */
    private void addDialogsToContent(List<JIAExtensibleDialog> extensibleChildrenList) {
        
        extensibleChildrenList.forEach((dialog) -> {
            // tiene hijos
            if(!dialog.getListaHijos().isEmpty())
                addDialogsToContent(dialog.getListaHijos());
            // no tiene hijos
            else
                cardPane.add(dialog, checkRepeatedName(JIAUtils.getProperCaption(dialog))); });
    }

    /**
     * Comprueba si el nombre con el que se va a añadir el diálogo está repetido, y, en tal caso,
     * lo modifica con el fin de que no lo esté, básicamente añadiendo un asterisco (*) al final.
     *
     * @param name El nombre a comprobar
     * @return El nombre final con el que se añadirá el diálogo
     */
    private String checkRepeatedName(String name) {
        
        // si el nombre está repetido, llama recursivamente con el nombre modificado
        return componentNames.contains(name) ? checkRepeatedName(name + "*") : name;
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * En el caso del TreeViewDialog, si es llamado desde #addExtensibleChildrenList, se le pasa como Dimension
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
        // en el caso del TreeViewDialog, la zona donde va el diálogo hijo es el panel
        // de contenido situado en el lado derecho, por lo que es el tamaño de ese panel
        // lo que debemos comparar con el diálogo hijo, y no el TreeViewDialog completo;
        // para ello, restamos el ancho del JTree, y el ancho del divisor del SplitPanel
        final int parentWidth = parentSize.width
                - (this.getSplitPane().getDividerLocation() + this.getSplitPane().getDividerSize());
        // y con todo ello, tenemos el tamaño del panel de contenido del TreeViewDialog
        final Dimension contentPaneSize = new Dimension(parentWidth, parentHeight);
        // inicialmente, cogemos el tamaño del panel de contenido como el final
        final Dimension finalSize = new Dimension(contentPaneSize);
        
        // a continuación, modificamos el tamaño final asignándole el mayor ancho y
        // alto de los tamaños pasados como parámetros al método (el padre y su hijo)
        finalSize.width = Math.max(contentPaneSize.width, childSize.width);
        finalSize.height = Math.max(contentPaneSize.height, childSize.height);
            
        // aún hay diálogos de más alto nivel en la jerarquía, por tanto no puede ser el principal
        if(this.getAncestor() != null) {
            // si el tamaño final no es igual que el del panel del padre, es porque el
            // panel del padre no era lo suficientemente grande para contener al hijo
            if(!finalSize.equals(contentPaneSize)) {
                // por lo tanto, redimensionamos el padre, asignándole como nuevo tamaño
                // el finalSize que ha quedado, y sumando el ancho del JTree y el divider
                this.setCurrentSize(new Dimension(finalSize.width
                                                    + this.getSplitPane().getDividerLocation()
                                                    + this.getSplitPane().getDividerSize(),
                                                  finalSize.height));
                this.setPreferredSize(new Dimension(finalSize.width
                                                    + this.getSplitPane().getDividerLocation()
                                                    + this.getSplitPane().getDividerSize(),
                                                  finalSize.height));
                // y debemos llamar recursivamente al redimensionamiento del abuelo,
                // pasándole el nuevo tamaño del padre (su hijo) para compararlos;
                // le pasamos el tamaño del padre, no el del panel de contenido
                this.getAncestor().resizeThis(this.getCurrentSize());
            }
            else;
            // en caso de que el padre sea suficientemente grande, el tamaño final seguirá siendo igual que
            // el del padre, por lo que no será necesario redimensionar el padre, y el proceso termina aquí
        }
        // se ha llegado al diálogo de nivel superior de la jerarquía
        else {
            // si el tamaño final no es igual que el del panel del padre, es porque el
            // panel del padre no era lo suficientemente grande para contener al hijo
            if(!finalSize.equals(contentPaneSize)) {
                // es un diálogo intermedio, pero el superior de esta jerarquía
                if(!this.isFactoria()) {
                    // por lo tanto, redimensionamos el padre, asignándole como nuevo tamaño
                    // el finalSize que ha quedado, y sumando el ancho del JTree y el divider
                    this.setCurrentSize(new Dimension(finalSize.width
                                                        + this.getSplitPane().getDividerLocation()
                                                        + this.getSplitPane().getDividerSize(),
                                                      finalSize.height));
                    this.setPreferredSize(new Dimension(finalSize.width
                                                        + this.getSplitPane().getDividerLocation()
                                                        + this.getSplitPane().getDividerSize(),
                                                      finalSize.height));
                }
                // es el diálogo principal, el cual siempre es el superior de cualquier jerarquía
                else {
                    Dimension finalSizeWithButtons = new Dimension(finalSize.width
                                                        + this.getSplitPane().getDividerLocation()
                                                        + this.getSplitPane().getDividerSize(),
                                                      finalSize.height + JIAUtils.DEFAULT_DIALOG_SIZE.height);
                    // asignamos el tamaño, sumando al alto el tamaño por defecto de los botones, y sumando
                    // el ancho del JTree y el divider, para el diálogo principal y la ventana que lo envuelve
                    this.setCurrentSize(finalSizeWithButtons);
                    this.setPreferredSize(finalSizeWithButtons);
                    this.getTopLevelAncestor().setMinimumSize(finalSizeWithButtons);
            
                    if(this.getTopLevelAncestor() instanceof JFrame)
                        ((JFrame) this.getTopLevelAncestor()).pack();
                }
            }
            else;
            // en caso de que el padre sea suficientemente grande, el tamaño final seguirá siendo igual que
            // el del padre, por lo que no será necesario redimensionar el padre, y el proceso termina aquí
        }
        
        // en caso de que el padre sea suficientemente grande, el tamaño final seguirá siendo igual que
        // el del padre, por lo que no será necesario redimensionar el padre, y el proceso termina aquí
    }
    
    /////////////////////// GETTERS & SETTERS /////////////////////////////////////////////////////////////////////

    /**
     * Devuelve el nombre de la raíz de la vista de árbol.
     *
     * @return El nombre de la raíz
     */

    public String getTreeRootName() {
        return treeRootName;
    }

    /**
     * Asigna al diálogo el nombre de la raíz de la vista de árbol pasado como parámetro.
     *
     * @param treeRootName El nombre de la raíz
     */
    public void setTreeRootName(String treeRootName) {
        
        this.treeRootName = treeRootName;
        if(treeView != null && treeView.getModel() != null && treeView.getModel().getRoot() != null)
                    ((DefaultMutableTreeNode) treeView.getModel().getRoot()).setUserObject(treeRootName);
    }
    
    /**
     * Devuelve la vista de árbol de este diálogo.
     *
     * @return La vista de árbol
     */
    public JTree getTreeView() {
        return treeView;
    }

    /**
     * Asigna al diálogo la vista de árbol pasado como parámetro.
     *
     * @param treeView La vista a asignar
     */
    public void setTreeView(JTree treeView) {
        this.treeView = treeView;
    }

    /**
     * Devuelve el panel divisor de este diálogo.
     *
     * @return El panel divisor
     */
    public JSplitPane getSplitPane() {
        return splitPane;
    }

    /**
     * Asigna al diálogo el panel divisor pasado como parámetro.
     *
     * @param splitPane El panel a asignar
     */
    public void setSplitPane(JSplitPane splitPane) {
        this.splitPane = splitPane;
    }

    /**
     * Devuelve el panel de tarjetas de este diálogo.
     *
     * @return El panel de tarjetas
     */
    public JPanel getCardPane() {
        return cardPane;
    }

    /**
     * Asigna al diálogo el panel de tarjetas pasado como parámetro.
     *
     * @param cardPane El panel a asignar
     */
    public void setCardPane(JPanel cardPane) {
        this.cardPane = cardPane;
    }
}