package com.grpretail.comfandi.fe.businesslogic.vo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.asic.utils.StringFormater;
import com.grpretail.comfandi.monitor.trx.ComfandiPosTransaction;
//import com.grpretail.comfandi.procesosautomaticos.facturaelectronica.model.ServiceOrderVo;
import com.grpretail.facturaelectronica.comfandi.business.utils.vos.AlmacenVo;
import com.grpretail.facturaelectronica.comfandi.dian.utils.CarvajalProcessUtils;
import com.grpretail.facturaelectronica.comfandi.mensajeria.utils.DIANUtils;
import com.grpretail.facturaelectronica.comfandi.mensajeria.utils.WrittenAmount;
import com.grpretail.facturaelectronica.comfandi.mensajeria.vos.ServiceOrderTenderVo;
import com.grpretail.facturaelectronica.comfandi.mensajeria.vos.ServiceOrderVo;
import com.grpretail.trxonline.automaticjobs.facturaelectronica.utils.BusinessLogicParameters;
import com.grpretail.utils.DIANDigitoVerificacion;
import com.grpretail.utils.StringFormatter;

public class FEJSONMessageVo {
	
	private ServiceOrderVo orderVo;
	private AlmacenVo almacenVo;
	private String dateTime;
	String nombreCompletoCliente = "Consumidor Final";

	private String tipoIdentificacion;
	private String tipoDocIdentidad;
	int digitoVerificacion = 0;	
	
