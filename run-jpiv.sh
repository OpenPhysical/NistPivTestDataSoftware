#!/bin/bash

# PIV Test Data Generator Launcher
# Automatically sets up classpath and launches JPIV with proper dependencies

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BINARIES_DIR="${SCRIPT_DIR}/Binaries"
JPIV_JAR="${BINARIES_DIR}/JPIV Test Data Generator.jar"
BC_PROVIDER="${BINARIES_DIR}/bcprov-jdk15-132.jar"
BC_MAIL="${BINARIES_DIR}/bcmail-jdk15-1.38.jar"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

print_status() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

print_status "PIV Test Data Generator Launcher"
echo "======================================="

# Check if Java is available
if ! command -v java >/dev/null 2>&1; then
    print_error "Java not found. Please install Java 8 or newer."
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n1 | cut -d'"' -f2)
print_status "Using Java version: $JAVA_VERSION"

# Check if JPIV jar exists
if [[ ! -f "$JPIV_JAR" ]]; then
    print_error "JPIV Test Data Generator not found at: $JPIV_JAR"
    exit 1
fi

# Check for dependencies
if [[ ! -f "$BC_PROVIDER" ]]; then
    print_error "BouncyCastle Provider missing: $BC_PROVIDER"
    exit 1
fi

if [[ ! -f "$BC_MAIL" ]]; then
    print_error "BouncyCastle Mail missing: $BC_MAIL"
    exit 1
fi

# Build classpath
CLASSPATH="$JPIV_JAR"
if [[ -f "$BC_PROVIDER" ]]; then
    CLASSPATH="$CLASSPATH:$BC_PROVIDER"
fi
if [[ -f "$BC_MAIL" ]]; then
    CLASSPATH="$CLASSPATH:$BC_MAIL"
fi

print_status "Classpath configured with BouncyCastle dependencies"
print_status "Launching JPIV Test Data Generator..."

# Launch JPIV with proper classpath
# Note: Can't use -jar with -cp, need to specify main class
MAIN_CLASS="gov.nist.piv.PersoUI"
exec java -cp "$CLASSPATH" "$MAIN_CLASS" "$@"