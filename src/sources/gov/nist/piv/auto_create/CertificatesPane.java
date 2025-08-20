/*
 * Portions of this software were developed by the National Institute of Standards and Technology (NIST)
 * in the course of official duties. Under 17 U.S.C. §105, such works are not subject to copyright in
 * the United States.
 *
 * All other portions were developed by the OpenPhysical Foundation and/or project contributors,
 * and are dedicated to the public domain.
 *
 * See LICENSE.md for full terms.
 *
 * This software is provided "as is", without warranty of any kind, including implied warranties of
 * merchantability or fitness for a particular purpose.
 */

package gov.nist.piv.auto_create;

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/auto_create/CertificatesPane.class */
public class CertificatesPane extends DataPane {
  private static final long serialVersionUID = 1;

  public CertificatesPane() {
    super("Certificates");
    ListManager certSerialNumber = new ListManager("Cert Serial Number");
    certSerialNumber.setAvailable(new String[] {"1"});
    add(certSerialNumber);
    ListManager validFrom = new ListManager("Valid from", 14, 14);
    validFrom.setDescription(
        "Time and date that begins the validity of the certificate.  YYYYMMDDhhmmss");
    validFrom.setAvailable(new String[] {"20070419112233"});
    add(validFrom);
    ListManager validTo = new ListManager("Valid to", 14, 14);
    validTo.setDescription("Time and date that the the certificate expires.  YYYYMMDDhhmmss");
    validTo.setAvailable(new String[] {"20090419112233"});
    add(validTo);
    PathManager publicKeyData = new PathManager("Public Key File");
    publicKeyData.setDescription("Path to the public key data.");
    publicKeyData.setAvailable(new String[] {"./extra_files/publicKey"});
    add(publicKeyData);
    ListManager commonName = new ListManager("Common Name");
    commonName.setAvailable(new String[] {"common_name - PIV Test"});
    add(commonName);
    ListManager organization = new ListManager("Organization");
    organization.setAvailable(new String[] {"U.S. Government"});
    add(organization);
    ListManager organizationalUnit = new ListManager("Organizational Unit");
    organizationalUnit.setAvailable(new String[] {"organizational Unit - PIV Test"});
    add(organizationalUnit);
    ListManager country = new ListManager("Country");
    country.setAvailable(new String[] {"US"});
    add(country);
    ListManager httpURI = new ListManager("CRL http URI");
    httpURI.setAvailable(
        new String[] {"http://fictitious.nist.gov/fictitiousCRLdirectory/fictitiousCRL1.crl"});
    add(httpURI);
    ListManager ldapURI = new ListManager("CRL ldap URI");
    ldapURI.setAvailable(
        new String[] {
          "ldap://smime2.nist.gov/cn=Good%20CA,o=Test%20Certificates,c=US?certificateRevocationList"
        });
    add(ldapURI);
    ListManager accessHttpURI = new ListManager("Authority Info Access http URI");
    accessHttpURI.setAvailable(
        new String[] {
          "http://fictitious.nist.gov/fictitiousCertsOnlyCMSdirectory/certsIssuedToGoodCA.p7c"
        });
    add(accessHttpURI);
    ListManager accessLdapURI = new ListManager("Authority Info Access ldap URI");
    accessLdapURI.setAvailable(
        new String[] {
          "ldap://smime2.nist.gov/cn=Good%20CA,o=Test%20Certificates,c=US?cACertificate,crossCertificatePair"
        });
    add(accessLdapURI);
    ListManager accessOcspURI = new ListManager("Authority Info Access ocsp URI");
    accessOcspURI.setAvailable(new String[] {"http://fictitious.nist.gov/fictitiousOCSPLocation/"});
    add(accessOcspURI);
  }
}
