package com.grpretail.comfandi.monitor.trx;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Vector;

import org.ramm.jwaf.locator.ResourceLocator;

import com.asic.ac.utils.DateFormatter;
import com.asic.ac.utils.Relleno;
import com.asic.author.manager.trx.AbstractPosTransaction;
import com.asic.objetos.Respuesta;
import com.grpretail.comfandi.business.utils.BusinessLogicParam;



public class ComfandiPosTransaction extends AbstractPosTransaction{

	// Códigos de respuesta del control de transacción y comunicaciones
	//public final static String ID_APPROVED_JSON_ANSWER = "\"approved\"";
	//public final static String ID_REJECTED_JSON_ANSWER = "\"rejected\"";
	
	protected static String theCurrentServiceToken = null;
	//public static String MODULE_VERSION = "V1.0 09 Dic 2020";
	public static String MODULE_VERSION = "V1.0 06 Ene 2021";
	
	public static String CURRENT_CENTURY = "20";
	
	
	public static String theCurrentToken = null;
	private static ResourceBundle bundle_;
	// Otros atributos estáticos
	//public static final int IDCLIENTE_LENGTH			= 12;
	//public static final int TARJETA_LENGTH				= 18;
	public static final int FECHA_HORA_LENGTH			= 12;
	public static final String ID_APPL_ERROR_CODE		= "1";
	public static final String ID_APPL_SUCCESS_CODE		= "2";
	
	
	//public static final String ID_TRX_ERROR_CODE		= "1";
	public static final String ID_TRX_SUCCESS_CODE		= "0";
	public static final String ID_CTR_TRX_NO_EXISTE		= "O";
	public static final String ID_SUCCESSFUL_ANSWER    = "1";


	
	// Elementos estáticos de la clase
	protected static Hashtable aParseDatos,aParseRespuesta,aParseDataEntry,aHashRespuesta,aHashBusinessLogicParams;
	protected static Hashtable aParseProceso;
	public static final String BUSINESSSETTINGS_FILENAME	= "BusinessParams";
	public static final String SERVER_ANSWER_CODES_FILENAME = "ServerAnswerCodes";
	public static final String WS_NO_CONNECTION_REASON_MSG  = "reason: RC: 55  El recurso de red o el dispositivo especificados ya no se encuentran disponibles";
	
	/*protected static String BATCH_INPUTDIRECTORY;
	protected static String BATCH_OUTPUTDIRECTORY;
	protected static String BATCH_SEPARATOR;
	protected static String LOG_TRX_FILE;
	protected static String LOG_TRX_FILE_EXT;*/
	//protected static String CON_OUTPUT_DIRECTORY;
	//protected static String CON_SEPARATOR;
	public static final String DBSETTINGS_FILENAME	= "OtrosParametros";	
	
	private static final int[] PARSE_SOLICITUD_GENERICA = {2, // Aplicación
														   2, // Función
														   3, // Longitud de datos
														   4, // Cadena (New)
														   4, // Tienda (New)
														   6, // Número de la terminal
														   10,// Operador  (New)
														   6, // Número o consecutivo de la trx 
														   6, // Número de la factura (New-factura fiscal)
														   12,// Fecha y Hora de la solicitud de la trx
														   2  // Ultimo estado del Appl Manager
														   };
	private static final int[] PARSE_RESPUESTA_GENERICA = {
															2, //Aplicación
															2, //Función
															3, //Longitud de datos
															1, // Código de rta del Appl Manager
															1, // Código de rta de transacciones
															2 // Último estado del Appl Manager
															};
	public static final int ANCHO_SOLICITUD_GENERICA;
	protected static final int ANCHO_RESPUESTA_GENERICA;
	private static final int[] ACUM_SOLICITUD_GENERICA = new int[PARSE_SOLICITUD_GENERICA.length];
	private static final int[] ACUM_RESPUESTA_GENERICA = new int[PARSE_RESPUESTA_GENERICA.length];
	
