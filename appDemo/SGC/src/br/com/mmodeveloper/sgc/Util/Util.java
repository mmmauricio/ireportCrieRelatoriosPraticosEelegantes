package br.com.mmodeveloper.sgc.Util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {

	public Date formataDateTime(String data) {
		Calendar calData= Calendar.getInstance();
		int ano,mes,dia;
		ano=mes=dia=0;
		if (!data.equalsIgnoreCase("")){
			dia = Integer.parseInt(data.substring(0,2));
			mes = Integer.parseInt(data.substring(2,4));	
			ano = Integer.parseInt(data.substring(4,8));	
		}
		calData.set(Calendar.YEAR,ano);
		calData.set(Calendar.MONTH,mes-1);
		calData.set(Calendar.DAY_OF_MONTH,dia);
		return calData.getTime();	
	}
	
	public static String getDscMes(Date data) {
		String retorno = new String();
		int mes = Integer.parseInt(Util.getDataMM(data));
		switch(mes) {	 
        	case 1:
	 	 		retorno = "Janeiro";
	 	 		break;
        	case 2:
	    	    retorno = "Fevereiro";
	 	 		break;
        	case 3:
	    	    retorno = "Março";
	 	 		break;
        	case 4:
	    	    retorno = "Abril";
	 	 		break;
        	case 5:
	    	    retorno = "Maio";
	 	 		break;
        	case 6:
	    	    retorno = "Junho";
	 	 		break;
        	case 7:
	    	    retorno = "Julho";
	 	 		break;
        	case 8:
	    	    retorno = "Agosto";
	 	 		break;
        	case 9:
	    	    retorno = "Setembro";
	 	 		break;
        	case 10:
	    	    retorno = "Outubro";
	 	 		break;
        	case 11:
	      	    retorno = "Novembro";
	 	 		break;
        	case 12:
	    	    retorno = "Dezembro";
	 	 		break;
		}
		return  retorno ;
      }
	
	public static String getDiaDaSemana(Date data){
		String retorno=null;
		Calendar dataInicio = Calendar.getInstance();
		int dia=0;
		dataInicio.setTime(data);
		dia = dataInicio.get(Calendar.DAY_OF_WEEK);
		switch(dia) {	 
    		case 1:
    			retorno = "Domingo";
    			break;
    		case 2:
    			retorno = "Segunda-Feira";
    			break;
    		case 3:
    			retorno = "Terça-Feira";
    			break;
    		case 4:
    			retorno = "Quarta-Feira";
    			break;
    		case 5:
    			retorno = "Quinta-Feira";
    			break;
    		case 6:
    			retorno = "Sexta-Feira";
    			break;
    		case 7:
    			retorno = "Sabado";
    			break;
		}

		return retorno;
	}
	
	 	 	 
	 public static String getDatayyyy(java.util.Date data) {
		 SimpleDateFormat dataSimples = new SimpleDateFormat("yyyy");
		 return data!=null ? dataSimples.format(data) : "";
	 } 
	 public static String getDataMM(java.util.Date data) {
		 SimpleDateFormat dataSimples = new SimpleDateFormat("MM");
		 return data!=null ? dataSimples.format(data) : "";
	 } 
	 public static String getDatadd(java.util.Date data) {
		 SimpleDateFormat dataSimples = new SimpleDateFormat("dd");
		 return data!=null ? dataSimples.format(data) : "";
	 } 
	
	 public static String dataSimplesParaString(java.util.Date data) {
		 SimpleDateFormat dataSimples = new SimpleDateFormat("dd/MM/yyyy");
		 return data!=null ? dataSimples.format(data) : "";
	 }
	 
	 public static String getDatayyyymmdd(java.util.Date data) {
		 SimpleDateFormat dataSimples = new SimpleDateFormat("yyyyMMdd");
		 return data!=null ? dataSimples.format(data) : "";
	 }
	 
	 public static String moeda(float v) {
			DecimalFormat moeda = new DecimalFormat("#,##0.00");    
			return moeda.format(v);
		}
		public static String moeda(double v) {
			DecimalFormat moeda = new DecimalFormat("#,##0.00");    
			return moeda.format(v);
		}
	 	 	 
		
	public static boolean hasValue(String str)
	{
		if (str != null && !str.trim().equals(""))
			return true;

		return false;
	}
 
	public static boolean hasValue(Object obj)
	{
		if (obj != null)
			return true;

		return false;
	}
	
}
