package com.grpretail.utils;

import java.lang.reflect.Method;

public class RunTimeOut implements Runnable {
	
	private Object aObj;
	private Method aMethod;
	private Object[] aArg;
	private int aRunMethod = -1;
	private Object aReturn;
	
	public static final int STEP_DEFAULT = 1000;
	
	private RunTimeOut(Object pObj, Method pMethod, Object[] pArg) {
		aObj = pObj;
		aMethod = pMethod;
		aArg = pArg;
	}
	
	public void run() {
		try {
			if (aMethod.getReturnType() != null) {
				Class[] class_ = aMethod.getParameterTypes();
				aReturn = aMethod.invoke(aObj,aArg);
			} else {
				aMethod.invoke(aObj,aArg);
			}
			
			aRunMethod = 0;
		} catch (Exception ex) {
			System.out.println("Ex running method: " + ex);
			aRunMethod = 1;
		}
	}
	
	public static Object[] execute(Class pClass, String pMethod, Object[] pArg) {
		return execute(pClass,pMethod,pArg,-1);
	}
	
	public static Object[] execute(Class pClass, String pMethod, Object[] pArg, int pTimeOut,Object pObj) {
		return execute(pClass, pMethod, pArg, pTimeOut,pObj,STEP_DEFAULT);
	}

	public static Object[] execute(Class pClass, String pMethod, Object[] pArg, int pTimeOut,Object pObj,int pStep) {
		int answer_ = -1;
		Object obj_ = null;
		
		Class[] pTypes_;
		if (pArg == null) {
			pTypes_ = null;
		} else {
			pTypes_ = getPrimitiveClassArray(pArg);
		}
		
		Method method_ = null;
		int triesCounter_ = 0;
		while (method_ == null && triesCounter_ < 2) {
			try {
				method_ = pClass.getMethod(pMethod,pTypes_);
			} catch (NoSuchMethodException ex) {
				if (triesCounter_ == 0) {
					pTypes_ = getClassArray(pArg);
				}
				method_ = null;
			}
			triesCounter_++;
		}
		
		if (method_ != null) {
		
			RunTimeOut run_ = new RunTimeOut(pObj == null ? pClass : pObj,method_,pArg);
			Thread thread_ = new Thread(run_);
	
			if (pTimeOut > 0) {
				thread_.start();
				int milsElapsed_ = 0;
		
				System.out.print("trying to execute " + pMethod + " time out = " + pTimeOut);
				while (run_.aRunMethod == -1 && milsElapsed_ <= pTimeOut*1000) {
					if (run_.aRunMethod == -1) {
						try {
							Thread.currentThread().sleep(pStep);
						} catch (Exception ex) {}
						milsElapsed_ += pStep;
					}
				}
				System.out.println("");
			} else {
				run_.run();
			}
			answer_ = run_.aRunMethod;
			obj_ = run_.aReturn;
			if (answer_ == -1) {
				System.out.println("Timeout occurred!!!");
				try {
					thread_.interrupt();
				} catch (Exception ex) {}
				obj_ = null;
			} else if (answer_ == 0) {
				System.out.println("method executed succesfully");
			}
			run_ = null;
		} else {
			System.out.println("Method " + pMethod + " not found");
		}
		return new Object[] {new Integer(answer_),obj_};

	}
	
	public static Object[] execute(Object pObj, String pMethod, Object[] pArg, int pTimeOut) {
		return execute(pObj, pMethod, pArg, pTimeOut, STEP_DEFAULT);
	}

	public static Object[] execute(Object pObj, String pMethod, Object[] pArg, int pTimeOut,int pStep) {
		Class class_ = pObj.getClass();
		return execute(class_,pMethod,pArg,pTimeOut,pObj,pStep);
	}
	
	public static Object[] execute(Class pClass, String pMethod, Object[] pArg, int pTimeOut) {
		return execute(pClass,pMethod,pArg,pTimeOut,null);
	}
	
	private static Class primitiveType(Class wrapType) {
		if (wrapType.equals(Integer.class)) {
			return Integer.TYPE;
		} else if (wrapType.equals(Long.class)) {
			return Long.TYPE;
		} else if (wrapType.equals(Float.class)) {
			return Float.TYPE;
		} else if (wrapType.equals(Double.class)) {
			return Double.TYPE;
		} else if (wrapType.equals(Boolean.class)) {
			return Boolean.TYPE;
		} else if (wrapType.equals(Byte.class)) {
			return Byte.TYPE;
		} else if (wrapType.equals(Character.class)) {
			return Character.TYPE;
		} else return wrapType;
	}
	
	private static Class[] getPrimitiveClassArray(Object[] pObj) {
		Class[] class_ = new Class[pObj.length];
		for (int i = 0 ; i < pObj.length ; i++) {
			class_[i] = primitiveType(pObj[i].getClass());
		}
		return class_;
	}

	private static Class[] getClassArray(Object[] pObj) {
		Class[] class_ = new Class[pObj.length];
		for (int i = 0 ; i < pObj.length ; i++) {
			class_[i] = pObj[i].getClass();
		}
		return class_;
	}
	
}
