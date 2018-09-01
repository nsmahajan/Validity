# Validity

Step 1: Make sure Java is installed on pc.


Step 2: Install Maven


Step 3: Download the Validity respository


Step 3: Open Eclipse and right click in the project explorer window and then select import an exisiting maven project.
		Select the root directory as the demo folder.
		
		
Step 4: Open command prompt


Step 5: Navigate to the demo folder where pom.xml file resides.


Step 6: type: mvn clean package spring-boot:repackage . This will build a new war file. This step is important as it packages the react application.


Step 7: Step 6 creates a target folder under demo folder.


Step 8: cd/target and then type : java -jar demo-0.0.1-SNAPSHOT.war on the command line


Step 9: Open web browser and type: localhost:8080/helloWorld


Step 10: You will be able to see the output of the project.

To view the ouput of the rest call type:  localhost:8080/getRecords


