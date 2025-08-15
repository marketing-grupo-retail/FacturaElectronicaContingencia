package com.grpretail.trxonline.automaticjobs.facturaelectronica.utils;

public class BusinessLogicParameters {

	public static final String ID_WS_AUTHENTICATION_URL 	= "AUTHENTICATION_URL"; //URL atenticaci�n al WS facturaci�n electr�nica
	public static final String WS_AUTHENTICATION_PLATFORM  	= "AUTHENTICATION_PLATFORM"; //Platform para conexi�n al WS
	
	
	// Tabla de c�digos para las diferentes transacciones
	public static final int APLICACION_LEALTAD					= 2;
	public static final int APLICACION_CHEQUES					= 3;
	public static final int APLICACION_BONOS					= 4;
	
	public static final int APLICACION_LEALTAD_COMFANDI			= 20;
	
	public static final int FUNCION_CARGAPARAM_WAS				= 206;
	public static final int FUNCION_CARGAPARAM_WASLOCAL			= 207;

	public static final int FUNCION_DESCONOCIDA					= 9999;
	
	public static final int MAX_REGS_TO_RETRIEVE_FROM_DB 				= 500;
	//public static final String USER_DATA_ITEM_ADDITIONAL_INFO 		= "62";
	public static final String USER_DATA_CUSTOMER_INFO					= "20040608";
	public static final String USER_DATA_TAXES_INFO						= "20221102"; // User data con info impuestos cada entrada en trx
	public static final String USER_DATA_SMALL_BILL_ADDITIONAL_INFO 	= "63";
	public static final String USER_DATA_TRX_FISCAL_INFO				= "65";

	public static final String ID_EMITTED_BONOS_STATUS			= "EMITTED_BONUS_STATUS";
	public static final String ID_PARTIAL_BONOS_STATUS			= "PARTIAL_BONUS_STATUS";
	public static final String ID_USED_BONOS_STATUS				= "USED_BONUS_STATUS";
	public static final String ID_NULL_BONOS_STATUS				= "NULL_BONUS_STATUS";	
	
	public static final int FUNCION_CONSULTA_FACTURA_A_PAGAR		= 1201;	// Por dentro de la trx
	public static final int FUNCION_ACTUALIZ_PAGO_FAC_TRXONLINE		= 1203;	// Por dentro de la trx
	

	// DATOS GENERALES DE LA APLICACION
	public static final String PAR_CURRENT_DATE_FUNTION_NAME			= "CURRENT DATE";//Para DB2
	public static final String PAR_CURRENT_TIMESTAMP_FUNTION_NAME		= "CURRENT TIMESTAMP";//Para DB2
	public static final String PAR_CURRENT_TIME_FUNTION_NAME			= "CURRENT TIME";//Para DB2
	public static final String PAR_CENTURY								= "20";	
	
	//public static final String CURRENT_DATE_FUNTION_NAME			= "SYSDATE";//Para Oracle
	

	// DATOS GENERALES DE LA APLICACION
	public static final int ID_TRX_IN_FILE_NO_DB					= 1;
	public static final int ID_TRX_IN_DB_NO_FILE					= 2;
	public static final int ID_TRX_BAD_ESTRUCTURE					= 3;
	public static final int ID_TRX_TWICE_IN_DB						= 4;		

	// CACS: Descuentos escalonados Pfizer
	//public static final String ID_PFIZER_PROGRAM						= "PFIZER_PROGRAMA";
	//public static final String ID_PFIZER_RETAIL							= "PFIZER_CADENA";
	//public static final String ID_PFIZER_OPERATOR_NAME					= "PFIZER_OPERADOR";
	//public static final String ID_SIMPLE_WS_URL_1						= "SIMPLE_WS_URL_1";
	//public static final String ID_SIMPLE_WS_URL_2						= "SIMPLE_WS_URL_2";
	//public static final String ID_SIMPLE_WS_AGREEMENT_ID_PREFIX			= "SIMPLE_WS_AGRE_ID_PREFIX";
	//public static final String ID_SIMPLE_WS_SEARCH_TYPE_2					= "SIMPLE_WS_SEARCH_TYPE_2";

	
	// TABLA GENERICA DE TRANSACCIONES DE PAGO - PROYECTO: PAGO ELECTRONIO COMFANDI
		
