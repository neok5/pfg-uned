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

import app.common.enums.PatientSexEnum;
import app.model.datospaciente.entity.Birthdate;

/**
 * Modelo para los datos personales del paciente, extiende de {@link DatoGeneralesModel}.
 *
 * @author Alberto Bausá Cano
 */
public class DatosPersonalesModel extends DatoGeneralesModel {
    
    public DatosPersonalesModel() { }
    
    private Birthdate birthDate;
    private PatientSexEnum patientSex;
    private String email;
    private String postalAddress;
    private Long phoneNumber;

    public Birthdate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Birthdate birthDate) {
        this.birthDate = birthDate;
    }

    public PatientSexEnum getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(PatientSexEnum patientSex) {
        this.patientSex = patientSex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}