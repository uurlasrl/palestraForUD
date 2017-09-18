/**
 * E1Bpackec9.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mc_style.functions.soap.sap.document.sap_com;

public class E1Bpackec9  implements java.io.Serializable {
    private java.lang.String itemnoAcc;

    private java.lang.String fieldname;

    private java.lang.String character;

    public E1Bpackec9() {
    }

    public E1Bpackec9(
           java.lang.String itemnoAcc,
           java.lang.String fieldname,
           java.lang.String character) {
           this.itemnoAcc = itemnoAcc;
           this.fieldname = fieldname;
           this.character = character;
    }


    /**
     * Gets the itemnoAcc value for this E1Bpackec9.
     * 
     * @return itemnoAcc
     */
    public java.lang.String getItemnoAcc() {
        return itemnoAcc;
    }


    /**
     * Sets the itemnoAcc value for this E1Bpackec9.
     * 
     * @param itemnoAcc
     */
    public void setItemnoAcc(java.lang.String itemnoAcc) {
        this.itemnoAcc = itemnoAcc;
    }


    /**
     * Gets the fieldname value for this E1Bpackec9.
     * 
     * @return fieldname
     */
    public java.lang.String getFieldname() {
        return fieldname;
    }


    /**
     * Sets the fieldname value for this E1Bpackec9.
     * 
     * @param fieldname
     */
    public void setFieldname(java.lang.String fieldname) {
        this.fieldname = fieldname;
    }


    /**
     * Gets the character value for this E1Bpackec9.
     * 
     * @return character
     */
    public java.lang.String getCharacter() {
        return character;
    }


    /**
     * Sets the character value for this E1Bpackec9.
     * 
     * @param character
     */
    public void setCharacter(java.lang.String character) {
        this.character = character;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof E1Bpackec9)) return false;
        E1Bpackec9 other = (E1Bpackec9) obj;
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
            ((this.character==null && other.getCharacter()==null) || 
             (this.character!=null &&
              this.character.equals(other.getCharacter())));
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
        if (getCharacter() != null) {
            _hashCode += getCharacter().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(E1Bpackec9.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "E1bpackec9"));
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
        elemField.setFieldName("character");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Character"));
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