	static {
		
		// Los elementos guía para interpretar los datos de entrada, proceso y respuesta
		// aParseDatos, aParseProceso y aParseRespuesta son propios de la combinación
		// Aplicación + Función
		/*try {
			bundle_ = ResourceLocator.get(DBSETTINGS_FILENAME);
		} catch (FailedLocatingPropertiesFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
		LOG_TRX_FILE=bundle_.getString("LOG_TRX_FILE");
		LOG_TRX_FILE_EXT=bundle_.getString("LOG_TRX_FILE_EXT");
		BATCH_INPUTDIRECTORY=bundle_.getString("CON_INPUT_DIRECTORY");
		BATCH_OUTPUTDIRECTORY=bundle_.getString("CON_OUTPUT_DIRECTORY");
		BATCH_SEPARATOR=bundle_.getString("CON_SEPARATOR");		
		*/
		int ancho_ = 0;
		for (int i = 0 ; i < PARSE_SOLICITUD_GENERICA.length ; i++) {
			ancho_ += PARSE_SOLICITUD_GENERICA[i];
			ACUM_SOLICITUD_GENERICA[i] = ancho_;
		}
		ANCHO_SOLICITUD_GENERICA = ancho_;
		
		ancho_ = 0;
		for (int i = 0 ; i < PARSE_RESPUESTA_GENERICA.length ; i++) {
			ancho_ += PARSE_RESPUESTA_GENERICA[i];
			ACUM_RESPUESTA_GENERICA[i] = ancho_;
		}
		ANCHO_RESPUESTA_GENERICA = ancho_;

		
		aParseDatos 	= new Hashtable();
		aParseRespuesta	= new Hashtable();
		aParseProceso	= new Hashtable();
		
		// Los elementos guía para construir los data entry, aParseDataEntry, son propios
		// de Aplicación + Función + Estado
		// Se usará un estado genérico -1 cuando el data entry sea independiente del estado
		
		aParseDataEntry	= new Hashtable();
		
	}

	// Información recibida de la caja en trama genérica
	private String aTotalTrama;
	private String aAplicacion;
	private String aFuncion;
	private int aLongitud;
	private String aCadena;
	private String aTienda;
	private String aNumTerminal;
	private String aOperador;
	private String aConsecutivo;	
	private String aNumFactura;
	private String aFechaHoraInicioTrx;	
	private Vector aDatos;
	
	//	Respuesta a enviar a la POS o cliente que hace el requerimiento
	protected Respuesta aRta; 
	
	// Información procesada
	private String aFechaPosteo;
	//private String aCodigoProceso;
	private String aRrn;
	private String aMedioPago;

	private String aFechaHoraInicioProceso;
	private String aFechaHoraReciboRespuesta;
	//private Vector aProceso;
	// Data entry
	private String aTipoDataEntry;
	private Vector aDataEntry;
	// Elementos de la respuesta
	private String aCodRespuesta;
	//private RespuestaServidor aRespuestaServidor;
	private String aCodControl;
	private Vector aRespuesta;
	// Estado de la trx
	private String aEstado;
	
		
	public ComfandiPosTransaction(String pTrama) throws Exception {
		aRta=new Respuesta();		
		parseTrama(pTrama);
		initializeRespuesta(pTrama.substring(0,4));
	}
	
	public void setTrama(String pTrama) throws Exception {
		System.out.println("Starting trx with data: " + pTrama);
		parseTrama(pTrama);
		//System.out.println("Después deStarting trx with data: " + pTrama);
		// Inicializa los campos del objeto rta (Parte genérica) y los de la parte no genérica los inicializa
		// con espacios en blanco.
		initializeRespuesta(pTrama.substring(0,4));
	}
		
	public ComfandiPosTransaction() throws Exception {
		aRta=new Respuesta();
	}
	
	public void parseTramaGenerica(String pTrama) throws Exception {
		setTotalTrama(pTrama);
		setAplicacion(pTrama.substring(0,ACUM_SOLICITUD_GENERICA[0]).trim());
		//System.out.println("1->"+pTrama.substring(0,ACUM_SOLICITUD_GENERICA[0]).trim());
		setFuncion(pTrama.substring(ACUM_SOLICITUD_GENERICA[0],ACUM_SOLICITUD_GENERICA[1]).trim());
		//System.out.println("2->"+pTrama.substring(0,ACUM_SOLICITUD_GENERICA[1]).trim());
		// Esta longitud es la longitud de la trama de entrada.
		setLongitud(Integer.parseInt(pTrama.substring(ACUM_SOLICITUD_GENERICA[1],ACUM_SOLICITUD_GENERICA[2]).trim()));
		setCadena(pTrama.substring(ACUM_SOLICITUD_GENERICA[2],ACUM_SOLICITUD_GENERICA[3]).trim());
		setTienda(pTrama.substring(ACUM_SOLICITUD_GENERICA[3],ACUM_SOLICITUD_GENERICA[4]).trim());
		setNumTerminal(pTrama.substring(ACUM_SOLICITUD_GENERICA[4],ACUM_SOLICITUD_GENERICA[5]).trim());
		setOperador(pTrama.substring(ACUM_SOLICITUD_GENERICA[5],ACUM_SOLICITUD_GENERICA[6]).trim());
		setConsecutivo(pTrama.substring(ACUM_SOLICITUD_GENERICA[6],ACUM_SOLICITUD_GENERICA[7]).trim());
		setNumFactura(pTrama.substring(ACUM_SOLICITUD_GENERICA[7],ACUM_SOLICITUD_GENERICA[8]).trim());
		setFechaHoraInicioTrx(pTrama.substring(ACUM_SOLICITUD_GENERICA[8],ACUM_SOLICITUD_GENERICA[9]).trim());
		// Este es el último estado del applManager que llega desde la solicitud.
		setEstado(pTrama.substring(ACUM_SOLICITUD_GENERICA[9],ACUM_SOLICITUD_GENERICA[10]).trim());
	}
	
