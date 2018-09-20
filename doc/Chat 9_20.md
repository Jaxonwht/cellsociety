Julia
>I have created a UIManager class and added some simple functionality. If you comment out the initializeSimulation method in CA (just to ignore errors running), then you can run CA and a window pops up allowing user to choose a file, displays what file was chosen, and another button called start. I will clean up the code tomorrow and also add build the simulation scene so that i can use set on action with the startButton (right now nothing happens if you click it). I think that code should happen within the UIManager
Also I had to make a small change to ReadXML - the method returnInt was void so it was throwing errors when I tried to run CA, so I just made it return the Integer value that was determined in that method
All changes are pushed

Haotian
>Thx!
Does the button work as expected?
I was thinking about a UI class, the problem is if I put everything like initializeSimulation, or reloadSimulation
I have to pass a lot of stuff from main to that class, including... like everything
09:11

Julia
>Ok. I will keep working with it this morning and see what kind of dependencies come up and maybe rethink the structure

Julia
>I am getting pretty lost with connecting XMLreader to the UI
Because so much needs to happen in the chooseXMLButton.setOnAction method
And I am getting exceptions from the XMLreader that I don't understand
I will keep trying to sort it out and update you in lecture
Ok quick update I am debugging in ReadXML, there are some typos that are causing type errors
There are some problems I'm not sure how to fix. For example the class reads in everything from the files as ints. But at least one value (probCatch) will not be an int
Another problem is reading in stateNumber from XML file
I added a debugging line to ReadXML so that every time it tries to get a value from the xml file, it prints "Fetching value of <str>" then we can see that the error was caused by the last printed str attribute being read in from the file
Yunhao will you be able to debug this at some point today?