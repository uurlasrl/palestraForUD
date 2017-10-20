package org.uurla.services.extension;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.provider.ComplexType;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.NavigationProperty;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmSchemaView;
import org.uurla.services.PersistenceFactory;
import org.uurla.services.UurlaServiceKit;
import org.uurla.services.auth.UserDao;
import org.uurla.services.db.entities.HandleConfiguration;
import org.uurla.services.db.entities.HandleConfigurationAuth;

public class FunctionProcessingExtension implements JPAEdmExtension {

	private String getRoleCondition(PersistenceFactory pf) {
		List<String> lsOfGroups = pf.getGroups();
		String oldRolename = null;
		String roleCondition = "";
		for (String rolename : lsOfGroups) {
			if (oldRolename != null)
				roleCondition = roleCondition + "A1.rolename = '" + oldRolename + "' OR ";
			oldRolename = rolename;
		}
		if (oldRolename != null)
			roleCondition = "(" + roleCondition + "A1.rolename = '" + oldRolename + "' )";

		return roleCondition;
	}
	/*
	 * private String[] getListOfMethods(EntityManager em, Class classe, String
	 * roleCondition){ ArrayList<String> lmet=new ArrayList<String>(); Query qr2
	 * = em.
	 * createQuery("SELECT A1.method FROM HandleConfigurationAuth A1 WHERE javaClass = '"
	 * +classe.getName()+"' AND "+roleCondition
	 * +" GROUP BY A1.method",String.class ); lmet=(ArrayList<String>)
	 * qr2.getResultList();
	 * 
	 * if(lmet==null) return null;
	 */
	/*
	 * Method[] mets=classe.getMethods(); for(Method met:mets){
	 * if(met.getAnnotationsByType(EdmFunctionImport.class)!=null){
	 * lmet.add(met.getName()); } }
	 */
	/*
	 * int siz=lmet.size(); return (siz==0)?null:lmet.toArray(new String[siz]);
	 * }
	 */

	@Override
	public void extendWithOperation(JPAEdmSchemaView view) {
		
		ClassLoader cl = this.getClass().getClassLoader();
		Class classe;
		try {
			classe = cl.loadClass("org.uurla.services.odata.handler.GeneralODataFunctions");
			view.registerOperations(classe, null);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

	@Override
	public InputStream getJPAEdmMappingModelStream() {
		return null;
	}

	@Override
	public void extendJPAEdmSchema(final JPAEdmSchemaView view) {
		Schema edmSchema = view.getEdmSchema();

//Esempio di inserimento dei complex type		
		
		//ComplexType cp = getComplexType1();
		ComplexType cp2 = getComplexType2();
		List<ComplexType> lcp = edmSchema.getComplexTypes();
		if (lcp == null) {
			lcp = new ArrayList<ComplexType>();
			//lcp.add(cp);
			lcp.add(cp2);
			edmSchema.setComplexTypes(lcp);
		} else {
			//lcp.add(cp);
			lcp.add(cp2);
		}
	}

	private ComplexType getComplexType1() {
		ComplexType complexType = new ComplexType();

		List<Property> properties = new ArrayList<Property>();

		SimpleProperty property = new SimpleProperty();

		property.setName("Id");
		property.setType(EdmSimpleTypeKind.Int64);
		properties.add(property);

		property = new SimpleProperty();
		property.setName("Nome");
		property.setType(EdmSimpleTypeKind.String);
		properties.add(property);

		property = new SimpleProperty();
		property.setName("Type");
		property.setType(EdmSimpleTypeKind.String);
		// property.setFacets(new EdmFacets().getMaxLength())
		properties.add(property);

		property = new SimpleProperty();
		property.setName("Days");
		property.setType(EdmSimpleTypeKind.Double);
		properties.add(property);

		complexType.setName("TestTable");
		complexType.setProperties(properties);

		return complexType;
	}

	private ComplexType getComplexType2() {
		ComplexType complexType = new ComplexType();

		List<Property> properties = new ArrayList<Property>();

		SimpleProperty property = new SimpleProperty();
		property.setName("Object");
		property.setType(EdmSimpleTypeKind.String);
		properties.add(property);

		property = new SimpleProperty();
		property.setName("Type");
		property.setType(EdmSimpleTypeKind.String);
		properties.add(property);

		property = new SimpleProperty();
		property.setName("MimeType");
		property.setType(EdmSimpleTypeKind.String);
		properties.add(property);

		complexType.setName("FileDownloadUtility");
		complexType.setProperties(properties);

		return complexType;

	}

}
