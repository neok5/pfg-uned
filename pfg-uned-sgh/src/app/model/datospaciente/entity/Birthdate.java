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
 * Clase que representa la fecha de nacimiento como tres enteros, para día, mes y año.
 *
 * @author Alberto Bausá Cano
 */
public class Birthdate implements IModel {
    
    private Integer day;
    private Integer month;
    private Integer year;

    public Birthdate(Integer day, Integer month, Integer year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}