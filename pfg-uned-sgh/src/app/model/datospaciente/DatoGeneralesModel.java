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
package app.model.datospaciente;

import app.model.datospaciente.entity.DNI;
import app.common.enums.PatientStateEnum;
import app.model.IModel;

/**
 * Modelo para los datos generales del paciente.
 *
 * @author Alberto Bausá Cano
 */
public class DatoGeneralesModel implements IModel {

    public DatoGeneralesModel() { }

    public DatoGeneralesModel(String name, String surname,
            DNI dni, Long codeSNS, PatientStateEnum state) {
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.codeSNS = codeSNS;
        this.state = state;
    }

    private String name;
    private String surname;
    private DNI dni;
    private Long codeSNS;
    private PatientStateEnum state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public DNI getDni() {
        return dni;
    }

    public void setDni(DNI dni) {
        this.dni = dni;
    }

    public Long getCodeSNS() {
        return codeSNS;
    }

    public void setCodeSNS(Long codeSNS) {
        this.codeSNS = codeSNS;
    }

    public PatientStateEnum getState() {
        return state;
    }

    public void setState(PatientStateEnum state) {
        this.state = state;
    }
}