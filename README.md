There are 2 ways to compile the project. Assuming one is in the evidence folder, here are the commands to compile:

javac -cp pt.jar:.:support.jar `find poof -name *.java`
find poof -name "*.java" -print | xargs javac -cp pt.jar:.:support.jar

To execute the project, here is the command:

java -cp  -cp pt.jar:.:support.jar poof.textui.Shell

If you want to start the application indicating a file with the initial status of the application, here is the command:

java -Dimport=ficheiro.im -cp  -cp pt.jar:.:support.jar poof.textui.Shell
