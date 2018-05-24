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

import app.model.IModel;

/**
 * Clase que sirve como representación de un paciente en el sistema. Engloba
 * todos sus datos, generales, así como los personales, clínicos y bancarios.
 *
 * @author Alberto Bausá Cano
 */
public class ResumenPacienteModel implements IModel {
    
    public ResumenPacienteModel() { }

    public ResumenPacienteModel(DatoGeneralesModel datosPaciente, DatosPersonalesModel datosPersonales,
                        DatosClinicosModel datosClinicos, DatosBancariosModel datosBancarios) {
        this.datosGenerales = datosPaciente;
        this.datosPersonales = datosPersonales;
        this.datosClinicos = datosClinicos;
        this.datosBancarios = datosBancarios;
    }
    
    private DatoGeneralesModel datosGenerales;
    private DatosPersonalesModel datosPersonales;
    private DatosClinicosModel datosClinicos;
    private DatosBancariosModel datosBancarios;

    public DatoGeneralesModel getDatosGenerales() {
        return datosGenerales;
    }

    public void setDatosGenerales(DatoGeneralesModel datosGenerales) {
        this.datosGenerales = datosGenerales;
    }

    public DatosPersonalesModel getDatosPersonales() {
        return datosPersonales;
    }

    public void setDatosPersonales(DatosPersonalesModel datosPersonales) {
        this.datosPersonales = datosPersonales;
    }

    public DatosClinicosModel getDatosClinicos() {
        return datosClinicos;
    }

    public void setDatosClinicos(DatosClinicosModel datosClinicos) {
        this.datosClinicos = datosClinicos;
    }

    public DatosBancariosModel getDatosBancarios() {
        return datosBancarios;
    }

    public void setDatosBancarios(DatosBancariosModel datosBancarios) {
        this.datosBancarios = datosBancarios;
    }
}