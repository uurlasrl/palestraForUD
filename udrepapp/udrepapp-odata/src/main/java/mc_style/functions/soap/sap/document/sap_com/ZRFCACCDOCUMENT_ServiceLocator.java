/**
 * ZRFCACCDOCUMENT_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mc_style.functions.soap.sap.document.sap_com;

public class ZRFCACCDOCUMENT_ServiceLocator extends org.apache.axis.client.Service implements mc_style.functions.soap.sap.document.sap_com.ZRFCACCDOCUMENT_Service {

    public ZRFCACCDOCUMENT_ServiceLocator() {
    }


    public ZRFCACCDOCUMENT_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ZRFCACCDOCUMENT_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ZRFCACCDOCUMENT
    private java.lang.String ZRFCACCDOCUMENT_address = "http://saprds01.umbriadigitale.it:8001/sap/bc/srt/rfc/sap/zrfcaccdocument/200/zrfcaccdocument/zrfcaccdocument";

    public java.lang.String getZRFCACCDOCUMENTAddress() {
        return ZRFCACCDOCUMENT_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ZRFCACCDOCUMENTWSDDServiceName = "ZRFCACCDOCUMENT";

    public java.lang.String getZRFCACCDOCUMENTWSDDServiceName() {
        return ZRFCACCDOCUMENTWSDDServiceName;
    }

    public void setZRFCACCDOCUMENTWSDDServiceName(java.lang.String name) {
        ZRFCACCDOCUMENTWSDDServiceName = name;
    }

    public mc_style.functions.soap.sap.document.sap_com.ZRFCACCDOCUMENT_PortType getZRFCACCDOCUMENT() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ZRFCACCDOCUMENT_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getZRFCACCDOCUMENT(endpoint);
    }

    public mc_style.functions.soap.sap.document.sap_com.ZRFCACCDOCUMENT_PortType getZRFCACCDOCUMENT(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            mc_style.functions.soap.sap.document.sap_com.ZRFCACCDOCUMENT_BindingStub _stub = new mc_style.functions.soap.sap.document.sap_com.ZRFCACCDOCUMENT_BindingStub(portAddress, this);
            _stub.setPortName(getZRFCACCDOCUMENTWSDDServiceName());

            _stub.setUsername("CRU_DEVG");
            _stub.setPassword("centova");
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setZRFCACCDOCUMENTEndpointAddress(java.lang.String address) {
        ZRFCACCDOCUMENT_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (mc_style.functions.soap.sap.document.sap_com.ZRFCACCDOCUMENT_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                mc_style.functions.soap.sap.document.sap_com.ZRFCACCDOCUMENT_BindingStub _stub = new mc_style.functions.soap.sap.document.sap_com.ZRFCACCDOCUMENT_BindingStub(new java.net.URL(ZRFCACCDOCUMENT_address), this);
                _stub.setPortName(getZRFCACCDOCUMENTWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ZRFCACCDOCUMENT".equals(inputPortName)) {
            return getZRFCACCDOCUMENT();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZRFCACCDOCUMENT");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZRFCACCDOCUMENT"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ZRFCACCDOCUMENT".equals(portName)) {
            setZRFCACCDOCUMENTEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
