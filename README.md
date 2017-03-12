/////////////////////////////////////////////////
Assignment 3
/////////////////////////////////////////////////
To run DTPacMan:
In the executor class, instantiate DTPacMan and pass it into
whatever function you are using. If you want to create a new 
DT file, create it in data/decision and then point to it within 
the DTPacMan constructor.
*** DTPacMan is pointing to the dt file we want to use for the test ***
/////////////////////////////////////////////////
To run RAPPacMan:
In the executor class, instantiate RAPPacMAn and pass it into
whatever function you are using. If you want to create a new
RAP file, create it in data/rap and then point to it within
the RAPPacMan constructor.
*** RAPPacMan is pointing to the rap file we want to use for the test ***
/////////////////////////////////////////////////
Decision Tree Code:
Decision Tree class controls the execution. Instantiated with a String
pointing to the dt file to read. It will create the decision tree and 
then will return a move when makeDecision is called with a game state.

Other classes are used as nodes or leafs within the tree.
/////////////////////////////////////////////////
RAP Code:
RAP class controls the execution. Instantiated with a String denoting
the rap file to read. Creates map of RAPInstance. Call execute with 
a game state and it will return the next move to make.

Other classes are the primitive or task net RAPs and some helper classes.
/////////////////////////////////////////////////
Documentation for the DT file structure is available in the 
DTfile file.
/////////////////////////////////////////////////
Documentaiton for the RAP file structure is available in the 
RAPfile file.
/////////////////////////////////////////////////