	//	TABLA DE DOCUMENTOS_PAGO - PROYECTO: FONEDE (FONDO DE APOYO AL DESEMPLEADO)
	//public static final String DB_TABLE_SERVICE_PAYMENT_TRX		= "SERVIREF";
	public static final String DB_TABLE_TRX_ONLINE_HEADER		= "TPOS_HEADER";
	//public static final String DB_COLUM_DP_DOC_ID			= "K082_ID_DO_PG";// Llave primaria de la tabla

	
	//public static final String DB_COLUM_DP_CRED_DOC_TYPE	= "T082_TP_DO_GIR_A"; //Tipo de documento de la tabla credenciales
	public static final String DB_COLUM_TOL_STORE								= "STORE"; // Almac�n
	public static final String DB_COLUM_TOL_TERMINAL							= "TERMINAL"; // N�mero de terminal
	public static final String DB_COLUM_TOL_TRX_NUMBER							= "TRANSNUM"; // N�mero trx
	public static final String DB_COLUM_TOL_DAY									= "DAY"; // Fecha
	public static final String DB_COLUM_TOL_TIME								= "TIME"; // Hora
	public static final String DB_COLUM_TOL_DATE_TIME							= "FECHAHORA"; // Fecha y hora trx
	public static final String DB_COLUM_TOL_INSERT_DATE_TIME					= "INFECHAHORA"; // Fecha y hora inserci�n trx en DB
	public static final String DB_COLUM_TOL_OPERATOR							= "OPERATOR"; // Operador
	public static final String DB_COLUM_TOL_HEADER_TRX_TYPE						= "TRANSTYPE"; // Tipo de transacci�n
	public static final String DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_STATUS		= "IL_ESTADO"; // IL: Inventario en l�nea estado
	public static final String DB_COLUM_TOL_HEADER_ONLINE_INVENTORY_WORKED_DATE	= "IL_WORK_DATE"; // IL: Inventario en l�nea fecha-hora(incluye per�odo)
	public static final String DB_COLUM_TOL_HEADER_ELECT_BILLING_STATUS			= "ELECT_BILL_STATUS"; // IL: Inventario en l�nea estado
	public static final String DB_COLUM_TOL_HEADER_GROSS_POS					= "GROSSPOS"; // Gross positivo
	public static final String DB_COLUM_TOL_HEADER_GROSS_NEG					= "GROSSNEG"; // Gross negativo
	public static final String DB_COLUM_TOL_HEADER_TYPE							= "TIPO"; // Usado como binario para prender o apagar flags
	

	public static final String DB_TABLE_TRX_ONLINE_ITEM_ENTRY	= "TPOS_ITEMENTRY";
	
	public static final String DB_COLUM_TOL_ITM_ENTRY_ITEMCODE						= "ITEMCODE";
	public static final String DB_COLUM_TOL_ITM_ENTRY_QTYORWGT						= "QTYORWGT";
	public static final String DB_COLUM_TOL_ITM_ENTRY_DEPARTMENT					= "DEPARTME";
	public static final String DB_COLUM_TOL_ITM_ENTRY_DEPARTMENT2					= "FAMILYNU1";
	public static final String DB_COLUM_TOL_ITM_ENTRY_XPRICE						= "XPRICE";
	public static final String DB_COLUM_TOL_ITM_ENTRY_SALEPRICE						= "SALEPRICE"; // Precio unitario de venta del producto
	public static final String DB_COLUM_TOL_ITM_ENTRY_INDICAT31						= "INDICAT31"; // {8: Anulaci�n 2:Devoluci�n}
	public static final String DB_COLUM_TOL_ITM_ENTRY_INDICAT16						= "INDICAT16";
	public static final String DB_COLUM_TOL_ITM_ENTRY_INDICAT17						= "INDICAT17"; /// 1: Es pesable  0: No pesable
	public static final String DB_COLUM_TOL_ITM_ENTRY_INDICAT210					= "INDICAT210";
	public static final String DB_COLUM_TOL_ITM_ENTRY_INDICAT211					= "INDICAT211"; // {1: Precio es negativo}
	public static final String DB_COLUM_TOL_ITM_ENTRY_INDICAT46						= "INDICAT46";
	public static final String DB_COLUM_TOL_ITM_ENTRY_OFFSETX						= "OFFSETX"; // Posici�n del string dentro de la trx
	
	public static final String DB_COLUM_TOL_ITM_ENTRY_EXEMP_FLAG					= "TIPO"; // Flag de excento
	public static final String DB_COLUM_TOL_ITM_ENTRY_VAT_RATE						= "PRCIVA"; // Tasa de IVA
	public static final String DB_COLUM_TOL_ITM_ENTRY_CONSUM_TAX_RATE				= "IMPCONRATE"; // Tasa de impoconsumo
	public static final String DB_COLUM_TOL_ITM_ENTRY_MEASURE_UNIT					= "UNIDMEDIDA"; // Unidad de medida
	public static final String DB_COLUM_TOL_ITM_ENTRY_DISCOUNT_VALUE				= "DESCUENTO"; // Descuento
	
