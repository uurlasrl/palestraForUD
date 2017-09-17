package org.uurla.services.auth;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.uurla.services.UurlaServiceKit;
import org.uurla.services.db.entities.AppParameter;


public class PasswordEncryptionService {
	static final String propertyNameOfSalt="CONST_SEC";
	static byte[] gbSalt=null;
	public boolean authenticate(String attemptedPassword, byte[] encryptedPassword)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Encrypt the clear-text password using the same salt that was used to
		// encrypt the original password
		byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword);

		// Authentication succeeds if encrypted password that the user entered
		// is equal to the stored hash
		return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
	}

	public byte[] getEncryptedPassword(String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		// PBKDF2 with SHA-1 as the hashing algorithm. Note that the NIST
		// specifically names SHA-1 as an acceptable hashing algorithm for
		// PBKDF2
		String algorithm = "PBKDF2WithHmacSHA1";
		// SHA-1 generates 160 bit hashes, so that's what makes sense here
		int derivedKeyLength = 160;
		// Pick an iteration count that works for you. The NIST recommends at
		// least 1,000 iterations:
		// http://csrc.nist.gov/publications/nistpubs/800-132/nist-sp800-132.pdf
		// iOS 4.x reportedly uses 10,000:
		// http://blog.crackpassword.com/2010/09/smartphone-forensics-cracking-blackberry-backup-passwords/
		int iterations = 20000;
		KeySpec spec = new PBEKeySpec(password.toCharArray(), generateSalt(), iterations, derivedKeyLength);
		SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
		return f.generateSecret(spec).getEncoded();
	}

	/*
	 * ritorna il valore statico di gbSalt se diverso da null 
	 * se uguale a null legge dal DB il valore di gbSalt se esiste
	 * se non esiste genera il valore e lo salva sul DB
	 */
	public synchronized byte[] generateSalt() throws NoSuchAlgorithmException {
		if (gbSalt == null){
			
			EntityManagerFactory emf = UurlaServiceKit.getPersistenceFactoryImpl().getEmfAdmin();
			EntityManager em=emf.createEntityManager();
			AppParameter ap= (AppParameter)em.find(AppParameter.class, propertyNameOfSalt);
			if (ap == null){
				// VERY important to use SecureRandom instead of just Random
				SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
				// Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
				gbSalt = new byte[8];
				random.nextBytes(gbSalt);
				ap= new AppParameter(propertyNameOfSalt,convertToString(gbSalt));
				em.getTransaction().begin();
				em.persist(ap);
				em.getTransaction().commit();
				em.close();
			}else{
				gbSalt = convertToBytes(ap.getValue());
			}
		}
		return gbSalt;
	}
	public byte[] convertToBytes(String str){
		String str1=str.trim();
		int l=str1.length();
		byte[] result=new byte[l/3];
		for(int i=0;i<(l/3);i++){
			result[i]= (byte) ( ((str1.charAt(i*3  )-'0') * 100)+
								((str1.charAt(i*3+1)-'0') * 10 )+
								((str1.charAt(i*3+2)-'0')      ));
		}
		return result;
		
	}
	public String convertToString(byte[] bites){
		String result="";
		for (int i=0;i < bites.length ;i++){
//			int s = (int)bites[i] & 0xFF;
			String str=""+((int)bites[i] & 0xFF);
			str="000".substring(str.length())+str;
			result+=str;
			/*if(s<100){
				result+="0";
			}else{
				int t=s/100;
				s-=(t*100);
				result+=(((int)'0')+t);
			}
			if(s<10){
				result+="0";
			}else{
				int t=s/10;
				s-=(t*10);
				result+=(((int)'0')+t);
			}
			if(s==0)
				result+="0";
			else
				result+=(((int)'0')+s);
				*/
		}
		return result;
	}
}
