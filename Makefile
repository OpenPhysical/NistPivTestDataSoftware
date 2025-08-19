# PIV Test Data Software - Build System
# Cross-platform makefile for setup and building

.PHONY: all setup test clean jpiv loader deps help

# Default target
all: setup

# Colors for output (works on Unix-like systems)
RED := \033[31m
GREEN := \033[32m
YELLOW := \033[33m
BLUE := \033[34m
NC := \033[0m # No Color

# Platform detection
UNAME_S := $(shell uname -s 2>/dev/null || echo "Windows")
ifeq ($(UNAME_S),Linux)
    PLATFORM := linux
endif
ifeq ($(UNAME_S),Darwin)
    PLATFORM := macos
endif
ifeq ($(findstring MINGW,$(UNAME_S)),MINGW)
    PLATFORM := windows
endif
ifeq ($(findstring MSYS,$(UNAME_S)),MSYS)
    PLATFORM := windows
endif
ifndef PLATFORM
    PLATFORM := windows
endif

help:
	@echo "PIV Test Data Software - Build System"
	@echo "====================================="
	@echo ""
	@echo "Available targets:"
	@echo "  setup     - Download dependencies and set up environment"
	@echo "  jpiv      - Launch JPIV Test Data Generator"
	@echo "  loader    - Launch PIV Data Loader"
	@echo "  test      - Test that dependencies are working"
	@echo "  clean     - Clean downloaded dependencies"
	@echo "  help      - Show this help message"
	@echo ""
	@echo "Platform detected: $(PLATFORM)"

setup:
	@echo "$(BLUE)[SETUP]$(NC) Making scripts executable..."
	@chmod +x run-jpiv.sh run-piv-loader.sh
	@echo "$(GREEN)[INFO]$(NC) PIV Test Data Software setup complete!"
	@echo ""
	@echo "Quick start:"
	@echo "  make jpiv     # Launch JPIV Test Data Generator"
	@echo "  make loader   # Launch PIV Data Loader"

jpiv:
	@echo "$(GREEN)[LAUNCH]$(NC) Starting JPIV Test Data Generator..."
	@./run-jpiv.sh

loader:
	@echo "$(GREEN)[LAUNCH]$(NC) Starting PIV Data Loader..."
	@./run-piv-loader.sh

test:
	@echo "$(BLUE)[TEST]$(NC) Testing Java and dependencies..."
	@java -version || (echo "$(RED)[ERROR]$(NC) Java not found" && exit 1)
	@test -f "Binaries/JPIV Test Data Generator.jar" || (echo "$(RED)[ERROR]$(NC) JPIV jar not found" && exit 1)
	@test -f "Binaries/bcprov-jdk15-132.jar" || (echo "$(RED)[ERROR]$(NC) BouncyCastle Provider not found" && exit 1)
	@test -f "Binaries/bcmail-jdk15-1.38.jar" || (echo "$(RED)[ERROR]$(NC) BouncyCastle Mail not found" && exit 1)
	@echo "$(GREEN)[OK]$(NC) All dependencies found!"

clean:
	@echo "$(YELLOW)[CLEAN]$(NC) Nothing to clean - no build artifacts"
	@echo "$(GREEN)[OK]$(NC) Clean complete!"

# Windows-specific targets (when run in MSYS/MinGW/PowerShell)
ifeq ($(PLATFORM),windows)
setup:
	@echo "Windows platform detected"
	@echo "Scripts are for Unix/Linux/macOS only"
	@echo "On Windows:"
	@echo "  - Double-click 'JPIV Test Data Generator.jar' in Binaries folder"
	@echo "  - Double-click 'PIV Data Loader.exe' in Binaries folder"
	@echo "  - Or use run-piv-tools.bat"

jpiv:
	@echo "Starting JPIV Test Data Generator (Windows)..."
	@cd Binaries && start "JPIV" java -jar "JPIV Test Data Generator.jar"

loader:
	@echo "Starting PIV Data Loader (Windows native)..."
	@cd Binaries && start "PIV Data Loader" "PIV Data Loader.exe"
endif