# PIV Test Data Software - Modernized

This repository contains the NIST PIV Test Data Software (version 1.0.3, July 2007) with modern wrapper scripts that make it easy to run on any platform.

**Source**: This software was downloaded from https://csrc.nist.rip/Projects/PIV/Download, an archive of the original NIST Computer Security Resource Center website.

> **⚠️ Disclaimer**: This software is being made available as a service to the community. No representations are made as to the correctness or functionality of the software. This software is intended for testing and development purposes only. Do not use in production environments without proper security review.

## What's Been Added

The original NIST software has been enhanced with:

### 🚀 Easy Setup & Launch
- **Simple setup**: `make setup` makes scripts executable
- **Cross-platform launchers**: Works on Windows, macOS, and Linux
- **Dependencies included**: BouncyCastle JARs bundled in repository

### 🛠️ Build System
- **Makefile**: Standard build targets (setup, jpiv, loader, test, clean)
- **Shell scripts**: Individual launchers for each tool
- **Batch files**: Windows-friendly interface


## Quick Start

### Unix/Linux/macOS:
1. **Setup** (one-time):
   ```bash
   make setup
   ```

2. **Launch JPIV Test Data Generator**:
   ```bash
   make jpiv
   # or
   ./run-jpiv.sh
   ```

3. **PIV Data Loader** (Windows-only):
   ```bash
   ./run-piv-loader.sh  # Will show Windows-only message
   ```

### Windows:
1. **JPIV Test Data Generator**:
   - Double-click `Binaries/JPIV Test Data Generator.jar`
   - Or run: `java -jar "Binaries/JPIV Test Data Generator.jar"`

2. **PIV Data Loader**:
   - Double-click `Binaries/PIV Data Loader.exe`

3. **Menu Interface**:
   - Double-click `run-piv-tools.bat` for a menu


## Dependencies Included

The repository includes:
- BouncyCastle Provider v1.32 (bcprov-jdk15-132.jar)
- BouncyCastle Mail utilities v1.38 (bcmail-jdk15-1.38.jar)

## Platform Support

| Platform | JPIV Generator | PIV Data Loader |
|----------|----------------|-----------------|
| Windows  | ✅ Native      | ✅ Native       |
| macOS    | ✅ Java        | ❌ Windows-only |
| Linux    | ✅ Java        | ❌ Windows-only |

## Files Added

- `run-jpiv.sh` - JPIV launcher with classpath management
- `run-piv-loader.sh` - PIV Data Loader launcher (Windows detection)
- `run-piv-tools.bat` - Windows menu interface
- `Makefile` - Build system

## Requirements

- Java 8+ (OpenJDK recommended)
- Windows OS (for PIV Data Loader - smart card operations)
- Smart card reader drivers (Windows only)

## Original NIST Documentation

See the `Docs/` directory for original NIST documentation and user guides.

## About

This is a modernized version of the NIST PIV Test Data Software that resolves dependency issues and provides cross-platform compatibility. The original software was developed by NIST for generating and loading test data on PIV-compliant smart cards.

The original software was retrieved from https://csrc.nist.rip/Projects/PIV/Download, which preserves historical NIST security resources that are no longer available on the current NIST website.

## Contributing

This repository preserves and modernizes historical NIST software. Contributions that improve cross-platform compatibility, fix bugs, or enhance usability are welcome.

## License

The original NIST software is in the public domain. The modernization enhancements are provided as-is for educational and research purposes. See [LICENSE](LICENSE) for details.