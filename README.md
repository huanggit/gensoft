# gensoft

use gradle and springboot 

to check bugs, run
gradle check

to build, run
gradle build

test run
gradle bootRun

for production, get the /build/lib/*.jar file and 
copy src/resources/config/application.yml with jar file in the same dir,
change the config file, run (after run, check the testServerRun page)
java -jar *.jar


