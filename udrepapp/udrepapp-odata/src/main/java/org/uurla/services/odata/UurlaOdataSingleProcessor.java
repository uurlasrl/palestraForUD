package org.uurla.services.odata;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.batch.BatchHandler;
import org.apache.olingo.odata2.api.batch.BatchResponsePart;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.commons.InlineCount;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmStructuralType;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties.ODataEntityProviderPropertiesBuilder;
import org.apache.olingo.odata2.api.ep.callback.TombstoneCallback;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotAcceptableException;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
import org.apache.olingo.odata2.api.exception.ODataNotImplementedException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataProcessor;
import org.apache.olingo.odata2.api.processor.ODataRequest;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.processor.ODataResponse.ODataResponseBuilder;
import org.apache.olingo.odata2.api.uri.ExpandSelectTreeNode;
import org.apache.olingo.odata2.api.uri.NavigationPropertySegment;
import org.apache.olingo.odata2.api.uri.PathInfo;
import org.apache.olingo.odata2.api.uri.PathSegment;
import org.apache.olingo.odata2.api.uri.SelectItem;
import org.apache.olingo.odata2.api.uri.UriInfo;
import org.apache.olingo.odata2.api.uri.UriParser;
import org.apache.olingo.odata2.api.uri.info.DeleteUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetComplexPropertyUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityLinkCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityLinkUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetLinksCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetLinksUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetFunctionImportUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetMediaResourceUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetMetadataUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetServiceDocumentUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetSimplePropertyUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;
import org.apache.olingo.odata2.core.commons.ContentType;
import org.apache.olingo.odata2.core.commons.XmlHelper;
import org.apache.olingo.odata2.core.ep.aggregator.EntityInfoAggregator;
import org.apache.olingo.odata2.core.ep.util.CircleStreamBuffer;
import org.apache.olingo.odata2.core.uri.UriParserImpl;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPATombstoneContext;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAPaging;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAProcessor;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.factory.ODataJPAFactory;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmMapping;
import org.apache.olingo.odata2.jpa.processor.core.ODataJPAProcessorDefault;
import org.apache.olingo.odata2.jpa.processor.core.access.data.JPAEntityParser;
import org.apache.olingo.odata2.jpa.processor.core.callback.JPAExpandCallBack;
import org.apache.olingo.odata2.jpa.processor.core.callback.JPATombstoneCallBack;
import org.uurla.services.PersistenceFactory;
import org.uurla.services.UurlaServiceKit;

public class UurlaOdataSingleProcessor extends ODataJPAProcessorDefault {
	
	public UurlaOdataSingleProcessor(ODataJPAContext oDataJPAContext) {
		super(oDataJPAContext);
	}

	Level lev = Level.INFO;

	@Override
	public ODataResponse readEntitySimplePropertyValue(GetSimplePropertyUriInfo uriInfo, String contentType) throws ODataException {
/*		PersistenceFactory uupf = UurlaServiceKit.getPersistenceFactoryImpl();
		String pr = uupf.getAppParameterValue(uupf.getAppName() + "-readEntitySimplePropertyValue", true);
		if (pr == null || !pr.trim().equalsIgnoreCase("true")) {
			getLogger().log(Level.INFO, "reject readEntitySimplePropertyValue");
			throw new ODataNotImplementedException();
		}
		getLogger().log(Level.INFO, "enter readEntitySimplePropertyValue");
		checkAuthListener((UriInfo) uriInfo, contentType);

		return logOdtResp((UriInfo)uriInfo,super.readEntitySimplePropertyValue(uriInfo, contentType));
		*/
		return super.readEntitySimplePropertyValue(uriInfo, contentType);
	}

	@Override
	public ODataResponse updateEntitySimplePropertyValue(PutMergePatchUriInfo uriInfo, InputStream content, String requestContentType, String contentType) throws ODataException {

			throw new ODataNotImplementedException();
	}

