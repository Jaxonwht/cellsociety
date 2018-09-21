Julia
>I will be working on UIManager this morning
 we have to make a change in ReadXML - I will update you with what I change
 I made 2 changes I think it is working now - in readState method, stateNumber was not being read in, I changed this line so that it uses .getNamedItem like you use for stateName, instead of trying to get stateNumber with returnInt method which uses .getElementsByTagName
 2nd change - I was getting index out of bounds exceptions so I looked at how you were determining row and col from index and I changed it so you do not add 1 to the index anymore.. assuming the grid is supposed to look like this for 5x5 then this is working now. merging these changes with master branch. submitted a merge request with my current changes but did not accept it
                                                                                                                                                                                                                                                                                                                  all the animation function we need (pause/step/speed etc) is working but I did not build the actual grid of cells yet, we can discuss how we want to control the image view of each cell when we meet
                                                                                                                                                                                                                                                                                                                  also the version of the code in my merge request can be run if you want to see it for yourself, sorry if the code is not easily readable as I did not have time to completely clean it up before pushing!

Yunhao
>I see your changes, they are great!

Haotian
>@Julia Saveliff I don’t quite understand the not adding 1 part we’lol talk later. Also do you all think removing indexes from cell cause any problem?
 You can try to merge my code to master and see if there’s any problem

Julia
>Im not at my computer but will do later

Yunhao
>@王皓天 she fixed the bug in my XML shouldnt add 1 if add 1 the grid will not be constructed correctly
 I'm starting on writing codes for specific simulations. @Haotian Wang I see ur determineNextStates() method in Game of life clas that you update cells one by one.
 I am not sure whether we should check cell one by one or update simultaneously, we can discuss about that later.

Haotian
>This is done simultaneously
 We can’t update one by one, against the rule

Julia
>yeah i have it
 the current problem i am having is that the simulation grid does not change until about the 300th step, and after that the entire grid stays black
 that may require some debugging in Rule.determineNextStates() and Rule.updateGrid()

Haotian
>Hmmmm do you print the step to get the number three indeed? ok @Yunhao  Qing, we decided to change from using ImageView to Shape
                                                             ImageView eats up too much memory. and @Yunhao  Qing your setburningtime reports error
                                                                                                because now at the testing stage, we hardcorded gameoflifecell into Grid. so if you do grid.item(i, j), it returns a gameoflifecell. @Julia Saveliff how do you get the program to run with all the errors, I want to test whether shapes work

Julia
>I have just been commenting out lines that throw errors
 Since none of the errors are in code that is needed to run the simulation

Haotian
>and now run is not clickable
 is it because there are two packages?

Julia
>Hm
 Is your branch updated? I can take a look at it

Haotian
>yeah
 i managed to run even though intellij told me "simulation.ca doesn't exist"
 but i managed to run
 and
 with shapes it's super fast