	public String getElementTrama(int pElement) {
		String answer_ = null;
		int rangoInf_ = 0,rangoSup_ = 0;
		try {
			if (pElement < ACUM_SOLICITUD_GENERICA.length) {
				rangoInf_ = pElement == 0 ? 0 : ACUM_SOLICITUD_GENERICA[pElement - 1];
				rangoSup_ = ACUM_SOLICITUD_GENERICA[pElement];
			} else {
				int[] parseArray_ = (int[])aParseDatos.get(getAplicacion() + getFuncion());
				rangoSup_ = ANCHO_SOLICITUD_GENERICA;
				for (int i = 0 ; i <= pElement - ACUM_SOLICITUD_GENERICA.length ; i++) {
					rangoInf_ = rangoSup_;
					rangoSup_ += parseArray_[i];
				}
			}
			String trama_ = getTotalTrama();
			answer_ = trama_.substring(rangoInf_,Math.min(rangoSup_,trama_.length()));
		} catch (Exception ex) {
			answer_ = "";
		}
		return answer_;
	}
	
	public void parseTrama(String pTrama) throws Exception {
		parseTramaGenerica(pTrama);
		int position_ = ANCHO_SOLICITUD_GENERICA;
		int[] parseArray_ = (int[])aParseDatos.get(pTrama.substring(0,4));
		aDatos = new Vector();
		if (parseArray_ != null)  {
			for (int i = 0 ; i < parseArray_.length - 1; i++) {
				//System.out.println("elemento i->"+pTrama.substring(position_,position_ + parseArray_[i])+"-"+i);
				aDatos.addElement(pTrama.substring(position_,position_ + parseArray_[i]).trim());
				position_+=parseArray_[i];
			}
			if (position_ < pTrama.length()) {
					aDatos.addElement(pTrama.substring(position_,Math.min(position_ + parseArray_[parseArray_.length-1],pTrama.length())).trim());
			}			
		}
		setOtrosParam(Integer.parseInt(getAplicacion()+getFuncion()));
	}
	
	public void parseRespuesta(String pTrama) {
		try {
			int[] parseArray_ = (int[])aParseRespuesta.get(pTrama.substring(0,4));
			if (parseArray_ != null) {
				//setCodRespuesta(pTrama.substring(ACUM_RESPUESTA_GENERICA[2],ACUM_RESPUESTA_GENERICA[3]));
				// Esta colocando la tienda desde la trama de entrada.
				setCodControl(pTrama.substring(ACUM_RESPUESTA_GENERICA[3],ACUM_RESPUESTA_GENERICA[4]));
				//setEstado(pTrama.substring(ACUM_RESPUESTA_GENERICA[4],ACUM_RESPUESTA_GENERICA[5]));
				int position_ = ANCHO_RESPUESTA_GENERICA;
				aDatos = new Vector();
				for (int i = 0 ; i < parseArray_.length ; i++) {
					if (position_ < pTrama.length()) {
						setRespuestaElement(pTrama.substring(position_,Math.min(position_ + parseArray_[i],pTrama.length())),i);
					}
					position_+=parseArray_[i];
				}
			}
		} catch (Exception ex) {
		}
	}

	public Respuesta getRespuesta() {
		// Header contiene el Código de rta del Appl Manager y el Código de rta de transacciones 
 		//String Header_=aCodRespuesta + aCodControl + getEstado();
		StringBuffer respuesta_ = new StringBuffer("");
		int[] parseArray_ = (int[])aParseRespuesta.get(getAplicacion() + getFuncion());
		// Calcula la longitud de la respuesta no genérica
		int longitud_=0;
		if (parseArray_ != null) {
			for (int i = 0 ; i < parseArray_.length ; i++) {
				longitud_+=parseArray_[i];
			}
		}
		//String temp_=getAplicacion()+getFuncion()+Relleno.carga(String.valueOf(longitud_),3,'0',0,3)+Header_;
		aRta.setLongitud(Relleno.carga(String.valueOf(longitud_),3,'0',0,3));
		aRta.setRespuestaNoGenerica(getRespuestaNoGenerica());
		return	aRta;
	}
	
