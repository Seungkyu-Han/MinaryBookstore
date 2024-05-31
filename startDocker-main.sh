# docker 컨테이너 중지 및 이미지 삭제
sudo docker stop minary-main
sudo docker rm minary-main
sudo docker image prune -f

#최신버전 pull 후에 빌드
git pull origin main
./gradlew clean build -x test

# docker 실행
sudo docker build -t minary:main .
sudo docker run --name minary-main -d -p 8080:8080 --rm minary:main