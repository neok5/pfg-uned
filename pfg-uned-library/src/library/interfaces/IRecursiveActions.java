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
 * Interfaz que define los métodos recursivos de validación, salvado de datos y finalización
 * que se ejecutan cuando son pulsados los botones de 'Ok' o 'Cancel' del diálogo principal.
 *
 * @author Alberto Bausá Cano
 */
public interface IRecursiveActions {
    
    /**
     * Valida el diálogo que lo implementa, y solo ese, de la forma que decida implementarlo el diseñador.
     * <p>
     * Se le da una implementación por defecto, la cual lanza una excepción avisando de que se debe redefinir.
     * De esta forma se convierte un error en tiempo de compilación, como la falta de implementación
     * para un método heredado, en un error en tiempo de ejecución, con el lanzamiento de la excepción.
     * <p>
     * La redefinición puede ser trivial, si así lo considera el diseñador.
     *
     * @return Verdadero si la validación es exitosa, falso en otro caso
     */
    default boolean validateThis() {
        
        System.out.println("Validando ExtensibleDialog...");
        throw new RuntimeException("Se debe redefinir la implementación de este método [validateThis].");
    }
    
    /**
     * Guarda los cambios realizados para este diálogo, de forma que persistan adecuadamente.
     * <p>
     * Se le da una implementación por defecto, la cual lanza una excepción avisando de que se debe redefinir.
     * De esta forma se convierte un error en tiempo de compilación, como la falta de implementación
     * para un método heredado, en un error en tiempo de ejecución, con el lanzamiento de la excepción.
     * <p>
     * La redefinición puede ser trivial, si así lo considera el diseñador.
     */
    default void saveThis() {
        
        System.out.println("Guardando ExtensibleDialog...");
        throw new RuntimeException("Se debe redefinir la implementación de este método [saveThis].");
    }
    
    /**
     * Limpia y se ocupa de las tareas de finalización en el cierre de un diálogo.
     * <p>
     * Se puede invocar tanto como último paso al pulsar 'Ok', como directamente el único paso al pulsar 'Cancel'.
     * <p>
     * Se le da una implementación por defecto, la cual lanza una excepción avisando de que se debe redefinir.
     * De esta forma se convierte un error en tiempo de compilación, como la falta de implementación
     * para un método heredado, en un error en tiempo de ejecución, con el lanzamiento de la excepción.
     * <p>
     * La redefinición puede ser trivial, si así lo considera el diseñador.
     */
    default void cleanThis() {
        
        System.out.println("Limpiando ExtensibleDialog...");
        throw new RuntimeException("Se debe redefinir la implementación de este método [cleanThis].");
    }
}