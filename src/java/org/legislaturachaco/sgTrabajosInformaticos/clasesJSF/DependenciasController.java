package org.legislaturachaco.sgTrabajosInformaticos.clasesJSF;

import org.legislaturachaco.sgTrabajosInformaticos.entidades.Dependencias;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.util.JsfUtil;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.util.JsfUtil.PersistAction;
import org.legislaturachaco.sgTrabajosInformaticos.sessionBeans.DependenciasFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("dependenciasController")
@SessionScoped
public class DependenciasController implements Serializable {

    @EJB
    private org.legislaturachaco.sgTrabajosInformaticos.sessionBeans.DependenciasFacade ejbFacade;
    private List<Dependencias> items = null;
    private Dependencias selected;

    public DependenciasController() {
    }
    
    public int getLongMaxObservaciones(){
        return Dependencias.LONG_MAX_OBSERVACIONES;
    }
    
    public int getCObservaciones(){
        return Dependencias.LONG_MAX_OBSERVACIONES/getFObservaciones();
    }
    
    public int getFObservaciones(){
        return 4;
    }
    
    public int getLongMaxDependencia(){
        return Dependencias.LONG_MAX_DEPENDENCIA;
    }
    
    public int getCDependencia(){
        return Dependencias.LONG_MAX_DEPENDENCIA/getFDependencia();
    }
    
    public int getFDependencia(){
        return 1;
    }
    
    public int getLongMaxNombreCorto(){
        return Dependencias.LONG_MAX_NOMBRE_CORTO;
    }
    
    public int getCNombreCorto(){
        return Dependencias.LONG_MAX_NOMBRE_CORTO/getFNombreCorto();
    }
    
    public int getFNombreCorto(){
        return 1;
    }

    public Dependencias getSelected() {
        return selected;
    }

    public void setSelected(Dependencias selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DependenciasFacade getFacade() {
        return ejbFacade;
    }

    public Dependencias prepareCreate() {
        selected = new Dependencias();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DependenciasCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DependenciasUpdated"));
    }

    public void destroy() {
        /*
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DependenciasDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
        */
    }

    public List<Dependencias> getItems() {
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

    public Dependencias getDependencias(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Dependencias> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Dependencias> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Dependencias.class)
    public static class DependenciasControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DependenciasController controller = (DependenciasController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "dependenciasController");
            return controller.getDependencias(getKey(value));
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
            if (object instanceof Dependencias) {
                Dependencias o = (Dependencias) object;
                return getStringKey(o.getIdDependencia());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Dependencias.class.getName()});
                return null;
            }
        }

    }

}