	public FEJSONMessageVo(ServiceOrderVo pOrder, AlmacenVo pAlmacenVo, String pDateTime) {
		orderVo = pOrder;
		almacenVo = pAlmacenVo;
		dateTime = pDateTime;

		tipoIdentificacion = CarvajalProcessUtils.getCustomerIdADQ1(orderVo.getCustomerIdType());
		tipoDocIdentidad = CarvajalProcessUtils.getCustomerIdADQ3(orderVo.getCustomerIdType());
		
		if (tipoIdentificacion== "1" && (orderVo.getCustomerId().trim().length()==9 ||orderVo.getCustomerId().trim().length()==10)) {
			//si tiene 9 o 10 digitos y los 3 primeros digitos > 800 y < 999
			//si adq 3 = 13 adq_22 va vacio o 0, de lo contrario digito de verificacion si es nit.  Y si adq 25 =31, adq 26 es el digito de verificcion
			if (orderVo.getCustomerId().trim().length()==10) {
				try {
					int initValuesNit_ = Integer.valueOf(orderVo.getCustomerId().trim().substring(0, 3));
					if (initValuesNit_>= 800 && initValuesNit_<=999 ) {
						digitoVerificacion = Integer.valueOf(orderVo.getCustomerId().trim().substring(9));
						orderVo.setCustomerId(orderVo.getCustomerId().substring(0, 9));
					}else
						digitoVerificacion = DIANDigitoVerificacion.getDianDigito(orderVo.getCustomerId().trim());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}else {
				try {
					int initValuesNit_ = Integer.valueOf(orderVo.getCustomerId().trim().substring(0, 3));
					if (initValuesNit_>= 800 && initValuesNit_<=999 ) {
						 digitoVerificacion= DIANDigitoVerificacion.getDianDigito(orderVo.getCustomerId().trim());
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}	
		}else {
			if (tipoIdentificacion== "1") { // Puede ser una cédula
				digitoVerificacion= DIANDigitoVerificacion.getDianDigito(orderVo.getCustomerId().trim());
				System.out.println("digitoVerificacion cEdula:"+digitoVerificacion);
			}
		}
		
	}
	
	public String getHeader(String pElectronicBillingType, String pEnvironment) throws ParseException {
		SimpleDateFormat sdf_ = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//		Date date_=sdf_.parse(getServiceDateTime(pOrder.getDateTime()));//2020-11-16T13:09:17.878Z
		//Date date_=sdf_.parse(dateTime);//2020-11-16T13:09:17.878Z
		Date date_ = new Date();
		Date currentDate_ = new Date();
		
//		String dateTime_ = getServiceDateTime(pOrder.getDateTime());
		sdf_.applyPattern("yyyy-MM-dd");
		String fecha_=   sdf_.format(date_);//TODO cambiar por la siguiete linea pues para pruebas no soporta trx de mas de 10 dias: sdf_.format(new Date());
		String fechaVencimiento_ = sdf_.format(currentDate_);
		sdf_.applyPattern("HH:mm:ss");
		String timeZone_ = "-05:00";
		String hora_= sdf_.format(date_)+timeZone_;
//		sdf_.applyPattern("yyyy-MM-dd HH:mm:ssXXX");
//		System.out.println("zona hoaria:"+sdf_.format(date_));
		
		String header_ = "{\n" 
			+ "	\"UploadRequest\": {\n"
			+ "		\"ClientId\": \"890303208\",\n"
			+ "		\"AccountId\": \"890303208_api01\",\n"
			+ "		\"Invoice\": {\n"
			+ "			\"ENC\": {\n"
			+ "				\"ENC_1\": \"INVOIC\",\n"
			+ "				\"ENC_2\": 890303208,\n"
			+ "				\"ENC_3\": "+orderVo.getCustomerId()+",\n"
			+ "				\"ENC_4\": \"UBL 2.1\",\n"
			+ "				\"ENC_5\": \"DIAN 2.1: Factura Electrónica de Venta\",\n"
			//+ "				\"ENC_6\": \"SETT255568\",\n"
			+ "				\"ENC_6\": \""+orderVo.getFiscalPrefix()+orderVo.getFiscalBillNumber()+"\",\n"
			+ "				\"ENC_7\": \""+ fecha_+ "\",\n"
			+ "				\"ENC_8\": \""+ hora_+ "\",\n"
			//+ "				\"ENC_9\": \"01\",\n"
			+ "				\"ENC_9\": \""+pElectronicBillingType+"\",\n"
			+ "				\"ENC_10\": \""+ "COP"+ "\",\n"//TODO CAMbiar moneda
			+ "				\"ENC_15\": "+ orderVo.getItemsList().size()+ ",\n"  // n�mero de ocurrencias del ITE_1
			//+ "				\"ENC_16\": \"2024-04-09\",\n"
			+ "				\"ENC_16\": \""+fechaVencimiento_+"\",\n"
			//+ "				\"ENC_17\": \"https://www.comfandi.co\",\n"
			//+ "				\"ENC_18\": \"https://www.comfandi.co\",\n"
//			+ "				\"ENC_20\": 2,\n"  //TODO Pruebas
//			+ "				\"ENC_20\": 1,\n"  //TODO Producción
			+ "				\"ENC_20\":"+pEnvironment+",\n"
			+ "				\"ENC_21\": \"10\"\n"
			+ "			},\n"
			//TODO Falta idMunicipio, nombre departamento codigo postal
			+ "			\"EMI\": {\n"
			+ "				\"EMI_1\": 1,\n"
			+ "				\"EMI_2\": 890303208,\n"
			+ "				\"EMI_3\": 31,\n"
			+ "				\"EMI_4\": \"No aplica\",\n"
			+ "				\"EMI_6\": \"CAJA DE COMPENSACION FAMILIAR DEL VALLE DEL CAUCA - COMFAMILIAR ANDI - COMFANDI\",\n"
			+ "				\"EMI_7\": \"COMFANDI\",\n"
			//+ "				\"EMI_10\": \"AV.CIUDAD DE CALI 42 B 21\",\n"
			+ "				\"EMI_10\": \""+almacenVo.getDireccion().trim()+"\",\n"
			+ "				\"EMI_11\": "+ almacenVo.getIdDepartamento()+ ",\n"
			+ "				\"EMI_13\": \""+almacenVo.getNombreMunicipio()+"\",\n"
			//+ "				\"EMI_14\": 760013,\n"//almacenVo_.getCodPostal()
			+ "				\"EMI_14\": \""+almacenVo.getCodPostal()+"\",\n"
			+ "				\"EMI_15\": \""+ almacenVo.getCodPais()+ "\",\n"
			+ "				\"EMI_19\": \""+ almacenVo.getNombreDpto()+ "\",\n"//almacenVo_.getNombreDpto
			+ "				\"EMI_21\": \""	+ almacenVo.getNombrePais()+ "\",\n"
			+ "				\"EMI_22\": 5,\n"
			//+ "				\"EMI_23\": 76001,\n"//almacenVo_.getIdMunicipio()
			+ "				\"EMI_23\": "+almacenVo.getIdMunicipio()+",\n"
			+ "				\"EMI_24\": \"CAJA DE COMPENSACION FAMILIAR DEL VALLE DEL CAUCA - COMFAMILIAR ANDI - COMFANDI\",\n"
			+ "				\"TAC\": {\n"
			+ "					\"TAC_1\": \"O-13;O-23\"\n"//revisar cuando cambia
			+ "				},\n"
			+ "				\"DFE\": {\n"
			+ "					\"DFE_1\": 76001,\n"
			+ "					\"DFE_2\": 76,\n"
			+ "					\"DFE_3\": \"CO\",\n"
			+ "					\"DFE_5\": \"Colombia\",\n"
			+ "					\"DFE_6\": \"VALLE DEL CAUCA\",\n"
			+ "					\"DFE_7\": \"CALI\"\n"
			+ "				},\n"
			+ "				\"ICC\": {\n"
			//+ "					\"ICC_9\": \"SETT\"\n" //+"\"DRF_4\": \"SETT\","
			+ "					\"ICC_9\": \""+orderVo.getFiscalPrefix()+"\"\n" //+"\"DRF_4\": \"SETT\","
			+ "				},\n"
			+ "				\"CDE\": {\n"
			+ "					\"CDE_1\": 1,\n"
			+ "					\"CDE_2\": \"MERCADEO SOCIAL\",\n"
			+ "					\"CDE_3\": \"6024866565\",\n"
			+ "					\"CDE_4\": \"recepcionfacturaelectronica@comfandi.com.co\",\n"
			+ "					\"CDE_6\": \"MERCADEO SOCIAL\"\n"
			+ "				},\n"
			+ "				\"GTE\": {\n"
			+ "					\"GTE_1\": \"ZA\",\n"
			+ "					\"GTE_2\": \"IVA e INC\"\n"
			+ "				}\n"
			+ "			},\n";
		return header_;
	}
	
	public String getCustomer()  {
		
		String nombreCliente_ = "";
		String apellidoCliente_ = "Consumidor Final";
		String direccionCliente_ = "";
//		String telefonoCliente_ = "6024866565";
//		String correoCliente_ ="recepcionfacturaelectronica@comfandi.com.co";
		if (!orderVo.getCustomerId().endsWith("2222222222")) {
			nombreCompletoCliente = orderVo.getCustomerName().trim()+ " "+ orderVo.getCustomerLastName().trim();
			nombreCliente_ = orderVo.getCustomerName().trim();
			apellidoCliente_ = orderVo.getCustomerLastName().trim();
			direccionCliente_= orderVo.getAddress();
			direccionCliente_= DIANUtils.removeSpecialCharacters(direccionCliente_);
//			telefonoCliente_ = orderVo.getPhoneNumber();
//			correoCliente_ = orderVo.getEmail();
		} 
//		int tipoDocIdentidad_ = 13;
//		if (orderVo.getCustomerIdType().trim().equals("NIT") ) { //&& !orderVo.getCustomerId().endsWith("2222222222")
//			tipoDocIdentidad_ = 31;
//		}
		//int customerDocType_ = orderVo.customerDocType()// 1 natural, 4 jutridica
		String tipoIdentificacion_ = CarvajalProcessUtils.getCustomerIdADQ1(orderVo.getCustomerIdType());
		String tipoDocIdentidad_ = CarvajalProcessUtils.getCustomerIdADQ3(orderVo.getCustomerIdType());
		String customer_ = 
				"			\"ADQ\": {\n"
				+"				\"ADQ_1\": "+tipoIdentificacion_+ ",\n"//1 cuandno es nit
				+"				\"ADQ_2\": "+ orderVo.getCustomerId()+ ",\n"
				+"				\"ADQ_3\": "+ tipoDocIdentidad_+ ",\n"
								//TODO revisar regimen cuando adquiriente es consumidor final
				+"				\"ADQ_4\": \"No aplica\",\n"//Régimen al que pertenece el Adquiriente, para consumidor final se debe informar el cógido“49” 
				+"				\"ADQ_6\": \""+ nombreCompletoCliente+ "\",\n";
				if(tipoIdentificacion_.equals("1"))
					customer_  +=	"				\"ADQ_7\": \""+ nombreCompletoCliente+ "\",\n";
				if(tipoIdentificacion_.equals("2")) {
					customer_ += 
					 "				\"ADQ_8\": \""+nombreCliente_+ "\",\n"
					+"				\"ADQ_9\": \""+ apellidoCliente_+ "\",\n";
				}
				customer_ +=
				"				\"ADQ_10\": \""+ direccionCliente_+ "\",\n"
				
				+"				\"ADQ_11\": "+ almacenVo.getIdDepartamento()+ ",\n"
				+"				\"ADQ_12\": \"\",\n"
				+"				\"ADQ_13\": \""+ almacenVo.getNombreMunicipio()+ "\",\n"
				+"				\"ADQ_14\": \"\",\n"
				+"				\"ADQ_15\": \""	+ almacenVo.getCodPais()+ "\",\n"
				+"				\"ADQ_18\": \"\",\n"
				+"				\"ADQ_19\": \""+ almacenVo.getNombreDpto()+ "\",\n"//almacenVo.getNombreDpto
				+"				\"ADQ_21\": \""+ almacenVo.getNombrePais()+ "\",\n";
				if(tipoIdentificacion_.equals("1"))
					customer_ +="				\"ADQ_22\": "+digitoVerificacion+",\n"; //si adq 3 = 13 va vacio, de lo contrario digito de verificacion si es nit y si adq 25 =31, adq 26 es el digito de verificcion
				//else
					//customer_ +="				\"ADQ_22\": ,\n"; //si adq 3 = 13 va vacio, de lo contrario digito de verificacion si es nit y si adq 25 =31, adq 26 es el digito de verificcion
				customer_ +=
				//"				\"ADQ_23\": 76001,\n" //almacenVo.getIdMunicipio()
				"				\"ADQ_23\": "+almacenVo.getIdMunicipio()+",\n"		
				+"				\"ADQ_24\": "+ orderVo.getCustomerId()+ ",\n"
				+"				\"TCR\": {\n"
				+"					\"TCR_1\": \"R-99-PN\"\n"
				+"				},\n"
				
//				+"				\"ILA\": {\n"  NO VA
//				+"					\"ILA_1\": \""+ nombreCompletoCliente_+ "\",\n"
//				+"					\"ILA_2\": "+ orderVo.getCustomerId()+ ",\n"
//				+"					\"ILA_3\": "+ tipoDocIdentidad_	+ ",\n"
//				+"					\"ILA_4\": 0\n"
//				+"				},\n"
//				+"				\"DFA\": {\n" NOVA//TODO SOLO VA PARA ADQUIRIENTES RESPONSABLES NO VA
//				+"					\"DFA_1\": \"CO\",\n"
//				+"					\"DFA_2\": 11,\n"
//				+"					\"DFA_3\": \"\",\n"
//				+"					\"DFA_4\": 11001,\n"
//				+"					\"DFA_5\": \"Colombia\",\n"
//				+"					\"DFA_6\": \"Bogot�\",\n"
//				+"					\"DFA_7\": \"BOGOT� D.C.\"\n"
//				+"				},\n"
				
				+"				\"CDA\": {\n"
				+"					\"CDA_1\": 1,\n"
				+"					\"CDA_2\": \""+ nombreCompletoCliente+ "\",\n"//consusimdor final 22222.. solo tiene cda1  cda2
				+"					\"CDA_3\": \""+ orderVo.getPhoneNumber()+ "\",\n"
				+"					\"CDA_4\": \""+ orderVo.getEmail().trim()+ "\"\n"
				+"				},\n"
				+"				\"GTA\": {\n"
				+"					\"GTA_1\": \"ZA\",\n"
				+"					\"GTA_2\": \"IVA e INC\"\n"
				+"				}\n"
				+"			},\n";
		return customer_;
	}

	public String getFooter(String pStrTrxId, BigDecimal pTrxTotalAmount) {
		String nota1_ = ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParameters.NOTA1).getValue();
		String nota1FechaVig_ = ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParameters.NOTA1Fechavigencia).getValue();
		String nota3_ = ComfandiPosTransaction.getBusinessLogicParam(BusinessLogicParameters.NOTA3).getValue();
		SimpleDateFormat sdf_ = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaFin_= new Date(), fechaIni_= new Date();
		try {
			fechaFin_ = sdf_.parse(orderVo.getFiscalResolEndDate());
			fechaIni_ = sdf_.parse(orderVo.getFiscalResolIniDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int diferenciaMeses_  = calcularMesesAFecha(fechaIni_, fechaFin_);
		StringBuffer footer_ = new StringBuffer();
		footer_.append ("			\"CTS\": {\n");
		footer_.append ( "					\"CTS_1\": \"CGEN14\"\n");  
		footer_.append ( "				},\n");
		footer_.append ( "			\"NOT\":  [{\n");
		// CACS: Nota 1
//		footer_.append ( "					\"NOT_1\": \"1.-AUTORETENEDORES DE RENTA RES. 1660 DE 2000-03-07, GRANDES CONTRIBUYENTES RESOLUCIÓN 012220 26-12-2022, AGENTE RETENEDOR DE IVA, AUTORETENEDORES DE ICA EN BUGA, CARTAGO, GINEBRA, TULUA, JAMUNDI, POPAYAN, BUGALAGRANDE, PEREIRA Y PALMIRA. VIGILADO SUPERSALUD Y SUPERSUBSIDIO FAMILIAR |RESOLUCION DE FACTURACION DIAN ");
		footer_.append ( "					\"NOT_1\": \"");
		footer_.append(nota1_);
		footer_.append(" ");
		footer_.append ( orderVo.getFiscalResolNumber() );
//		footer_.append ( " 2023 - 05 - 02 VIGENCIA ");
		footer_.append(" ");
		footer_.append(nota1FechaVig_);
		footer_.append(" ");
		footer_.append ( diferenciaMeses_);
		footer_.append ( " MESES, CONSECUTIVO ");
		footer_.append ( orderVo.getFiscalPrefix());
		footer_.append ( orderVo.getFiscalResolFirstNumber());
		footer_.append ( " HASTA ");
		footer_.append ( orderVo.getFiscalPrefix());
		footer_.append ( orderVo.getFiscalResolLastNumber());
		footer_.append ( "\"\n");		  
		footer_.append ( "					},\n");
		footer_.append ( "					{\n");
		// CACS: Nota 3
		footer_.append ( "					\"NOT_1\": \"3.-");
		footer_.append ( orderVo.getCustomerId());
		footer_.append ( " ");
		footer_.append ( nombreCompletoCliente);
		footer_.append ( ".IUT:");
		footer_.append ( pStrTrxId);//TODO Validar si numero de remsion es = a numero de minifactura
		//footer_.append ( ". MINIFACTURA:");
		//footer_.append ( pStrTrxId);
		footer_.append ( ".|");
		StringBuffer tender_ = new StringBuffer();
		for(int i=0;i<orderVo.getTendersList().size();i++){
			ServiceOrderTenderVo tendVo_ = (ServiceOrderTenderVo)orderVo.getTendersList().get(i);
			if(!tendVo_.isVoided()) {
				System.out.println("Id del POS->"+tendVo_.getTenderId());
				tender_.append ( tendVo_.getTenderDescription());
				tender_.append (  " $ ");
				tender_.append (  tendVo_.getTenderAmount());
				tender_.append (  ",");
			}
		}
		tender_.deleteCharAt(tender_.lastIndexOf(","));
		footer_.append(tender_.toString());
//		footer_.append ( "|COMFANDI ES UNA EMPRESA AUTORIZADA PARA SER AUTORETENEDOR DE ICA Y RENTA, POR ENDE, EL RECEPTOR DE LA FACTURA NO DEBE APLICAR RETENCIONES DE ICA NI RENTA\"\n");		  
		footer_.append(nota3_);
		footer_.append ( "\"\n");
		footer_.append ( "					},\n");
		footer_.append ( "					{\n");
		// CACS: Nota 6
		footer_.append ( "					\"NOT_1\": \"6.-|");
		WrittenAmount a = new WrittenAmount();
		footer_.append (a.convertir(pTrxTotalAmount.toString()+"",  "PESO", "PESOS", "CENTAVO", "CENTAVOS", "CON", true));
		footer_.append ( "\"\n");		  
		footer_.append ( "					}],\n");
		footer_.append ( "			\"REF\": [\n");
		footer_.append ( "				{\n");
		footer_.append ( "					\"REF_1\": \"FTC\",\n");// Invoice
		footer_.append ( "					\"REF_2\": \""+orderVo.getFiscalPrefix()+orderVo.getFiscalBillNumber()+"\"\n");// Invoice
		footer_.append ( "				}\n");
		//apsm 21 julio 2025 se agrega el campo orc con el uit
//		footer_.append ( "			]\n");
		footer_.append ( "			],\n");
		footer_.append ( "			\"ORC\": {\n");
		footer_.append ( "				\"ORC_1\": \""+pStrTrxId+"\"\n");
		footer_.append ( "			  }\n");	
		
		footer_.append ( "		}\n");
		footer_.append ( "	}\n");
		footer_.append ( "}");	
		return footer_.toString();

	}


	
	  public static int calcularMesesAFecha(Date fechaInicio, Date fechaFin) {
	         try {
	             //Fecha inicio en objeto Calendar
	             Calendar startCalendar = Calendar.getInstance();
	             startCalendar.setTime(fechaInicio);
	             //Fecha finalización en objeto Calendar
	             Calendar endCalendar = Calendar.getInstance();
	             endCalendar.setTime(fechaFin);
	             //Cálculo de meses para las fechas de inicio y finalización
	             int startMes = (startCalendar.get(Calendar.YEAR) * 12) + startCalendar.get(Calendar.MONTH);
	             int endMes = (endCalendar.get(Calendar.YEAR) * 12) + endCalendar.get(Calendar.MONTH);
	             //Diferencia en meses entre las dos fechas
	             int diffMonth = endMes - startMes;
	             return diffMonth;
	         } catch (Exception e) {
	             return 0;
	         }
	  }

}
