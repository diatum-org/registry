set -e
cd ./fe
npm install
ng build --prod --base-href /app/
cd ../be
mvn install -DskipTests
