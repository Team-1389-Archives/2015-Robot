NOTE
======================================
Git clone this repo into the source folder of your java project.

WPILib Documentation Can Be Found HERE
======================================
http://first.wpi.edu/FRC/roborio/release/docs/java/

Noteable Changes Between Java and C++
======================================
Since team 1380 has been using C++ for years, here are the notable changes from jumping from C++ to java 

	-Java does not use pointers, so do not use the "*" parameter when declaring an object.
	
	- Again, because we are no longer using pointers, use the "." operator instead of "->" when trying to access members of an object.
	
	- All WPILib syntax is now in camel case (camelCase vs. NotCamelCase)
	
	- Instead of using macros, we are using static final variables declared in "Constants.java"
	
	- We no longer used the START_ROBOT_CLASS(mainClass) function. The Class that inherits sample robot, iterative robot, etc. is automatically the main class.
	
	- Inheritance no longer uses the ":" operator, it now uses the "extends" operator

Setting Up Eclipse with WPILib for the 2015 season
======================================
1. Download 2015 FRC Update: http://www.ni.com/download/first-robotics-software-2015/5112/en/

2. Install update

3. Install eclipse for java: https://eclipse.org/downloads/packages/eclipse-ide-java-developers/lunasr1a

4. Install JDK and JRE. NOTE: YOU MUST HAVE JRE 8 and JDK 8 for your code to build (no idea why)!: 
http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

5. Make sure eclipse is using JDK 1.8 and JRE 1.8. Go to preferences -> Java -> Compiler and make sure JDK 1.8 is selected. Go to
preferences -> Java -> Installed JREs and make sure the folder with JRE 1.8 installed is selected. 

6. Download WPIlib for eclipse by opening eclipse, selecting Help -> Install new Software -> Add -> Name: "FRC plugins" Location: "http://first.wpi.edu/FRC/roborio/release/eclipse/" -> OK -> Click the drop down 
on the found plugin, select WPIlib for java, then install. Now, restart eclipse.

7. Build example code by going to New -> Project -> WPILib Robot Java Development -> Example Robot Java Project. Deploy the code by right clicking the project -> Run As -> WPILib java deploy 

8. If this works, you are done! If not, go to step 9.

9. If build fails because of an error along the lines of "Class not found: Javac1.8" create a new environment variable by goint to Control Panel -> System -> Advanced system settings -> Environment Variables
-> New System Variable -> Name: "JAVA_HOME" Value: "C:\Program Files\jre1.8.0_25"

10. Restart Eclipse and try again. 


Drive Control
