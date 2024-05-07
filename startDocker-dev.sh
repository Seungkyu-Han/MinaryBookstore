# docker 컨테이너 중지 및 이미지 삭제
sudo docker stop minary-dev
sudo docker rm minary-dev
sudo docker image prune -f

#최신버전 pull 후에 빌드
git pull origin dev
./gradlew clean build -x test

# docker 실행
sudo docker build -t minary:dev .
sudo docker run --name minary-dev -d -p 8080:8080 --rm minary:dev