	public static final String DB_COLUM_TOL_ITM_ENTRY_TAX_VALUE						= "VATTAXAMT"; // Valor impuesto
	public static final String DB_COLUM_TOL_ITM_ICUI_TAX_RATE						= "ICUITAXRATE"; // Tasa de ICUI
	
	//public static final String DB_COLUM_TOL_ITM_ENTRY_ONLINE_INVENTORY_WORKED_DATE	= "IL_WORK_DATE"; // IL: Inventario en l�nea estado

	
	public static final String DB_TABLE_TRX_ONLINE_USER_DATA				= "TPOS_USER_DATA";
	
	public static final String DB_COLUM_TOL_USR_DATA_DATA1					= "DATA1";
	public static final String DB_COLUM_TOL_USR_DATA_DATA2					= "DATA2";
	public static final String DB_COLUM_TOL_USR_DATA_DATA3					= "DATA3";
	public static final String DB_COLUM_TOL_USR_DATA_DATA4					= "DATA4";
	public static final String DB_COLUM_TOL_USR_DATA_DATA5					= "DATA5";
	public static final String DB_COLUM_TOL_USR_DATA_DATA6					= "DATA6";
	public static final String DB_COLUM_TOL_USR_DATA_DATA7					= "DATA7";
	public static final String DB_COLUM_TOL_USR_DATA_DATA8					= "DATA8";
	public static final String DB_COLUM_TOL_USR_DATA_DATA9					= "DATA9";
	public static final String DB_COLUM_TOL_USR_DATA_DATA10					= "DATA10";
	public static final String DB_COLUM_TOL_USR_DATA_DATA11					= "DATA11";
	public static final String DB_COLUM_TOL_USR_DATA_OFFSETX				= "OFFSETX";

	public static final String DB_TABLE_TRX_ONLINE_TENDER					= "TPOS_TENDER";
	
	public static final String DB_COLUM_TOL_TENDER_ID						= "TENDTYPE";
	public static final String DB_COLUM_TOL_TENDER_AMOUNT					= "AMTTENDE";
	public static final String DB_COLUM_TOL_TENDER_TYPE						= "TYPE";
	
	public static final String DB_TABLE_TRX_ONLINE_DISCOUNT					= "TPOS_DISCOUNT";
	
	public static final String DB_COLUM_TOL_DISCOUNT_TYPE					= "TYPE";
	public static final String DB_COLUM_TOL_DISCOUNT_AMOUNT					= "AMTDISCO";	
	
	
	/*
	public static final String DB_COLUM_SP_PAYMENT_DATE_2		= "FEPAGO"; // Fecha de pago
	public static final String DB_COLUM_SP_PAYMENT_TIME			= "HOPAGO"; // Hora de pago
	public static final String DB_COLUM_SP_TENDER_TYPE			= "MEDPGO";
	public static final String DB_COLUM_SP_STORE				= "CODCCO";
	public static final String DB_COLUM_SP_TRX_TIMESTAMP		= "FEHPOS";
	public static final String DB_COLUM_SP_TRX_NUMBER			= "TRANSP";
	public static final String DB_COLUM_SP_TERMINAL				= "TERMNP";
	public static final String DB_COLUM_SP_OPERATOR				= "OPERDP";
	public static final String DB_COLUM_SP_STATUS				= "ESTADO";
	public static final String DB_COLUM_SP_PERIOD				= "DERROR";
	*/
	/*
	public static final String DB_COLUM_SP_SUPPLYER_EAN			= "EANPRO"; // EAN del proveedor
	public static final String DB_COLUM_SP_REFERENCE_NUMBER		= "NREFER"; // N�mero de referencia
	public static final String DB_COLUM_SP_BILL_AMOUNT			= "MONTOR"; // Valor de la factura
	public static final String DB_COLUM_SP_EXPIRATION_DATE		= "FVENCI"; // Fecha vencimiento
	public static final String DB_COLUM_SP_PAYMENT_DATE_2		= "FEPAGO"; // Fecha de pago
	public static final String DB_COLUM_SP_PAYMENT_TIME			= "HOPAGO"; // Hora de pago
	public static final String DB_COLUM_SP_TENDER_TYPE			= "MEDPGO";
	public static final String DB_COLUM_SP_STORE				= "CODCCO";
	public static final String DB_COLUM_SP_TRX_TIMESTAMP		= "FEHPOS";
	public static final String DB_COLUM_SP_TRX_NUMBER			= "TRANSP";
	public static final String DB_COLUM_SP_TERMINAL				= "TERMNP";
	public static final String DB_COLUM_SP_OPERATOR				= "OPERDP";
	public static final String DB_COLUM_SP_STATUS				= "ESTADO";
	public static final String DB_COLUM_SP_PERIOD				= "DERROR";
	*/
	
