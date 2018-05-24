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
package app;

import app.common.Application;

/**
 * Clase principal del programa de prueba de la librería.
 * <p>
 * Las siglas SGH  del proyecto corresponden a Software de Gestión Hospitalaria,
 * puesto que el programa emula una aplicación con ese fin, siempre reducido a la
 * escala que supone una demostración de prueba, y al tiempo estimado para el PFG.
 *
 * @author Alberto Bausá Cano
 */
public class Main {

    /**
     * Método principal del programa de prueba de la librería, desde el cuál arranca todo.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application app = Application.getInstance();
        app.run();
    }
}