package com.grpretail.framework.business.factory;

import com.grpretail.framework.business.exception.ServiceException;


/**
 * Project: ITSO PiggyBank Application
 * 
 * The BusinessDelegateFactory is used to create the implementation
 * instance of the PiggyBank business delegate factory.
 *  
 * @author markus.meser@ch.ibm.com
 */
public abstract class BusinessDelegateFactory_Imp implements BusinessDelegateFactory {
	
//		private static LogHelper log = LogHelper.getInstance(BusinessDelegateFactory.class);
	
	protected static BusinessDelegateFactory delegateFactory;

	/**
	 * Constructor for BusinessDelegateFactory.
	 */
	protected BusinessDelegateFactory_Imp() {
		super();
	}
  
	
	/**
	 * Used to initialize the business delegate factory.
	 * 
	 * @param factoryClassName <code>String</code> the implementing business factory class name.
	 * @exception <code>ServiceException</code> is thrown if error on factory initialization occures.
	 */
	public static void init(String factoryClassName) throws ServiceException{
		if (delegateFactory == null) {
			initFactory(factoryClassName);
		}	
	}
	
	/**
	 * Returns business delegate factory instance.
	 * 
	 * @return <code>IBusinessDelegateFactory</code> the delegate factory instance.
	 * @exception <code>ServiceException</code> is thrown if error on factory initialization occures.
	 */
	public static BusinessDelegateFactory getInstance() throws ServiceException{
		if (delegateFactory == null) {
			throw new ServiceException("please initialize factory before calling getInstance !!");
		}
		return delegateFactory;	
	}	

	private static void initFactory(String factoryClassName) throws ServiceException{
		try {			
			delegateFactory = (BusinessDelegateFactory)Class.forName(factoryClassName).newInstance();
			
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}