	//public static final String DB_TABLE_SEND_FILE			= "SI_ARCHIVO_ENVIADO";	
	public static final String DB_TABLE_TOL_OLI_SEND_FILE				= "TOL_OLI_PERIOD_WORKED";
	public static final String DB_COLUM_TOLOLI_SF_PERIOD				= "PERIODO";
	public static final String DB_COLUM_TOLOLI_SF_DATE					= "FECHA";
	public static final String DB_COLUM_TOLOLI_SF_INSERT_DATE			= "FECHA_INSERTION";
	public static final String DB_COLUM_TOLOLI_SF_HEADER_UPDATED_REGS	= "REGS_ACTUAL_HEADER";
	public static final String DB_COLUM_TOLOLI_SF_ITMENTRY_UPDATED_REGS	= "REGS_ACTUAL_ITMENTRY";

	public static final String DB_TABLE_TENDERS	   			= "EMCALTMF";
	public static final String DB_COLUM_T_TENDER_TV  		= "MEDPGO";
	public static final String DB_COLUM_T_TENDER_CODE 		= "CODMED";
	
	public static final String DB_TABLE_STORES	   			= "EMCALALM";
	public static final String DB_COLUM_ST_STORE_COMF  		= "COMFAALM";
	public static final String DB_COLUM_ST_STORE_EMCALI		= "EMCALALM";	
	  
	/*public static final String DB_COLUM_DP_FECHA_PAGO_DOC	= "T082_F_PG_DO";
	public static final String DB_COLUM_DP_HORA_PAGO_DOC	= "T082_H_PG";
	public static final String DB_COLUM_DP_USUARIO_PAGO		= "T082_US_PG";
	public static final String DB_COLUM_DP_FECHA_COBRO		= "T082_F_COB";
	public static final String DB_COLUM_DP_FECHA_GRB_COBRO	= "T082_F_GRB_COB";
	public static final String DB_COLUM_DP_USUARIO_GRB_COB	= "T082_US_GRB_COB";
	public static final String DB_COLUM_DP_VALOR_COBRO		= "T082_VR_COBRADO";
	public static final String DB_COLUM_DP_ID_ST_COBRO		= "K044_ID_ST_COB";
	//public static final String DB_COLUM_DP_TRX_TYPE			= "T082_TP_TRANSACC";
	public static final String DB_COLUM_DP_WORKED_FLAG		= "T082_IND_CONCILIA";*/
	
	////********** Campos de la llave de actualizaci�n //////////////  
	public static final String DB_COLUM_DP_STORE_NUMBER		= "T04_C_PTO_VTA";
	public static final String DB_COLUM_DP_TERMINAL_NUMBER	= "T04_C_TERMINAL";
	public static final String DB_COLUM_DP_TRX_NUMBER		= "T04_NRO_TRANSA";
	public static final String DB_COLUM_DP_TRX_DATE			= "T04_FH_TRANSA";
	//public static final String DB_COLUM_DP_CREDENTIAL_NUMBER= "T04_C_BARRAS";
	public static final String DB_COLUM_DP_OPERATOR_NUMBER	= "T04_ID_CAJERO";
	
	public static final String DB_COLUM_DP_TRX_TYPE			= "T04_TIPO_TRANSA";
	public static final String DB_COLUM_DP_ACCOUNTING_DATE	= "T04_FH_CONTABLE";
	public static final String DB_COLUM_DP_CONFIRM_DATE		= "T04_F_CONFIRMA";


	//	TABLA DE CONCILIACION - PROYECTO: PAGO ELECTRONIO COMFANDI

	////********** Campos de la llave de actualizaci�n //////////////

	//public static final String DB_COLUM_PCL_ID					= "LT_ID";
	/*public static final String DB_TABLA_PUNTOS_CLIENTE			= "LT_PUNTOS_CLIENTE";
	
	public static final String DB_COLUM_LPC_ID_CLIENTE			= "LT_ID_CLIENTE"; 
	public static final String DB_COLUM_LPC_TOTAL_POINTS		= "LT_NUMERO_PUNTOS_TOTALES"; 
	public static final String DB_COLUM_LPC_REDEM_POINTS		= "LT_NUMERO_PUNTOS_REDIMIDOS";
	public static final String DB_COLUM_LPC_PERIOD_POINTS		= "LT_NUMERO_PUNTOS_ACUM_PERIODO";
	public static final String DB_COLUM_LPC_EXPIR_POINTS		= "LT_NUMERO_PUNTOS_A_VENCER";
	public static final String DB_COLUM_LPC_POINTS_EXPIR_DATE	= "LT_FECHA_VENCIMIENTO_PUNTOS";
	public static final String DB_COLUM_LPC_STAR_EVENT_DATE		= "LT_FECHA_INICIO_EVENTO";
	public static final String DB_COLUM_LPC_END_EVENT_DATE		= "LT_FECHA_FIN_EVENTO";*/
	