	public String getRespuestaNoGenerica() {
		// Header contiene el Código de rta del Appl Manager y el Código de rta de transacciones 
 
		StringBuffer respuesta_ = new StringBuffer("");
		int[] parseArray_ = (int[])aParseRespuesta.get(getAplicacion() + getFuncion());
		
		// Llena todos los campos de la respuesta no genérica
		if (parseArray_ != null) {
			for (int i = 0 ; i < parseArray_.length ; i++) {
				String str_ = i < aRespuesta.size() && aRespuesta.elementAt(i) != null ? 
					(String)aRespuesta.elementAt(i) : "";
				respuesta_.append(Relleno.carga(str_,parseArray_[i],' ',1,parseArray_[i]));
			}
		}
	
		String strRespuesta_ = respuesta_.toString();
		return	strRespuesta_;
		
	}	


	
// Inicializa el vector de la respuesta no genérica con espacios.
	
	private void initializeRespuesta(String pAppFunc) {
		// Código de rta del Appl Manager
		// Código de rta de transacciones
		//Aplicacion se coloca directamente en el parseo de la trama de entrada.
		setAplicacion(pAppFunc.substring(0,2));
		//Funcion se coloca directamente en el parseo de la trama de entrada.
		setFuncion(pAppFunc.substring(2));
		setCodRespuesta(ID_APPL_ERROR_CODE);
		setCodControl(ID_TRX_SUCCESS_CODE);
		// aRespuesta contiene los valores para los campos no genéricos de Rta.
		// El estado del Appl Manager se coloca desde directamente en el parseo de la trama de entrada. 
		//setEstado()
		aRespuesta = new Vector();
		int[] parseArray_ = (int[])aParseRespuesta.get(pAppFunc);
		if (parseArray_ != null) {
			for (int i = 0 ; i < parseArray_.length ; i++) {
				aRespuesta.add(Relleno.space(parseArray_[i]));
			}
		}
	}
	
	/*public String getRespuestaParcial(String pRespuesta1,String pRespuesta2) {
		setCodRespuesta(pRespuesta1);
		setCodControl(pRespuesta2);
		String answer_ = pRespuesta1 + pRespuesta2 + getEstado();
		int longitud_  = answer_.length();
		return	getAplicacion() +
				getFuncion() + 
				Relleno.carga(String.valueOf(longitud_),3,'0',0,3) +
				answer_;
	}*/

/*	private void initializeOtros(String pTrama,Hashtable pHash,Vector pVector) {
		int[] parseArray_ = (int[])pHash.get(pTrama);
		if (parseArray_ != null) {
			pVector = new Vector();

			for (int i = 0 ; i < parseArray_.length ; i++) {
				pVector.add(Relleno.space(parseArray_[i]));
			}
		}
	}*/
	
	/*public void asignVariosProceso(int pAF) throws Exception{
		aCodigoProceso 	= calculateCodigoProceso(pAF);
	}*/

	
	protected String getFechaHoraActual() {
		String fecha_ = DateFormatter.getActualDate() + 
			DateFormatter.getActualHour();
		return fecha_.substring(Math.max(0,fecha_.length() - FECHA_HORA_LENGTH),
			fecha_.length());
	}
	
	public void setOtrosParam(int pAF){}
	public Object execute(){
		return null;
	}
	


	/**
	 * Gets the aplicacion
	 * @return Returns a String
	 */
	public String getAplicacion() {
		return aRta.getAplicacion();
	}
	/**
	 * Sets the aplicacion
	 * @param aplicacion The aplicacion to set
	 */
	public void setAplicacion(String aplicacion) {
		aRta.setAplicacion(aplicacion);
	}


	/**
	 * Gets the funcion
	 * @return Returns a String
	 */
	public String getFuncion() {
		return aRta.getFuncion();
	}
	/**
	 * Sets the funcion
	 * @param funcion The funcion to set
	 */
	public void setFuncion(String funcion) {
		aRta.setFuncion(funcion);
	}


	/**
	 * Gets the longitud
	 * @return Returns a int
	 */
	public int getLongitud() {
		return aLongitud;
	}
	/**
	 * Sets the longitud
	 * @param longitud The longitud to set
	 */
	public void setLongitud(int longitud) {
		aLongitud = longitud;
	}


	/**
	 * Gets the numTerminal
	 * @return Returns a String
	 */
	public String getNumTerminal() {
		return aNumTerminal;
	}
	/**
	 * Sets the numTerminal
	 * @param numTerminal The numTerminal to set
	 */
	public void setNumTerminal(String numTerminal) {
		aNumTerminal = numTerminal;
	}

