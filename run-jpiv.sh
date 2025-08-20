#!/bin/bash

# PIV Test Data Generator Launcher
# Automatically sets up classpath and launches JPIV with proper dependencies

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BUILD_DIR="${SCRIPT_DIR}/build/distributions"
JPIV_JAR="${SCRIPT_DIR}/build/libs/jpiv-fat-2.0.0.jar"

# Legacy support for old binary location
BINARIES_DIR="${SCRIPT_DIR}/Binaries"
LEGACY_JPIV_JAR="${BINARIES_DIR}/JPIV Test Data Generator.jar"
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

# Use modern Java (Java 11+) - avoiding Java 8 due to known bugs
JAVA_CMD="java"

# Check if Java is available
if ! command -v $JAVA_CMD >/dev/null 2>&1; then
    print_error "Java not found. Please install Java 11 or newer."
    exit 1
fi

JAVA_VERSION=$($JAVA_CMD -version 2>&1 | head -n1 | cut -d'"' -f2)
print_status "Using Java version: $JAVA_VERSION"

# Check for minimum Java 11
check_java_version() {
    local version="$1"
    # Extract major version number
    if [[ $version =~ ^1\.([0-9]+) ]]; then
        # Java 8 and below use 1.x format
        local major="${BASH_REMATCH[1]}"
    elif [[ $version =~ ^([0-9]+) ]]; then
        # Java 9+ use direct major version
        local major="${BASH_REMATCH[1]}"
    else
        return 1
    fi
    
    if [[ $major -lt 11 ]]; then
        return 1
    fi
    return 0
}

if ! check_java_version "$JAVA_VERSION"; then
    print_error "Java 11 or newer is required. Found: $JAVA_VERSION"
    print_error "Java earlier than Java 11 is not supported."
    exit 1
fi

# Check if modern built jar exists, otherwise fall back to legacy
if [[ -f "$JPIV_JAR" ]]; then
    print_status "Using modernized JAR: $JPIV_JAR"
    # Fat JAR includes all dependencies
    CLASSPATH="$JPIV_JAR"
elif [[ -f "$LEGACY_JPIV_JAR" ]]; then
    print_warning "Using legacy JAR (rebuild recommended): $LEGACY_JPIV_JAR"
    JPIV_JAR="$LEGACY_JPIV_JAR"
    
    # Check for legacy dependencies
    if [[ ! -f "$BC_PROVIDER" ]]; then
        print_error "BouncyCastle Provider missing: $BC_PROVIDER"
        exit 1
    fi
    
    if [[ ! -f "$BC_MAIL" ]]; then
        print_error "BouncyCastle Mail missing: $BC_MAIL"
        exit 1
    fi
    
    # Build classpath with separate dependencies
    CLASSPATH="$JPIV_JAR:$BC_PROVIDER:$BC_MAIL"
else
    print_error "JPIV Test Data Generator not found. Run './gradlew build' first."
    exit 1
fi

print_status "Classpath configured"
print_status "Launching JPIV Test Data Generator..."

# Launch JPIV with proper classpath and compatibility flags
# Note: Can't use -jar with -cp, need to specify main class
MAIN_CLASS="gov.nist.piv.PersoUI"

# Minimal Java options for maximum 2007 compatibility
JAVA_OPTS=""
JAVA_OPTS="$JAVA_OPTS -Djava.awt.headless=false"                   # Ensure GUI mode
JAVA_OPTS="$JAVA_OPTS -Dsun.java2d.noddraw=true"                   # Disable DirectDraw
JAVA_OPTS="$JAVA_OPTS -Dsun.java2d.d3d=false"                      # Disable Direct3D
JAVA_OPTS="$JAVA_OPTS -Dsun.java2d.opengl=false"                   # Disable OpenGL
JAVA_OPTS="$JAVA_OPTS -XX:ReservedCodeCacheSize=128m"              # Increase code cache

# macOS specific fixes
if [[ "$OSTYPE" == "darwin"* ]]; then
    JAVA_OPTS="$JAVA_OPTS -Dapple.laf.useScreenMenuBar=false"      # Don't use system menu bar
    JAVA_OPTS="$JAVA_OPTS -Dcom.apple.mrj.application.apple.menu.about.name=JPIV" # App name
    JAVA_OPTS="$JAVA_OPTS -Xdock:name=JPIV"                        # Dock name
fi

print_status "Using modern Java with compatibility flags"
exec $JAVA_CMD $JAVA_OPTS -cp "$CLASSPATH" "$MAIN_CLASS" "$@"