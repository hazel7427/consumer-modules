#!/bin/bash
set -e

# MODULES=("chat-enter-consumer" "chat-enter-deliver-consumer" "message-consumer" "message-deliver-consumer")
MODULES=("chat-enter-consumer")

for module in "${MODULES[@]}"
do
  # 이미 gradlew가 있는지 확인하고 없는 경우에만 초기화
  if [ ! -f "$module/gradlew" ]; then
    echo "📦 Initializing Gradle wrapper for $module..."
    (cd $module && gradle wrapper)
  else
    echo "ℹ️ Gradle wrapper already exists for $module, skipping initialization."
  fi

  echo "📦 Refreshing dependencies for $module..."
  (cd $module && ./gradlew --refresh-dependencies)
  
  echo "📦 Building JAR for $module..."
  (cd $module && ./gradlew bootJar)

  echo "✅ JAR file built at $module/build/libs/$module-0.0.1-SNAPSHOT.jar"
done

echo "✅ All JARs built successfully!"

# 자동으로 첫 번째 모듈 실행
echo "🚀 Starting application: ${MODULES[0]}"
java -jar "${MODULES[0]}/build/libs/${MODULES[0]}-0.0.1-SNAPSHOT.jar"