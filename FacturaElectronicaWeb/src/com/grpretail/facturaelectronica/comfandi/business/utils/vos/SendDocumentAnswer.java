package com.grpretail.facturaelectronica.comfandi.business.utils.vos;

public class SendDocumentAnswer {
	//boolean successfulFlag;
	String status;
	String statuscode;
	String legalStatus;
	String governmentResponseCode;
	String transactionId;
	
	String code;
	String message;
	String QR;
	String CUFE;
	String totalMessage;
	String governmentResponseDescription;
	
	public SendDocumentAnswer(){
		//successfulFlag = false;
		status = "";
		code = "";
		message = "";
		QR = "";
		CUFE = "";
		transactionId = "";
		statuscode = "";
		legalStatus = "";
		governmentResponseCode = "";
		totalMessage = "";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getQR() {
		return QR;
	}

	public void setQR(String qR) {
		QR = qR;
	}

	public String getCUFE() {
		return CUFE;
	}

	public void setCUFE(String cUFE) {
		CUFE = cUFE;
	}

	public String getTotalMessage() {
		return totalMessage;
	}

	public void setTotalMessage(String totalMessage) {
		this.totalMessage = totalMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	

	public String getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}

	public String getLegalStatus() {
		return legalStatus;
	}

	public void setLegalStatus(String legalStatus) {
		this.legalStatus = legalStatus;
	}

	public String getGovernmentResponseCode() {
		return governmentResponseCode;
	}

	public void setGovernmentResponseCode(String governmentResponseCode) {
		this.governmentResponseCode = governmentResponseCode;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}	

	public String getGovernmentResponseDescription() {
		return governmentResponseDescription;
	}

	public void setGovernmentResponseDescription(String governmentResponseDescription) {
		this.governmentResponseDescription = governmentResponseDescription;
	}	
	
}
