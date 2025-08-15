package Test;

import com.grpretail.facturaelectronica.comfandi.business.utils.vos.SendDocumentAnswer;
import com.grpretail.json.JSONAnswer;
import com.grpretail.json.JSONData;
import com.grpretail.ws.JSONServicesFacturaElectronica;

public class ManejoJSON {

	public static void main(String[] args) {
		System.out.println(grjsonlb.grpretail.version.version.MOD_VERSION);
		// TODO Auto-generated method stub
		String strJSON = "{\"UploadResponse\":{\"CUFE\":\"6afca6b5dfef34eb169b8f3c7da6bd3bb06d85540af10bc7cae0f72181b3d0d37062227d8eb3beef131c5cd73c4c4809\",\"QR\":\"TnVtRmFjOiBTRVRUMjgKRmVjRmFjOiAyMDI0LTA0LTEzCkhvckZhYzogMTQ6NDc6MDAtMDU6MDAKTml0RmFjOiA4OTAzMDMyMDgKRG9jQWRxOiA3OTcwNTUyMgpWYWxGYWM6IDk3Nzg1Ny4zNQpWYWxJdmE6IDQxODU1Ljg4ClZhbE90cm9JbTogNDQzNC43OApWYWxUb2xGYWM6IDEwMjQxNDguMDEKQ1VGRTogNmFmY2E2YjVkZmVmMzRlYjE2OWI4ZjNjN2RhNmJkM2JiMDZkODU1NDBhZjEwYmM3Y2FlMGY3MjE4MWIzZDBkMzcwNjIyMjdkOGViM2JlZWYxMzFjNWNkNzNjNGM0ODA5Cmh0dHBzOi8vY2F0YWxvZ28tdnBmZS1oYWIuZGlhbi5nb3YuY28vZG9jdW1lbnQvc2VhcmNocXI/ZG9jdW1lbnRrZXk9NmFmY2E2YjVkZmVmMzRlYjE2OWI4ZjNjN2RhNmJkM2JiMDZkODU1NDBhZjEwYmM3Y2FlMGY3MjE4MWIzZDBkMzcwNjIyMjdkOGViM2JlZWYxMzFjNWNkNzNjNGM0ODA5\",\"status\":\"ACEPTADO\",\"statuscode\":\"ACEPTADO\",\"legalStatus\":\"ACEPTADO\",\"governmentResponseCode\":\"00\",\"governmentResponseDescription\":\"[Regla: FAS14, Notificación: No corresponde a un valor valido de la lista, Regla: FAJ41, Notificación: El contenido de este elemento no corresponde al nombre y código valido., Regla: FAD09e, Notificación: Valida que fecha de generación de la factura sea igual a la fecha de firma]\",\"transactionId\":\"83a181f2e4694816958cb238838ceb04\"}}";
		//strJSON = "{\"message\":\"Factura duplicada\",\"code\":\"IR_009\",\"detail\":\"[ERROR-DI2]: El comprobante: 73373c8dbf474891840e8c6277c37d20.xml, no fue procesado. Ya existe un comprobante con ese mismo tipo y número, enviado a la entidad reguladora.\",\"QR\":\"TnVtRmFjOiBTRVRUMjcKRmVjRmFjOiAyMDI0LTA0LTEzCkhvckZhYzogMTQ6Mzg6MDAtMDU6MDAKTml0RmFjOiA4OTAzMDMyMDgKRG9jQWRxOiA3OTcwNTUyMgpWYWxGYWM6IDMxNjYzNi43NQpWYWxJdmE6IDI0NDc2LjQ3ClZhbE90cm9JbTogNDQzNC43OApWYWxUb2xGYWM6IDM0NTU0OC4wMApDVUZFOiA3OWMzNmFkYTdmMWFiNDM2YWRkNmM1NTFlNWVjMzcwZTQ0Y2EzYjczOWQ5MjkxZjc2OGNiZjFmZTY3NTYwMGQ5ZjY0YmNiMjA5NTQyYmUwMDFjMDBmZmFiNzE0MDc3YjgKaHR0cHM6Ly9jYXRhbG9nby12cGZlLWhhYi5kaWFuLmdvdi5jby9kb2N1bWVudC9zZWFyY2hxcj9kb2N1bWVudGtleT03OWMzNmFkYTdmMWFiNDM2YWRkNmM1NTFlNWVjMzcwZTQ0Y2EzYjczOWQ5MjkxZjc2OGNiZjFmZTY3NTYwMGQ5ZjY0YmNiMjA5NTQyYmUwMDFjMDBmZmFiNzE0MDc3Yjg=\",\"CUFE\":\"79c36ada7f1ab436add6c551e5ec370e44ca3b739d9291f768cbf1fe675600d9f64bcb209542be001c00ffab714077b8\"}";
		
		JSONServicesFacturaElectronica.getSendDocumentAnswer(strJSON);
		/*
		SendDocumentAnswer answer_ = new SendDocumentAnswer();
		try {
			JSONAnswer jsonAnswer_ = new JSONAnswer(strJSON);
			if (jsonAnswer_.getTags() != null && jsonAnswer_.getTags().size()>0){		
				JSONData tag_ = (JSONData)jsonAnswer_.getTags().get(0);
				JSONData resultsTag_ =  tag_.getTag("status");
				if(resultsTag_ != null)
				if (resultsTag_ != null){
					System.out.println("Status->"+resultsTag_.getValue());
					answer_.setStatus(removeQuotes(resultsTag_.getValue()));
				}else
					System.out.println("No existe tag 'status'");				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		*/
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
