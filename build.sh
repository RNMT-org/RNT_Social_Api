#!/bin/bash

# RNT Social API Build Script
# This script ensures proper code generation and compilation

set -e  # Exit on error

echo "🏗️  RNT Social API Build Script"
echo "================================"
echo ""

# Set Java 21
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

echo "☕ Using Java: $(java -version 2>&1 | head -n 1)"
echo ""

cd "$(dirname "$0")"

echo "📦 Step 1: Building parent and modules..."
mvn clean install -DskipTests -pl rnt-core,code-generator
echo ""

echo "📄 Step 2: Generating .cg files (XML → .cg)..."
cd rnt-social-api-main
mvn exec:java@generate-cg-files
echo ""

echo "💻 Step 3: Generating Java code (.cg → Java)..."
mvn exec:java@generate-java-code
echo ""

echo "🔨 Step 4: Compiling application..."
mvn compile
echo ""

echo "✅ Build complete!"
echo ""
echo "🚀 To run the application:"
echo "   cd rnt-social-api-main && mvn spring-boot:run"
echo ""
echo "📚 To view API documentation:"
echo "   http://localhost:8080/swagger-ui.html"
