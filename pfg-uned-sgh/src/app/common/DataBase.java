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
package app.common;

import app.common.enums.PatientSexEnum;
import app.common.enums.PatientStateEnum;
import app.common.enums.UserRoleEnum;
import app.model.datospaciente.entity.DNI;
import app.model.datospaciente.DatosBancariosModel;
import app.model.datospaciente.DatosClinicosModel;
import app.model.datospaciente.DatoGeneralesModel;
import app.model.datospaciente.DatosPersonalesModel;
import app.model.datospaciente.ResumenPacienteModel;
import app.model.datospaciente.entity.Birthdate;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Clase que simula la base de datos de la aplicación. Se utiliza para albergar
 * todos los datos
 *
 * @author Alberto Bausá Cano
 */
public class DataBase implements Serializable {
    
    // Rol actual del usuario que ha iniciado sesión en el sistema
    private UserRoleEnum currentRole;
    // Colección de usuarios (profesionales) registrados: <user,pass>
    private final Map<String, String> registeredUsers = new HashMap<>();
    // Colección de pacientes registrados: <codeSNS,ResumenPacienteModel>
    private final Map<Long, ResumenPacienteModel> registeredPatients = new TreeMap<>();
    
    protected DataBase() {
        // Usuarios registrados en el sistema
        initRegisteredUsers();
        // Pacientes registrados en el sistema
        initRegisteredPatients();
    }
    
    private void initRegisteredUsers() {
        registeredUsers.put("Triaje", "");
        registeredUsers.put("Facultativo", "");
    }

    private void initRegisteredPatients() {
        ResumenPacienteModel patient;
        DatoGeneralesModel datosGenerales;
        
        
        datosGenerales = new DatoGeneralesModel("Tristán", "Tiburcio Rodriguez",
                new DNI(10000001, 'A'), 900000001L, PatientStateEnum.Registrado);
        patient = new ResumenPacienteModel(datosGenerales, new DatosPersonalesModel(),
                new DatosClinicosModel(), new DatosBancariosModel());
        patient.getDatosPersonales().setPatientSex(PatientSexEnum.Hombre);
        registeredPatients.put(900000001L, patient);
        
        
        datosGenerales = new DatoGeneralesModel("Maura", "Lope Nieves",
                new DNI(10000002, 'B'), 900000002L, PatientStateEnum.Espera);
        patient = new ResumenPacienteModel(datosGenerales, new DatosPersonalesModel(),
                new DatosClinicosModel(), new DatosBancariosModel());
        patient.getDatosPersonales().setPatientSex(PatientSexEnum.Mujer);
        registeredPatients.put(900000002L, patient);
        
        
        datosGenerales = new DatoGeneralesModel("Pastor", "Marino Salcedo",
                new DNI(10000003, 'C'), 900000003L, PatientStateEnum.Registrado);
        patient = new ResumenPacienteModel(datosGenerales, new DatosPersonalesModel(),
                new DatosClinicosModel(), new DatosBancariosModel());
        patient.getDatosPersonales().setPatientSex(PatientSexEnum.Desconocido);
        registeredPatients.put(900000003L, patient);
        
        
        datosGenerales = new DatoGeneralesModel("Ascensión", "Lucía Chávez",
                new DNI(10000004, 'D'), 900000004L, PatientStateEnum.Ingreso);
        patient = new ResumenPacienteModel(datosGenerales, new DatosPersonalesModel(),
                new DatosClinicosModel(), new DatosBancariosModel());
        patient.getDatosPersonales().setPatientSex(PatientSexEnum.Desconocido);
        registeredPatients.put(900000004L, patient);
        
        
        datosGenerales = new DatoGeneralesModel("Samanta", "Machado Guitiérrez",
                new DNI(10000005, 'E'), 900000005L, PatientStateEnum.Alta);
        patient = new ResumenPacienteModel(datosGenerales, new DatosPersonalesModel(),
                new DatosClinicosModel(), new DatosBancariosModel());
        patient.getDatosPersonales().setPatientSex(PatientSexEnum.Mujer);
        registeredPatients.put(900000005L, patient);
        
        
        datosGenerales = new DatoGeneralesModel("Leoncio", "Perla Orellana",
                new DNI(10000006, 'F'), 900000006L, PatientStateEnum.Alta);
        patient = new ResumenPacienteModel(datosGenerales, new DatosPersonalesModel(),
                new DatosClinicosModel(), new DatosBancariosModel());
        patient.getDatosPersonales().setPatientSex(PatientSexEnum.Hombre);
        registeredPatients.put(900000006L, patient);
        
        
        datosGenerales = new DatoGeneralesModel("Felisa", "Laura Guerra",
                new DNI(10000007, 'G'), 900000007L, PatientStateEnum.Registrado);
        patient = new ResumenPacienteModel(datosGenerales, new DatosPersonalesModel(),
                new DatosClinicosModel(), new DatosBancariosModel());
        patient.getDatosPersonales().setPatientSex(PatientSexEnum.Mujer);
        patient.getDatosBancarios().setAccountNumber(99944433212L);
        patient.getDatosBancarios().setHealthInsurance(Boolean.TRUE);
        patient.getDatosBancarios().setInsuranceCompany("ADESLAS");
        patient.getDatosClinicos().setAsignedDoctor("Pepito Juan Froilán");
        patient.getDatosPersonales().setBirthDate(new Birthdate(23, 05, 1992));
        patient.getDatosPersonales().setPostalAddress("Calle Falsa 1,2,3");
        patient.getDatosPersonales().setEmail("email@ejemplo.net");
        registeredPatients.put(900000007L, patient);
    }
    
    /////////////////////// GETTERS & SETTERS /////////////////////////////////////////////////////////////////////

    public UserRoleEnum getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(UserRoleEnum cRole) {
        currentRole = cRole;
    }

    public Map<String, String> getRegisteredUsers() {
        return registeredUsers;
    }

    public Map<Long, ResumenPacienteModel> getRegisteredPatients() {
        return registeredPatients;
    }

    /////////////////////// CLASE INTERNA PARA ERRORES ///////////////////////////////////////////////////////////
    
    /**
    * Clase que agrupa diferentes códigos de mensajes que se pueden enviar a lo largo de la aplicación.
    * Se utilizan constantes estáticas para referenciar los códigos de mensajes como variables de clase.
    * 
    * @author Alberto Bausá Cano
    */
   public static class MessageCodes {
       public final static String ID_RESUMEN_SLIDER = "001";
       public final static String ID_RESUMEN_STATE_IMAGE = "002";
   }
}