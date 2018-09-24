cell society
====

This project implements a cellular automata simulator.

Authors in alphabetical order: Julia Saveliff, Haotian Wang, Yunhao Qing

### Timeline

Start Date: 09/15/2018

Finish Date: 09/23/2018

Hours Spent:

### Primary Roles
* CA: Julia Saveliff
* UIManager: Julia Saveliff
* Grid: Julia Saveliff
* Cell: Haotian Wang
    * GameOfLifeCell: Haotian Wang
    * SegregationCell: Haotian Wang
    * SpreadingOfFireCell: Yunhao Qing
    * WartorCell: Yunhao Qing
* Rule: Haotian Wang
    * GameOfLifeRule: Haotian Wang
    * SegregationRule: Haotian Wang, Julia Saveliff
    * SpreadingOfFireRule: Yunhao Qing
    * WatorRule: Yunhao Qing
* ReadXML: Julia Saveliff, Yunhao Qing, Haotian Wang
* XML format: Yunhao Qing

### Resources Used

###### Images
* [alive.gif](https://commons.wikimedia.org/wiki/File:Solid_white.png)
* [dead.gif](https://commons.wikimedia.org/wiki/File:Solid_black.png)

###### Articles
* [Schelling's Model of Segregation
](http://nifty.stanford.edu/2014/mccown-schelling-model-segregation/)
* [Oracle documentation on Circle](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/shape/Circle.html)
* [Java: Self for static method calls within the same class](https://stackoverflow.com/questions/22700944/java-self-for-static-method-calls-within-the-same-class)
* [Calling a subclass method from superclass](https://stackoverflow.com/questions/10021603/calling-a-subclass-method-from-superclass)
* [Is there a way to instantiate a class by name in Java?](https://stackoverflow.com/questions/9886266/is-there-a-way-to-instantiate-a-class-by-name-in-java)
* [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway's_Game_of_Life#Rules)
* [Oracle documentation on Class](https://docs.oracle.com/javase/7/docs/api/java/lang/Class.html#getConstructor(java.lang.Class...))
* [Initialize a static final field in the constructor](https://stackoverflow.com/questions/5093744/initialize-a-static-final-field-in-the-constructor)
* [Integer.class vs int.class
](https://l.messenger.com/l.php?u=https%3A%2F%2Fstackoverflow.com%2Fquestions%2F22470985%2Finteger-class-vs-int-class&h=AT0oxh3T6S7cgm3CMzhRzM-bvukfJYa5z3dXlGkvSAJkB-cd-ucubCPXM4pLevC69Rc95Srq_DW-I0h1FdTZsHNwYd470REraRFeRU-z1YK_47OpJFGXvmkI6ulIDw)
* [How to permanently remove few commits from remote branch
](https://stackoverflow.com/questions/3293531/how-to-permanently-remove-few-commits-from-remote-branch)
* [Split() String method in Java with examples
](https://www.geeksforgeeks.org/split-string-java-examples/)
* [Is there a way to instantiate a class by name in Java?
](https://stackoverflow.com/questions/9886266/is-there-a-way-to-instantiate-a-class-by-name-in-java)
* [How to convert Double to int directly? [duplicate]
](https://stackoverflow.com/questions/5404149/how-to-convert-double-to-int-directly)
* [Oracle documentation on Random](https://docs.oracle.com/javase/7/docs/api/java/util/Random.html)


### Running the Program

Main class: CA

Data files needed: 

* `simulation/UI_text.properties` required to set all user interface text

* XML files to run simulation with:

    * `data/fire.xml`
    * `data/fireNoRegrowth.xml`
    * `data/gameOfLife.xml`
    * `data/gameOfLifeBig.xml`
    * `data/segregation.xml`
    * `data/segregationBig.xml`
    * `data/Wator.xml`
    * `data/Wator.xml`

Interesting data files:

* `data/gameOfLifeFlicker.xml` oscillates between two states
* `data/gameOfLifeReappear.xml` involves a reappearing pattern of states
* `data/WatorLonelyShark.xml` shows what happens when there's only one non-reproducing shark

Features implemented:

Assumptions or Simplifications:

* Simulation automatically stops after 500 generations 

* If a cell is listed as two different states in the XML file, it will be assigned whichever 
state is read last

* To avoid potential collisions in simulations where cells are to "move" about the grid, 
the current state and next state of different cells may be compared

Known Bugs:

* There are certain cases for invalid XML values that are not checked. For example, if a cell is assigned outside
of the grid's dimensions or if a cell goes unassigned

* If during a simulation, the user presses the `Load new simulation` button, but then just
presses `Start simulation` without loading a file, the simulation screen will not clear

Extra credit:


### Notes


### Impressions

