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

/**
 * Modelo para los datos bancarios del paciente, extiende de {@link DatoGeneralesModel}.
 *
 * @author Alberto Bausá Cano
 */
public class DatosBancariosModel extends DatoGeneralesModel {
    
    public DatosBancariosModel() { }
    
    private Long accountNumber;
    private Boolean healthInsurance;
    private String insuranceCompany;

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Boolean getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(Boolean healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }
}