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
package app.model.menuprincipal;

import app.model.IModel;
import app.model.datospaciente.ResumenPacienteModel;
import java.util.Map;

/**
 * Modelo para la pantalla del menu principal del rol 'Triaje'.
 * 
 * @author Alberto Bausá Cano
 */
public class MenuTriajeModel implements IModel {
    
    public MenuTriajeModel() { }

    public MenuTriajeModel(Map<Long, ResumenPacienteModel> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }
    
    private Map<Long, ResumenPacienteModel> listaPacientes;

    public Map<Long, ResumenPacienteModel> getListaPacientes() {
        return listaPacientes;
    }

    public void setListaPacientes(Map<Long, ResumenPacienteModel> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }
}