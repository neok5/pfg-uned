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
package app.model.datospaciente.entity;

import app.model.IModel;

/**
 * Clase que representa un DNI como una secuencia de dígitos y una letra.
 *
 * @author Alberto Bausá Cano
 */
public class DNI implements IModel {
    
    private Integer digits;
    private char letter;

    public DNI(Integer digits, char letter) {
        this.digits = digits;
        this.letter = letter;
    }

    public Integer getDigits() {
        return digits;
    }

    public void setDigits(Integer digits) {
        this.digits = digits;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    @Override
    public String toString() {
        return digits + "-" + letter;
    }
}