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
package app.view_controller.menuprincipal;

import app.common.Utils;
import app.model.datospaciente.ResumenPacienteModel;
import app.view_controller.datospaciente.DatosBancariosVC;
import app.view_controller.datospaciente.DatosClinicosVC;
import app.view_controller.datospaciente.DatosGeneralesVC;
import app.view_controller.datospaciente.DatosPersonalesVC;
import app.view_controller.datospaciente.ResumenPacienteVC;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import library.JIATreeViewDialog;

/**
 *
 * @author Alberto Bausá Cano
 */
public class FilaPacienteFacultativoVC extends library.JIASimpleDialog {

    private final ResumenPacienteModel model;
        
    /**
     * Creates new form FilaPacienteFacultativoVC
     * 
     * @param resumenPacienteModel El modelo para el diálogo
     */
    public FilaPacienteFacultativoVC(ResumenPacienteModel resumenPacienteModel) {
        model = resumenPacienteModel;
        initComponents();
        initData();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lbSexImage = new javax.swing.JLabel();
        btnStartAttention = new javax.swing.JButton();
        lbCompleteName = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(470, 40));
        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        add(lbSexImage, gridBagConstraints);

        btnStartAttention.setBackground(new java.awt.Color(255, 153, 153));
        btnStartAttention.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnStartAttention.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/start.png"))); // NOI18N
        btnStartAttention.setText(" Iniciar atención");
        btnStartAttention.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startPatientAttention(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 15);
        add(btnStartAttention, gridBagConstraints);

        lbCompleteName.setText("Nombre completo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        add(lbCompleteName, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void startPatientAttention(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startPatientAttention
        if(JOptionPane.showConfirmDialog(null, "¿Desea iniciar la atención de este paciente?",
        "Confirmar inicio atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
            
            ((MenuFacultativoVC) ((JButton) evt.getSource()).getParent().getParent()).getTopLevelAncestor().setVisible(false);
            
            ResumenPacienteModel modeloPaciente =
                    ((FilaPacienteFacultativoVC) ((JButton) evt.getSource()).getParent()).getModel();
            
            ResumenPacienteVC resumenPacienteVC = (ResumenPacienteVC) Utils.initDialog(new ResumenPacienteVC(modeloPaciente));
            DatosGeneralesVC datosGeneralesVC = (DatosGeneralesVC) Utils.initDialog(new DatosGeneralesVC(modeloPaciente.getDatosGenerales()));
            datosGeneralesVC.setCaption("General");
            DatosPersonalesVC datosPersonalesVC = (DatosPersonalesVC) Utils.initDialog(new DatosPersonalesVC(modeloPaciente.getDatosPersonales()));
            datosPersonalesVC.setCaption("Personal");
            DatosClinicosVC datosClinicosVC = (DatosClinicosVC) Utils.initDialog(new DatosClinicosVC(modeloPaciente.getDatosClinicos()));
            datosClinicosVC.setCaption("Clínica");
            DatosBancariosVC datosBancariosVC = (DatosBancariosVC) Utils.initDialog(new DatosBancariosVC(modeloPaciente.getDatosBancarios()));
            datosBancariosVC.setCaption("Bancaria");
            
            JIATreeViewDialog fichaPaciente = (JIATreeViewDialog)
                Utils.generateDialog(Type.TREE, "fichaPaciente", "FICHA PACIENTE ["
                    + modeloPaciente.getDatosGenerales().getSurname() + ", " + modeloPaciente.getDatosGenerales().getName() + "]");
            fichaPaciente.setTreeRootName("Ficha de paciente");
            fichaPaciente.addExtensibleChild(resumenPacienteVC, "Resumen");
            fichaPaciente.addExtensibleChildrenList(Arrays.asList(datosGeneralesVC, datosPersonalesVC, datosClinicosVC, datosBancariosVC), "Información");
            fichaPaciente.getTreeView().expandRow(0); // expandimos la raíz por defect
            fichaPaciente.getTreeView().expandRow(2); // y también el nodo hijo
            ((JFrame) fichaPaciente.getTopLevelAncestor()).setMinimumSize(new Dimension(700, 500));
            Utils.centrarVentana(fichaPaciente);
            
            ((JFrame) fichaPaciente.getTopLevelAncestor()).addWindowListener(new WindowAdapter() {
                @Override public void windowClosed(WindowEvent e) {
                    ((MenuFacultativoVC) ((JButton) evt.getSource()).getParent().getParent()).getTopLevelAncestor().setVisible(true);
                    ((MenuFacultativoVC) ((JButton) evt.getSource()).getParent().getParent()).getTopLevelAncestor().revalidate();
                }
            });
        }
    }//GEN-LAST:event_startPatientAttention
    
    @Override
    public boolean validateThis() { return true; } // nada que validar

    @Override
    public void saveThis() { } // nada que guardar

    @Override
    public void cleanThis() { }

    @Override
    public void getExternVal(String id, Object value) { }

    private void initData() {
        lbSexImage.setIcon(new ImageIcon(getTriajePriorityImagePath()));
        lbCompleteName.setText(getCompleteName());
        lbCompleteName.setToolTipText(getCompleteName());
        btnStartAttention.setToolTipText("Iniciar atención al paciente. Abre su ficha");
    }
    
    public String getCompleteName() {
        return (model != null && model.getDatosGenerales() != null) ?
                model.getDatosGenerales().getName() + ", " + model.getDatosGenerales().getSurname() : "";
    }    
    
    private String getTriajePriorityImagePath() {
        File aux = new File("");
        String path = "";
        try { path = aux.getCanonicalPath() + "/src/images"; } catch (IOException ioe) { }
        
        if(!path.trim().isEmpty() && model.getDatosClinicos().getTriajePriority() != null) {

            Integer val = model.getDatosClinicos().getTriajePriority();
            if(val >= 0 && val <= 3)
                path += "/prioridad1Normal.png";
            else if(val > 3 && val <= 5)
                path += "/prioridad2Leve.png";
            else if(val > 5 && val <= 7)
                path += "/prioridad3Moderado.png";
            else if(val > 7 && val <= 8)
                path += "/prioridad4Grave.png";
            else if(val > 8 && val < 11)
                path += "/prioridad5Critico.png";
            else
                path += "/prioridad0Original.png";
        }
        
        return path;
    }

    public ResumenPacienteModel getModel() {
        return model;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStartAttention;
    private javax.swing.JLabel lbCompleteName;
    private javax.swing.JLabel lbSexImage;
    // End of variables declaration//GEN-END:variables
}
