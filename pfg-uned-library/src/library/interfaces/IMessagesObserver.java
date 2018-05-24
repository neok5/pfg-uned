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
package library.interfaces;

/**
 * Interfaz que define el código que puede implementar un diálogo para enviar y recibir mensajes externos
 * desde otros diálogos. Es decir, habilita el paso de mensajes (desde y hacia) diálogos de la librería.
 *
 * @author Alberto Bausá Cano
 */
public interface IMessagesObserver {
    
    /**
     * Habilita la recepción de un mensaje desde otro diálogo externo.
     * <p>
     * Se le da una implementación por defecto, la cual lanza una excepción avisando de que se debe redefinir.
     * De esta forma se convierte un error en tiempo de compilación, como la falta de implementación
     * para un método heredado, en un error en tiempo de ejecución, con el lanzamiento de la excepción.
     * <p>
     * La redefinición puede ser trivial, si así lo considera el diseñador.
     *
     * @param id Identificador del mensaje recibido
     * @param value Valor recibido de forma externa
     */
    default void getExternVal(final String id, final Object value) {
        
        System.out.println("Recibiendo mensaje para ExtensibleDialog...");
        throw new RuntimeException("Se debe redefinir la implementación de este método [getExternVal].");
    }
}