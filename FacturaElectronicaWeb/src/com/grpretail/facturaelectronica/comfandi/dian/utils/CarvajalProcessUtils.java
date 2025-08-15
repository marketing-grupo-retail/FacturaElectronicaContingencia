package com.grpretail.facturaelectronica.comfandi.dian.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class CarvajalProcessUtils {
	// EDF: Error Dato Fiscal
	public final static String ID_EDF_001 			= "EDF001";
	public final static String ID_EDF_MSG_001 		= "TRX NO ENVIADA A CARVAJAL. NO TIENE USER DATA FISCAL";
	
	// EDC: Error Dato Cliente
	public final static String ID_EDC_001 			= "EDC001";
	public final static String ID_EDC_MSG_001 		= "TRX NO ENVIADA A CARVAJAL. NO TIENE USER DATA DE CLIENTE";	

	// EDI: Error Datos Item
	public final static String ID_EDI_001 			= "EDI001";
	public final static String ID_EDI_MSG_001 		= "ERROR EN DATOS DE ITEM";
	
	// EDA: Error Datos Almacén
	public final static String ID_EDA_001 			= "EDA001";
	public final static String ID_EDA_MSG_001 		= "ERROR EN DATOS DE ALMACEN";
	
	// EDA: Error Datos Tender
	public final static String ID_EDT_001 			= "EDT001";
	public final static String ID_EDT_MSG_001 		= "ERROR EN DATOS DE TENDER";	
	
	public final static String ID_BILL_ALREADY_SENDED_MSG  = "Regla: 90, Rechazo: Documento procesado anteriormente.";
	
	public final static String ID_TAXES_PROBLEM_MSG  			= "Regla: FAS01b, Rechazo: Existe mas de un grupo con informacion de totales para un mismo tributo en la factura";
	public final static String ID_CHECK_DIGIT_MSG  				= "Regla: FAK24, Rechazo: DV no corresponde al NIT informado";
	public final static String ID_STRUCTURE_DOCUMENT_ERROR_MSG 	= "Error en la estructura del documento."; 
	public final static String ID_RESOLUTION_ERROR_MSG 			= "El número de comprobante enviado no se encuentra dentro de un rango de numeración asignado por la DIAN para el emisor";
	public final static String ID_RESOLUTION_ERROR_MSG_002 		= "El número del comprobante enviado no esta contenido dentro del rango configurado para el tipo de factura.";
	public final static String ID_RESOLUTION_ERROR_MSG_003 		= "el campo DRF 2 - Fecha de inicio del perÃ­odo de autorizaciÃ³n de la numeraciÃ³n. Formato AAAA-MM-DD  es obligatorio";
	public final static String ID_RESOLUTION_ERROR_MSG_0031 	= "el campo DRF 2 - Fecha de inicio del período de autorización de la numeración. Formato AAAA-MM-DD  es obligatorio";
	public final static String ID_IDCUSTOMER_ERROR_MSG 			=  "Regla: FAK21, Rechazo: ID de adquiriente no Informado";//apsm 18Julio2025
	
	public final static String ID_CARV_001 			= "CAR001";
	public final static String ID_CARV_R90 			= "CARR90"; // Rechazado x la regla 90
	//public final static String ID_CARV_MSG_001 		= "TRX NO ENVIADA A EXPRESSMED. NO TIENE USER DATA FISCAL";
	
	public final static String ID_CARV_002 			= "CAR002";
	public final static String ID_CARV_RTX01 		= "CART01"; // Rechazado x Taxes 01:  (e.g., Valor del impuesto mal configurado

	public final static String ID_CARV_003 			= "CAR003";
	public final static String ID_CARV_DS01 		= "CARD01"; // CARvajal Document error (DS01: Document Strucuture 01). e.g., Error en estructura documento	

	public final static String ID_CARV_004 			= "CAR004";
	public final static String ID_CARV_FR01 		= "CARF01"; // CARvajal Fiscal error (FR01: Fiscal Resolution 01). e.g., Error en resolución fiscal
	public final static String ID_CARV_FR02 		= "CARF02"; // CARvajal Fiscal error (FR01: Fiscal Resolution 02). e.g., Error en resolución fiscal
	public final static String ID_CARV_FR03 		= "CARF03"; // CARvajal Fiscal error (FR01: Fiscal Resolution 03). e.g., Error en fechas resolución fiscal
	
	public final static String ID_CARV_RAD01 		= "RAD01"; // Rechazado x Adquiriente 01:  (e.g., Dígito de verificación erróneo
	
	public final static String COMF_UNIT_CODE = "UD";
	public final static String COMF_WEIGH_CODE = "KLG";
	
	public final static String DIAN_UNIT_CODE = "94";
	public final static String DIAN_WEIGH_CODE = "KGM";
	
	public final static String DIAN_TENDER_CASH_CODE 	= "10";
	public final static String DIAN_TENDER_BONUS_CODE 	= "71";
	public final static String DIAN_TENDER_OTHERS_CODE 	= "ZZZ";
	
	public final static String DIAN_TENDER_CASH_METHOD_CODE 	= "1";
	public final static String DIAN_TENDER_CREDIT_METHOD_CODE 	= "2";
	
	public static String getDianUnitCode(String pComfUnitCode){
		String answer_ = "";
		if(pComfUnitCode != null){
			if(pComfUnitCode.equals(COMF_UNIT_CODE))
				answer_ = DIAN_UNIT_CODE;
			else if(pComfUnitCode.equals(COMF_WEIGH_CODE))
				answer_ = DIAN_WEIGH_CODE;
				
		}
		return answer_;
	}
	
	public static String getDIANTenderCode(int pSMATenderId){
		String answer_ = "";
		if(pSMATenderId > 0){
			if(pSMATenderId == 11)
				answer_ = DIAN_TENDER_CASH_CODE;
			else if(pSMATenderId == 35)
				answer_ = DIAN_TENDER_BONUS_CODE;
			else if(pSMATenderId == 41)	
				answer_ = DIAN_TENDER_OTHERS_CODE;
		}
		return answer_;
	}
	
	public static String getDIANTenderMethod(int pSMATenderId){
		String answer_ = "";
		if(pSMATenderId > 0){
			if(pSMATenderId == 11)
				answer_ = DIAN_TENDER_CASH_METHOD_CODE;
			else if(pSMATenderId == 35)
				answer_ = DIAN_TENDER_CASH_METHOD_CODE;
			else if(pSMATenderId == 41)	
				answer_ = DIAN_TENDER_CASH_METHOD_CODE;
		}
		return answer_;
	}
	
	public static BigDecimal getBaseValue(long pTotalValue, float pTaxRate){
		return getBaseValue(pTotalValue, pTaxRate, 2);
	}
	
	public static BigDecimal getBaseValue(long pTotalValue, float pTaxRate, int pDecNumber, RoundingMode pRoudingMode){
		//float multiplier_ = (pTaxRate/100) + 1;
		BigDecimal multiplier_ = new BigDecimal((pTaxRate/100) + 1);
		multiplier_ = multiplier_.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal totalValue_ = new BigDecimal(pTotalValue);
		BigDecimal IVARate_ = new BigDecimal(pTaxRate);
		BigDecimal base_ = new BigDecimal(0);
		base_ = totalValue_.divide(multiplier_, pDecNumber, pRoudingMode);
		//System.out.println("base1_->"+base_);
		//base_ = base_.multiply(new BigDecimal(10));
		//System.out.println("base2_->"+base_);
		return base_;
	}
	
	public static BigDecimal getBaseValue(long pTotalValue, float pTaxRate, int pDecNumber){
		float fMultiplier_ = (pTaxRate/100) + 1;
		BigDecimal multiplier_ = new BigDecimal(fMultiplier_);
		multiplier_ = multiplier_.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal totalValue_ = new BigDecimal(pTotalValue);
		BigDecimal IVARate_ = new BigDecimal(pTaxRate);
		BigDecimal base_ = new BigDecimal(0);
		base_ = totalValue_.divide(multiplier_, pDecNumber, RoundingMode.HALF_UP);
		//System.out.println("base1_->"+base_);
		//base_ = base_.multiply(new BigDecimal(10));
		//System.out.println("base2_->"+base_);
		return base_;
	}
	
	public static BigDecimal getTaxValue(long pTotalValue, float pTaxRate){
		//float divider_ = (pTaxRate/100) + 1;
		BigDecimal divider_ = new BigDecimal((pTaxRate/100) + 1);
		divider_ = divider_.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal totalValue_ = new BigDecimal(pTotalValue);
		float fIVARate_ = pTaxRate/100;
		BigDecimal IVARate_ = new BigDecimal(fIVARate_);
		IVARate_ = IVARate_.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal taxValue_ = new BigDecimal(0);
		taxValue_ = totalValue_.multiply(IVARate_);
		taxValue_ = taxValue_.divide(divider_, 2, RoundingMode.HALF_UP);
		//System.out.println("base1_->"+base_);
		//base_ = base_.multiply(new BigDecimal(10));
		//System.out.println("base2_->"+base_);
		return taxValue_;
	}

	public static BigDecimal getTaxValue(BigDecimal pTotalValue, float pTaxRate){
		//float divider_ = (pTaxRate/100) + 1;
		BigDecimal divider_ = new BigDecimal((pTaxRate/100) + 1);
		divider_ = divider_.setScale(3, BigDecimal.ROUND_HALF_UP);
		//BigDecimal totalValue_ = new BigDecimal(pTotalValue);
		float fIVARate_ = pTaxRate/100;
		BigDecimal IVARate_ = new BigDecimal(fIVARate_);
		IVARate_ = IVARate_.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal taxValue_ = new BigDecimal(0);
		taxValue_ = pTotalValue.multiply(IVARate_);
		taxValue_ = taxValue_.divide(divider_, 2, RoundingMode.HALF_UP);
		//System.out.println("base1_->"+base_);
		//base_ = base_.multiply(new BigDecimal(10));
		//System.out.println("base2_->"+base_);
		return taxValue_;
	}

	public static BigDecimal getTaxValueFromBaseValue(BigDecimal pBaseValue, float pTaxRate){
		//float divider_ = (pTaxRate/100) + 1;
		//BigDecimal divider_ = new BigDecimal((pTaxRate/100) + 1);
		//divider_ = divider_.setScale(3, BigDecimal.ROUND_HALF_UP);
		//BigDecimal totalValue_ = new BigDecimal(pTotalValue);
		float fIVARate_ = pTaxRate/100;
		BigDecimal IVARate_ = new BigDecimal(fIVARate_);
		IVARate_ = IVARate_.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal taxValue_ = new BigDecimal(0);
		taxValue_ = pBaseValue.multiply(IVARate_);
		//taxValue_ = taxValue_.divide(divider_, 2, RoundingMode.HALF_UP);
		//System.out.println("base1_->"+base_);
		//base_ = base_.multiply(new BigDecimal(10));
		//System.out.println("base2_->"+base_);
		return taxValue_;
	}	
	
	public static String removeSpecialCharacters(String pDescription){
		String answer_ = pDescription;
		while(answer_.indexOf("*")>=0){
			answer_ = answer_.substring(0, answer_.indexOf("*"))+answer_.substring(answer_.indexOf("*")+1);
		}
		while(answer_.indexOf("/")>=0){
			answer_ = answer_.substring(0, answer_.indexOf("/"))+answer_.substring(answer_.indexOf("/")+1);
		}
		while(answer_.indexOf("�")>=0){
			answer_ = answer_.substring(0, answer_.indexOf("�"))+answer_.substring(answer_.indexOf("�")+1);
		}
		while(answer_.indexOf("�")>=0){
			answer_ = answer_.substring(0, answer_.indexOf("�"))+answer_.substring(answer_.indexOf("�")+1);
		}
		while(answer_.indexOf("&")>=0){
			answer_ = answer_.substring(0, answer_.indexOf("&"))+answer_.substring(answer_.indexOf("&")+1);
		}		
		return answer_;
	}
	
	public static String getCustomerIdADQ3(long pDocId){
		if (pDocId == 1){ // Cédula ciudadanía
			return "13";
		}else if(pDocId == 2){ // Cédula Extranjería
			return "22";
		}else if(pDocId == 3){ // Permiso permanencia 
			return "47";
		}else if(pDocId == 4){ // NIT
			return "31";
		}/*else if(pDocId == 5){ // Tipo 5 es mixto (c�dula/nit) pero no se env�a a factura electr�nica
			return "NIT";
		}*/else if(pDocId == 6){ // Tarjeta de identidad 
			return "12";
		}else if(pDocId == 7){ // NIU
			return "91";	
		}	
		return pDocId+"";
	}

	public static String getCustomerIdADQ1(long pDocId){
		if(pDocId == 4){ //4=cod Nit vfcliedf
			return "1";//juridica
		}else{
			return "2";	//natural
		}	
	}

	
}
