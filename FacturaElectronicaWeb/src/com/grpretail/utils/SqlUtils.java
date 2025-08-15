package com.grpretail.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class SqlUtils {
	
	static class ConnectionPool extends Hashtable {
		
		public void closeConnections() {
			System.out.println("Closing connections...");
			for (Enumeration enum_ = elements() ; enum_.hasMoreElements() ;) {
				Connection conn_ = (Connection)enum_.nextElement();
				try {
					conn_.close();
				} catch (SQLException sqlEx) {
				} finally {
					remove(conn_);
				}
			}
		}

		
		protected void finalize() throws Throwable {
			closeConnections();
			super.finalize();
		}
	}
	
	private static ConnectionPool aConnectionPool;
	
	static class ParametrosConexion {
		private String aClass;
		private String aUrl;
		private String aUser;
		private String aPwd;
		
		ParametrosConexion(String pClass,String pUrl,String pUser,String pPwd) {
			aClass = pClass;
			aUrl = pUrl;
			aUser = pUser;
			aPwd = pPwd;
		}
		
		public String toString() {
			return 
				"Class: " + aClass + "/" + 
				"Url: " + aUrl + "/" + 
				"User: " + aUser + "/" + 
				"Pwd: " + aPwd + "/";
		}
		
		public boolean equals(Object pObj) {
			boolean answer_ = false;
	
			try {
				ParametrosConexion connP_ = (ParametrosConexion)pObj;
				answer_ = 
					connP_.aClass.equals(aClass) && 
					connP_.aUrl.equals(aUrl) && 
					connP_.aUser.equals(aUser) && 
					connP_.aPwd.equals(aPwd);
			} catch (Exception ex) {
			}
			return answer_;
			
		}
	}
	
	public static void executeSentence(String pSentence,String pClass,String pUrl,String pUser,String pPwd) {
		Connection conn_ = getConnection(pClass,pUrl,pUser,pPwd);
		if (conn_ != null) {
			System.out.println("Trying to execute sentence " + pSentence);
			try {
				Statement st_ = conn_.createStatement();
				st_.execute(pSentence);
				System.out.println("Succesful sentence");
			} catch (Exception ex) {
				System.out.println("Error doing sentence: " + ex);
			} finally {
				try {
					conn_.close();
				} catch (SQLException sqlEx) {
				}
				conn_ = null;
	 		}

		}

	}
	
	public static boolean insertaRegistro(String pSentence,String pClass,String pUrl,String pUser,String pPwd) {
		
		boolean answer_ = false;
		
		Connection conn_ = getConnection(pClass,pUrl,pUser,pPwd);
		
		if (conn_ != null) {
			System.out.println("Trying to execute insert " + pSentence);
			try {
				Statement st_ = conn_.createStatement();
				st_.execute(pSentence);
				System.out.println("Succesful insert");
				answer_ = true;
			} catch (Exception ex) {
				System.out.println("Error doing insert: " + ex);
				answer_ = false;
			} finally {
				try {
					conn_.close();
				} catch (SQLException sqlEx) {
				}
				conn_ = null;
	 		}

		}
		
		return answer_;
	}


	public static int actualizaTabla(String pSentence,String pClass,String pUrl,String pUser,String pPwd) {
		
		int answer_ = 0;
		Connection conn_ = getConnection(pClass,pUrl,pUser,pPwd);

		if (conn_ != null) {
			System.out.println("Trying to execute update " + pSentence);
			try {
				Statement st_ = conn_.createStatement();
				answer_ = st_.executeUpdate(pSentence);
				System.out.println("Succesful update: " + answer_ + " records");
			} catch (Exception ex) {
				System.out.println("Error doing update: " + ex);
				answer_ = -1;
			} finally {
				try {
					conn_.close();
				} catch (SQLException sqlEx) {
				}
				conn_ = null;
	 		}

		}
		
		return answer_;
	}
	
	public static Vector consultaTabla(String pSentence,String pClass,String pUrl,String pUser,String pPwd) {

		Vector answer_ = null;
		boolean cnxOk_ = true;
		Connection conn_ = getConnection(pClass,pUrl,pUser,pPwd);
		
		if (conn_ != null) {
			System.out.println("Trying to execute query " + pSentence);
	 		try {
	 			Statement st_ = conn_.createStatement();
	 			ResultSet rs_ = st_.executeQuery(pSentence);
	 			
	 			ResultSetMetaData rsm_ = rs_.getMetaData();
	 			int numCols_ = rsm_.getColumnCount();
	 			
 				answer_ = new Vector();
				
				while (rs_.next()) {
					Vector row_ = new Vector();
					for (int i = 1 ; i <= numCols_ ; i++) {
						row_.addElement(rs_.getString(i));
					}
					answer_.addElement(row_);
				}
				
				System.out.println("Succesful query: " + answer_.size() + " records");
			
	
			} catch (Exception ex) {
				answer_ = null;
				System.out.println("Error doing query: " + ex);
			} finally {
				try {
					conn_.close();
				} catch (SQLException sqlEx) {
				}
				conn_ = null;
	 		}
		}
		
		return answer_;
	}
	
	private static ConnectionPool getConnectionPool() {
		if (aConnectionPool == null) {
			aConnectionPool = new ConnectionPool();
		}
		return aConnectionPool;
	}
	
	private static Connection getConnection(String clase_,String url_,String user_,String pwd_) {
		
		Object param_ = new ParametrosConexion(clase_,url_,user_,pwd_).toString();
		
		Connection answer_ = (Connection)getConnectionPool().get(param_);
		
		if (answer_ != null) {
			boolean isClosed_ = true;
			try {
				isClosed_ = answer_.isClosed();
			} catch (SQLException sqlEx) {
				System.out.println("Exception verifying connection: " + sqlEx);
				isClosed_ = true;
			}
			if (isClosed_) {
				System.out.println("Connection exists but is closed");
				aConnectionPool.remove(answer_);
				answer_ = null;
			}
		}
		
		if (answer_ == null) {
			try {
				Class.forName(clase_);
				System.out.println("Trying to get connection...");
				answer_ = DriverManager.getConnection(url_, user_, pwd_);
				System.out.println("Putting object '" + param_ + "' connection...");
				getConnectionPool().put(param_,answer_);
			} catch (Exception ex) {
				answer_ = null;
				System.out.println("Cnx bad: " + ex);
			}
		}
		
		return answer_;
	}
	
	public static void closeConnectionPool() {
		try {
			aConnectionPool.finalize();
		} catch (Throwable th) {
		}
		aConnectionPool = null;
	}
	
}