	public static final String DB_TABLA_LABORATORY					= "DE_LABORATORIO";
	public static final String DB_COLUM_LAB_ID						= "ID";
	public static final String DB_COLUM_LAB_NAME					= "NOMBRE"; 
	public static final String DB_COLUM_LAB_NIT						= "NIT";
	public static final String DB_COLUM_LAB_ADDRESS					= "DIRECCION";
	public static final String DB_COLUM_LAB_PHONE_NUMBER			= "TELEFONO";
	public static final String DB_COLUM_LAB_CONTACT					= "CONTACTO";
	public static final String DB_COLUM_LAB_CUSTOMER_TYPE			= "CODIGO_TIPOCLIENTE";


	public static final String DB_TABLA_CUSTOMER_TRXS			= "DE_TRANSACCION";
	//ID CODEMP NROIDE CODART NUMEROPRODDCTO DESCUENTO CODCCO NUM_TERMINAL OPERADOR NUM_FACTURA FECHA_HORA_TRX_POS FECHACOMPRA

	public static final String DB_COLUM_DES_COMPANY_ID				= "CODEMP";
	public static final String DB_COLUM_DES_ID_CLIENTE				= "NROIDE"; 
	public static final String DB_COLUM_DES_ITEM_CODE				= "CODART"; 
	public static final String DB_COLUM_DES_ITEM_PRICE				= "PRECIOVENTA";
	public static final String DB_COLUM_DES_SOLD_ITEM_NUM			= "NUMEROPRODDCTO";
	public static final String DB_COLUM_DES_DISCOUNT				= "DESCUENTO";
	public static final String DB_COLUM_DES_DISCOUNT_PERC			= "PORCE_DESCUENTO";
	public static final String DB_COLUM_DES_LAST_SCALE				= "ULTIMA_ESCALA_APLIC";
	public static final String DB_COLUM_DES_TRX_INDEX				= "INDICE_TRX";
	public static final String DB_COLUM_DES_STORE					= "CODCCO";
	public static final String DB_COLUM_DES_TERMINAL				= "NUM_TERMINAL";
	public static final String DB_COLUM_DES_OPERATOR				= "OPERADOR";
	public static final String DB_COLUM_DES_TRX_NUMBER				= "NUM_FACTURA";
	public static final String DB_COLUM_DES_POS_DATE_TIME			= "FECHA_HORA_TRX_POS";
	public static final String DB_COLUM_DES_TRX_DATE				= "FECHACOMPRA";
	public static final String DB_COLUM_DES_DB_TRX_DATE				= "FECHACOMPRADB";
	public static final String DB_COLUM_DES_DB_SOURCE				= "ORIGEN";
	public static final String DB_COLUM_DES_CONSEC_FISCAL			= "CONSEC_FISCAL";
	
	public static final String DB_TABLA_SCALE					= "DE_ESCALA";
	//ID          ID_LABORATORIO NOMBRE                         E1    E2    E3    E4    E5    E6    DIASBENEF REINICIO
	
	public static final String DB_COLUM_SCA_ID						= "ID";	
	public static final String DB_COLUM_SCA_LABOR_ID				= "ID_LABORATORIO";
	public static final String DB_COLUM_SCA_NAME					= "NOMBRE";
	public static final String DB_COLUM_SCA_DISCOUNT_1				= "E1";
	public static final String DB_COLUM_SCA_DISCOUNT_2				= "E2";
	public static final String DB_COLUM_SCA_DISCOUNT_3				= "E3";
	public static final String DB_COLUM_SCA_DISCOUNT_4				= "E4";
	public static final String DB_COLUM_SCA_DISCOUNT_5				= "E5";
	public static final String DB_COLUM_SCA_DISCOUNT_6				= "E6";
	public static final String DB_COLUM_SCA_BENEF_DAYS				= "DIASBENEF";
	public static final String DB_COLUM_SCA_RESTART_FLAG			= "REINICIO";
	
