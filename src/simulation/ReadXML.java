package simulation;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class ReadXML {
    private String name;
    private int row;
    private int column;
    private int width;
    private int height;
    private int probCatch;
    private int reproductionFish;
    private int reproductionShark;
    private int threshold;
    private int[][] cellState;
    private HashMap<Integer,String> stateNum;
    Document document;



   /**
    * Example on how to use this Class;
    * ReadXML xmlreader = new ReadXML("Wator1.xml");
    * String name = xmlreader.getName(); 
    * //This will tell you whether the xml is gol, fire, wator or segregation.
    * int width = xmlreader.getWidth();
    * int height = xmlreader.getHeight();
    * row and column can be obtained similarly.
    * //If the xml is wator for example;
    * //int reproductionFish = xmlreader.getReproductionFish;
    * int[][] cellSate = xmlreader.getCellState
    * //This returns a 2D array with numbers like 0,1,2 to indicate cell state.
    * HashMap<Integer,String> stateNum = xmlreader.getStateNum;
    * //This returns something like {<0,empty><1,X><2,O>}, so one can track
    * //the corresponding stateNumber and stateName in the main program.
    */
    
    
    public ReadXML (File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.parse(file);
        document.getDocumentElement().normalize();
        stateNum = new HashMap<>();
        readGrid();
        readByType();
        readState();

    }

    private void readByType(){
        name = document.getElementsByTagName("name").item(0).getTextContent();
        if (name.equals("spreadingOfFire"))
            readFire();
        else if (name.equals("segregation"))
            readSegregation();
        else if (name.equals("wator"))
            readWator();
    }

    private void readState(){
        NodeList nodeList = document.getElementsByTagName("state");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            int stateNumber = returnInt("stateNumber");
            String stateName = node.getAttributes().getNamedItem("stateName").getNodeValue();
            stateNum.put(stateNumber,stateName);
            String temp = node.getTextContent();
            for (String s : temp.split(" ")){
                int index = Integer.valueOf(s)+1;
                cellState[index/column][index%column] = stateNumber;
            }
        }
    }

    private void readWator() {
        reproductionFish = returnInt("reproductionFish");
        reproductionShark = returnInt("reproductionShark");
    }
    

    private void readSegregation() {
        threshold = returnInt("threshold");
    }

    public void readFire(){
        probCatch = returnInt("probCatch");
    }

    public void readGrid(){
        width = returnInt("width");
        height = returnInt("height");
        row = returnInt("row");
        column = returnInt("column");
        cellState = new int[row][column];
    }
    
    private int returnInt(String str){
        return Integer.valueOf( document.getElementsByTagName(str).item(0).getTextContent());
    }

    public String getName(){return name;}

    public int getRow(){return row;}

    public int getColumn(){return column;}

    public int getWidth(){return width;}

    public int getHeight(){return height;}

    public int getThreshold(){return threshold;}

    public int getReproductionFish(){return reproductionFish;}

    public int getReproductionShark(){return reproductionShark;}

    public int getProbCatch(){return probCatch;}

    public int[][] getCellState(){return cellState;}

    public HashMap<Integer,String> getStateNum(){return stateNum;}
}


