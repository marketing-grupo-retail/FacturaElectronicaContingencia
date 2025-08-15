package com.grpretail.ws;

import com.grpretail.facturaelectronica.comfandi.business.utils.vos.AuthenticationAnswer;
import com.grpretail.facturaelectronica.comfandi.business.utils.vos.SendDocumentAnswer;
import com.grpretail.json.JSONAnswer;
import com.grpretail.json.JSONData;
//TODO Produccion quitar
//import com.grpretail.json.v2.JSONAnswer;
//import com.grpretail.json.v2.JSONData;

public class JSONServicesFacturaElectronica {
	public final static String ID_COD_BILL_ALREADY_EXISTS = "IR_009";
	public final static String ID_MSG_BILL_ALREADY_EXISTS = "Factura duplicada";
	public final static String ID_COD_STRUCTURE_DOC_ERROR = "IR_007";
	public final static String MSG_STRUCTURE_DOCUMENT_ERROR = "Error en la estructura del documento.";
	
	public final static String MSG_REMISSION_ERRORS_OCCURRED = "One or more validation errors occurred.";
	public final static String MSG_NO_ITEMS_IN_REMISSION = "No hay productos en la remision";
	public final static String ID_MSG_UNIT_NOT_VALID_IN_REMISSION = "BILLING.TRANSACTION.ADD.UNIT_NOT_VALID";
	
	
	public static AuthenticationAnswer getAuthenticationAnswer(String pStrAnswer){
		System.out.println("Desde getAuthenticationAnswer");
		AuthenticationAnswer answer_ = new AuthenticationAnswer();
		try {
			JSONAnswer jsonAnswer_ = new JSONAnswer(pStrAnswer);
			if (jsonAnswer_.getTags() != null && jsonAnswer_.getTags().size()>0){
				//EntMensaje entMsg_ = new EntMensaje();
				//JSONData tag_ = (JSONData)answer_.getTags().get(0);
				JSONData resultsTag_ =  jsonAnswer_.getTag("token");
				if (resultsTag_ != null && resultsTag_.getValue() != null){
					/*
					JSONData currAnswer_ = (JSONData)resultsTag_.getObjectsArray().get(0);
					if (currAnswer_ != null && currAnswer_.getValues() != null && currAnswer_.getValues().size()>0){
						JSONData currJSONData_ = (JSONData)currAnswer_.getThisLevelTag("token");
						if (currJSONData_ != null && currJSONData_.getValue() != null && currJSONData_.getValue().length()>0){
							*/
							System.out.println("Token retornado->"+resultsTag_.getValue());
							String strToken_ = resultsTag_.getValue();
							/*
							currJSONData_ = (JSONData)currAnswer_.getThisLevelTag("tokenExpire");
							if (currJSONData_ != null && currJSONData_.getValue() != null){
								System.out.println("Token retornado->"+currJSONData_.getValue());*/
								answer_.setToken(removeQuotes(strToken_));
								answer_.setSuccessfulFlag(true);
							//}

							/*
							if (currJSONData_.getValue().equals(ID_APPROVED_JSON_ANSWER)){
								System.out.println("Pago exitoso");
								answer_.setSuccessfulFlag(true);
								currJSONData_ = (JSONData)currAnswer_.getThisLevelTag("id");
								if (currJSONData_ != null && currJSONData_.getValue() != null){	
									System.out.println("Id del pago->"+currJSONData_.getValue());
									answer_.setPaymentId(currJSONData_.getValue());
								}else{
									System.out.println("Pago exitoso pero no hay id. No deberIa pasar");
									answer_.setPaymentId("0");
								}	
							}else if(currJSONData_.getValue().equals(ID_REJECTED_JSON_ANSWER)){
								System.out.println("Pago rechazado");
								answer_.setRejectedFlag(true);
							}else{
								System.out.println("Hubo error en pago. Status->"+currJSONData_.getValue());
							}
							*/
						/*		
						}else
							System.out.println("No hay tag de Status");
					}else
						System.out.println("No existe el tag que necesitamos");
					*/	
				}else
					System.out.println("No hay rta");
			}				
		} catch (Exception e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}
		return answer_;
	}
	
