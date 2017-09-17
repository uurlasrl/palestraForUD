package org.uurla.services.odata.handler.complex;

public class FileDownloadUtility {
	
	String object;
	String type;
	String mimeType;
	
	public FileDownloadUtility() {}
	public FileDownloadUtility(String object, String type, String mimeType) {
		this.object = object;
		this.type = type;
		this.mimeType = mimeType;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
}
