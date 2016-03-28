package org.legislaturachaco.sgTrabajosInformaticos.clasesJSF;

import org.legislaturachaco.sgTrabajosInformaticos.entidades.Patrimonios;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.util.JsfUtil;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.util.JsfUtil.PersistAction;
import org.legislaturachaco.sgTrabajosInformaticos.sessionBeans.PatrimoniosFacade;

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
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("patrimoniosController")
@SessionScoped
@ViewScoped
public class PatrimoniosController implements Serializable {

    @EJB
    private org.legislaturachaco.sgTrabajosInformaticos.sessionBeans.PatrimoniosFacade ejbFacade;
    private List<Patrimonios> items = null;
    private Patrimonios selected;

    public PatrimoniosController() {
    }
    
    public int obtenerLongMaxPatrimonio(){
        return Patrimonios.LONG_MAX_PATRIMONIO;
    }
    
    public int obtenerLongMaxComentario(){
        return Patrimonios.LONG_MAX_COMENTARIO;
    }

    public Patrimonios getSelected() {
        return selected;
    }

    public void setSelected(Patrimonios selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PatrimoniosFacade getFacade() {
        return ejbFacade;
    }

    public Patrimonios prepareCreate() {
        selected = new Patrimonios();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PatrimoniosCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PatrimoniosUpdated"));
    }

    public void destroy() {
        /*
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PatrimoniosDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
        */
    }

    public List<Patrimonios> getItems() {
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

    public Patrimonios getPatrimonios(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Patrimonios> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Patrimonios> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    /**
     * Devuelve los patrimonios que no estan dados de baja.
     * @return 
     */
    public List<Patrimonios> obtenerPatrimoniosDisponibles(){
        List<Patrimonios> patrimoniosDisponibles= new ArrayList();
        for(Patrimonios pat: this.getItems()){
            if((!pat.isBaja())){
                patrimoniosDisponibles.add(pat);
            }
        }
        return patrimoniosDisponibles;
    }

    @FacesConverter(forClass = Patrimonios.class)
    public static class PatrimoniosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PatrimoniosController controller = (PatrimoniosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "patrimoniosController");
            return controller.getPatrimonios(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Patrimonios) {
                Patrimonios o = (Patrimonios) object;
                return getStringKey(o.getIdPatrimonio());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Patrimonios.class.getName()});
                return null;
            }
        }

    }

}
