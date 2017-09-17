package org.uurla.services.odata;


import javax.persistence.EntityManagerFactory;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPATransaction;
import org.apache.olingo.odata2.jpa.processor.api.OnJPAWriteContent;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.uurla.services.PersistenceFactory;
import org.uurla.services.UurlaServiceKit;
import org.uurla.services.extension.FunctionProcessingExtension;

public class UurlaJPAServiceFactory extends UurlaODataJPAServiceFactory {

	@Override
	public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
		PersistenceFactory uupf= UurlaServiceKit.getPersistenceFactoryImpl();
		EntityManagerFactory emf = uupf.getEmf();
	
		ODataJPAContext oDataJPAContext = getODataJPAContext();

		uupf.setODataJPAContext(oDataJPAContext);

		oDataJPAContext.setJPAEdmExtension(new FunctionProcessingExtension());
		oDataJPAContext.setJPAEdmMappingModel("WEB-INF/classes/JPAEDMModel01.xml");
		oDataJPAContext.setEntityManagerFactory(emf);
		oDataJPAContext.setPersistenceUnitName(uupf.getPersistenceUnitName());

		return oDataJPAContext;
	}

	@Override
	public <T extends ODataCallback> T getCallback(Class<T> callbackInterface) {
		// TODO Auto-generated method stub
		return super.getCallback(callbackInterface);
	}

	@Override
	protected void setOnWriteJPAContent(OnJPAWriteContent onJPAWriteContent) {
		// TODO Auto-generated method stub
		super.setOnWriteJPAContent(onJPAWriteContent);
	}

	@Override
	protected void setODataJPATransaction(ODataJPATransaction oDataJPATransaction) {
		// TODO Auto-generated method stub
		super.setODataJPATransaction(oDataJPATransaction);
	}

	@Override
	protected void setDetailErrors(boolean setDetailErrors) {
		// TODO Auto-generated method stub
		super.setDetailErrors(setDetailErrors);
	}

	@Override
	public ODataService createODataSingleProcessorService(EdmProvider provider, ODataSingleProcessor processor) {
		// TODO Auto-generated method stub
		
		//UurlaOdataSingleProcessor sp = new UurlaOdataSingleProcessor(processor);
		ODataService ods = super.createODataSingleProcessorService(provider, processor);
		
		PersistenceFactory uupf= UurlaServiceKit.getPersistenceFactoryImpl();
		
		try {
			uupf.setEntityDataModel((Object)ods.getEntityDataModel());
		} catch (ODataException e) {
			
			e.printStackTrace();
		}
		return ods;
	}

}
