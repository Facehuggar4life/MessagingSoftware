--------------------------------------------------------------------
Messaging Software
By Owen Abaza
--------------------------------------------------------------------
INTRODUCTION

Messaging software is a p2p messaging application written in Java using the a Swing GUI and the TCP network protocal.
The application offers a simple UI and a concise method of communication
--------------------------------------------------------------------
INSTALLATION

To install the application unpack the zip and move the MessagingSoftware-master directory that contains the Message directory and this file
to wherever you please.
--------------------------------------------------------------------
COMPILATION AND RUNNING

To compile and run the program navigate to the MessagingSoftware-master\Message in console directory and run the command "javac -d . *.java",
then to run the program run the command "java -cp . Message.Main". A few items will be printed to the console and the GUI should 
then draw itself.
--------------------------------------------------------------------
GUI EXPLAINATION

The GUI consists of many symbols that could confuse some users. Here is a breakdown of the components and their respective functions:
"+" This button is used to add a contact. The user will be prompted to enter the contact's IP and name after pressing this button
"-" This button will disconnect from the current contact and remove the contact
"↻"This button will cause the program to attempt to reconnect to the current contact
"▲" This will send the contents of the adjacent text field to the current contact
Additionally, the combo box beneath the "+" and "-" signs is used to select a contact
--------------------------------------------------------------------
