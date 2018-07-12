cd build\libs
del *.*

cd ..\..
call gradlew clean build
call run
cd ..\..

