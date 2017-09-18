package mc_style.functions.soap.sap.document.sap_com;

public class ZRFCACCDOCUMENTProxy implements mc_style.functions.soap.sap.document.sap_com.ZRFCACCDOCUMENT_PortType {
  private String _endpoint = null;
  private mc_style.functions.soap.sap.document.sap_com.ZRFCACCDOCUMENT_PortType zRFCACCDOCUMENT_PortType = null;
  
  public ZRFCACCDOCUMENTProxy() {
    _initZRFCACCDOCUMENTProxy();
  }
  
  public ZRFCACCDOCUMENTProxy(String endpoint) {
    _endpoint = endpoint;
    _initZRFCACCDOCUMENTProxy();
  }
  
  private void _initZRFCACCDOCUMENTProxy() {
    try {
    	

    	
      zRFCACCDOCUMENT_PortType = (new mc_style.functions.soap.sap.document.sap_com.ZRFCACCDOCUMENT_ServiceLocator()).getZRFCACCDOCUMENT();
      if (zRFCACCDOCUMENT_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zRFCACCDOCUMENT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zRFCACCDOCUMENT_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zRFCACCDOCUMENT_PortType != null)
      ((javax.xml.rpc.Stub)zRFCACCDOCUMENT_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public mc_style.functions.soap.sap.document.sap_com.ZRFCACCDOCUMENT_PortType getZRFCACCDOCUMENT_PortType() {
    if (zRFCACCDOCUMENT_PortType == null)
      _initZRFCACCDOCUMENTProxy();
    return zRFCACCDOCUMENT_PortType;
  }
  
  public void ZRfcaccdocument(mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacap09[] pe1Bpacap09, 
  		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacar09[] pe1Bpacar09, 
  		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpaccahd[] pe1Bpaccahd, 
  		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpaccait[] pe1Bpaccait, 
  		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpaccr09[] pe1Bpaccr09, 
  		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacextc[] pe1Bpacextc, 
  		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacgl09[] pe1Bpacgl09, 
  		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpache09[] pe1Bpache09, 
  		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpackec9[] pe1Bpackec9, 
  		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpackev9[] pe1Bpackev9, 
  		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacpa09[] pe1Bpacpa09, 
  		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacpc09[] pe1Bpacpc09, 
  		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacre09[] pe1Bpacre09, 
  		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpactx09[] pe1Bpactx09, 
  		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpacwt09[] pe1Bpacwt09, 
  		mc_style.functions.soap.sap.document.sap_com.Zrie1Bpparex[] pe1Bpparex, 
  		java.lang.String pidocnum, java.lang.String psndpor, 
  		java.lang.String psndprn, java.lang.String psndprt, 
  		java.lang.String zzTcode, java.lang.String zzqualImporto, 
  		javax.xml.rpc.holders.StringHolder belnr, 
  		javax.xml.rpc.holders.StringHolder bukrs, 
  		javax.xml.rpc.holders.StringHolder gjahr, 
  		mc_style.functions.soap.sap.document.sap_com.holders.Zwtbapiret2Holder msgreturn, 
  		javax.xml.rpc.holders.StringHolder pdocnum, javax.xml.rpc.holders.IntHolder presult) throws java.rmi.RemoteException{
    if (zRFCACCDOCUMENT_PortType == null)
      _initZRFCACCDOCUMENTProxy();
    zRFCACCDOCUMENT_PortType.ZRfcaccdocument(pe1Bpacap09, pe1Bpacar09, pe1Bpaccahd, pe1Bpaccait, pe1Bpaccr09, pe1Bpacextc, pe1Bpacgl09, pe1Bpache09, pe1Bpackec9, pe1Bpackev9, pe1Bpacpa09, pe1Bpacpc09, pe1Bpacre09, pe1Bpactx09, pe1Bpacwt09, pe1Bpparex, pidocnum, psndpor, psndprn, psndprt, zzTcode, zzqualImporto, belnr, bukrs, gjahr, msgreturn, pdocnum, presult);
  }
  
  
}