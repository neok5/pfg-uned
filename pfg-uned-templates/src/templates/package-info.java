@TemplateRegistrations({
    @TemplateRegistration(
        folder = "GUIForms",
        iconBase="templates/JIAPanelIcon.png", 
        displayName = "#SimpleJIAPanel_displayName", 
        content = {"SimpleJIAPanel.java.template", "SimpleJIAPanel.form.template"},
        description = "SimpleJIADescription.html",
        scriptEngine="freemarker"),
    @TemplateRegistration(
        folder = "GUIForms",
        iconBase="templates/JIAPanelIcon.png", 
        displayName = "#TabJIAPanel_displayName", 
        content = {"TabJIAPanel.java.template", "TabJIAPanel.form.template"},
        description = "TabJIADescription.html",
        scriptEngine="freemarker"),
    @TemplateRegistration(
        folder = "GUIForms",
        iconBase="templates/JIAPanelIcon.png", 
        displayName = "#TreeViewJIAPanel_displayName", 
        content = {"TreeViewJIAPanel.java.template", "TreeViewJIAPanel.form.template"},
        description = "TreeViewJIADescription.html",
        scriptEngine="freemarker")
})
@Messages({"SimpleJIAPanel_displayName=Diálogo Simple vacío",
           "TabJIAPanel_displayName=Diálogo Pestañas vacío",
           "TreeViewJIAPanel_displayName=Diálogo Vista de Árbol vacío"})
package templates;

import org.netbeans.api.templates.TemplateRegistration;
import org.netbeans.api.templates.TemplateRegistrations;
import org.openide.util.NbBundle.Messages;