	//CODEMP CODART    ID_ESCALA   ID_LABORATORIO MAXPRODMES
	public static final String DB_TABLA_SCALE_OF_ITEM				= "DE_PRODXESCALA";
	public static final String DB_COLUM_ISC_COMPANY_COD				= "CODEMP"; 
	public static final String DB_COLUM_ISC_ITEM_ID					= "CODART"; 
	public static final String DB_COLUM_ISC_SCALE_ID				= "ID_ESCALA";
	public static final String DB_COLUM_ISC_LABOR_ID				= "ID_LABORATORIO";
	public static final String DB_COLUM_ISC_MAX_ITEM_MONTH			= "MAXPRODMES";
	
	
	public static final String DB_COLUM_LPC_PERIOD_POINTS		= "LT_NUMERO_PUNTOS_ACUM_PERIODO";
	public static final String DB_COLUM_LPC_EXPIR_POINTS		= "LT_NUMERO_PUNTOS_A_VENCER";
	public static final String DB_COLUM_LPC_POINTS_EXPIR_DATE	= "LT_FECHA_VENCIMIENTO_PUNTOS";
	public static final String DB_COLUM_LPC_STAR_EVENT_DATE		= "LT_FECHA_INICIO_EVENTO";
	public static final String DB_COLUM_LPC_END_EVENT_DATE		= "LT_FECHA_FIN_EVENTO";	
	 
	public static final String DB_TABLA_TRX_LEALTAD				= "LT_TRX_LEALTAD";
	public static final String DB_COLUM_LTL_ID					= "TLT_ID";
	public static final String DB_COLUM_LTL_ID_CLIENTE			= "TLT_ID_CLIENTE";
	public static final String DB_COLUM_LTL_PRY					= "TLT_PRY";
	public static final String DB_COLUM_LTL_SVR					= "TLT_SVR";
	public static final String DB_COLUM_LTL_CADENA				= "TLT_CADENA"; 
	public static final String DB_COLUM_LTL_TIENDA				= "TLT_TIENDA"; 	
	public static final String DB_COLUM_LTL_NUM_TERMINAL		= "TLT_NUM_TERMINAL"; 	
	public static final String DB_COLUM_LTL_OPERADOR			= "TLT_OPERADOR"; 		
	public static final String DB_COLUM_LTL_NUM_FACTURA			= "TLT_NUM_FACTURA";	
	public static final String DB_COLUM_LTL_FECH_HORA_TRX_POS	= "TLT_FECHA_HORA_TRX_POS";
	public static final String DB_COLUM_LTL_FECHA_HORA_TRX		= "TLT_FECHA_HORA_TRX";
	       
	     
	     
	 		
	public static final String DB_COLUM_LTL_PUNTOS_ACUM_TRX	    = "TLT_NUM_PUNTOS_ACUM_TRX"; 
	public static final String DB_COLUM_LTL_PUNTOS_REDEM_TRX	= "TLT_NUM_PUNTOS_REDEM_TRX"; 
	public static final String DB_COLUM_LTL_PUNTOS_ACUM_SALDO	= "TLT_NUM_PUNTOS_ACUM_SALDO";
	public static final String DB_COLUM_LTL_PUNTOS_REDEM_SALDO	= "TLT_NUM_PUNTOS_REDEM_SALDO";
	public static final String DB_COLUM_LTL_MONTO_TRANSACCION	= "TLT_MONTO_TRX";

	/*
	public static final String DB_TABLA_BONOS_LEALTAD			= "LT_BONOS_LEALTAD";
	public static final String DB_COLUM_LBL_ID					= "LBL_ID";
	public static final String DB_COLUM_LBL_ID_CLIENTE			= "LBL_ID_CLIENTE";
	public static final String DB_COLUM_LBL_NUMERO				= "LBL_NUMERO";
	public static final String DB_COLUM_LBL_VALOR				= "LBL_VALOR";
	public static final String DB_COLUM_LBL_VALOR_USADO			= "LBL_VALOR_USADO"; 
	public static final String DB_COLUM_LBL_FECHA_CREACION		= "LBL_FECHA_CREACION"; 	
	public static final String DB_COLUM_LBL_FECHA_USO			= "LBL_FECHA_USO"; 	
	public static final String DB_COLUM_LBL_ESTADO				= "LBL_ESTADO"; 		
	 */
	/*CREATE TABLE LT_BONOS_LEALTAD ( 
		LBL_ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY ( START WITH +1 , INCREMENT BY +1 , NO CACHE ), 
		LBL_ID_CLIENTE DECIMAL(15) NOT NULL,
		LBL_NUMERO DECIMAL(20) NOT NULL,
		LBL_VALOR DECIMAL(11) NOT NULL,
		LBL_VALOR_USADO DECIMAL(11) NOT NULL WITH DEFAULT 0,
		LBL_FECHA_CREACION TIMESTAMP  NOT NULL  WITH DEFAULT CURRENT TIMESTAMP,
		LBL_FECHA_USO TIMESTAMP,
		LBL_ESTADO CHAR(1) NOT NULL
	);*/


	
	public static final String DB_SQ_PCL_NEXT_VALUE				= "SQ_LT_TRX_PUNTOS_CLIENTE.NEXTVAL";
	
