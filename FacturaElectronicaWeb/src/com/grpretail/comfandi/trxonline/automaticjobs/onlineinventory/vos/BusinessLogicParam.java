/*
 * Created on 27/07/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.grpretail.comfandi.trxonline.automaticjobs.onlineinventory.vos;

/**
 * @author COMFANDI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BusinessLogicParam {
	
		/*public static final String ID_VIGENCIA			= "VIGENCIA";       // Vigencia en días del subsidio
		public static final String ID_MAX_SUBSIDIO		= "MAX_SUBSIDIO";	// Valor Máximo de subsidio a pagar por este medio
		public static final String ID_CONCIL_TIMEOUT	= "CONCIL_TIMEOUT";	// Timeout en minutos que se puede dejar un proceso de conciliación para permitir otros sobre la misma tienda.
		public static final String ID_SYSTEM_INIT_DATE	= "SYSTEM_INIT_DATE";	// Fecha inicial del sistema
		public static final String ID_REPETITIONS_NUMBER= "REPETITIONS_NUMBER";	// Número de intentos para hacer un requerimiento a una DB
		// Estados de los documentos(subsidios Comfandi) - PROYECTO: PAGO ELECTRONIO COMFANDI 
		public static final String ID_DOC_EMITED_STATUS	= "DOC_EMITED_STATUS";
		public static final String ID_DOC_PAYED_STATUS	= "DOC_PAYED_STATUS";
		public static final String ID_DOC_BLOQ_STATUS	= "DOC_BLOQ_STATUS";
		public static final String ID_DB_USER			= "DB_USER";
		public static final String ID_ST_COB			= "ST_COB";
		// Estados de las credenciales (documentos para cobro de subsidio Comfandi)  - PROYECTO: PAGO ELECTRONIO COMFANDI		
		public static final String ID_CRED_DELIVERED_STATUS						= "CRED_DELIVERED_STATUS";
		public static final String ID_CRED_CANCELED_STATUS						= "CRED_CANCELED_STATUS";
		public static final String ID_BLOOD_RELATIONSHIP_WORKER_STATUS			= "BLOOD_RELATIONSHIP_WORKER_STATUS";
		public static final String ID_BLOOD_RELATIONSHIP_THIRD_PART_STATUS		= "BLOOD_RELATIONSHIP_THIRD_PART_STATUS";
		//	Tipo de documentos de pago - PROYECTO: PAGO ELECTRONIO COMFANDI
		public static final String ID_BON_DOC_PAYMENT_TYPE	= "BONO_DOC_PYMT_TYPE"; //Tipo de documento "BONO"*/		
	
		// Parámetros varios
		public static final String ID_PAR_CENTURY								= "PAR_CENTURY";//Siglo		
		

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

