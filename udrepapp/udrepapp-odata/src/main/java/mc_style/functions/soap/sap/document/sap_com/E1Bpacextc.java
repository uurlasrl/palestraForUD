/**
 * E1Bpacextc.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mc_style.functions.soap.sap.document.sap_com;

public class E1Bpacextc  implements java.io.Serializable {
    private java.lang.String field1;

    private java.lang.String field2;

    private java.lang.String field3;

    private java.lang.String field4;

    public E1Bpacextc() {
    }

    public E1Bpacextc(
           java.lang.String field1,
           java.lang.String field2,
           java.lang.String field3,
           java.lang.String field4) {
           this.field1 = field1;
           this.field2 = field2;
           this.field3 = field3;
           this.field4 = field4;
    }


    /**
     * Gets the field1 value for this E1Bpacextc.
     * 
     * @return field1
     */
    public java.lang.String getField1() {
        return field1;
    }


    /**
     * Sets the field1 value for this E1Bpacextc.
     * 
     * @param field1
     */
    public void setField1(java.lang.String field1) {
        this.field1 = field1;
    }


    /**
     * Gets the field2 value for this E1Bpacextc.
     * 
     * @return field2
     */
    public java.lang.String getField2() {
        return field2;
    }


    /**
     * Sets the field2 value for this E1Bpacextc.
     * 
     * @param field2
     */
    public void setField2(java.lang.String field2) {
        this.field2 = field2;
    }


    /**
     * Gets the field3 value for this E1Bpacextc.
     * 
     * @return field3
     */
    public java.lang.String getField3() {
        return field3;
    }


    /**
     * Sets the field3 value for this E1Bpacextc.
     * 
     * @param field3
     */
    public void setField3(java.lang.String field3) {
        this.field3 = field3;
    }


    /**
     * Gets the field4 value for this E1Bpacextc.
     * 
     * @return field4
     */
    public java.lang.String getField4() {
        return field4;
    }


    /**
     * Sets the field4 value for this E1Bpacextc.
     * 
     * @param field4
     */
    public void setField4(java.lang.String field4) {
        this.field4 = field4;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof E1Bpacextc)) return false;
        E1Bpacextc other = (E1Bpacextc) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.field1==null && other.getField1()==null) || 
             (this.field1!=null &&
              this.field1.equals(other.getField1()))) &&
            ((this.field2==null && other.getField2()==null) || 
             (this.field2!=null &&
              this.field2.equals(other.getField2()))) &&
            ((this.field3==null && other.getField3()==null) || 
             (this.field3!=null &&
              this.field3.equals(other.getField3()))) &&
            ((this.field4==null && other.getField4()==null) || 
             (this.field4!=null &&
              this.field4.equals(other.getField4())));
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
        if (getField1() != null) {
            _hashCode += getField1().hashCode();
        }
        if (getField2() != null) {
            _hashCode += getField2().hashCode();
        }
        if (getField3() != null) {
            _hashCode += getField3().hashCode();
        }
        if (getField4() != null) {
            _hashCode += getField4().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(E1Bpacextc.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "E1bpacextc"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("field1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Field1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("field2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Field2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("field3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Field3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("field4");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Field4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
