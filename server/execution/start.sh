printf 'Starting the application by docker...'
docker-compose down
docker-compose build --no-cache
docker-compose up
printf 'The application was started!'