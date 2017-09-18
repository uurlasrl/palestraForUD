/**
 * E1Bpackev9.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mc_style.functions.soap.sap.document.sap_com;

public class E1Bpackev9  implements java.io.Serializable {
    private java.lang.String itemnoAcc;

    private java.lang.String fieldname;

    private java.lang.String currType;

    private java.lang.String currency;

    private java.lang.String currencyIso;

    private java.lang.String amtValcom;

    private java.lang.String baseUom;

    private java.lang.String baseUomIso;

    private java.lang.String quaValcom;

    public E1Bpackev9() {
    }

    public E1Bpackev9(
           java.lang.String itemnoAcc,
           java.lang.String fieldname,
           java.lang.String currType,
           java.lang.String currency,
           java.lang.String currencyIso,
           java.lang.String amtValcom,
           java.lang.String baseUom,
           java.lang.String baseUomIso,
           java.lang.String quaValcom) {
           this.itemnoAcc = itemnoAcc;
           this.fieldname = fieldname;
           this.currType = currType;
           this.currency = currency;
           this.currencyIso = currencyIso;
           this.amtValcom = amtValcom;
           this.baseUom = baseUom;
           this.baseUomIso = baseUomIso;
           this.quaValcom = quaValcom;
    }


    /**
     * Gets the itemnoAcc value for this E1Bpackev9.
     * 
     * @return itemnoAcc
     */
    public java.lang.String getItemnoAcc() {
        return itemnoAcc;
    }


    /**
     * Sets the itemnoAcc value for this E1Bpackev9.
     * 
     * @param itemnoAcc
     */
    public void setItemnoAcc(java.lang.String itemnoAcc) {
        this.itemnoAcc = itemnoAcc;
    }


    /**
     * Gets the fieldname value for this E1Bpackev9.
     * 
     * @return fieldname
     */
    public java.lang.String getFieldname() {
        return fieldname;
    }


    /**
     * Sets the fieldname value for this E1Bpackev9.
     * 
     * @param fieldname
     */
    public void setFieldname(java.lang.String fieldname) {
        this.fieldname = fieldname;
    }


    /**
     * Gets the currType value for this E1Bpackev9.
     * 
     * @return currType
     */
    public java.lang.String getCurrType() {
        return currType;
    }


    /**
     * Sets the currType value for this E1Bpackev9.
     * 
     * @param currType
     */
    public void setCurrType(java.lang.String currType) {
        this.currType = currType;
    }


    /**
     * Gets the currency value for this E1Bpackev9.
     * 
     * @return currency
     */
    public java.lang.String getCurrency() {
        return currency;
    }


    /**
     * Sets the currency value for this E1Bpackev9.
     * 
     * @param currency
     */
    public void setCurrency(java.lang.String currency) {
        this.currency = currency;
    }


    /**
     * Gets the currencyIso value for this E1Bpackev9.
     * 
     * @return currencyIso
     */
    public java.lang.String getCurrencyIso() {
        return currencyIso;
    }


    /**
     * Sets the currencyIso value for this E1Bpackev9.
     * 
     * @param currencyIso
     */
    public void setCurrencyIso(java.lang.String currencyIso) {
        this.currencyIso = currencyIso;
    }


    /**
     * Gets the amtValcom value for this E1Bpackev9.
     * 
     * @return amtValcom
     */
    public java.lang.String getAmtValcom() {
        return amtValcom;
    }


    /**
     * Sets the amtValcom value for this E1Bpackev9.
     * 
     * @param amtValcom
     */
    public void setAmtValcom(java.lang.String amtValcom) {
        this.amtValcom = amtValcom;
    }


    /**
     * Gets the baseUom value for this E1Bpackev9.
     * 
     * @return baseUom
     */
    public java.lang.String getBaseUom() {
        return baseUom;
    }


    /**
     * Sets the baseUom value for this E1Bpackev9.
     * 
     * @param baseUom
     */
    public void setBaseUom(java.lang.String baseUom) {
        this.baseUom = baseUom;
    }


    /**
     * Gets the baseUomIso value for this E1Bpackev9.
     * 
     * @return baseUomIso
     */
    public java.lang.String getBaseUomIso() {
        return baseUomIso;
    }


    /**
     * Sets the baseUomIso value for this E1Bpackev9.
     * 
     * @param baseUomIso
     */
    public void setBaseUomIso(java.lang.String baseUomIso) {
        this.baseUomIso = baseUomIso;
    }


    /**
     * Gets the quaValcom value for this E1Bpackev9.
     * 
     * @return quaValcom
     */
    public java.lang.String getQuaValcom() {
        return quaValcom;
    }


    /**
     * Sets the quaValcom value for this E1Bpackev9.
     * 
     * @param quaValcom
     */
    public void setQuaValcom(java.lang.String quaValcom) {
        this.quaValcom = quaValcom;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof E1Bpackev9)) return false;
        E1Bpackev9 other = (E1Bpackev9) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.itemnoAcc==null && other.getItemnoAcc()==null) || 
             (this.itemnoAcc!=null &&
              this.itemnoAcc.equals(other.getItemnoAcc()))) &&
            ((this.fieldname==null && other.getFieldname()==null) || 
             (this.fieldname!=null &&
              this.fieldname.equals(other.getFieldname()))) &&
            ((this.currType==null && other.getCurrType()==null) || 
             (this.currType!=null &&
              this.currType.equals(other.getCurrType()))) &&
            ((this.currency==null && other.getCurrency()==null) || 
             (this.currency!=null &&
              this.currency.equals(other.getCurrency()))) &&
            ((this.currencyIso==null && other.getCurrencyIso()==null) || 
             (this.currencyIso!=null &&
              this.currencyIso.equals(other.getCurrencyIso()))) &&
            ((this.amtValcom==null && other.getAmtValcom()==null) || 
             (this.amtValcom!=null &&
              this.amtValcom.equals(other.getAmtValcom()))) &&
            ((this.baseUom==null && other.getBaseUom()==null) || 
             (this.baseUom!=null &&
              this.baseUom.equals(other.getBaseUom()))) &&
            ((this.baseUomIso==null && other.getBaseUomIso()==null) || 
             (this.baseUomIso!=null &&
              this.baseUomIso.equals(other.getBaseUomIso()))) &&
            ((this.quaValcom==null && other.getQuaValcom()==null) || 
             (this.quaValcom!=null &&
              this.quaValcom.equals(other.getQuaValcom())));
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
        if (getItemnoAcc() != null) {
            _hashCode += getItemnoAcc().hashCode();
        }
        if (getFieldname() != null) {
            _hashCode += getFieldname().hashCode();
        }
        if (getCurrType() != null) {
            _hashCode += getCurrType().hashCode();
        }
        if (getCurrency() != null) {
            _hashCode += getCurrency().hashCode();
        }
        if (getCurrencyIso() != null) {
            _hashCode += getCurrencyIso().hashCode();
        }
        if (getAmtValcom() != null) {
            _hashCode += getAmtValcom().hashCode();
        }
        if (getBaseUom() != null) {
            _hashCode += getBaseUom().hashCode();
        }
        if (getBaseUomIso() != null) {
            _hashCode += getBaseUomIso().hashCode();
        }
        if (getQuaValcom() != null) {
            _hashCode += getQuaValcom().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(E1Bpackev9.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "E1bpackev9"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemnoAcc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ItemnoAcc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fieldname");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Fieldname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CurrType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currency");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Currency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currencyIso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CurrencyIso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amtValcom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AmtValcom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("baseUom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BaseUom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("baseUomIso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BaseUomIso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quaValcom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "QuaValcom"));
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
