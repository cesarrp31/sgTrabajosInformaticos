package org.legislaturachaco.sgTrabajosInformaticos.clasesJSF;

import org.legislaturachaco.sgTrabajosInformaticos.entidades.Trabajos;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.util.JsfUtil;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.util.JsfUtil.PersistAction;
import org.legislaturachaco.sgTrabajosInformaticos.sessionBeans.TrabajosFacade;

import java.io.Serializable;
import java.util.Date;
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
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.Fechas;

@Named("trabajosController")
@SessionScoped
@ViewScoped
public class TrabajosController implements Serializable {

    @EJB
    private org.legislaturachaco.sgTrabajosInformaticos.sessionBeans.TrabajosFacade ejbFacade;
    private List<Trabajos> items = null;
    private Trabajos selected;
    
    private static final int FILAS_SINTOMAS= 5;
    

    public TrabajosController() {
    }

    public Date obtenerFechaActual(){
        return Fechas.obtenerFechaActual();
    }
    
    public int obtenerLongMaxSintomas(){
        return Trabajos.LONG_MAX_SINTOMAS;
    }
    
    public int filasSintomas(){
        return FILAS_SINTOMAS;
    }
    
    public int columnasSintomas(){
        return obtenerLongMaxSintomas()/FILAS_SINTOMAS;
    }
    
    public Trabajos getSelected() {
        return selected;
    }

    public void setSelected(Trabajos selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TrabajosFacade getFacade() {
        return ejbFacade;
    }

    public Trabajos prepareCreate() {
        selected = new Trabajos();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TrabajosCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TrabajosUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TrabajosDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Trabajos> getItems() {
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

    public Trabajos getTrabajos(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Trabajos> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Trabajos> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Trabajos.class)
    public static class TrabajosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TrabajosController controller = (TrabajosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "trabajosController");
            return controller.getTrabajos(getKey(value));
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
            if (object instanceof Trabajos) {
                Trabajos o = (Trabajos) object;
                return getStringKey(o.getIdTrabajo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Trabajos.class.getName()});
                return null;
            }
        }

    }

}
