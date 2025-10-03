#!/bin/bash
# Step 2: .cg → Java Code Generation

export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

echo "Step 2: Generating Java code from .cg files..."
java -cp "$(mvn dependency:build-classpath -q -Dmdep.outputFile=/dev/stdout):target/classes" \
  RunGenerator step2 generate-xmls

echo ""
echo "✅ Step 2 Complete! Java code generated."
echo "Next: Run 'mvn clean compile' to compile everything"