	public static SendDocumentAnswer getSendDocumentAnswer(String pStrAnswer){
		System.out.println("Desde getSendDocumentAnswer");
		SendDocumentAnswer answer_ = new SendDocumentAnswer();
		try {
			JSONAnswer jsonAnswer_ = new JSONAnswer(pStrAnswer);
			if (jsonAnswer_.getTags() != null && jsonAnswer_.getTags().size()>0){
				JSONData tag_ = (JSONData)jsonAnswer_.getTags().get(0);
				JSONData resultsTag_ =  tag_.getTag("status");
				
				//EntMensaje entMsg_ = new EntMensaje();

				if(resultsTag_ != null) {
					if (resultsTag_ != null){
						System.out.println("Status->"+resultsTag_.getValue());
						answer_.setStatus(removeQuotes(resultsTag_.getValue()));
					}else
						System.out.println("No existe tag 'status'");
					resultsTag_ =  tag_.getTag("statuscode");
					if (resultsTag_ != null){
						System.out.println("Status Code->"+resultsTag_.getValue());
						answer_.setStatuscode(removeQuotes(resultsTag_.getValue()));
					}else
						System.out.println("No existe tag 'statuscode'");				
					resultsTag_ =  tag_.getTag("legalStatus");
					if (resultsTag_ != null){
						System.out.println("Legal Status->"+resultsTag_.getValue());
						answer_.setLegalStatus(removeQuotes(resultsTag_.getValue()));
					}else
						System.out.println("No existe tag 'legalStatus'");
					
					resultsTag_ =  tag_.getTag("governmentResponseCode");
					if (resultsTag_ != null){
						System.out.println("Government Response Code->"+resultsTag_.getValue());
						answer_.setGovernmentResponseCode(removeQuotes(resultsTag_.getValue()));
					}else
						System.out.println("No existe tag 'governmentResponseCode'");
					
					resultsTag_ =  tag_.getTag("transactionId");
					if (resultsTag_ != null){
						System.out.println("Transaction Id->"+resultsTag_.getValue());
						answer_.setTransactionId(removeQuotes(resultsTag_.getValue()));
					}else {
						String strTransactionId_ = getManualTransactionId(pStrAnswer, "transactionId");
						if(!strTransactionId_.equals("")){
							System.out.println("Transaction Id->"+strTransactionId_);
							answer_.setTransactionId(removeQuotes(strTransactionId_));
						}else	
							System.out.println("No existe tag 'transactionId'");
					}	
		
					resultsTag_ =  tag_.getTag("CUFE");
					if (resultsTag_ != null){
						System.out.println("CUFE->"+resultsTag_.getValue());
						answer_.setCUFE(removeQuotes(resultsTag_.getValue()));
					}else
						System.out.println("No existe tag 'CUFE'");
					
					resultsTag_ =  tag_.getTag("QR");
					if (resultsTag_ != null){
						System.out.println("QR->"+resultsTag_.getValue());
						answer_.setQR(removeQuotes(resultsTag_.getValue()));
					}else
						System.out.println("No existe tag 'QR'");
					
					resultsTag_ =  tag_.getTag("governmentResponseDescription");
					if (resultsTag_ != null){
						System.out.println("governmentResponseDescription->"+resultsTag_.getValue());
						answer_.setGovernmentResponseDescription(removeQuotes(resultsTag_.getValue()));
					}else
						System.out.println("No existe tag 'governmentResponseDescription'");					
				
				}else {	
					resultsTag_ =  jsonAnswer_.getTag("code");
					if (resultsTag_ != null){
						System.out.println("COdigo error->"+resultsTag_.getValue());
						answer_.setCode(removeQuotes(resultsTag_.getValue()));
					}else
						System.out.println("No existe tag 'code'");
					
					resultsTag_ =  jsonAnswer_.getTag("message");
					if (resultsTag_ != null){
						System.out.println("Mensaje->"+resultsTag_.getValue());
						answer_.setMessage(removeQuotes(resultsTag_.getValue()));
					}else
						System.out.println("No existe tag 'message'");	
					
					resultsTag_ =  jsonAnswer_.getTag("CUFE");
					if (resultsTag_ != null){
						System.out.println("CUFE->"+resultsTag_.getValue());
						answer_.setCUFE(removeQuotes(resultsTag_.getValue()));
					}else
						System.out.println("No existe tag 'CUFE'");
					
					resultsTag_ =  jsonAnswer_.getTag("QR");
					if (resultsTag_ != null){
						System.out.println("QR->"+resultsTag_.getValue());
						answer_.setQR(removeQuotes(resultsTag_.getValue()));
					}else {
						String strQR_ = getManualTransactionId(pStrAnswer, "QR");
						if(!strQR_.equals("")){
							System.out.println("QR->"+strQR_);
							answer_.setQR(removeQuotes(strQR_));						
						}else
							System.out.println("No existe tag 'QR'");
					}	
				}
				answer_.setTotalMessage(pStrAnswer);
			}else
				System.out.println("No hay rta");
		} catch (Exception e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}
		return answer_;
	}	
	
	public static String getManualTransactionId(String pStrAnswer, String pTagName) {
		String answer_ = "";
		try {
			//if(pStrAnswer.indexOf("\"transactionId\":")>0) {
			if(pStrAnswer.indexOf("\""+pTagName+"\":")>0) {
				String remainString_ = pStrAnswer.substring(pStrAnswer.indexOf("\""+pTagName+"\":")+(3+pTagName.length()));
				remainString_ = remainString_.trim();
				if(remainString_.startsWith("\"")) {
					remainString_ = remainString_.substring(1);
					if(remainString_.indexOf("\"")>0)
						answer_ = remainString_.substring(0,remainString_.indexOf("\""));
				}		
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}catch(Throwable t) {
			t.printStackTrace();
		}
		return answer_;
	}
	
    public static String removeQuotes(String pStr){
        String answer_ = "";
        if(pStr != null && !pStr.trim().equals("")){
            String strTemp_ = pStr.trim();
            //if (strTemp_.startsWith("\""))
                //System.out.println("Comienza con comilla");
            if (strTemp_.length()>=2 && strTemp_.startsWith("\"") && strTemp_.endsWith("\"")){
                answer_= strTemp_.substring(1,(strTemp_.length()-1));
            }else
                answer_ = pStr;
        }
        return answer_;
    }
}