	/**
	 * Gets an element of any Vector
	 * @param pVector
	 * @param pElement
	 * @return Returns a String
	 */
	private String getVectorElement(Vector pVector,int pElement) {
		String answer_ = null;
		try {
			answer_ = (String)pVector.elementAt(pElement);
		} catch (Exception ex) {
			answer_ = "";
		}
		return answer_;
	}

	/**
	 * Sets an element of any Vector
	 * @param pVector
	 * @param pString
	 * @param pElement
	 */
	public void setVectorElement(Vector pVector,String pString,int pElement,Hashtable pHash) {
		try {
			//System.out.println("getAplicacion() + getFuncion()con As:"+getAplicacion()+"-" + getFuncion());
			int[] parseArray_ = (int[])pHash.get(getAplicacion() + getFuncion());
			if (parseArray_ == null) {
				pVector.set(pElement,pString);
			} else {
				pVector.set(pElement,
					Relleno.carga(pString,parseArray_[pElement],' ',1,parseArray_[pElement]));
			}
		} catch (Exception ex) {

		}
	}


	/**
	 * Gets an element of Datos
	 * @param pElement
	 * @return Returns a String
	 */
	public String getDatosElement(int pElement) {
		return getVectorElement(aDatos,pElement);
	}

	/**
	 * Sets an element of Datos
	 * @param pString
	 * @param pElement
	 */
	public void setDatosElement(String pString,int pElement) {
		setVectorElement(aDatos,pString,pElement,aParseDatos);
	}

	/**
	 * Gets an element of Proceso
	 * @param pElement
	 * @return Returns a String
	 */
	/*public String getProcesoElement(int pElement) {
		return getVectorElement(aProceso,pElement);
	}*/

	/**
	 * Sets an element of Proceso
	 * @param pString
	 * @param pElement
	 */
	/*public void setProcesoElement(String pString,int pElement) {
		setVectorElement(aProceso,pString,pElement,aParseProceso);
	}*/

	/**
	 * Gets an element of Respuesta
	 * @param pElement
	 * @return Returns a String
	 */
	public String getRespuestaElement(int pElement) {
		return getVectorElement(aRespuesta,pElement);
	}

	/**
	 * Sets an element of Respuesta
	 * @param pString
	 * @param pElement
	 */
	public void setRespuestaElement(String pString,int pElement) {
		setVectorElement(aRespuesta,pString,pElement,aParseRespuesta);
	}

	/**
	 * Gets the estado
	 * @return Returns a String
	 */
	public String getEstado() {
		return aRta.getUltEstado();
	}
	/**
	 * Sets the estado
	 * @param estado The estado to set
	 */
	private void setEstado(String estado) {
		System.out.println("Setting state: " + estado);
		aRta.setUltEstado(estado);
	}
	/**
	 * Sets the estado
	 * @param estado The estado to set
	 */
	/*public void setEstado(int estado) {
		setEstado(Manejaformato.formatoNumero(estado,"00"));
	}*/


	/**
	 * Gets the codRespuesta
	 * @return Returns a String
	 */
	public String getCodRespuesta() {
		return aRta.getCodRespuesta();
	}
	/**
	 * Sets the codRespuesta
	 * @param codRespuesta The codRespuesta to set
	 */
	public void setCodRespuesta(String codRespuesta) {
		System.out.println("Setting answer: " + codRespuesta);
		aRta.setCodRespuesta(codRespuesta);
	}


	/**
	 * Gets the codControl
	 * @return Returns a String
	 */
	public String getCodControl() {
		return aRta.getCodRtaTrans();
	}
	/**
	 * Sets the codControl
	 * @param codControl The codControl to set
	 */
	public void setCodControl(String codControl) {
		aRta.setCodRtaTrans(codControl);
	}


	/**
	 * Gets the fechaPosteo
	 * @return Returns a String
	 */
	public String getFechaPosteo() {
		return aFechaPosteo;
	}
	/**
	 * Sets the fechaPosteo
	 * @param fechaPosteo The fechaPosteo to set
	 */
	public void setFechaPosteo(String fechaPosteo) {
		aFechaPosteo = fechaPosteo;
	}


	/**
	 * Gets the codigoProceso
	 * @return Returns a String
	 */
	/*public String getCodigoProceso() {
		return aCodigoProceso;
	}*/
	/**
	 * Sets the codigoProceso
	 * @param codigoProceso The codigoProceso to set
	 */
	/*public void setCodigoProceso(String codigoProceso) {
		aCodigoProceso = codigoProceso;
	}*/


	/**
	 * Gets the rrn
	 * @return Returns a String
	 */
	public String getRrn() {
		return aRrn;
	}
	/**
	 * Sets the rrn
	 * @param rrn The rrn to set
	 */
	public void setRrn(String rrn) {
		aRrn = rrn;
	}


