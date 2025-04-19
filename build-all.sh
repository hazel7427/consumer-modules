#!/bin/bash
set -e

# MODULES=("chat-enter-consumer" "chat-enter-deliver-consumer" "message-consumer" "message-deliver-consumer")
MODULES=("chat-enter-consumer")


for module in "${MODULES[@]}"
do
  echo "🚀 Processing module: $module"
  
  # 모듈 디렉토리로 이동
  cd "$module"
  
  # Gradle 래퍼 초기화 (필요한 경우)
  if [ ! -f "gradlew" ]; then
    echo "📦 Initializing Gradle wrapper..."
    gradle wrapper
  else
    echo "ℹ️ Gradle wrapper already exists, skipping initialization."
  fi

  # 의존성 리프레시 (주석 처리됨)
  # echo "📦 Refreshing dependencies..."
  # ./gradlew --refresh-dependencies
  
  # JAR 빌드
  echo "📦 Building JAR..."
  ./gradlew bootJar

  echo "✅ JAR file built at build/libs/$module-0.0.1-SNAPSHOT.jar"
  
  # 다음 모듈을 위해 다시 루트 디렉토리로 이동
  # cd "$ROOT_DIR"
done
