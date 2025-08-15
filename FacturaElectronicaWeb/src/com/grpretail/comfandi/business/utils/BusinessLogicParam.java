/*
 * Created on 27/07/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.grpretail.comfandi.business.utils;

import com.grpretail.trxonline.automaticjobs.facturaelectronica.utils.BusinessLogicParameters;

/**
 * @author COMFANDI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 * apsm
 */
public class BusinessLogicParam {
	
		//public static final String ID_ACCESS_TOKEN				= "ACCESS_TOKEN";    // Token de acceso de mercado pago	
		public static final String ROUTE_FILE_CERTIFICATE 		= "FILE_CERTIFICATE"; //Ruta del certificado en el servidor
		public static final String ID_WS_AUTHENTICATION_URL 	= "AUTHENTICATION_URL"; //URL atenticaci�n al WS
		public static final String ID_SEND_DOCUMENT_URL  		= "SEND_DOCUMENT_URL"; //URL envio documento al WS
		public static final String WS_AUTHENTICATION_USER  		= "AUTHENTICATION_USER"; //Usuario atenticaci�n al WS
		public static final String WS_AUTHENTICATION_PASSWORD  	= "AUTHENTICATION_PASSWORD"; //Password atenticaci�n al WS
		public static final String WS_AUTHENTICATION_PLATFORM  	= "AUTHENTICATION_PLATFORM"; //Password atenticaci�n al WS
		public static final String ID_CARVAJAL_ENVIRONMENT  	= "AMBIENTE_CARVAJAL"; //Ambiente Carvajal
		
		public static final String ID_BAGS_ITEM_CODE  			= "BAGS_ITEM_CODE"; // PLU del ítem de bolsas
		public static final String ID_BAGS_ITEM_VALUE  			= "BAGS_ITEM_VALUE"; // Valor el item de bolsas
		
		// Estados de las facturas (documentos para cobro de subsidio Comfandi)  - PROYECTO: EMCALI COMFANDI		
		public static final String ID_PAYED_RESERVATION_CODE					= "COD_RESERVA_PAGADA";
		public static final String ID_REG_IN_PROCESSING_CODE					= "COD_REGISTRO_EN_PROCESAMIENTO";
		public static final String ID_WORKED_REGISTER_CODE						= "COD_REGISTRO_TRABAJADO";
		public static final String ID_SENDED_REGISTER_CODE						= "COD_REGISTRO_ENVIADO_AT"; // Registros enviados en archivos de totales
		//public static final String ID_AVANXO_RESERVATIONS_WS_USER				= "USUARIO_WEB_SERVICE_RESERVAS_AVANXO"; // Registros enviados en archivos de totales
		//public static final String ID_AVANXO_RESERVATIONS_WS_PASSWORD			= "CONTRASENA_WEB_SERVICE_RESERVAS_AVANXO"; // Registros enviados en archivos de totales
		//public static final String ID_AVANXO_LOGIN_WS_URL						= "URL_WEB_SERVICE_LOGIN_AVANXO"; // Registros enviados en archivos de totales		
		public static final String ID_EDUCATION_WS_URL					= "URL_EDUCATION_WEB_SERVICE"; // Servicios de educaci�n (consulta y pago de recibos)
		public static final String ID_SECURITY_EDUCATION_WS_URL			= "URL_SECURITY_EDUCATION_WEB_SERVICE"; // Servicio de seguridad de educaci�n (obtenci�n y validaci�n de token)
		
		public static final String ID_EDUCATION_WS_LOGIN				= "WEB_SERVICE_LOGIN"; // 
		public static final String ID_EDUCATION_WS_PASSWORD				= "WEB_SERVICE_PASSWORD"; //
		
		
		public static final String ID_CRED_CANCELED_STATUS						= "CRED_CANCELED_STATUS";
		public static final String ID_BLOOD_RELATIONSHIP_WORKER_STATUS			= "BLOOD_RELATIONSHIP_WORKER_STATUS";
		public static final String ID_BLOOD_RELATIONSHIP_THIRD_PART_STATUS		= "BLOOD_RELATIONSHIP_THIRD_PART_STATUS";
		//	Tipo de documentos de pago - PROYECTO: PAGO ELECTRONIO COMFANDI
		//public static final String ID_BON_DOC_PAYMENT_TYPE	= "BONO_DOC_PYMT_TYPE"; //Tipo de documento "BONO"
		//public static final String ID_CASH_DOC_PAYMENT_TYPE	= "CASH_DOC_PYMT_TYPE"; //Tipo de documento "EFECTIVO"

