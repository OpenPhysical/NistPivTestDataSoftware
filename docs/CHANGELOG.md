# Changelog

All notable changes to the NIST PIV Test Data Software are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.0.0] - 2025-08-20

### Major Modernization Release

This release represents a complete modernization of the original NIST PIV Test Data Software (version 1.0.3, July 2007), bringing it into compatibility with modern Java environments and security standards.

### Added
- **Modern Build System**: Gradle build system with proper dependency management
- **Professional Logging**: Log4j2 integration for structured logging and debugging
- **Enhanced Security**: Updated BouncyCastle cryptographic libraries to version 1.81
- **Cross-Platform Support**: Improved compatibility across Windows, macOS, and Linux
- **Auto-Configuration**: Automatic loading of default PIV configuration at startup
- **Debug Capabilities**: Enhanced debugging output for troubleshooting keystore and configuration issues
- **Professional Structure**: Reorganized codebase with standard directory layout
- **Distribution Packaging**: Professional packaging and release artifacts
- **Code Formatting**: Integrated Spotless for consistent code formatting
- **UI Improvements**: 
  - Increased main window size by 20% for better visibility (840x852)
  - Doubled output console window size (300px height)
  - Added debug message for keystore loading operations

### Changed
- **Java Compatibility**: Updated from Java 1.5 to Java 8+ requirements
- **BouncyCastle Libraries**: Upgraded from v1.32/1.38 (2007) to v1.81 (2024)
  - `bcprov-jdk15-132.jar` → `bcprov-jdk18on-1.81`
  - `bcmail-jdk15-1.38.jar` → `bcpkix-jdk18on-1.81` + `bcutil-jdk18on-1.81`
- **API Modernization**: Updated deprecated cryptographic APIs
  - Replaced `sun.security.x509` with `javax.security.auth.x500.X500Principal`
  - Updated `CMSSignedDataGenerator` for modern BouncyCastle API
  - Migrated `X509V3CertificateGenerator` to `JcaX509v3CertificateBuilder`
  - Updated ASN.1 APIs (`DERObject` → `ASN1Primitive`)
- **Directory Structure**: Renamed `decompiled/` to `src/` for professional standards
- **Version Scheme**: Updated to semantic versioning (2.0.0)

### Fixed
- **Scroll Functionality**: Resolved broken scroll behavior in output windows and data panes
- **Keystore Authentication**: Fixed password loading from XML configuration files
- **Configuration Loading**: Resolved empty configuration values causing FileNotFoundException
- **Path Compatibility**: Fixed Windows vs Unix path separator issues
- **Memory Management**: Improved scroll buffer management for large outputs
- **UI Responsiveness**: Fixed viewport layout issues preventing proper scrolling

### Security
- **Updated Cryptography**: All cryptographic operations now use modern, supported libraries
- **Secure Defaults**: Improved default configuration security
- **Key Management**: Enhanced keystore password handling and validation

### Technical Details
- **Dependencies**: 
  - BouncyCastle Provider v1.81
  - BouncyCastle PKIX v1.81
  - BouncyCastle Utilities v1.81
  - Log4j2 Core v2.23.1
  - Log4j2 API v2.23.1
- **Build**: Gradle 8.10.2
- **Java**: OpenJDK 8+ compatible
- **Platforms**: Windows, macOS, Linux

### Migration Notes
This is a **breaking change** from the original 1.0.3 version due to:
- Updated Java runtime requirements (Java 8+ vs Java 1.5)
- Modern cryptographic library dependencies
- Changed build system and distribution format
- Updated configuration file handling

Users upgrading from the original 2007 version should:
1. Update to Java 8 or later
2. Use the new Gradle-based build system
3. Review configuration files for compatibility
4. Update any integration scripts to use new jar locations

---

## [1.0.3] - 2007-07-10 (Original NIST Release)

### Added
- "PIV Test" identifier in generated data elements (CN, OU fields) to differentiate test data from production data
- UPN (User Principal Name) to PIV authentication certificate for Windows logon compatibility
- PIV interim extension (OID = 2.16.840.1.101.3.6.9.1) to PIV authentication certificate
- Content signer certificate and private key to JKS keystore
- Email address (RFC822 name) to Subject Alt Name extension for digital signature and key management certificates

### Changed
- Set Agency Code and Organizational Identifier in FASCN to fixed value of "3201"
- Modified JPIV to use content signer for signing CHUID, fingerprint, facial image, and security object

## [1.0.2] - 2006-08-06

### Added
- Buffer Length field (0xEE) to CHUID

## [1.0.1] - 2006-05-02

### Fixed
- Null CCC entries now correctly have length of zero instead of length 1 with byte 0x00
- PIV Certificate container structuring corrected
- IssuerID "too long" errors now correctly reported as IssuerID errors instead of Name errors
- Element order in PIV Signer DN attribute corrected (CN, C, O)
- Person Identifier entry in FASC-N now correctly encoded
- Subject DNs now conform to Common Policy section 3.1.1 format
- Security Object's embedded OID string correctly set to ICAO LDS Security Object OID

### Changed
- Removed extKeyUsage fields from all certificates except Card Authentication Certificate
- NACI indicator now only appears in Card Authentication Certificates

## [1.0.0] - 2006-04-10

### Added
- Initial release of NIST PIV Test Data Software

---

## Legend
- **Added**: New features
- **Changed**: Changes in existing functionality  
- **Deprecated**: Soon-to-be removed features
- **Removed**: Removed features
- **Fixed**: Bug fixes
- **Security**: Security improvements

[2.0.0]: https://github.com/your-repo/compare/v1.0.3...v2.0.0
[1.0.3]: https://github.com/your-repo/releases/tag/v1.0.3
[1.0.2]: https://github.com/your-repo/releases/tag/v1.0.2
[1.0.1]: https://github.com/your-repo/releases/tag/v1.0.1
[1.0.0]: https://github.com/your-repo/releases/tag/v1.0.0