/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.Tecnicos;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

/**
 *
 * @author coperalta
 */
public class MiConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        final DualListModel<Tecnicos> dualList;
        try{
            dualList = (DualListModel<Tecnicos>) ((PickList)component).getValue();
            Tecnicos objeto = getObjectFromList(dualList.getSource(),value);
            if(objeto==null){
                objeto = getObjectFromList(dualList.getTarget(),value);
            }

            return objeto;
        }catch(ClassCastException cce){
            throw new ConverterException();
        }catch(NumberFormatException nfe){
            throw new ConverterException();
        }
    }
    
    private Tecnicos getObjectFromList(final List<?> list, final String identifier) {
        for(final Object object:list){
            final Tecnicos tecnicos = (Tecnicos) object;
            if(tecnicos.getIdTecnico().equals(identifier)){
                    return tecnicos;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null) return "";
        
        return value.toString();
    }    
}
