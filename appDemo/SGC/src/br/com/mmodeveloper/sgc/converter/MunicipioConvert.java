package br.com.mmodeveloper.sgc.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.mmodeveloper.sgc.model.Municipio;

@FacesConverter(forClass=Municipio.class) 
public class MunicipioConvert implements Converter{

	@Override 
	public Object getAsObject(FacesContext context, UIComponent component, String value) { 
		int index = value.indexOf(':');

		if (index != -1) { 
			return new Municipio(Integer.parseInt(value.substring(0, index)), value.substring(index + 1)); 
		} 
		return value; 
	} 

	@Override 
	public String getAsString(FacesContext context, UIComponent component, Object value) { 
		try { 
			Municipio optionItem = (Municipio) value; 
			return optionItem.getId() + ":" + optionItem.getNome(); 
		} catch (Exception e) { 
		} return (String) value; 
	} 
}