		public static final String ID_BONO_TRX_TYPE	= "BONO_TRX_TYPE"; //Tipo de trx de bono (subsidio desempleado)
		public static final String ID_CASH_TRX_TYPE	= "CASH_TRX_TYPE"; //Tipo de trx de efectivo (subsidio de transporte)		

		public static final String DB_TABLA_DESCRIPCION_ITEM			= "GCARDSF";

		public static final String DB_COLUM_DI_COMPANI              = "CODEMP";
		public static final String DB_COLUM_DI_PLU		         	= "CODART";		
		public static final String DB_COLUM_DI_DESCRIPTION         	= "DESCAR";

		public static final String DB_TABLA_DESCRIPCION_ITEM_2		= "GCARTIF";

		public static final String DB_COLUM_DI2_COMPANI             = "CODEMP";
		public static final String DB_COLUM_DI2_PLU		         	= "CODART";		
		public static final String DB_COLUM_DI2_DESCRIPTION         = "TEXPOS";	
		
		public static final String DB_TABLA_DESCRIPCION_ITEM_AGREEMENT		= "CONNARTF";
		
		public static final String DB_COLUM_ITEM_AGREEMET_PLU		       = "PLUCON";
		public static final String DB_COLUM_ITEM_AGREEMET_DESCRIPTION	   = "DESTII";
		
		public static final String DB_TABLA_CUSTOMERS				= "VFCLIEDF";

		public static final String DB_COLUM_CUS_COMPANY              	= "CODEMP";
		public static final String DB_COLUM_CUS_CUSTOMER_ID        		= "NROIDE";
		public static final String DB_COLUM_CUS_CUSTOMER_DOC_TYPE		= "HOBBY";		
		public static final String DB_COLUM_CUS_CUSTOMER_NAMES      	= "NOMCLI";		
		public static final String DB_COLUM_CUS_CUSTOMER_LAST_NAMES    	= "APECLI";
		public static final String DB_COLUM_CUS_CUSTOMER_ADDRESS    	= "DIRRES";
		public static final String DB_COLUM_CUS_CUSTOMER_EMAIL	    	= "EMAIL";
		public static final String DB_COLUM_CUS_CUSTOMER_CELLPHONE    	= "CELULAR";

		public static final String DB_TABLA_TRX_FAC_ELEC_CARVAJAL	= "TRX_FAC_ELEC_CARVAJAL";
		public static final String DB_COLUM_TFC_ID					= "TFC_ID";
		public static final String DB_COLUM_TFC_ID_CLIENTE			= "TFC_ID_CLIENTE";

		public static final String DB_COLUM_TFC_TIENDA				= "TFC_TIENDA"; 	
		public static final String DB_COLUM_TFC_NUM_TERMINAL		= "TFC_TERMINAL"; 	
		//public static final String DB_COLUM_TFN_OPERADOR			= "TFC_OPERADOR"; 		
		public static final String DB_COLUM_TFC_NUM_FACTURA			= "TFC_NUM_FACTURA";
		public static final String DB_COLUM_TFC_FECHA				= "TFC_FECHA";
		public static final String DB_COLUM_TFC_HORA				= "TFC_HORA";
		public static final String DB_COLUM_TFC_FECH_HORA_TRX_DB	= "TFC_FECHA_HORA_TRX_DB";
		
		 		
		public static final String DB_COLUM_TFC_WS_TRX_ID	    	= "TFC_WS_TRX_ID"; 
		public static final String DB_COLUM_TFC_WS_TRX_CUFE	    	= "TFC_WS_TRX_CUFE";
		public static final String DB_COLUM_TFC_WS_TRX_TRX_TOTAL  	= "TFC_WS_TRX_TOTAL";
		public static final String DB_COLUM_TFC_WS_TRX_TAXES_TOTAL  = "TFC_WS_TRX_TAXES_TOTAL";
		public static final String DB_COLUM_TFC_FLAG_REENVIO		= "TFC_FLAG_REENVIO";
		public static final String DB_COLUM_TFC_ID_TRANSACCION_EM	= "TFC_IUT"; // Id trx formado x datos de trx en SMA
		public static final String DB_COLUM_TFC_WS_TRX_CUFE_CONT	= "TFC_WS_TRX_CUFE_CONT"; // CUFE contingencia
		public static final String DB_COLUM_TFC_WS_TRX_ID_CONT	    = "TFC_WS_TRX_ID_CONT";
		public static final String DB_TFC_FISC_NUMBER_CONT			= "TFC_FISC_NUMBER_CONT"; // Número fiscal contingencia
		
