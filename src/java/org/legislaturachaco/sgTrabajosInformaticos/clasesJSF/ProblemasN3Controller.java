package org.legislaturachaco.sgTrabajosInformaticos.clasesJSF;

import org.legislaturachaco.sgTrabajosInformaticos.entidades.ProblemasN3;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.util.JsfUtil;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.util.JsfUtil.PersistAction;
import org.legislaturachaco.sgTrabajosInformaticos.sessionBeans.ProblemasN3Facade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.ProblemasN1;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.ProblemasN2;

@Named("problemasN3Controller")
@SessionScoped
@ViewScoped
public class ProblemasN3Controller implements Serializable {

    @EJB
    private org.legislaturachaco.sgTrabajosInformaticos.sessionBeans.ProblemasN3Facade ejbFacade;
    private List<ProblemasN3> items = null;
    private ProblemasN3 selected;
    
    private ProblemasN2 tempN2;
    private ProblemasN1 tempN1;

    public ProblemasN3Controller() {
    }
    
    public ProblemasN2 getTempN2() {
        return tempN2;
    }

    public void setTempN2(ProblemasN2 tempN2) {
        this.tempN2 = tempN2;
        System.out.println("0 - "+tempN1+" "+tempN2);
    }

    public ProblemasN1 getTempN1() {
        return tempN1;
    }

    public void setTempN1(ProblemasN1 tempN1) {
        this.tempN1 = tempN1;
        System.out.println("0 - "+tempN1+" "+tempN2);
    }
    
    /**
     * Actualiza el id de Nivel 1.
     */
    public void buscarProblemasN2(){
        System.out.println("0 - "+tempN1+" "+tempN2);        
    }
    
    /**
     * Actualiza el id de Nivel 2.
     * 
     */
    public void setearIdProblemas(){
        System.out.println("0 - "+tempN1+" "+tempN2);
        //selected.setIdProblemasN1(tempN1);
        //selected.setIdProblemaN2(tempN2);
    }
    
    public int obtenerLongMaxProblema(){
        return ProblemasN3.LONG_MAX_PROBLEMA;
    }

    public ProblemasN3 getSelected() {
        return selected;
    }

    public void setSelected(ProblemasN3 selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProblemasN3Facade getFacade() {
        return ejbFacade;
    }

    public ProblemasN3 prepareCreate() {
        selected = new ProblemasN3();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProblemasN3Created"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProblemasN3Updated"));
    }

    public void destroy() {
        /*
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProblemasN3Deleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
        */
    }

    public List<ProblemasN3> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public ProblemasN3 getProblemasN3(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ProblemasN3> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ProblemasN3> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ProblemasN3.class)
    public static class ProblemasN3ControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProblemasN3Controller controller = (ProblemasN3Controller) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "problemasN3Controller");
            return controller.getProblemasN3(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ProblemasN3) {
                ProblemasN3 o = (ProblemasN3) object;
                return getStringKey(o.getIdProblemaN3());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ProblemasN3.class.getName()});
                return null;
            }
        }

    }

}
