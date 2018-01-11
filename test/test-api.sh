#
# Test Each Movie API 
#
#
# Future maybe
# Assumes need html to text tool installed
#  OSX - brew install lynx
#      - tool called textutil
#  html2text - another tool available 
#      npm install -g html-to-text - as part of nodejs
# -------------------------------------

if [ $# == 1 ]
then
   machine=http://127.0.0.1:8090
   token=$1
elif [ $# == 2 ]
then
   machine=http://$1
   token=$2
else
  echo "Usage: " 
  echo "     test-api.sh <Movie server or http://localhost:8000>> <encoded token> "
  echo ""
  exit 0
fi

echo "   Time of Day"
echo "   --------------------------------"
curl -sX GET "${machine}/api/timeOfDay" > tmp.html
results=`sed -e 's/<[^>]*>//g' tmp.html`
echo "   Results: ${results}"
echo 

echo "   Get Movie Name API"
echo "   --------------------------------"
curl -sX GET "${machine}/api/movie/16af8093-e43b-4756-8d2b-c214ecac6256" 
echo 

echo
echo "   Get all Movies Name API"
echo "   --------------------------------"
curl -sX GET "${machine}/api/movie/list"  | python -mjson.tool
echo 

echo
echo "    POST Create Movie"
echo "   --------------------------------"
curl -s -H "Content-Type: application/json" \
	-X POST "${machine}/api/movie/create?name=ThisWillBeDone&genre=Kids&year=1987&rating=2.5" 
#curl -s -H "Content-Type: application/json" -X POST "${machine}/api/movie/create/" \
#	--data "name=ToyStory" \
#	--data "genre=kids" \
#	--data "year=2004" \
#	--data "rating=3.5" 
echo 


echo 
echo "    PUT Update Movie: 7bd6e7a3-7b00-49e5-a3df-1d56173386dd"
echo "   --------------------------------"
curl -s -H "Content-Type: application/json" -X PUT "${machine}/api/movie/update?id=7bd6e7a3-7b00-49e5-a3df-1d56173386dd&name=Toy%20Story&genre=Family&year=2021&rating=1.5" | python -mjson.tool
#curl -s -H "Content-Type: application/json" -X PUT "${machine}/api/movie/update?id=7bd6e7a3-7b00-49e5-a3df-1d56173386dd" \
#	--data "name=Toy%20Story" \
#	--data "genre=Family" \
#	--data "year=2001" \
#	--data "rating=3.5" | python -mjson.tool
echo

echo 
echo
echo "   Get all Movies Name API"
echo "   --------------------------------"
curl -sX GET "${machine}/api/movie/list"  | python -mjson.tool
echo 

echo 
echo "    Delete Movie: 9492a56c-87f8-4015-8810-23bb3743fedf"
echo "   --------------------------------"
#curl -s -H "Content-Type: application/json" -X DELETE "${machine}/api/movie/delete/"  \
#	--data "id=1L" | python -mjson.tool
#curl -sX DELETE "${machine}/api/movie/delete?id=1"  \
#> tmp.html
curl -sX DELETE "${machine}/api/movie/delete/9492a56c-87f8-4015-8810-23bb3743fedf" | python -mjson.tool
echo 
 
echo
echo "   Verifying 2Toy Story has been deleted"
echo "   --------------------------------"
curl -sX GET "${machine}/api/movie/list"  | python -mjson.tool
echo 

