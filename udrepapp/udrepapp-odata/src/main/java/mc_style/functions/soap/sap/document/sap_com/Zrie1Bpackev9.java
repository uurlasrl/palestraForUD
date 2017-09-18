/**
 * Zrie1Bpackev9.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mc_style.functions.soap.sap.document.sap_com;

public class Zrie1Bpackev9  implements java.io.Serializable {
    private int znum;

    private mc_style.functions.soap.sap.document.sap_com.E1Bpackev9 sdata;

    public Zrie1Bpackev9() {
    }

    public Zrie1Bpackev9(
           int znum,
           mc_style.functions.soap.sap.document.sap_com.E1Bpackev9 sdata) {
           this.znum = znum;
           this.sdata = sdata;
    }


    /**
     * Gets the znum value for this Zrie1Bpackev9.
     * 
     * @return znum
     */
    public int getZnum() {
        return znum;
    }


    /**
     * Sets the znum value for this Zrie1Bpackev9.
     * 
     * @param znum
     */
    public void setZnum(int znum) {
        this.znum = znum;
    }


    /**
     * Gets the sdata value for this Zrie1Bpackev9.
     * 
     * @return sdata
     */
    public mc_style.functions.soap.sap.document.sap_com.E1Bpackev9 getSdata() {
        return sdata;
    }


    /**
     * Sets the sdata value for this Zrie1Bpackev9.
     * 
     * @param sdata
     */
    public void setSdata(mc_style.functions.soap.sap.document.sap_com.E1Bpackev9 sdata) {
        this.sdata = sdata;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Zrie1Bpackev9)) return false;
        Zrie1Bpackev9 other = (Zrie1Bpackev9) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.znum == other.getZnum() &&
            ((this.sdata==null && other.getSdata()==null) || 
             (this.sdata!=null &&
              this.sdata.equals(other.getSdata())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getZnum();
        if (getSdata() != null) {
            _hashCode += getSdata().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Zrie1Bpackev9.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "Zrie1bpackev9"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("znum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Znum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sdata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Sdata"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "E1bpackev9"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
