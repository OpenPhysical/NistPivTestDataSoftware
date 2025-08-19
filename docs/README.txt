Name   : JPIV Test Data Generator and PIV Data Loader
Release: 07/13/2007
Version: 1.0.3

Name   : PIV Data Loader
Release: 07/13/2007
Version: 1.0


This software package facilitates the creation of PIV data objects and loading them on the conformant PIV card.  The "JPIV Test Data Generator.jar" file contains compiled classes for data generation and the "PIV Data Loader.exe" file is the Data Loader application.  Both files are executables.  



--Requirements--

- A Java 1.5.xx Runtime Environment
- The BouncyCastle crypto provider, version 1.32 (bcprov-jdk15-132.jar)
- The BouncyCastle mail utilities, version 1.32 (bcmail-jdk15-132.jar)
- The Crpto++ DLL version 5.2.3


The above BouncyCastle jar files can be downloaded here:
http://www.bouncycastle.org/download/bcprov-jdk15-132.jar
http://www.bouncycastle.org/download/bcmail-jdk15-132.jar

The Crypto ++ DLL can be downloaded from http://www.cryptopp.com.


--Execution--

JPIV Test Data Generator:  The BouncyCastle jars must be in the classpath, but for ease of use, simply store them in the same directory as jpiv.jar

On Microsoft Windows systems that are appropriately configured, double-clicking the jpiv.jar icon will launch the user interface.

If that fails, from a command prompt with java in the path type:

java -jar /path/to/jpiv.jar

Data Loader:  Follow the installation instructions in "PIV_Data_Loader_Users_Guide.pdf".

--Contents--

./jpiv.jar - the executable jar file containing all the classes

./extra_files/facial_image - a CBEFF formatted facial image record

./extra_files/jks_keystore - a sample Java Keystore containing a root certificate, CA certificate, and content signing certificate.

./extra_files/minutiae - a sample CBEFF formatted minutiae record

./extra_files/publicKey - an RSA1024 example of the returned field from a pivGenerateAsymmetricKeyPair call (for Certificate generation).

./PIV Data Loader.exe - PIV Data Loader application

--Keystore--

keystore password: pivpw1
Root Certificate Alias: pivtestroot
Root Certificate Private Key Password: pivtestrootpw
Issuing CA Alias: pivtestca
Issuing CA Private Key Password: pivtestcapw
Content Signer Alias: pivtestsigner
Content Signer Private Key Password: pivtestsignerpw


*Note: The Issuing CA provided in jks_keystore is used to sign certificates.  The Content Signer provided in jks_keystore is used to sign the CHUID, biometric data elements, and the Security Object.


--Future Maintenance--

If an agency needs to customize the data generator for their purposes, they may contact NIST for the source code.

