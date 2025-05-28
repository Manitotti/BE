#!/bin/bash

# ================= 설정 =================
JAR_NAME="manilib-0.0.1-SNAPSHOT.jar"
LOCAL_JAR_PATH="build/libs/$JAR_NAME"
REMOTE_DIR="/home/ubuntu"
DOCKER_IMAGE_NAME="manitotti-app"
DOCKER_CONTAINER_NAME="manitotti-web"
EC2_USER_HOST="ubuntu@3.34.194.90"
EC2_KEY_PATH="./manitotti-test-ver1.pem"
# =======================================

echo "📦 Step 1: 프로젝트 빌드 중..."
./gradlew clean build

if [ $? -ne 0 ]; then  echo "❌ 빌드 실패!"
  exit 1
fi

echo "🚀 Step 2: EC2에 JAR 전송 중..."
scp -i "$EC2_KEY_PATH" -r ./Dockerfile ./build/libs/*.jar ./application.yaml ./docker-compose.yml "$EC2_USER_HOST:$REMOTE_DIR"


if [ $? -ne 0 ]; then
  echo "❌ JAR 전송 실패!"
  exit 1
fi

echo "🔗 Step 3: EC2에 SSH 접속하여 Docker 컨테이너 재배포..."
ssh -i "$EC2_KEY_PATH" "$EC2_USER_HOST" <<EOF
cd $REMOTE_DIR

echo "🛑 기존 컨테이너 중지 및 제거..."
docker stop $DOCKER_CONTAINER_NAME || true
docker rm $DOCKER_CONTAINER_NAME || true

echo "🔄 Docker 이미지 다시 빌드..."
docker build -t $DOCKER_IMAGE_NAME .

echo "🏃 새 컨테이너 실행..."
docker run -d --name $DOCKER_CONTAINER_NAME -p 8080:8080 $DOCKER_IMAGE_NAME

echo "✅ 배포 완료! 컨테이너 상태:"
docker ps | grep $DOCKER_CONTAINER_NAME