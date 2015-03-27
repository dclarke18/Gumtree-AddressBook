# Gumtree-AddressBook
Implementation of the Gumtree Address book sample exercise. See [https://github.com/gumtreeuk/address-book](https://github.com/gumtreeuk/address-book).

Uses maven for build and can be executed from the command line using java -jar.
For work still to do bugs etc please visit - [the github issues list](https://github.com/dclarke18/Gumtree-AddressBook/issues).

build:
`mvn clean package assembly:single`
*Please ignore stack traces in log4j output from the unit test, that's expected behaviour and cleaning the output is still on the todo list.*

run:
`java -jar target/Gumtree-AddressBook-<version>-jar-with-dependencies.jar`