		public static final String DB_TABLA_FAC_ELEC_REJECT_TRXS_CARVAJAL	= "TRX_FECO_TRXS_RECHAZA_CARVAJAL";
		public static final String DB_COLUM_FERTC_REGISTER_STATUS			= "TFR_STATUS"; // Estado en el registro en la tabla de rechazos
		
		public static final String DB_COLUM_FERTC_STORE						= "TFR_STORE";
		public static final String DB_COLUM_FERTC_TERMINAL					= "TFR_TERMINAL";
		public static final String DB_COLUM_FERTC_TRX_NUMBER				= "TFR_TRANSNUM";
		public static final String DB_COLUM_FERTC_DATE						= "TFR_DAY";
		public static final String DB_COLUM_FERTC_TIME						= "TFR_TIME";
		
		// Campos adicionados para reportes 
		//public static final String DB_COLUM_TFN_VALOR_FACTURA    	= "TFN_VALOR_FACTURA"; 
		//public static final String DB_COLUM_TFN_ID_TRANSACCION_EM	= "TFN_ID_TRANSACCION_EM"; // Id trx en Expressmed
		
		// CACS: Tabla de errores envio Expressmed
		public static final String DB_TABLA_TRX_FAC_ELECT_TRXS_RECHAZADAS		= "TRX_FECO_TRXS_RECHAZA_CARVAJAL";
		public static final String DB_COLUM_TFR_TIENDA							= "TFR_STORE"; 	
		public static final String DB_COLUM_TFR_NUM_TERMINAL					= "TFR_TERMINAL"; 	
		public static final String DB_COLUM_TFR_NUM_FACTURA						= "TFR_TRANSNUM"; 		
		public static final String DB_COLUM_TFR_FECHA_TRX						= "TFR_DAY";	
		public static final String DB_COLUM_TFR_HORA_TRX						= "TFR_TIME";
		public static final String DB_COLUM_TFR_DATE_TIME_TRX					= "TFR_DATE_TIME_TRX";
		//public static final String DB_COLUM_TFR_REQ_PART_NUM					= "TFR_PART";
		//public static final String DB_COLUM_TFR_REQ_PART_DATA					= "TFR_PART_DATA";
		public static final String DB_COLUM_TFR_ANSWER_MSG						= "TFR_ERROR_DATA";
		public static final String DB_COLUM_TFR_ERROR_CODE						= "TFR_ERROR_CODE";
		
		public static final String DB_COLUM_TFR_ID_TRANSACCION_EM				= "TFR_IUT"; // Id trx formado x datos de trx en SMA
		public static final String DB_COLUM_TFR_FISC_NUMBER						= "TFR_FISC_NUMBER"; //Número fiscal	
		
		 		


		
		
		
		// Par�metros varios
		public static final String ID_PAR_CENTURY								= "PAR_CENTURY";//Siglo
		public static final String PAR_CENTURY								= "20";
		

		private String aName;
		private String aValue;
	
		public BusinessLogicParam() {
		}
	
		public BusinessLogicParam(String pName,String pValue) {
			setName(pName);
			setValue(pValue);
		}

		/**
		 * @return
		 */
		public String getName() {
			return aName;
		}
		
				/**
				 * @return
				 */
		public String getValue() {
			return aValue;
		}
		
		/**
		 * @param string
		 */
		public void setName(String string) {
			aName = string;
		}
		
		/**
		 * @param string
		*/
		public void setValue(String string) {
			aValue = string;
		}
		
}