	/**
	 * Gets the medioPago
	 * @return Returns a String
	 */
	public String getMedioPago() {
		return aMedioPago;
	}
	/**
	 * Sets the medioPago
	 * @param medioPago The medioPago to set
	 */
	public void setMedioPago(String medioPago) {
		aMedioPago = medioPago;
	}


	/**
	 * Gets the tipoDataEntry
	 * @return Returns a String
	 */
	public String getTipoDataEntry() {
		return aTipoDataEntry;
	}
	/**
	 * Sets the tipoDataEntry
	 * @param tipoDataEntry The tipoDataEntry to set
	 */
	public void setTipoDataEntry(String tipoDataEntry) {
		aTipoDataEntry = tipoDataEntry;
	}


	/**
	 * Gets the fechaHoraInicioProceso
	 * @return Returns a String
	 */
	public String getFechaHoraInicioProceso() {
		return aFechaHoraInicioProceso;
	}
	/**
	 * Sets the fechaHoraInicioProceso
	 * @param fechaHoraInicioProceso The fechaHoraInicioProceso to set
	 */
	public void setFechaHoraInicioProceso(String fechaHoraInicioProceso) {
		aFechaHoraInicioProceso = fechaHoraInicioProceso;
	}


	/**
	 * Gets the fechaHoraReciboRespuesta
	 * @return Returns a String
	 */
	public String getFechaHoraReciboRespuesta() {
		return aFechaHoraReciboRespuesta;
	}
	/**
	 * Sets the fechaHoraReciboRespuesta
	 * @param fechaHoraReciboRespuesta The fechaHoraReciboRespuesta to set
	 */
	public void setFechaHoraReciboRespuesta(String fechaHoraReciboRespuesta) {
		aFechaHoraReciboRespuesta = fechaHoraReciboRespuesta;
	}


	/**
	 * Gets the fechaHoraInicioTrx
	 * @return Returns a String
	 */
	public String getFechaHoraInicioTrx() {
		return aFechaHoraInicioTrx;
	}
	/**
	 * Sets the fechaHoraInicioTrx
	 * @param fechaHoraInicioTrx The fechaHoraInicioTrx to set
	 */
	public void setFechaHoraInicioTrx(String fechaHoraInicioTrx) {
		aFechaHoraInicioTrx = fechaHoraInicioTrx;
	}


	/**
	 * Gets the consecutivo
	 * @return Returns a String
	 */
	public String getConsecutivo() {
		return aConsecutivo;
	}
	/**
	 * Sets the consecutivo
	 * @param consecutivo The consecutivo to set
	 */
	public void setConsecutivo(String consecutivo) {
		aConsecutivo = consecutivo;
	}


	/**
	 * Gets the totalTrama
	 * @return Returns a String
	 */
	public String getTotalTrama() {
		return aTotalTrama;
	}
	/**
	 * Sets the totalTrama
	 * @param totalTrama The totalTrama to set
	 */
	public void setTotalTrama(String totalTrama) {
		aTotalTrama = totalTrama;
	}


	/**
	 * Gets the cadena
	 * @return Returns a String
	 */
	public String getCadena() {
		int longCadena_ = 4;
		if (aCadena == null) {
			return Relleno.space(longCadena_);
		} else {
			return Relleno.carga(aCadena,longCadena_,' ',1,longCadena_);
		}
	}

	/**
	 * Gets the tienda
	 * @return Returns a String
	 */
	public String getTienda() {
		int longTienda_ = 4;
		if (aTienda == null) {
			return Relleno.space(longTienda_);
		} else {
			return Relleno.carga(aTienda,longTienda_,' ',1,longTienda_);
		}
	}

	/**
	 * Sets the cadena
	 * @param cadena The cadena to set
	 */
	public void setCadena(String cadena) {
		aCadena = cadena;
	}


	/**
	 * Sets the tienda
	 * @param tienda The tienda to set
	 */
	public void setTienda(String tienda) {
		aTienda = tienda;
	}
	
	public static BusinessLogicParam getBusinessLogicParam(String pIdParam) {
		try{
			Hashtable hash_ = getHashBusinessLogicParams();
			BusinessLogicParam answerParam_=null;
			if (hash_ != null) {
				answerParam_=(BusinessLogicParam)hash_.get(pIdParam);
			}
			if (answerParam_ == null) {
				loadBusinessParam(pIdParam);
				answerParam_=(BusinessLogicParam)getHashBusinessLogicParams().get(pIdParam);
			}
			if (answerParam_ == null) {	
				return new BusinessLogicParam("PAR_NO_FOUND","XX");
			} else {
				return answerParam_;
			}
		}catch(Exception e){		
			return new BusinessLogicParam("PAR_NO_FOUND","XX");
		}
	}	

