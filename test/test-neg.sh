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
   machine=http://127.0.0.1:8080
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

echo "   Get Movie Name API with wrong parameters"
echo "   --------------------------------"
curl -sX GET "${machine}/api/movie" \
	--data "name":"Automation Testing" > tmp.html
results=`sed -e 's/<[^>]*>//g' tmp.html`
echo "Results :  $results"
echo 

echo "   all Movies Name API with wrong HttpMethod"
echo "   --------------------------------"
curl -sX "${machine}/api/movie/list" > tmp.html
results=`sed -e 's/<[^>]*>//g' tmp.html`
echo "Results :  $results"
echo 

#echo "    POST Update Movie"
#echo "   --------------------------------"
#curl -sX POST "${machine}/api/movie/create" \
#	--data "id":1 \
#	--data "name":"Toy Story" \
#	--data "genre":"kids" \
#	--data "year":"2004" \
#	--data "rating":"3.5" > tmp.html
#results=`sed -e 's/<[^>]*>//g' tmp.html`
#echo "Results: ${results}"

echo 
echo "    Delete Movie with invalid id"
echo "   --------------------------------"
curl -sX DELETE "${machine}/api/movie"  \
	--data "id":"300" > tmp.html
results=`sed -e 's/<[^>]*>//g' tmp.html`
echo "Results: ${results}"
echo 
 

