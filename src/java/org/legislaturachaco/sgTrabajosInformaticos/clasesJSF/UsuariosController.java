package org.legislaturachaco.sgTrabajosInformaticos.clasesJSF;

import org.legislaturachaco.sgTrabajosInformaticos.entidades.Usuarios;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.util.JsfUtil;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.util.JsfUtil.PersistAction;
import org.legislaturachaco.sgTrabajosInformaticos.sessionBeans.UsuariosFacade;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.Utiles;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo.ldap.ProcesoLogeoDominio;
import org.primefaces.validate.ClientValidator;

@Named("usuariosController")
@FacesValidator("validadorNombreUsuarioDominio")
@SessionScoped
public class UsuariosController implements Serializable, Validator, ClientValidator {

    @EJB
    private org.legislaturachaco.sgTrabajosInformaticos.sessionBeans.UsuariosFacade ejbFacade;
    private List<Usuarios> items = null;
    private Usuarios selected;
    
    private String nombreUsuarioDominio;
    private boolean validado= false;

    public UsuariosController() {
    }

    public Usuarios getSelected() {
        return selected;
    }

    public boolean seleccionadoUsuarioDominio(){
        return ((selected!=null)&&
                (selected.getNombreUsuarioDominio()==null||
                (selected.getNombreUsuarioDominio().isEmpty())));
    }
    
    public void isUsuarioDominioValido(FacesContext ctx, UIComponent component, Object value){
        String usuarioDominio= value.toString();
        
        if(!ProcesoLogeoDominio.existeUsuarioEnDominio(usuarioDominio)){
            FacesMessage fm= new FacesMessage("Usuario no encontrado");
            fm.setSeverity(FacesMessage.SEVERITY_WARN);
            ctx.addMessage(null, fm);
            throw new ValidatorException(fm);
        }
    }

    public String getNombreUsuarioDominio() {
        return nombreUsuarioDominio;
    }

    public void setNombreUsuarioDominio(String nombreUsuarioDominio) {
        this.nombreUsuarioDominio = nombreUsuarioDominio;
        
        if(ProcesoLogeoDominio.existeUsuarioEnDominio(nombreUsuarioDominio)){
            selected.setNombreUsuarioDominio(nombreUsuarioDominio);
        }else{
            System.out.println("Error validacion nombre usuario dominio");
            FacesMessage fm= new FacesMessage("Usuario no encontrado");
            fm.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, fm);
            throw new ValidatorException(fm);
        }
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String nud = (String) value;
        
        if(ProcesoLogeoDominio.existeUsuarioEnDominio(nud)){
            validado= true;
        }else{
            validado= false;
            System.out.println("Error validación: nombre usuario dominio "+nud);
            FacesMessage fm= new FacesMessage("Usuario no encontrado");
            fm.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, fm);
            throw new ValidatorException(fm);
        }
    }

    @Override
    public Map<String, Object> getMetadata() {
        return null;
    }

    @Override
    public String getValidatorId() {
        return "validadorUsuarioDominio";
    }

    public boolean isValidado() {
        System.out.println("Es válido el nobmre de usuario "+validado);
        return validado;
    }
    
    public void setValidado(boolean validado){
        this.validado= validado;
    }
    
    public void setSelected(Usuarios selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UsuariosFacade getFacade() {
        return ejbFacade;
    }

    public Usuarios prepareCreate() {
        selected = new Usuarios();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        try {
            selected.setPassword(Utiles.sha256(selected.getPassword()));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error al convertir el password del usuario a sha2");
        }
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UsuariosCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UsuariosUpdated"));
    }

    public void destroy() {
        /*
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UsuariosDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
        */
    }

    public List<Usuarios> getItems() {
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

    public Usuarios getUsuarios(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Usuarios> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Usuarios> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Usuarios.class)
    public static class UsuariosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuariosController controller = (UsuariosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuariosController");
            return controller.getUsuarios(getKey(value));
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
            if (object instanceof Usuarios) {
                Usuarios o = (Usuarios) object;
                return getStringKey(o.getUsuario());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Usuarios.class.getName()});
                return null;
            }
        }

    }

}
