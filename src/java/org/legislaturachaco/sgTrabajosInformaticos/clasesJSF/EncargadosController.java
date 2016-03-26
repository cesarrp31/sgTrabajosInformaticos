package org.legislaturachaco.sgTrabajosInformaticos.clasesJSF;

import org.legislaturachaco.sgTrabajosInformaticos.entidades.Encargados;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.util.JsfUtil;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.util.JsfUtil.PersistAction;
import org.legislaturachaco.sgTrabajosInformaticos.sessionBeans.EncargadosFacade;

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

@Named("encargadosController")
@SessionScoped
public class EncargadosController implements Serializable {

    @EJB
    private org.legislaturachaco.sgTrabajosInformaticos.sessionBeans.EncargadosFacade ejbFacade;
    private List<Encargados> items = null;
    private Encargados selected;

    public EncargadosController() {
    }

    public Encargados getSelected() {
        return selected;
    }

    public void setSelected(Encargados selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EncargadosFacade getFacade() {
        return ejbFacade;
    }

    public Encargados prepareCreate() {
        selected = new Encargados();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EncargadosCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EncargadosUpdated"));
    }

    public void destroy() {
        /*
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EncargadosDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
        */
    }

    public List<Encargados> getItems() {
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

    public Encargados getEncargados(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Encargados> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Encargados> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Encargados.class)
    public static class EncargadosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EncargadosController controller = (EncargadosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "encargadosController");
            return controller.getEncargados(getKey(value));
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
            if (object instanceof Encargados) {
                Encargados o = (Encargados) object;
                return getStringKey(o.getIdEncargado());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Encargados.class.getName()});
                return null;
            }
        }

    }

}
