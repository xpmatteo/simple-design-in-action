
set -e
cd "$(dirname $0)/.."

if [ ! -d target/dependency ]; then
	mvn package
fi

java -cp target/classes:"target/dependency/*" it.xpug.example.main.Main