	@Override
	public ODataResponse deleteEntitySimplePropertyValue(DeleteUriInfo uriInfo, String contentType) throws ODataException {

		throw new ODataNotImplementedException();
	}


	@Override
	public ODataResponse readEntitySimpleProperty(GetSimplePropertyUriInfo uriInfo, String contentType) throws ODataException {

		return super.readEntitySimpleProperty(uriInfo, contentType);
	}

	@Override
	public ODataResponse updateEntitySimpleProperty(PutMergePatchUriInfo uriInfo, InputStream content, String requestContentType, String contentType) throws ODataException {

		throw new ODataNotImplementedException();
	}

	@Override
	public ODataResponse readEntityMedia(GetMediaResourceUriInfo uriInfo, String contentType) throws ODataException {
		return super.readEntityMedia(uriInfo, contentType);
	}

	@Override
	public ODataResponse updateEntityMedia(PutMergePatchUriInfo uriInfo, InputStream content, String requestContentType, String contentType) throws ODataException {
		throw new ODataNotImplementedException();
	}

	@Override
	public ODataResponse deleteEntityMedia(DeleteUriInfo uriInfo, String contentType) throws ODataException {
		throw new ODataNotImplementedException();
	}

	@Override
	public ODataResponse readEntityLinks(GetEntitySetLinksUriInfo uriInfo, String contentType) throws ODataException {
		return super.readEntityLinks(uriInfo, contentType);
	}

	@Override
	public ODataResponse countEntityLinks(GetEntitySetLinksCountUriInfo uriInfo, String contentType) throws ODataException {
		return super.countEntityLinks(uriInfo, contentType);
	}

	@Override
	public ODataResponse createEntityLink(PostUriInfo uriInfo, InputStream content, String requestContentType, String contentType) throws ODataException {
		throw new ODataNotImplementedException();
	}

	@Override
	public ODataResponse readEntityLink(GetEntityLinkUriInfo uriInfo, String contentType) throws ODataException {
		return super.readEntityLink(uriInfo, contentType);
	}

	@Override
	public ODataResponse existsEntityLink(GetEntityLinkCountUriInfo uriInfo, String contentType) throws ODataException {
		return super.existsEntityLink(uriInfo, contentType);
	}

	@Override
	public ODataResponse updateEntityLink(PutMergePatchUriInfo uriInfo, InputStream content, String requestContentType, String contentType) throws ODataException {
		throw new ODataNotImplementedException();
	}

	@Override
	public ODataResponse deleteEntityLink(DeleteUriInfo uriInfo, String contentType) throws ODataException {
		throw new ODataNotImplementedException();
	}

	@Override
	public ODataResponse readEntityComplexProperty(GetComplexPropertyUriInfo uriInfo, String contentType) throws ODataException {
		return super.readEntityComplexProperty(uriInfo, contentType);
	}

	@Override
	public ODataResponse updateEntityComplexProperty(PutMergePatchUriInfo uriInfo, InputStream content, String requestContentType, boolean merge, String contentType) throws ODataException {
		throw new ODataNotImplementedException();
	}

	/**
	 * count (va gestito l'Override)
	 */
	@Override
	public ODataResponse readEntitySet(GetEntitySetUriInfo uriInfo, String contentType) throws ODataException {
		return super.readEntitySet(uriInfo, contentType);
	}

	/**
	 * count (va gestito l'Override)
	 */
	@Override
	public ODataResponse countEntitySet(GetEntitySetCountUriInfo uriInfo, String contentType) throws ODataException {
		return super.countEntitySet(uriInfo, contentType);
	}

	/**
	 * creazione (va gestito l'Override)
	 */
	@Override
	public ODataResponse createEntity(PostUriInfo uriInfo, InputStream content, String requestContentType, String contentType) throws ODataException {
		throw new ODataNotImplementedException();
	}

	/**
	 * lettura (va gestito l'Override)
	 */
	@Override
	public ODataResponse readEntity(GetEntityUriInfo uriInfo, String contentType) throws ODataException {
		return super.readEntity(uriInfo, contentType);
	}

