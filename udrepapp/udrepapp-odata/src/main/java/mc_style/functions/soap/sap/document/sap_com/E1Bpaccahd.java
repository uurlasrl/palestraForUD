/**
 * E1Bpaccahd.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mc_style.functions.soap.sap.document.sap_com;

public class E1Bpaccahd  implements java.io.Serializable {
    private java.lang.String docNo;

    private java.lang.String docTypeCa;

    private java.lang.String resKey;

    private java.lang.String fikey;

    private java.lang.String paymentFormRef;

    public E1Bpaccahd() {
    }

    public E1Bpaccahd(
           java.lang.String docNo,
           java.lang.String docTypeCa,
           java.lang.String resKey,
           java.lang.String fikey,
           java.lang.String paymentFormRef) {
           this.docNo = docNo;
           this.docTypeCa = docTypeCa;
           this.resKey = resKey;
           this.fikey = fikey;
           this.paymentFormRef = paymentFormRef;
    }


    /**
     * Gets the docNo value for this E1Bpaccahd.
     * 
     * @return docNo
     */
    public java.lang.String getDocNo() {
        return docNo;
    }


    /**
     * Sets the docNo value for this E1Bpaccahd.
     * 
     * @param docNo
     */
    public void setDocNo(java.lang.String docNo) {
        this.docNo = docNo;
    }


    /**
     * Gets the docTypeCa value for this E1Bpaccahd.
     * 
     * @return docTypeCa
     */
    public java.lang.String getDocTypeCa() {
        return docTypeCa;
    }


    /**
     * Sets the docTypeCa value for this E1Bpaccahd.
     * 
     * @param docTypeCa
     */
    public void setDocTypeCa(java.lang.String docTypeCa) {
        this.docTypeCa = docTypeCa;
    }


    /**
     * Gets the resKey value for this E1Bpaccahd.
     * 
     * @return resKey
     */
    public java.lang.String getResKey() {
        return resKey;
    }


    /**
     * Sets the resKey value for this E1Bpaccahd.
     * 
     * @param resKey
     */
    public void setResKey(java.lang.String resKey) {
        this.resKey = resKey;
    }


    /**
     * Gets the fikey value for this E1Bpaccahd.
     * 
     * @return fikey
     */
    public java.lang.String getFikey() {
        return fikey;
    }


    /**
     * Sets the fikey value for this E1Bpaccahd.
     * 
     * @param fikey
     */
    public void setFikey(java.lang.String fikey) {
        this.fikey = fikey;
    }


    /**
     * Gets the paymentFormRef value for this E1Bpaccahd.
     * 
     * @return paymentFormRef
     */
    public java.lang.String getPaymentFormRef() {
        return paymentFormRef;
    }


    /**
     * Sets the paymentFormRef value for this E1Bpaccahd.
     * 
     * @param paymentFormRef
     */
    public void setPaymentFormRef(java.lang.String paymentFormRef) {
        this.paymentFormRef = paymentFormRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof E1Bpaccahd)) return false;
        E1Bpaccahd other = (E1Bpaccahd) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.docNo==null && other.getDocNo()==null) || 
             (this.docNo!=null &&
              this.docNo.equals(other.getDocNo()))) &&
            ((this.docTypeCa==null && other.getDocTypeCa()==null) || 
             (this.docTypeCa!=null &&
              this.docTypeCa.equals(other.getDocTypeCa()))) &&
            ((this.resKey==null && other.getResKey()==null) || 
             (this.resKey!=null &&
              this.resKey.equals(other.getResKey()))) &&
            ((this.fikey==null && other.getFikey()==null) || 
             (this.fikey!=null &&
              this.fikey.equals(other.getFikey()))) &&
            ((this.paymentFormRef==null && other.getPaymentFormRef()==null) || 
             (this.paymentFormRef!=null &&
              this.paymentFormRef.equals(other.getPaymentFormRef())));
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
        if (getDocNo() != null) {
            _hashCode += getDocNo().hashCode();
        }
        if (getDocTypeCa() != null) {
            _hashCode += getDocTypeCa().hashCode();
        }
        if (getResKey() != null) {
            _hashCode += getResKey().hashCode();
        }
        if (getFikey() != null) {
            _hashCode += getFikey().hashCode();
        }
        if (getPaymentFormRef() != null) {
            _hashCode += getPaymentFormRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(E1Bpaccahd.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "E1bpaccahd"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DocNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docTypeCa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DocTypeCa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ResKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fikey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Fikey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentFormRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PaymentFormRef"));
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
