#!/bin/bash
# Step 1: XML → .cg Conversion

export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

echo "Step 1: Compiling project..."
javac -cp "$(mvn dependency:build-classpath -q -Dmdep.outputFile=/dev/stdout):target/classes" \
  -d target/classes \
  -sourcepath src/main/java \
  RunGenerator.java 2>/dev/null || mvn compile -q -DskipTests

echo ""
echo "Step 1: Converting XML to .cg files..."
java -cp "$(mvn dependency:build-classpath -q -Dmdep.outputFile=/dev/stdout):target/classes" \
  RunGenerator step1 generate-xmls

echo ""
echo "✅ Step 1 Complete! Review .cg files in: src/main/resources/generate-xmls/.cg/"
echo "Next: Run ./generate-step2.sh"
