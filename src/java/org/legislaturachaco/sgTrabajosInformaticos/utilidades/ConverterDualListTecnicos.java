/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.Tecnicos;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

@FacesConverter("converterDualListTecnicos")
@ViewScoped
public class ConverterDualListTecnicos implements Converter, Serializable {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null) return "";
        
        return value.toString();
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return getObjectFromUIPickListComponent(component,value);
    }

    private Tecnicos getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<Tecnicos> dualList;
        try{
            dualList = (DualListModel<Tecnicos>) ((PickList)component).getValue();
            Tecnicos patrimonio = getObjectFromList(dualList.getSource(),value);
            if(patrimonio==null){
                patrimonio = getObjectFromList(dualList.getTarget(),value);
            }

            return patrimonio;
        }catch(ClassCastException cce){
                throw new ConverterException();
        }catch(NumberFormatException nfe){
                throw new ConverterException();
        }
    }

    private Tecnicos getObjectFromList(final List<Tecnicos> lista, final String tecnicoBuscado) {
        System.out.println("Buscando: "+tecnicoBuscado);
        CharSequence cs;
        int idBuscado;
        for(final Tecnicos tecnico:lista){
            idBuscado= Integer.parseInt(tecnicoBuscado);
            System.out.println("---"+tecnicoBuscado+". Actual: "+tecnico);
            if(idBuscado == tecnico.getIdTecnico()){
                System.out.println("-------------"+tecnicoBuscado+". Encontrado: "+tecnico);
                return tecnico;
            }
        }
        throw new RuntimeException("Objeto no encontrado: "+tecnicoBuscado);
    }
}