	/*public static final String DB_TABLA_CLIENTE					= "MOL_CLIENTES";
	public static final String DB_COLUM_CL_ID					= "ID";             
	public static final String DB_COLUM_CL_ID_TIPOCLIENTE		= "ID_TIPOCLIENTE";
	public static final String DB_COLUM_CL_CODIGO				= "CODIGO";
	public static final String DB_COLUM_CL_CEDULA				= "CEDULA";
	public static final String DB_COLUM_CL_NOMBRES				= "NOMBRES";
	public static final String DB_COLUM_CL_APELLIDOS			= "APELLIDOS";
	public static final String DB_COLUM_CL_DIRECCION			= "DIRECCION";
	public static final String DB_COLUM_CL_TELEFONO				= "TELEFONO";*/
	
	public static final String DB_TABLA_LOYALTY_PARAMETERS		= "LT_PARAMETROS_PTS";
	public static final String DB_COLUM_LY_VENCIMIENTO			= "VENCIMIENTO";             
	public static final String DB_COLUM_LY_DURATION				= "DURACION";
	public static final String DB_COLUM_LY_NOTIFICATION			= "NOTIFICACION";
	public static final String DB_COLUM_LY_ACCUMULATION			= "ACUMULACION ";
	public static final String DB_COLUM_LY_START_DATE			= "FECHA_INI DATE";
	public static final String DB_COLUM_LY_END_DATE				= "FECHA_FIN DATE";
	public static final String DB_COLUM_CL_NOMBRES				= "NOMCLI";
	public static final String DB_COLUM_CL_APELLIDOS			= "APECLI";
	public static final String DB_COLUM_CL_ID_TIPOCLIENTE		= "TIPO";
 	
	
	public static final String DB_COLUM_VD_TRX_FILE_FLAG		= "T189_FILE_FLAG";
	public static final String DB_COLUM_VD_TRX_DB_FLAG			= "T189_DB_FLAG";
	public static final String DB_COLUM_VD_TRX_LOG_FLAG			= "T189_LOG_FLAG";
	public static final String DB_COLUM_VD_DOC_NUMBER			= "T189_TIPO_TRX";
	  
	public static final String DB_COLUM_VD_STORE_NUMBER		= "T189_ID_SUPERM";
	public static final String DB_COLUM_VD_TERMINAL_NUMBER		= "T189_ID_TERMINAL";
	public static final String DB_COLUM_VD_TRX_NUMBER			= "T189_ID_TRANSACC";
	public static final String DB_COLUM_VD_TRX_DATE			= "T189_FH_TRANSACC";
	public static final String DB_COLUM_VD_CREDENTIAL_NUMBER	= "T189_CO_BARRA";
	public static final String DB_COLUM_VD_OPERATOR_NUMBER		= "T189_ID_CAJERO";
	//public static final String DB_COLUM_CON_FECHA_ULT_CONCI		= "T189_FEC_ULT_CONC";
	//public static final String DB_COLUM_CON_VALOR_TRX_SUBSIDIO	= "T189_VR_DO_GLB";	
	
	//	TABLA DE RESUMEN DE CONCILIACION - PROYECTO: PAGO ELECTRONIO COMFANDI
	public static final String DB_TABLA_RESUM_CONCIL			= "T190_RESUMEN_CONCILIACION";
	public static final String DB_COLUM_REC_ID					= "T190_ID";
	public static final String DB_COLUM_REC_STORE_NUMBER		= "T190_ID_SUPERM";
	public static final String DB_COLUM_REC_CONCIL_DATE			= "T190_CONCIL_DATE";			
	public static final String DB_COLUM_REC_STORE_TOTAL_VALUE	= "T190_VR_TOTAL_SUPER";
	public static final String DB_COLUM_REC_DB_TOTAL_VALUE		= "T190_VR_TOTAL_DB";
	public static final String DB_COLUM_REC_LOG_TOTAL_VALUE		= "T190_VR_TOTAL_LOG";	
	public static final String DB_COLUM_REC_STORE_TRX_NUMBER	= "T190_NUM_TRX_SUPER";
	public static final String DB_COLUM_REC_DB_TRX_NUMBER		= "T190_NUM_TRX_DB";
	public static final String T190_NUM_TRX_LOG					= "T190_NUM_TRX_LOG";

	
	//	TABLA DE CREDENCIALES - PROYECTO: PAGO ELECTRONIO COMFANDI
	
