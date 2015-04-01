# stx86-validator
Validate xml files against ST.86 xsd schemas. Schemas are located in the jar.

### Build
```bash
mvn clean compile assembly:single
```
The command will build an executable jar.

### Run
```bash
java -jar target/st86-validator-0.0.1-SNAPSHOT-jar-with-dependencies.jar files...
```
