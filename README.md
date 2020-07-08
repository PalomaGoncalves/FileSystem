The aim of the project is to create an application that simulates and manages a file system. The application keeps a directory of work, in the context of which file system operations are performed. There is also a user component, where users can make changes during the execution of the application.

There are 2 ways to compile the project. Assuming one is in the evidence folder, here are the commands to compile:

javac -cp pt.jar:.:support.jar `find poof -name *.java`
find poof -name "*.java" -print | xargs javac -cp pt.jar:.:support.jar

To execute the project, here is the command:

java -cp  -cp pt.jar:.:support.jar poof.textui.Shell

If you want to start the application indicating a file with the initial status of the application, here is the command:

java -Dimport=ficheiro.im -cp  -cp pt.jar:.:support.jar poof.textui.Shell
