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

import app.common.enums.UserRoleEnum;
import app.model.menuprincipal.MenuFacultativoModel;
import app.model.menuprincipal.MenuTriajeModel;
import app.view_controller.LoginVC;
import app.view_controller.menuprincipal.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import library.JIAExtensibleDialog;
import library.JIASimpleDialog;

/**
 * Aplicación para una demo de software de gestión hospitalaria.
 *
 * @author Alberto Bausá Cano
 */
public class Application {
    
    // Base de datos. Estática y pública, accesible para todos.
    public static DataBase db;
    
    /**
     * Constructor privado, para evitar que se pueda instanciar.
     */
    private Application() {
        // Inicialización de la base de datos
        initDataBase();
    }
    
    /**
     * Llamado desde la librería para lanzar la ejecución de la aplicación.
     */
    public void run() {
        // Pantalla inicial de inicio de sesión
        showLoginForm();
    }
    
    /////////////////////// INTERFAZ PRIVADA /////////////////////////////////////////////////////////////////////
    
    private void initDataBase() {
        // se intenta leer la DB de fichero y en caso de no existir se crea una nueva
        DataBase temp = Utils.readDB();
        db = temp != null ? temp : new DataBase();
    }
    
    private void showLoginForm() {
        
        final LoginVC loginVC = (LoginVC) Utils.initDialog(new LoginVC());
        
        JIASimpleDialog pantallaLogin = (JIASimpleDialog)
            Utils.generateDialog(JIAExtensibleDialog.Type.SIMPLE, "loginVC", "INICIO DE SESIÓN");
        pantallaLogin.addExtensibleChild(loginVC, "");
        Utils.centrarVentana(pantallaLogin);
        
        ((JFrame) pantallaLogin.getTopLevelAncestor()).addWindowListener(new WindowAdapter() {
            @Override public void windowClosed(WindowEvent e) {
                openMainMenu(db.getCurrentRole());
            } });
    }
    
    private void openMainMenu(UserRoleEnum currentRole) {
        
        // Dado que la pantalla de Login es el punto de salida de la aplicación, cada vez que la cerremos
        // de manera definitiva, y con ella la aplicación (pulsando en la X o en el botón de 'Cancelar'),
        // debemos activar la persistencia con el fin de conservar el estado de ejecución de la apicación
        if(currentRole == null)
            Utils.writeDB(db);
        
        else {
            switch(currentRole) {
                case Triaje:
                    openTriajeMenu();
                    break;
                case Facultativo:
                    openFacultativoMenu();
                    break;
            }
        }
    }
    
    private void openTriajeMenu() {
        
        final MenuTriajeVC menuTriajeVC = (MenuTriajeVC) Utils.initDialog(
                new MenuTriajeVC(new MenuTriajeModel(db.getRegisteredPatients())));
        
        JIASimpleDialog menuTriaje = (JIASimpleDialog)
            Utils.generateDialog(JIAExtensibleDialog.Type.SIMPLE, "menuTriajeVC", "MENÚ PRINCIPAL TRIAJE");
        menuTriaje.addExtensibleChild(menuTriajeVC, "");
        Utils.centrarVentana(menuTriaje);
        
        ((JFrame) menuTriaje.getTopLevelAncestor()).addWindowListener(new WindowAdapter() {
            @Override public void windowClosed(WindowEvent e) {
                db.setCurrentRole(null);
                showLoginForm(); } });
    }
    
    private void openFacultativoMenu() {
        
        final MenuFacultativoVC menuFacultativoVC = (MenuFacultativoVC) Utils.initDialog(
                new MenuFacultativoVC(new MenuFacultativoModel(db.getRegisteredPatients())));
        
        JIASimpleDialog menuFacultativo = (JIASimpleDialog)
            Utils.generateDialog(JIAExtensibleDialog.Type.SIMPLE, "menuFacultativoVC", "MENÚ PRINCIPAL FACULTATIVO");
        menuFacultativo.addExtensibleChild(menuFacultativoVC, "");
        Utils.centrarVentana(menuFacultativo);
        
        ((JFrame) menuFacultativo.getTopLevelAncestor()).addWindowListener(new WindowAdapter() {
            @Override public void windowClosed(WindowEvent e) {
                db.setCurrentRole(null);
                showLoginForm(); } });
    }

    /////////////////////// PATRÓN SINGLETON /////////////////////////////////////////////////////////////////////

    /**
     * Instancia única de la clase (Singleton).
     */
    private static Application INSTANCIA = null;
    
    /**
     * Crea, en caso de no existir, una instancia de la aplicación, y la devuelve.
     *
     * @return El singleton existente (o creado) para la aplicación
     */
    public static final Application getInstance() {
        
        if(INSTANCIA == null)
            createInstance();
        
        return INSTANCIA;
    }

    /**
     * Si no existe una instancia de la Aplicación, la crea. Se trata de un método con acceso interno
     * sincronizado con el fin de evitar problemas en el caso de ejecución concurrente o multi-hilo.
     */
    private static void createInstance() {
        
        synchronized(Application.class) {
            if(INSTANCIA == null)
                INSTANCIA = new Application();
        }
    }
    
    /**
     * Se sobreescribe el método clone() de la clase Object, el cual lanza una excepción
     * con el fin de indicar que la instancia del Singleton de Application no es clonable.
     * 
     * @return nada
     * @throws CloneNotSupportedException En el caso de que se intente clonar la instancia
     */
    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public final Application clone() throws CloneNotSupportedException {
        
        throw new CloneNotSupportedException("La clase Application está"
                + " implementada usando el patrón Singleton, y por tanto, no es clonable.");
    }
}