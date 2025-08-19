#!/bin/bash

# PIV Data Loader Launcher
# Cross-platform launcher for PIV Data Loader with Wine support

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BINARIES_DIR="${SCRIPT_DIR}/Binaries"
PIV_LOADER="${BINARIES_DIR}/PIV Data Loader.exe"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
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

print_note() {
    echo -e "${BLUE}[NOTE]${NC} $1"
}

print_status "PIV Data Loader Launcher"
echo "==============================="

# Check if PIV Data Loader exists
if [[ ! -f "$PIV_LOADER" ]]; then
    print_error "PIV Data Loader not found at: $PIV_LOADER"
    exit 1
fi

# Detect platform and launch accordingly
case "$OSTYPE" in
    msys*|win32*|cygwin*)
        # Windows - run directly
        print_status "Windows detected - launching PIV Data Loader directly"
        cd "$BINARIES_DIR"
        exec "./PIV Data Loader.exe" "$@"
        ;;
    *)
        print_error "PIV Data Loader is Windows-only"
        print_note "This application requires Windows to run properly with smart card readers"
        print_note "Use the JPIV Test Data Generator (run-jpiv.sh) for cross-platform PIV data creation"
        exit 1
        ;;
esac