	public static Hashtable getHashBusinessLogicParams() {

		return aHashBusinessLogicParams;
	}

	public Object getAnswer(String pIdRespuesta){
		return null;
	}

	private static void loadBusinessParam(String pParamId) {
		/*aHashBusinessLogicParams = new Hashtable();
		
		String sentencia_ = "SELECT " +
			ParametrosWas.DB_COLUM_BLP_NAME+", "+
			ParametrosWas.DB_COLUM_BLP_VALUE+" "+ 
			"FROM "+
			ParametrosWas.DB_TABLA_BUSINESS_LOGIC_PARAMS; 
		
		Vector vector_ = SqlUtils.consultaTabla(
			sentencia_,
			Handler.getCnxClassName(),
			Handler.getCnxUrl(),
			Handler.getCnxUserID(),
			Handler.getCnxPassword()
		);
		
		for (Enumeration enum_ = vector_.elements() ; enum_.hasMoreElements() ;) {
			Vector row_ = (Vector)enum_.nextElement();
			String id_ = ((String)row_.elementAt(0)).trim();
			BusinessLogicParam param_ = new BusinessLogicParam(
				id_,
				(String)row_.elementAt(1)
			);
			aHashBusinessLogicParams.put(id_,param_);
		}*/
		String paramValue_=null;										   	
		BusinessLogicParam param_=null; 
		try {
			ResourceBundle bundle_ = ResourceLocator.get(BUSINESSSETTINGS_FILENAME);
			paramValue_ = bundle_.getString(pParamId);
			if (aHashBusinessLogicParams==null)
				aHashBusinessLogicParams = new Hashtable();
			param_=new BusinessLogicParam();
			param_.setName(pParamId);
			param_.setValue(paramValue_.trim());		
			aHashBusinessLogicParams.put(pParamId,param_);
		}catch (NoSuchElementException ex1){
			System.out.println ("Elemento no definido correctamente en el archivo de parametros de negocio ");
			ex1.printStackTrace();
		}catch (Exception ex)  {
			System.out.println("ERROR: Parameter "+pParamId+" not properly defined in parameters file..." + ex);
		}		
	}		
	
	


	protected String getNumAutoriz() {
		String answer_ = null;
		
		/*
		String fecha_ = getCompleteDate(getFechaHoraInicioTrx());
		String fechaActual_ = DateFormatter.getActualDate() + 
			DateFormatter.getActualHour();
		
		String sentencia_ = "INSERT INTO " + 
			ParametrosWas.DB_TABLA_TRXAPROB + "(" + 
				ParametrosWas.DB_COLUM_FECHAPROCESO + "," + 
				ParametrosWas.DB_COLUM_FECHAACTUAL + 
			") VALUES (" + 
				"'" + fecha_ + "'," + 
				"'" + fechaActual_ + "'" + 
			")";
		
		boolean exito_ = SqlUtils.insertaRegistro(
			sentencia_,
			Handler.getCnxClassName(),
			Handler.getCnxUrl(),
			Handler.getCnxUserID(),
			Handler.getCnxPassword()
		);
		
		if (exito_) {
			sentencia_ = "SELECT " + 
				ParametrosWas.DB_COLUM_NUMAUTORIZ + 
				" FROM " + 
				ParametrosWas.DB_TABLA_TRXAPROB;
			String filtro_ = " WHERE " + 
				ParametrosWas.DB_COLUM_FECHAPROCESO + " = '" + fecha_ + "' AND " + 
				ParametrosWas.DB_COLUM_FECHAACTUAL + " = '" + fechaActual_ + "'";
				
			Vector vector_ = SqlUtils.consultaTabla(
				sentencia_ + filtro_,
				Handler.getCnxClassName(),
				Handler.getCnxUrl(),
				Handler.getCnxUserID(),
				Handler.getCnxPassword()
			);
			
			if (vector_ != null && vector_.size() > 0) {
				Vector row_ = (Vector)vector_.elementAt(0);
				answer_ = utils.Relleno.carga((String)row_.elementAt(0),
					6,'0',0,6);
			}
		}
		*/
		
		/*String sentencia_ ="SELECT " + ParametrosWas.DB_SEQ_AUTORIZ + ".NEXTVAL FROM DUAL ";
		Vector vector_ = SqlUtils.consultaTabla(
			sentencia_,
			Handler.getCnxClassName(),
			Handler.getCnxUrl(),
			Handler.getCnxUserID(),
			Handler.getCnxPassword()
		);
		if (vector_ != null && vector_.size() > 0) {
			Vector row_ = (Vector)vector_.elementAt(0);
			answer_ = Manejaformato.formatoNumero(
				Integer.parseInt((String)row_.elementAt(0)),
				"000000");
		}*/

		
		return (answer_ == null ? "      " : answer_);
	}

