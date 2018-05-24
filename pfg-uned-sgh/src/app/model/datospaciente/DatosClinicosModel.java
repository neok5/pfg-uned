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
 * Modelo para los datos clínicos del paciente, extiende de {@link DatoGeneralesModel}.
 *
 * @author Alberto Bausá Cano
 */
public class DatosClinicosModel extends DatoGeneralesModel {
    
    public DatosClinicosModel() { }
    
    private String asignedDoctor;
    private String currentMedication;
    private Boolean rcp;
    private String[] allergies;
    private String allergiesByLine;
    private Integer triajePriority = 0;

    public String getAsignedDoctor() {
        return asignedDoctor;
    }

    public void setAsignedDoctor(String asignedDoctor) {
        this.asignedDoctor = asignedDoctor;
    }

    public String getCurrentMedication() {
        return currentMedication;
    }

    public void setCurrentMedication(String currentMedication) {
        this.currentMedication = currentMedication;
    }

    public Boolean getRcp() {
        return rcp;
    }

    public void setRcp(Boolean rcp) {
        this.rcp = rcp;
    }

    public String[] getAllergies() {
        return allergies;
    }

    public void setAllergies(String[] allergies) {
        this.allergies = allergies;
    }

    public String getAllergies(int index) {
        return this.allergies[index];
    }

    public void setAllergies(int index, String allergies) {
        this.allergies[index] = allergies;
    }

    public String getAllergiesByLine() {
        StringBuilder sb = new StringBuilder("");
        if(allergies != null)
            for(String s : allergies) sb.append(s).append("\n");
        this.allergiesByLine = sb.toString();
        return allergiesByLine;
    }

    public void setAllergiesByLine(String allergiesByLine) {
        this.allergiesByLine = allergiesByLine;
        setAllergies(allergiesByLine.split("\n"));
    }

    public Integer getTriajePriority() {
        return triajePriority;
    }

    public void setTriajePriority(Integer triajePriority) {
        this.triajePriority = triajePriority;
    }
}