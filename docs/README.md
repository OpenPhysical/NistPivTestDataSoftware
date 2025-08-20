# NIST PIV Test Data Generator

> **⚠️ IMPORTANT NOTICE**: This software was recreated through decompilation of the original NIST PIV Test Data Software (version 1.0.3, July 2007) and may contain errors or inaccuracies. While extensively tested and modernized, users should verify all outputs against official PIV specifications before use in critical applications.

A modernized version of the NIST Personal Identity Verification (PIV) Test Data Generator, updated for modern Java environments with enhanced security libraries and professional build tools.

## What is PIV Test Data?

Personal Identity Verification (PIV) cards are government-issued smart cards used for secure access to federal facilities and information systems. This software generates standardized test data for PIV card testing, development, and validation purposes.

**Generated PIV Data Elements:**
- Card Capability Container (CCC)
- Card Holder Unique Identifier (CHUID) 
- X.509 Certificates (Authentication, Digital Signature, Key Management, Card Authentication)
- Biometric data (Fingerprints, Facial Images)
- Printed Information
- Security Objects with cryptographic signatures

## Version 2.0.0 - Major Modernization

This release represents a complete modernization from the original 2007 codebase:

### 🚀 Modern Technology Stack
- **Java 11+** (upgraded from Java 1.5, Java earlier than 11 not supported)
- **BouncyCastle 1.81** (2024 - upgraded from 2007 v1.32/1.38)
- **Log4j2 2.25.1** for professional logging
- **Gradle Build System** with distribution packaging

### 🔒 Enhanced Security
- Updated cryptographic libraries with modern security standards
- Secure keystore password handling
- Modern X.509 certificate generation APIs
- Compatible with PIV data format specifications

### 🛠️ Developer Experience
- Professional project structure (`src/` organization)
- Comprehensive logging with configurable levels
- Cross-platform compatibility (Windows, macOS, Linux)
- Professional packaging and distribution

## Quick Start

### Prerequisites
- **Java 11 or later** (OpenJDK recommended, Java earlier than 11 not supported)
- **Windows OS** (for PIV Data Loader smart card operations)

### Installation & Usage

1. **Clone and Build**:
   ```bash
   git clone https://github.com/OpenPhysical/NistPivTestDataSoftware.git
   cd PIV_Test_Data_Software
   ./gradlew build
   ```

2. **Run PIV Test Data Generator**:
   ```bash
   ./gradlew run
   # or
   java -jar build/libs/jpiv-test-data-generator-2.0.0.jar
   ```

3. **Generate Test Data**:
   - Launch the application
   - Configure PIV parameters (or use auto-loaded defaults)
   - Click "Create Samples" to generate PIV test data
   - Output appears in `generated_data/sample_set_XXX/`

### Available Tools

| Tool | Platform Support | Description |
|------|------------------|-------------|
| **PIV Test Data Generator** | Windows, macOS, Linux | Generate PIV test data files |
| **PIV Data Loader** | Windows only | Load test data onto smart cards |

## Configuration

The software auto-loads configuration from `extra_files/auto_create_options.xml`:

```xml
<auto_create_options>
  <DataPane name="KeyStore">
    <DataManager header="KeyStore">
      <KeyStoreType>JKS</KeyStoreType>
      <KeyStorePath>./extra_files/jks_keystore</KeyStorePath>
      <KeyStorePassword>pivpw1</KeyStorePassword>
      <!-- Additional keystore configuration -->
    </DataManager>
  </DataPane>
  <!-- PIV data element configurations -->
</auto_create_options>
```

## Generated Output

Each sample set creates standardized PIV data files:

```
generated_data/sample_set_001/
├── CARD_CAPABILITY_CONTAINER
├── CHUID
├── X509_CERTIFICATE_PIV_AUTHENTICATION
├── X509_CERTIFICATE_CARD_AUTHENTICATION
├── X509_CERTIFICATE_DIGITAL_SIGNATURE
├── X509_CERTIFICATE_KEY_MANAGEMENT
├── CARD_HOLDER_FINGERPRINTS
├── CARD_HOLDER_FACIAL_IMAGE
├── PRINTED_INFORMATION
└── SECURITY_OBJECT
```

## Build & Development