	/**
	 * update di una entity (va gestito l'Override)
	 */
	@Override
	public ODataResponse updateEntity(PutMergePatchUriInfo uriInfo, InputStream content, String requestContentType, boolean merge, String contentType) throws ODataException {
		throw new ODataNotImplementedException();
	}

	/**
	 * tenta di cancellare una entity (va gestito l'Override)
	 */
	@Override
	public ODataResponse deleteEntity(DeleteUriInfo uriInfo, String contentType) throws ODataException {
		throw new ODataNotImplementedException();
	}

	// Forse
	@Override
	public ODataResponse existsEntity(GetEntityCountUriInfo uriInfo, String contentType) throws ODataException {
		return super.existsEntity(uriInfo, contentType);
	}

	// il batch poi richiama gli altri processor
	@Override
	public ODataResponse executeBatch(BatchHandler handler, String contentType, InputStream content) throws ODataException {
		return super.executeBatch(handler, contentType, content);
	}
	
//	public UurlaOdataSingleProcessor(ODataJPAContext oDataJPAContext,ODataSingleProcessor ip) {
//		super(null);
		// TODO Auto-generated constructor stub
//	}


	@Override
	public List<String> getCustomContentTypes(Class<? extends ODataProcessor> processorFeature) throws ODataException {
		return super.getCustomContentTypes(processorFeature);
	}

	@Override
	public ODataResponse readMetadata(GetMetadataUriInfo uriInfo, String contentType) throws ODataException {
		return super.readMetadata(uriInfo, contentType);
	}

	Logger log = null;

	Logger getLogger() {
		if (log == null)
			log = Logger.getLogger(this.getClass().getName());
		return log;
	}

	@Override
	public void setContext(ODataContext context) {
		PersistenceFactory pf = UurlaServiceKit.getPersistenceFactoryImpl();
		pf.setOdataContext(context);
		String[] appNames;
		try {
			String path =context.getPathInfo().getRequestUri().getPath();
			getLogger().log(Level.INFO,path);
			appNames = path.split("/");// context.getPathInfo().getServiceRoot().getPath().split("/");
			pf.setAppName(appNames[2]);

			List<Locale> locs = context.getAcceptableLanguages();
			Locale myl = new Locale("en", "US");
			if (locs != null && !locs.isEmpty()) {
				myl = locs.iterator().next();
			}
			pf.setContainerLocale(myl);
		} catch (ODataException e) {
			getLogger().log(Level.SEVERE, "the service path is not available", e);
			return;
		}
		super.setContext(context);
	}



	// da non implementare
	@Override
	public BatchResponsePart executeChangeSet(BatchHandler handler, List<ODataRequest> requests) throws ODataException {
		return super.executeChangeSet(handler, requests);
	}

	@Override
	public ODataResponse executeFunctionImport(GetFunctionImportUriInfo uriInfo, String contentType) throws ODataException {
		return super.executeFunctionImport(uriInfo, contentType);
	}

	@Override
	public ODataResponse executeFunctionImportValue(GetFunctionImportUriInfo uriInfo, String contentType) throws ODataException {
		return super.executeFunctionImportValue(uriInfo, contentType);
	}

	/*
	 * si tratta della lettura del metadata
	 * 
	 * @see org.apache.olingo.odata2.api.processor.ODataSingleProcessor#
	 * readServiceDocument(org.apache.olingo.odata2.api.uri.info.
	 * GetServiceDocumentUriInfo, java.lang.String)
	 */
	@Override
	public ODataResponse readServiceDocument(GetServiceDocumentUriInfo uriInfo, String contentType) throws ODataException {
		return super.readServiceDocument(uriInfo, contentType);
	}
	
	protected ODataResponse logOdtResp(UriInfo uriInfo, ODataResponse od){
		
		return od;
	}
	private void checkAuthListener(UriInfo uriInfo, String contentType) throws ODataException {
		
	}
	/* Response for Read Entity */




}
