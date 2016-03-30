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
import javax.faces.convert.FacesConverter;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.Patrimonios;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

@FacesConverter("converterDualListPatrimonios")
public class ConverterDualListPatrimonios implements Converter {

        @Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return getObjectFromUIPickListComponent(component,value);
	}

        @Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		String string;
		if(object == null){
			string="";
		}else{
			try{
				string = String.valueOf(((Patrimonios)object).getIdPatrimonio());
			}catch(ClassCastException cce){
				throw new ConverterException();
			}
		}
		return string;
	}

	@SuppressWarnings("unchecked")
	private Patrimonios getObjectFromUIPickListComponent(UIComponent component, String value) {
		final DualListModel<Patrimonios> dualList;
		try{
			dualList = (DualListModel<Patrimonios>) ((PickList)component).getValue();
			Patrimonios patrimonio = getObjectFromList(dualList.getSource(),value);
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

	private Patrimonios getObjectFromList(final List<?> list, final String identifier) {
		for(final Object object:list){
			final Patrimonios patrimonio = (Patrimonios) object;
			if(patrimonio.getIdPatrimonio().equals(identifier)){
				return patrimonio;
			}
		}
		return null;
	}
}
