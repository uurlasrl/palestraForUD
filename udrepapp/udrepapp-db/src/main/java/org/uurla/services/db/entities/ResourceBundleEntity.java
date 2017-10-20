package org.uurla.services.db.entities;

import java.io.Serializable;
import java.lang.Long;
import java.util.Locale;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Messages
 *
 */
//@Entity(name="resourcebundle")
public class ResourceBundleEntity implements Serializable {
	@Transient
	public String[][] getAuth(){
		return new String[0][0];
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1482167190546825442L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "bundle",length=254)
	private String bundle="default_bundle";
	@Column(name = "contry_code",length=2)
	private String countryCode="US";
	@Column(name = "language_code",length=2)
	private String languageCode="en";
	@Column(name = "variant_code",length=50)
	private String variantCode;
	@Column(name = "string_id",length=254)
	private String stringId;
	@Column(name = "value",length=254)
	private String value;
	
	
	
	public ResourceBundleEntity(){}
	
	public ResourceBundleEntity(String appName, Locale l, String variantCode, String stringId, String value) {
		super();
		this.bundle = appName;
		
		if (l!=null){
			this.languageCode = l.getLanguage();
			this.countryCode = l.getCountry();
		}
		this.variantCode = variantCode;
		this.stringId = stringId;
		this.value = value;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBundle() {
		return bundle;
	}
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getLanguageCode() {
		return languageCode;
	}
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	public String getVariantCode() {
		return variantCode;
	}
	public void setVariantCode(String variantCode) {
		this.variantCode = variantCode;
	}
	public String getStringId() {
		return stringId;
	}
	public void setStringId(String stringId) {
		this.stringId = stringId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	

}
