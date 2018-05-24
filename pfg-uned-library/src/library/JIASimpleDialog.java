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
import java.awt.GridBagConstraints;
import java.util.List;
import javax.swing.JFrame;

/**
 * Clase que actúa como diálogo de tipo Simple, y hereda de JIAExtensibleDialog, la raíz de la jerarquía.
 * Puede contener hasta un máximo de 2 diálogos, aunque también admite uno solo.
 *
 * @author Alberto Bausá Cano
 */
public class JIASimpleDialog extends JIAExtensibleDialog {
    
    /**
     * Constructor vacío, por defecto. Convención JavaBean.
     */
    public JIASimpleDialog() { }
    
    /////////////////////// INTERFAZ PÚBLICA /////////////////////////////////////////////////////////////////////
    
    /**
     * {@inheritDoc}
     * <p>
     * En el caso de SimpleDialog, puesto que no tiene componentes internos por defecto,
     * las comprobaciones se limitan a las comunes. Se añade a la interfaz por completitud.
     */
    @Override
    public final boolean setUpDialog() {
        return super.setUpDialog();
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * Por otro lado, para el caso de SimpleDialog, en primer lugar se realiza una comprobación de tamaño, para
     * asegurarse de que el diálogo no puede tener más de 2 diálogos como máximo. A continuación, se añade el
     * diálogo hijo al padre, respetando el layout utilizado (GridBagLayout). Por último, en caso de ser necesario,
     * se realiza un redimensionamiento adecuado, aumentando el tamaño para el diálogo y ventana principales.
     */
    @Override
    public final void addExtensibleChild(JIAExtensibleDialog extensibleChild, String caption) {
        
        // tamaño de la lista de hijos inicial (nunca mayor que 2)
        int nComponents = getListaHijos().size();
        
        // si el diálogo ya contiene 2 hijos, se lanza una excepción en tiempo de
        // ejecución al intentar añadir un tercero, como indican las especificaciones
        if(nComponents > 1)
            throw new RuntimeException("\n    - No se pueden añadir más de 2 hijos a un diálogo Simple."
                                     + "\n    - Error tratando de añadir: " + extensibleChild);
        
        if(extensibleChild != null) {
            // parte común a todos los tipos
            super.addExtensibleChild(extensibleChild, caption);

            // parte específica del SimpleDialog
            // se añade en la primera fila (no había diálogos previos),
            // o bien en la segunda fila (existía un diálogo previo)
            addWithConstraints(extensibleChild, nComponents);

            resizeThis(extensibleChild.getCurrentSize());
        }
    }
        
    /**
     * {@inheritDoc}
     * <p>
     * Por otro lado, como en el caso del SimpleDialog este método carece de utilidad real, lo que se hace es simular
     * varias llamadas al método que añade un diálogo individual. Aunque, como al igual que en dicho método el número
     * máximo de hijos que puede contener un diálogo simple es 2, se hace una comprobación (redundante), antes si
     * quiera de realizar las llamadas para añadirlos, puesto que en caso de no ser necesario, no se haría nada.
     */
    @Override
    public final void addExtensibleChildrenList(List<JIAExtensibleDialog> extensibleChildrenList, String caption) {
        
        // si el diálogo no tiene 'espacio' para los nuevos hijos, se lanza una excepción en
        // tiempo de ejecución al intentar añadir un tercero, como indican las especificaciones
        if(2 - getListaHijos().size() < extensibleChildrenList.size())
            throw new RuntimeException("\n    - Un diálogo Simple no puede tener más de 2 hijos."
                    + "\n    - Error tratando de añadir los diálogos, falta(n) "
                    + (extensibleChildrenList.size() - (2-getListaHijos().size())) + " espacio(s).");
        
        if(!extensibleChildrenList.isEmpty())
                extensibleChildrenList.forEach((dialog) -> { addExtensibleChild(dialog, ""); });
    }
    
    /////////////////////// INTERFAZ NO PÚBLICA /////////////////////////////////////////////////////////////////////

    /**
     * Establece las restricciones adecuadas para el GridBagLayout y añade el hijo
     * al contenedor del diálogo. En este caso, dado que hereda de JPanel, que es
     * por si mismo un contenedor, basta con usar el método add() de JPanel.
     * <p>
     * Asignamos un 45% de peso por diálogo para las filas, y el 10% restante para la fila de los botones.
     * Además, hacemos que cada diálogo ocupe una anchura de 2 columnas (cada botón ocupa 1, sumando en conjunto 2).
     *
     * @param extensibleChild El diálogo hijo a añadir
     * @param fila La fila donde añadirlo (solo será la primera [0] o segunda [1] fila
     */
    private void addWithConstraints(JIAExtensibleDialog extensibleChild, int fila) {
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = fila;
        constraints.gridwidth = 2;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        this.add(extensibleChild, constraints);
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * Dado que la disposición en los SimpleDialog es vertical, formando una columna, el tamaño a comparar
     * será el de la anchura del nuevo diálogo hijo a añadir.
     * <p>
     * En caso de que la anchura del diálogo hijo sea mayor que la del padre, la nueva anchura del padre
     * pasará a ser la del diálogo hijo (la máxima de ambas), las alturas de ambos, padre e hijo, se sumarán.
     * <p>
     * En caso de ser menor o igual que la anchura del diálogo padre, no será necesario modificar ésta,
     * siendo solo necesario modificar la altura, sumando ambas, la del diálogo padre y el diálogo hijo.
     */
    @Override
    protected final void resizeThis(Dimension childSize) {
        
        if(this.isFactoria()) {
            final Dimension parentSize = this.getCurrentSize();
            final Dimension finalSize = new Dimension(parentSize);

            finalSize.width = Math.max(parentSize.width, childSize.width);
            finalSize.height = parentSize.height + childSize.height;

            this.setCurrentSize(finalSize);
            this.setPreferredSize(finalSize);
            this.getTopLevelAncestor().setMinimumSize(finalSize);
            
            if(this.getTopLevelAncestor() instanceof JFrame)
                ((JFrame) this.getTopLevelAncestor()).pack();
        }
        else
            throw new RuntimeException("\n    - No se pueden insertar diálogos a un diálogo Simple"
                                                            + " si no es creado por la factoría.");
    }
}