	/*
	public static final String DB_TABLA_BILLS_TO_PAY			= "EMCALIFF";
	//public static final String DB_COLUM_BP_ID					= "TXXX_ID";
	public static final String DB_COLUM_BP_EAN_SUPPLYER			= "EANPRO";
	public static final String DB_COLUM_BP_REFERENCE_NUMBER		= "NREFER";
	public static final String DB_COLUM_BP_BILL_AMT				= "MONTOR";	
	public static final String DB_COLUM_BP_EXP_DATE				= "FVENCI";
	public static final String DB_COLUM_BP_VALID_DATE			= "FVALID";
	//public static final String DB_COLUM_BP_REG_STATUS			= "ESTADO";	
	public static final String DB_COLUM_BP_P_WORK_DATE			= "PWDATE";
	public static final String DB_COLUM_BP_T_WORK_DATE			= "TWDATE";
	*/
	
	
	public static final String DB_COLUM_BP_CREDENTIAL_NUMBER	= "T65_C_BARRAS";
	public static final String DB_COLUM_CR_CREDENTIAL_STATUS	= "T65_EST_REG";
	//public static final String DB_COLUM_CR_BLOOD_RELATIONSHIP	= "T140_PARENTESCO";
	public static final String DB_COLUM_CR_DOCUMENT_TYPE		= "T65_TIPO_ID";
	public static final String DB_COLUM_CR_BENEFICIARY_ID		= "T65_NRO_ID";

	// Registro de transacciones
	// Campos gen�ricos
	public static final String DB_COLUM_APLICACION			= "aplicacion";
	public static final String DB_COLUM_FUNCION				= "funcion";
	public static final String DB_COLUM_CODCOMERCIO			= "cod_comercio";
	public static final String DB_COLUM_IDTERMINALPOS		= "id_term_pos";
	public static final String DB_COLUM_CONSECUTRETAIL		= "consecut_retail";
	public static final String DB_COLUM_RRN					= "rrn";
	public static final String DB_COLUM_NUMAUTORIZ			= "num_autoriz";
	public static final String DB_COLUM_FECHAPROCESO		= "fecha_proceso";
	public static final String DB_COLUM_FECHAACTUAL			= "fecha_actual";
	public static final String DB_COLUM_FECHAPOSTEO			= "fecha_posteo";
	public static final String DB_COLUM_CODPROCESO			= "cod_proceso";
	public static final String DB_COLUM_CODRTAHOST			= "cod_resp_host";
	public static final String DB_COLUM_VALORTRX			= "valor";
	
	// Tabla de c�digos de respuesta del WAS
	public static final String DB_TABLA_ANSWER_CODES		= "ANSWER_CODES";
	public static final String DB_COLUM_AC_ID				= "ID";
	public static final String DB_COLUM_AC_STRING_ID		= "STRING_ID";
	public static final String DB_COLUM_AC_CODE				= "CODE";
	public static final String DB_COLUM_AC_MESSAGE			= "MESSAGE";	
	
	// Tabla de par�metros de l�gica de negocio
	/*public static final String DB_TABLA_BUSINESS_LOGIC_PARAMS	= "BUSINESS_LOGIC_PARAMS";
	public static final String DB_COLUM_BLP_ID					= "ID";
	public static final String DB_COLUM_BLP_NAME				= "NAME";
	public static final String DB_COLUM_BLP_VALUE				= "VALUE";*/

	
	// SEQUENCIAS
	// C�digo de autorizaci�n
	//public static final String DB_SEQ_AUTORIZ				= "AUTORIZ_SEQ";




	public static final int PAGE_REGISTER_NUMBER						= 500;//N�mero de registros para paginaci�n.
	
	
	// Bases de datos usadas en el proyecto  - PROYECTO: PAGO ELECTRONIO COMFANDI
	public static final String ID_DEFAULT_DATABASE				= "";//
	public static final String ID_DATABASE_TRXONLINE			= "tol";// Documentos de pago
	public static final String ID_DATABASE_VECINO_FIEL			= "vf";// Vecino fiel
	public static final String ID_DATABASE_PARAMETROS			= "pa";// Parámetros
	public static final String ID_DATABASE_ITEMS				= "it";// Parámetros
	
	//public static final String ID_DATABASE_TITAN				= "titan";	// Transacciones de log.
		
	
	public static final String DB_TABLE_STORE				= "gccecof";
	public static final String DB_TABLE_DEPARTAMENTO		= "GCPRVIF";
	public static final String DB_TABLE_MDP 				= "FE_MDPPOSDIAN";
	public static final String DB_TABLE_CODCIUDAD_POSTAL 	= "fe_codmun_postal";
	
	public static final String NOTA1 = "NOTA1";
	public static final String NOTA1Fechavigencia = "NOTA1Fechavigencia";
	public static final String NOTA3 = "NOTA3";
	
	
	
}

