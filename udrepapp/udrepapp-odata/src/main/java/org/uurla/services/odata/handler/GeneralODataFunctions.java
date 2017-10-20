package org.uurla.services.odata.handler;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.xml.rpc.holders.IntHolder;
import javax.xml.rpc.holders.StringHolder;

import org.apache.olingo.odata2.api.annotation.edm.EdmFacets;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.ReturnType;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.ReturnType.Type;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImportParameter;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.uurla.udrepapp.TestTable;


import mc_style.functions.soap.sap.document.sap_com.E1Bpache09;
import mc_style.functions.soap.sap.document.sap_com.ZRFCACCDOCUMENTProxy;
import mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacap09;
import mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacar09;
import mc_style.functions.soap.sap.document.sap_com.Zrie1Bpaccahd;
import mc_style.functions.soap.sap.document.sap_com.Zrie1Bpaccait;
import mc_style.functions.soap.sap.document.sap_com.Zrie1Bpaccr09;
import mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacextc;
import mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacgl09;
import mc_style.functions.soap.sap.document.sap_com.Zrie1Bpache09;
import mc_style.functions.soap.sap.document.sap_com.Zrie1Bpackec9;
import mc_style.functions.soap.sap.document.sap_com.Zrie1Bpackev9;
import mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacpa09;
import mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacpc09;
import mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacre09;
import mc_style.functions.soap.sap.document.sap_com.Zrie1Bpactx09;
import mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacwt09;
import mc_style.functions.soap.sap.document.sap_com.Zrie1Bpparex;
import mc_style.functions.soap.sap.document.sap_com.holders.Zwtbapiret2Holder;

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
	@EdmFunctionImport(name = "callWs", returnType = @ReturnType(type = Type.COMPLEX, isCollection = false))
	public org.uurla.services.odata.handler.complex.FileDownloadUtility callWs() throws RemoteException{
		

		
		// genero i dati da trasmettere
		E1Bpache09 sdata = new E1Bpache09("", "", "", "RFBU", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
		Zrie1Bpache09 _pe1Bpache09 = new Zrie1Bpache09(1,sdata);
		javax.xml.rpc.holders.StringHolder belnr=new StringHolder(); 
		javax.xml.rpc.holders.StringHolder bukrs=new StringHolder(); 
		javax.xml.rpc.holders.StringHolder gjahr=new StringHolder(); 
		mc_style.functions.soap.sap.document.sap_com.holders.Zwtbapiret2Holder msgreturn = new Zwtbapiret2Holder(); 
		javax.xml.rpc.holders.StringHolder pdocnum=new StringHolder();
		javax.xml.rpc.holders.IntHolder presult=new IntHolder();
		
		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacap09[] pe1Bpacap09=new Zrie1Bpacap09[0];
		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacar09[] pe1Bpacar09=new Zrie1Bpacar09[0];
		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpaccahd[] pe1Bpaccahd=new Zrie1Bpaccahd[0];
		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpaccait[] pe1Bpaccait=new Zrie1Bpaccait[0];
		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpaccr09[] pe1Bpaccr09=new Zrie1Bpaccr09[0];
		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacextc[] pe1Bpacextc=new Zrie1Bpacextc[0];
		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacgl09[] pe1Bpacgl09=new Zrie1Bpacgl09[0];
		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpache09[] pe1Bpache09=new Zrie1Bpache09[1]; 
		pe1Bpache09[0]=_pe1Bpache09;
		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpackec9[] pe1Bpackec9=new Zrie1Bpackec9[0];
		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpackev9[] pe1Bpackev9=new Zrie1Bpackev9[0];
		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacpa09[] pe1Bpacpa09=new Zrie1Bpacpa09[0];
		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacpc09[] pe1Bpacpc09=new Zrie1Bpacpc09[0];
		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacre09[] pe1Bpacre09=new Zrie1Bpacre09[0];
		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpactx09[] pe1Bpactx09=new Zrie1Bpactx09[0];
		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacwt09[] pe1Bpacwt09=new Zrie1Bpacwt09[0];
		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpparex[] pe1Bpparex=new Zrie1Bpparex[0];

		
		

				
	// Replace 'user' and 'password' by the actual values
		String authorizationHeader = "Basic " + org.apache.cxf.common.util.Base64Utility.encode("CRU_DEVG:centova".getBytes());

		// proxies
		
		
		ZRFCACCDOCUMENTProxy zs= new ZRFCACCDOCUMENTProxy();

//		WebClient.client(zs).header("Authorization", authorizationHeader);

		
		
				
		String ep=zs.getEndpoint();

		
		zs.ZRfcaccdocument(
				pe1Bpacap09, 
				pe1Bpacar09, 
				pe1Bpaccahd, 
				pe1Bpaccait, pe1Bpaccr09, pe1Bpacextc, pe1Bpacgl09, 
				pe1Bpache09, pe1Bpackec9, pe1Bpackev9, 
				pe1Bpacpa09, pe1Bpacpc09, pe1Bpacre09, pe1Bpactx09, pe1Bpacwt09, pe1Bpparex,
				"", "EDI_SNDPOR", "SLSCRDIG", "LS", "Z", "Z", belnr, bukrs, gjahr, msgreturn, pdocnum, presult);
		
		//HelloService hello = new HelloService();
		//HelloPortType helloPort = cliente.getHelloPort();
		
		
/*		
 * org.apache.cxf.endpoint.Client client = ClientProxyImpl.getClient(helloPort);
		
		HTTPConduit http = (HTTPConduit) client.getConduit();
		http.getClient().setProxyServer("proxy");
		http.getClient().setProxyServerPort(8080);
		http.getProxyAuthorization().setUserName("user proxy");
		http.getProxyAuthorization().setPassword("password proxy");
	*/	

		return null;
}
	@EdmFunctionImport(name = "getPdfReport03", returnType = @ReturnType(type = Type.COMPLEX, isCollection = true))
	public java.util.List<TestTable> getPdfReport03(
											  		
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
		
		
		TestTable t = new TestTable(1, "nome1", "tipo21", 1.75);
		ArrayList<TestTable> at=new ArrayList<TestTable>();
		at.add(t);
		t = new TestTable(2, "nome2", "tipo1", 2.75);
		at.add(t);
		return at;
	}
}
