#!/bin/bash

# 항상 deploy.sh가 있는 경로 기준으로 작동
cd "$(dirname "$0")"

# ================= 설정 =================
EC2_HOST="ubuntu@3.36.65.160"
EC2_KEY="./manitotti-test-ver1.pem"
REMOTE_BASE_DIR="/home/ubuntu/manilib"
REMOTE_JAR_DIR="$REMOTE_BASE_DIR/build/libs"
DOCKER_IMAGE="manitotti-app"
DOCKER_CONTAINER="manitotti-web"
JAR_NAME="manilib-0.0.1-SNAPSHOT.jar"
# =======================================

LOCAL_JAR_PATH="build/libs/$JAR_NAME"

echo "📦 Step 1: 프로젝트 빌드 중..."
./gradlew clean build
if [ $? -ne 0 ]; then
  echo "❌ 빌드 실패!"
  exit 1
fi

echo "📂 Step 2: EC2에 JAR 전송 중..."
# build/libs 디렉토리가 EC2에 없다면 생성
ssh -i "$EC2_KEY" "$EC2_HOST" "mkdir -p $REMOTE_JAR_DIR"

# JAR 파일도 Dockerfile이 있는 디렉토리로 복사
scp -i "$EC2_KEY" "$LOCAL_JAR_PATH" "$EC2_HOST:$REMOTE_BASE_DIR/"

if [ $? -ne 0 ]; then
  echo "❌ JAR 전송 실패!"
  exit 1
fi

echo "🐳 Step 3: EC2에서 Docker 빌드 및 컨테이너 실행..."
ssh -i "$EC2_KEY" "$EC2_HOST" <<EOF
cd $REMOTE_BASE_DIR

echo "🛑 기존 컨테이너 중지 및 제거..."
docker stop $DOCKER_CONTAINER || true
docker rm $DOCKER_CONTAINER || true

echo "🔧 Docker 이미지 빌드..."
docker build --no-cache -t $DOCKER_IMAGE . || exit 1

echo "🚀 새 컨테이너 실행 중..."
docker run -d --name $DOCKER_CONTAINER -p 8080:8080 $DOCKER_IMAGE || exit 1

echo "✅ 배포 완료! 현재 컨테이너 상태:"
docker ps | grep $DOCKER_CONTAINER
EOF
