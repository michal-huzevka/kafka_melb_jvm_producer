app_name=$1
target_ip=$2
target_port=$3

jar="$app_name-0.1.0.jar"
source_jar="build/libs/$jar"
target_jar="/home/$app_name/$jar"
target_host="$app_name@$target_ip"

echo "kill existing $app_name" 
ssh $target_host "/usr/bin/pgrep -f $jar > old.pid"
ssh $target_host '(kill -9 `cat old.pid` || true && rm old.pid)'

echo "building jar"
./gradlew build

echo "copying jar"
scp $source_jar $target_host:~

echo "starting $app_name"
ssh $target_host java -jar $target_jar &
