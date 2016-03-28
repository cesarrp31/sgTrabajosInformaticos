package org.legislaturachaco.sgTrabajosInformaticos.clasesJSF;

import org.legislaturachaco.sgTrabajosInformaticos.entidades.Tecnicos;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.util.JsfUtil;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.util.JsfUtil.PersistAction;
import org.legislaturachaco.sgTrabajosInformaticos.sessionBeans.TecnicosFacade;

import java.io.Serializable;
import java.util.ArrayList;
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

@Named("tecnicosController")
@SessionScoped
public class TecnicosController implements Serializable {

    @EJB
    private org.legislaturachaco.sgTrabajosInformaticos.sessionBeans.TecnicosFacade ejbFacade;
    private List<Tecnicos> items = null;
    private Tecnicos selected;

    public TecnicosController() {
    }
    
    public int obtenerLongMaxApellido(){
        return Tecnicos.LONG_MAX_APELLIDO;
    }
    
    public int obtenerLongMaxNombre(){
        return Tecnicos.LONG_MAX_NOMBRE;
    }
    
    public int obtenerLongMaxCorreo(){
        return Tecnicos.LONG_MAX_CORREO;
    }

    public Tecnicos getSelected() {
        return selected;
    }

    public void setSelected(Tecnicos selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TecnicosFacade getFacade() {
        return ejbFacade;
    }

    public Tecnicos prepareCreate() {
        selected = new Tecnicos();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TecnicosCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TecnicosUpdated"));
    }

    public void destroy() {
        /*
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TecnicosDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
        */
    }

    public List<Tecnicos> getItems() {
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

    public Tecnicos getTecnicos(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Tecnicos> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Tecnicos> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    /**
     * Devuelve los tecnicos que no estan dados de baja.
     * @return 
     */
    public List<Tecnicos> obtenerTecnicosDisponibles(){
        List<Tecnicos> tecnicosDisponibles= new ArrayList();
        for(Tecnicos pat: this.getItems()){
            if((!pat.isBaja())){
                tecnicosDisponibles.add(pat);
            }
        }
        return tecnicosDisponibles;
    }

    @FacesConverter(forClass = Tecnicos.class)
    public static class TecnicosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TecnicosController controller = (TecnicosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tecnicosController");
            return controller.getTecnicos(getKey(value));
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
            if (object instanceof Tecnicos) {
                Tecnicos o = (Tecnicos) object;
                return getStringKey(o.getIdTecnico());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Tecnicos.class.getName()});
                return null;
            }
        }

    }

}
