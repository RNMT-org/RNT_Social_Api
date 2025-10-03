#!/bin/bash

# RNT Code Generator - Cross-Platform Maven-Based Tool
# Works on Linux, Mac, Windows (with Git Bash)

echo "╔════════════════════════════════════════════════════════════════╗"
echo "║       RNT Social API - Code Generator (Maven-Based)           ║"
echo "╚════════════════════════════════════════════════════════════════╝"
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ ERROR: Maven is not installed!"
    echo "Please install Maven from: https://maven.apache.org/download.cgi"
    exit 1
fi

echo "✅ Maven found: $(mvn --version | head -n 1)"
echo ""

# Check if XML files exist
if [ ! -d "src/main/resources/generate-xmls" ]; then
    echo "❌ ERROR: XML folder not found!"
    echo "Expected location: src/main/resources/generate-xmls/"
    exit 1
fi

XML_COUNT=$(find src/main/resources/generate-xmls -name "*.xml" | wc -l)
if [ "$XML_COUNT" -eq 0 ]; then
    echo "❌ ERROR: No XML files found in generate-xmls folder!"
    echo "Please add entity XML files to: src/main/resources/generate-xmls/"
    exit 1
fi

echo "✅ Found $XML_COUNT XML entity file(s)"
echo ""

# List XML files
echo "📄 XML Files to process:"
find src/main/resources/generate-xmls -name "*.xml" -exec basename {} \; | sed 's/^/   - /'
echo ""

# Ask for confirmation
echo "⚠️  This will generate code for all entities"
echo "   Existing files may be overwritten!"
echo ""
read -p "Do you want to continue? (y/N): " -n 1 -r
echo ""

if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    echo "❌ Cancelled by user"
    exit 0
fi

echo ""
echo "🚀 Starting code generation using Maven..."
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""

# Run Maven with generate profile
mvn clean compile -Pgenerate -DxmlFolderPath=generate-xmls

EXIT_CODE=$?

if [ $EXIT_CODE -eq 0 ]; then
    echo ""
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo "╔════════════════════════════════════════════════════════════════╗"
    echo "║                     ✅ SUCCESS!                                 ║"
    echo "╚════════════════════════════════════════════════════════════════╝"
    echo ""
    echo "📁 Generated files location:"
    echo "   src/main/java/ir/rayanovinmt/rnt_social_api/{entityName}/"
    echo ""
    echo "📝 Next steps:"
    echo "   1. Review the generated code in your IDE"
    echo "   2. Run: mvn spring-boot:run"
    echo "   3. Test endpoints at: http://localhost:8080/swagger-ui.html"
    echo ""
    echo "💡 Tip: Use 'git diff' to see what changed"
    echo ""
else
    echo ""
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo "╔════════════════════════════════════════════════════════════════╗"
    echo "║                     ❌ FAILED!                                  ║"
    echo "╚════════════════════════════════════════════════════════════════╝"
    echo ""
    echo "Check the error messages above for details."
    echo ""
    echo "Common issues:"
    echo "  • Invalid XML syntax"
    echo "  • Missing required fields"
    echo "  • Duplicate entity names"
    echo "  • Invalid field types"
    echo ""
    exit $EXIT_CODE
fi
