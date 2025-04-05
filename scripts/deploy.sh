BRANCH=$(git rev-parse --abbrev-ref HEAD)

echo "Deploying branch: $BRANCH"

cd app/TechGalaxy_KTPM

git fetch -a
git checkout $BRANCH
git pull 

docker compose -f docker-compose.yml down 
docker compose -f docker-compose.yml pull
docker compose -f docker-compose.yml up -d
docker system prune -af