### Build Tasks
```bash
./gradlew build          # Build and test
./gradlew fatJar         # Create fat JAR with dependencies
./gradlew distZip        # Create distribution package
./gradlew clean          # Clean all artifacts
./gradlew run            # Run the application
```

### Logging Configuration

The application uses Log4j2 for comprehensive logging with user-configurable levels:

#### Log Files Created
- `logs/jpiv.log` - Main application log (INFO and above)
- `logs/jpiv-debug.log` - Debug information including XPath queries
- `logs/jpiv-error.log` - Errors and warnings only

#### Configuration Methods

**1. System Properties (Runtime)**
```bash
# Enable debug logging for all components
java -Djpiv.log.level=DEBUG -jar jpiv-fat-2.0.0.jar

# Enable crypto debugging only
java -Djpiv.log.level.crypto=DEBUG -jar jpiv-fat-2.0.0.jar

# Quiet operation (warnings/errors only)
java -Djpiv.log.level=WARN -jar jpiv-fat-2.0.0.jar
```

**2. Edit Configuration File**
Edit `src/resources/log4j2.xml` and rebuild:
- Change `level` attributes in `<Logger>` sections
- Available levels: `TRACE`, `DEBUG`, `INFO`, `WARN`, `ERROR`

**3. Common Configurations**
```xml
<!-- Verbose debugging -->
<Root level="DEBUG">

<!-- Crypto debugging only -->
<Logger name="com.tvec.utility.Crypto" level="DEBUG">

<!-- Quiet operation -->
<Root level="WARN">
```

#### Available Loggers
- `gov.nist.piv` - PIV application components
- `gov.nist.piv.auto_create` - Sample generation (debug to file only)
- `com.tvec.utility.Crypto` - Cryptographic operations
- `com.tvec.utility` - General utilities

### Project Structure
```
PIV_Test_Data_Software/
├── src/
│   ├── sources/         # Java source code
│   └── resources/       # Resources and configuration
├── extra_files/         # PIV configuration and test data
├── docs/               # Documentation and specifications
├── Binaries/           # Original NIST binaries
└── build.gradle        # Build configuration
```

## Important Disclaimers

### ⚠️ Decompilation Notice
This software was recreated through **decompilation** of the original NIST binary distribution. **Errors may exist** in the decompilation process. Users should:

- Verify all generated PIV data against official specifications
- Test thoroughly in non-production environments
- Report any discrepancies or issues
- Use at their own risk for critical applications

### 🏛️ NIST Original Disclaimer
This software was originally developed by the National Institute of Standards and Technology (NIST), an agency of the Federal Government. The original NIST software is in the public domain per Title 15 United States Code Section 105 (U.S. federal law).

**NIST provides this software "AS IS" with NO WARRANTY OF ANY KIND.** NIST makes no warranty regarding correctness, accuracy, reliability, or usefulness. Users agree to hold harmless the United States Government for any damages arising from use.

### 🔒 Security Considerations
- **Test Data Only**: Generated data is for testing and development purposes
- **Not for Production**: Do not use test certificates or keys in production systems
- **Validate Outputs**: Verify all generated data meets your security requirements
- **Modern Cryptography**: Updated to use current cryptographic standards

## Documentation

- **[CHANGELOG.md](CHANGELOG.md)** - Version history and changes
- **[Original NIST Docs](./)** - PIV specifications and user guides
- **[LICENSE](LICENSE.md)** - Public domain and Unlicense terms

## Contributing

This project preserves and modernizes historical NIST software for continued utility. Contributions welcome for:

- Bug fixes and validation improvements
- Cross-platform compatibility enhancements  
- Documentation and usability improvements
- Security updates and modernization

**Please Note**: This is a preservation project. Major architectural changes should be discussed via issues before implementation.

## Support & Issues

- **GitHub Issues**: Report bugs, validation errors, or enhancement requests
- **PIV Specifications**: Refer to [NIST SP 800-73](https://csrc.nist.gov/publications/detail/sp/800-73/4/final) for official PIV standards
- **Validation**: Compare outputs with official NIST PIV test vectors when available

## License

This modernized version is released under the [Unlicense](LICENSE.md) (public domain). The original NIST software remains in the public domain per U.S. federal law.

---

**Historical Preservation Notice**: This software preserves important cybersecurity tooling from the NIST Computer Security Resource Center archives. The original software and documentation were retrieved from https://csrc.nist.rip/Projects/PIV/Download before modernization.