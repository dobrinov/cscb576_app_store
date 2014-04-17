# CSCB576 Java RMI Application store (course work) #

1. Description
2. Installation instructions
3. Starting the server
4. Operating
	4.1 Administration client
		4.1.1 Starting the application
		4.1.2 Starting the server
		4.1.3 Stopping the server
		4.1.4 Pausing the server
		4.1.5 Adding new applications
	4.2 Customer client
		4.2.1 Starting the appplication
		4.2.2 Downloading applications

## Description ##
This is a simple course work in Java aiming to demonstrate knowledge over Java RMI. It consist of three separate programes comunicating through RMI: Application store server, Application store administration client, Application store end customer client.

### Application store server ###
Server which serves the information about the applications to the clients.

### Application store administration client ###
Provides ways to controll the server - starting, stopping and pausing.

### Application store end customer client ###
Provides end customers with an application with which they can download the available applications on the server.

## Installation instructions ##
In order to install the server and its customers do the following in the specified order:
1. Clone the GIT repository localy - git clone git@github.com:dobrinov/cscb576_app_store.git
2. Run the ANT job which will build the application and produce a JAR file - ant jar

## Starting the server ##
After the installation is completed the only thing, which has to be done is to execute the server.bat file. It will start the rmi registy and the server application.

## Operating ##

### Administration client ###

#### Starting the application ####
In order to start the administration client application execute administrator.bat. 

#### Starting the server ####
In order to start the server press the "start" button. Basically this only changes a state in the server application. It could be implemented so that the administration clien could be able to start the whole server.

#### Stopping the server ####
In order to stop the server press the "stop" button.

#### Pausing the server ####
In order to stop the server press the "pause" button.

#### Adding new applications ####
This is currently not implemented. Also no application binaries are available, but it could be done so that a directory with executables is present and by defining an entry in the applications.db file this executable can be exposed to the customer client application.

### Customer client ###

#### Starting the application ####
In order to start the administration client application execute customer.bat. 

#### Download an application ####
This is not implemented in the current version, but there could be a link for each entry in the applications table, which will make possible to download it.