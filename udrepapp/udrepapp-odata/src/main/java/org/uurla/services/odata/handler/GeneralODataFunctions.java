package org.uurla.services.odata.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.olingo.odata2.api.annotation.edm.EdmFacets;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.ReturnType;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.ReturnType.Type;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImportParameter;
import org.apache.olingo.odata2.api.exception.ODataException;

public class GeneralODataFunctions {

	// private EntityManager em;
	Level lev=Level.INFO;
	Logger getLogger() {
		return Logger.getLogger(this.getClass().getName());
	}

	public GeneralODataFunctions() {
		// PersistenceFactory uupf=
		// PersistenceServiceKit.getPersistenceFactoryImpl();
		// em = uupf.getEmf().createEntityManager();
	}

	@EdmFunctionImport(name = "getPdfReport03", returnType = @ReturnType(type = Type.COMPLEX, isCollection = false))
	public org.uurla.services.odata.handler.complex.FileDownloadUtility getPdfReport03(
											  		
												  /*
												   * date type
												   * 0 - se la data è presente usa il giorno
												   * 1 - se la data è presente usa la settimana
												   * 2 - se la data è presente usa il mese
												   * 3 - se la data è presente usa l'anno
												   */
											  		
											  @EdmFunctionImportParameter(name = "typefrom", facets = @EdmFacets(nullable = false)) final Integer typefrom,
											  @EdmFunctionImportParameter(name = "datefrom", facets = @EdmFacets(nullable = true)) final String datefromstr,
											  @EdmFunctionImportParameter(name = "typeto", facets = @EdmFacets(nullable = false)) final Integer typeto,
											  @EdmFunctionImportParameter(name = "dateto"  , facets = @EdmFacets(nullable = true)) final String datetostr,
											  		
											  		/*
											  		 * 0 - pdf
											  		 * 1 - foglio otf
											  		 * 2 - foglio excel
											  		 */
											  		
											  @EdmFunctionImportParameter(name = "typefile"  , facets = @EdmFacets(nullable = false)) final Integer typefile,
											  
											  @EdmFunctionImportParameter(name = "prjids"  , facets = @EdmFacets(nullable = true))  final String prjIds, 
											  
											  @EdmFunctionImportParameter(name = "resids"  , facets = @EdmFacets(nullable = true))  final String resIds 
											  ) throws ODataException{
		return null;
	}
}
