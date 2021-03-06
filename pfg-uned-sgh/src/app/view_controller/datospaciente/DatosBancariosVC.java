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
package app.view_controller.datospaciente;

import app.common.Utils;
import app.model.datospaciente.DatosBancariosModel;

/**
 * Diálogo para los datos bancarios de un paciente.
 *
 * @author Alberto Bausá Cano
 */
public class DatosBancariosVC extends library.JIASimpleDialog {

    private DatosBancariosModel model;
    /**
     * Creates new form DatosBancariosVC
     * 
     * @param datosBancariosModel Modelo para el diálogo
     */
    public DatosBancariosVC(DatosBancariosModel datosBancariosModel) {
        model = datosBancariosModel;
        initComponents();
        if(model.getHealthInsurance() != null)  rbHealthInsurance.setSelected(model.getHealthInsurance());
        if(rbHealthInsurance.isSelected()) { lbInsuranceCompany.setEnabled(true); tfCompanyInsurance.setEnabled(true); }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jLabel1 = new javax.swing.JLabel();
        tfAccountNumber = new javax.swing.JTextField();
        rbHealthInsurance = new javax.swing.JRadioButton();
        tfCompanyInsurance = new javax.swing.JTextField();
        lbInsuranceCompany = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setPreferredSize(new java.awt.Dimension(300, 140));
        setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Nº cuenta bancaria:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 10, 5);
        add(jLabel1, gridBagConstraints);

        tfAccountNumber.setColumns(20);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${model.accountNumber}"), tfAccountNumber, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 10, 20);
        add(tfAccountNumber, gridBagConstraints);

        rbHealthInsurance.setText("Seguro médico");
        rbHealthInsurance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activateCompanyInsuranceTF(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(rbHealthInsurance, gridBagConstraints);

        tfCompanyInsurance.setColumns(20);
        tfCompanyInsurance.setEnabled(rbHealthInsurance.isSelected());

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${model.insuranceCompany}"), tfCompanyInsurance, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 20);
        add(tfCompanyInsurance, gridBagConstraints);

        lbInsuranceCompany.setText("Compañía aseguradora");
        lbInsuranceCompany.setEnabled(rbHealthInsurance.isSelected());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 2, 20);
        add(lbInsuranceCompany, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("<html><font color= \"#173266\">Datos bancarios</font></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 5, 20);
        add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        add(jSeparator1, gridBagConstraints);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void activateCompanyInsuranceTF(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activateCompanyInsuranceTF
        tfCompanyInsurance.setEnabled(rbHealthInsurance.isSelected());
        lbInsuranceCompany.setEnabled((rbHealthInsurance.isSelected()));
    }//GEN-LAST:event_activateCompanyInsuranceTF

    @Override
    public boolean validateThis() {
        // valida que los campos no estén vacíos, y si está seleccionado
        // el seguro médico, que la compañía aseguradora no esté vacía
        return Utils.validateString(tfAccountNumber.getText())
                && (rbHealthInsurance.isSelected() ?
                    Utils.validateString(tfCompanyInsurance.getText()) : true);
    }

    @Override
    public void saveThis() {
        // guarda los datos en el modelo, y con ello, en la base de datos
        this.model.setAccountNumber(Long.parseLong(tfAccountNumber.getText()));
        this.model.setHealthInsurance(rbHealthInsurance.isSelected());
        this.model.setInsuranceCompany(model.getHealthInsurance() ? tfCompanyInsurance.getText() : "");
    }

    @Override
    public void cleanThis() { }

    @Override
    public void getExternVal(String id, Object value) { }

    public DatosBancariosModel getModel() {
        return model;
    }

    public void setModel(DatosBancariosModel model) {
        this.model = model;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbInsuranceCompany;
    private javax.swing.JRadioButton rbHealthInsurance;
    private javax.swing.JTextField tfAccountNumber;
    private javax.swing.JTextField tfCompanyInsurance;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
