package br.com.mmodeveloper.sgc.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import br.com.mmodeveloper.sgc.model.Cliente;


public class ClienteConvert implements Converter{

    @SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext arg0, UIComponent component, String value) {
    	Cliente retorno = null;
        FacesMessage msg;
        try {
            if ((value != null) && (!value.isEmpty()) && (component != null) && (component.getChildren() != null)
                    && (!component.getChildren().isEmpty())){
            	for (UIComponent comp : component.getChildren()) {
            		if(comp instanceof UISelectItems){
            			// pego a lista de selectItens do component.
            			UISelectItems selectItems = (UISelectItems) comp;
            			// pego os valores dessa lista
            			List<SelectItem> listaSelectItem = (List<SelectItem>) selectItems.getValue();
            			// percorro os valores para ver se encontro o que possui o valor igual a String passada no parametro.
            			for (SelectItem item : listaSelectItem) {
            				if ((item.getValue() instanceof Cliente)) {
            					Cliente aux = (Cliente) item.getValue();
            					if (value.equals(aux.getNome())) {
            						retorno = aux;
            						break;
            					}
            				}
            			}
            		}
            	}
            }
            return retorno;
        } catch (Exception e) {
            String text = "Erro ao converter o meio utilizado";
            msg = new FacesMessage(text);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        }
        throw new ValidatorException(msg);
    }
    public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
        if ((obj != null) && ((obj instanceof Cliente))) {
            return ((Cliente) obj).getNome();
        }
        return "";
    }
}