	/*
	protected void registrarTrx(final String pSentencia, final String pTrama) {
		Thread hilo_ = new Thread(new Runnable() {
			public void run() {
				boolean result_=SqlUtils.insertaRegistro(
					pSentencia,
					PosRequestsHandler.getCnxClassName(ParametrosWas.ID_DATABASE_TITAN),
					PosRequestsHandler.getCnxUrl(ParametrosWas.ID_DATABASE_TITAN),
					PosRequestsHandler.getCnxUserID(ParametrosWas.ID_DATABASE_TITAN),
					PosRequestsHandler.getCnxPassword(ParametrosWas.ID_DATABASE_TITAN)
				);
				System.out.println("Resultado de la trx->"+result_);
				
				if (!result_ && pTrama!=null){
					System.out.println("Trama Resultado de la trx->"+result_);
					//insertTrxInFile(pTrama);
				}
					
			}
		});
		
		hilo_.start();
	}
	*/
	
	protected void sendEmail(final String pMessage, final String pMessageId) {
		Thread hilo_ = new Thread(new Runnable() {
			public void run() {
				try{
					/*
					SimpleEmail email = new SimpleEmail();
					System.out.println("Enviando...");
					email.setHostName(MessagesToSend.getMsgSMTP(pMessageId));//Servidor SMTP
					//email.setAuthentication("celtix","seguridad");
					String ListOfTOs=MessagesToSend.getMsgTo(pMessageId);
					String totalEmail_=null;
					StringTokenizer strToken_ = new StringTokenizer(ListOfTOs,";");
					while (strToken_.hasMoreTokens()) {
						totalEmail_=strToken_.nextToken();
						email.addTo(totalEmail_.substring(0,totalEmail_.indexOf(",")), totalEmail_.substring(totalEmail_.indexOf(",")+1));			
					}
					String from_=MessagesToSend.getMsgFrom(pMessageId);
					//String from_="ciroalfonsoc@gmail.com,acadena";
					email.setFrom(from_.substring(0,from_.indexOf(",")), from_.substring(from_.indexOf(",")+1));
					email.setSubject(MessagesToSend.getMsgSubjet(pMessageId));
					String totalMessage_=MessagesToSend.getMsgMessage(pMessageId);
					totalMessage_+= pMessage;											
					email.setMsg(totalMessage_);
					//email.setSmtpPort(465);
					email.send();
					System.out.println("Mensaje enviado a "+totalEmail_);
					*/
				}catch(Exception e){
					e.printStackTrace();
				}finally{
				}
					
			}
		});
		
		hilo_.start();
	}
	
	/*protected void insertTrxInFile (String pTrama){
		FileOutputStream os_ = null;
		BufferedWriter bw_=null;		
		try{
			Calendar calendar = new GregorianCalendar();
			Date trialTime = new Date();
			calendar.setTime(trialTime);
			System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
			System.out.println("MONTH: " + Relleno.carga(""+(calendar.get(Calendar.MONTH)+1),2,'0',0,2));
			System.out.println("DAY_OF_MONTH: " + Relleno.carga(""+calendar.get(Calendar.DAY_OF_MONTH),2,'0',0,2));
			System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
			System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
						

			
			os_= new FileOutputStream(BATCH_OUTPUTDIRECTORY+BATCH_SEPARATOR+LOG_TRX_FILE+calendar.get(Calendar.YEAR)+Relleno.carga(""+(calendar.get(Calendar.MONTH)+1),2,'0',0,2)+Relleno.carga(""+calendar.get(Calendar.DAY_OF_MONTH),2,'0',0,2)+"."+LOG_TRX_FILE_EXT,true);
			bw_=new BufferedWriter(new OutputStreamWriter(os_));
			bw_.write(pTrama);
			bw_.newLine();
			bw_.flush();
			bw_.close();

		}catch(Exception e){
			//System.out.println("exception en el getString->");
			e.printStackTrace();
		}finally{
			if (bw_!=null){
				try {
					bw_.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}		
		}
	}*/


	/**
	 * @return
	 */
	public String getNumFactura() {
		return aNumFactura;
	}

	/**
	 * @return
	 */
	public String getOperador() {
		return aOperador;
	}


	/**
	 * @param string
	 */
	public void setNumFactura(String string) {
		aNumFactura = string;
	}

	/**
	 * @param string
	 */
	public void setOperador(String string) {
		aOperador = string;
	}


}

