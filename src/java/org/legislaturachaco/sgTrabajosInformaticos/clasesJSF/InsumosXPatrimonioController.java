package org.legislaturachaco.sgTrabajosInformaticos.clasesJSF;

import org.legislaturachaco.sgTrabajosInformaticos.entidades.InsumosXPatrimonio;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.util.JsfUtil;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.util.JsfUtil.PersistAction;
import org.legislaturachaco.sgTrabajosInformaticos.sessionBeans.InsumosXPatrimonioFacade;

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
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.Fechas;

@Named("insumosXPatrimonioController")
@SessionScoped
public class InsumosXPatrimonioController implements Serializable {

    @EJB
    private org.legislaturachaco.sgTrabajosInformaticos.sessionBeans.InsumosXPatrimonioFacade ejbFacade;
    private List<InsumosXPatrimonio> items = null;
    private InsumosXPatrimonio selected;

    public InsumosXPatrimonioController() {
    }

    public InsumosXPatrimonio getSelected() {
        return selected;
    }

    public void setSelected(InsumosXPatrimonio selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private InsumosXPatrimonioFacade getFacade() {
        return ejbFacade;
    }

    public InsumosXPatrimonio prepareCreate() {
        selected = new InsumosXPatrimonio();
        initializeEmbeddableKey();
        selected.setFechaDesde(Fechas.obtenerFechaActual());
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("InsumosXPatrimonioCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("InsumosXPatrimonioUpdated"));
    }

    public void destroy() {
        /*
        /*
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("InsumosXPatrimonioDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
        */
    }

    public List<InsumosXPatrimonio> getItems() {
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

    public InsumosXPatrimonio getInsumosXPatrimonio(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<InsumosXPatrimonio> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<InsumosXPatrimonio> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = InsumosXPatrimonio.class)
    public static class InsumosXPatrimonioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            InsumosXPatrimonioController controller = (InsumosXPatrimonioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "insumosXPatrimonioController");
            return controller.getInsumosXPatrimonio(getKey(value));
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
            if (object instanceof InsumosXPatrimonio) {
                InsumosXPatrimonio o = (InsumosXPatrimonio) object;
                return getStringKey(o.getIdInsumoXPatrimonio());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), InsumosXPatrimonio.class.getName()});
                return null;
            }
        }

    }

}
