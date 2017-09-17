package org.uurla.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ServiceLoader;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class UurlaServiceKit {
	private static PersistenceFactory pfIn;
	public static PersistenceFactory getPersistenceFactoryImpl() {
		
		if(pfIn != null) return pfIn;
		
		ServiceLoader<PersistenceFactory> PersistenceFactoryLoader = ServiceLoader.load(PersistenceFactory.class);
		PersistenceFactory pf = PersistenceFactoryLoader.iterator().next();
		Method mt;
//		PersistenceFactory pfIn;
		try {
			mt = pf.getClass().getMethod("getInstance", null);
			mt.setAccessible(true);
			pfIn = (PersistenceFactory)mt.invoke(pf, null);
		} catch (NoSuchMethodException e) {
			Logger log =Logger.getLogger(UurlaServiceKit.class);
			log.log(Level.ERROR,"Uurlapersistence factory instanciation error",e);
			return null;
		} catch (SecurityException e) {
			Logger log =Logger.getLogger(UurlaServiceKit.class);
			log.log(Level.ERROR,"Uurlapersistence factory instanciation error",e);
			return null;
		} catch (IllegalAccessException e) {
			Logger log =Logger.getLogger(UurlaServiceKit.class);
			log.log(Level.ERROR,"Uurlapersistence factory instanciation error",e);
			return null;
		} catch (IllegalArgumentException e) {
			Logger log =Logger.getLogger(UurlaServiceKit.class);
			log.log(Level.ERROR,"Uurlapersistence factory instanciation error",e);
			return null;
		} catch (InvocationTargetException e) {
			Logger log =Logger.getLogger(UurlaServiceKit.class);
			log.log(Level.ERROR,"Uurlapersistence factory instanciation error",e);
			return null;
		}
		return pfIn;
	}
}
