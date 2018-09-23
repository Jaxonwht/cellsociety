package simulation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * @author Julia Saveliff, Yunhao Qing, Haotian Wang
 */
public class ReadXML {
    private String name;
    private int row;
    private int column;
    private int width;
    private int height;
    private int[][] cellState;
    private List<Double> extraParameters;
    Document document;
    /*
    private double probCatch;
    private double probGrowth;
    private double burningCount;
    private double reproductionFish;
    private double reproductionShark;
    private double threshold;
    private HashMap<Integer,String> stateNum;
    */


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
        this.name = returnString("name");
        this.extraParameters = new ArrayList<>();
        readGrid();
        readState();
        readExtraParameters();
        // stateNum = new HashMap<>();
        // readByType();
    }

    private void readState() {
        NodeList nodeList = document.getElementsByTagName("state");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            int stateNumber = Integer.parseInt(node.getAttributes().getNamedItem("stateNumber").getNodeValue());
            // String stateName = node.getAttributes().getNamedItem("stateName").getNodeValue();
            // stateNum.put(stateNumber,stateName);
            String temp = node.getTextContent();
            for (String s : temp.split(" ")){
                int index = Integer.parseInt(s);
                cellState[index/column][index%column] = stateNumber;
            }
        }
    }

    /**
     * Return the extra parameters specified in the XML file if there is any.
     */
    private void readExtraParameters() {
        String parameters = returnString("extraParameters");
        if (!parameters.equals("")) {
            String[] parameterList = parameters.split(" ");
            for (String para : parameterList) {
                this.extraParameters.add(returnDouble(para));
            }
        }
    }

    private void readGrid(){
        width = returnInt("width");
        height = returnInt("height");
        row = returnInt("row");
        column = returnInt("col");
        cellState = new int[row][column];
    }
    
    private int returnInt(String tag) {
        return Integer.parseInt(returnString(tag));
    }
    
    private double returnDouble(String tag) {
        return Double.parseDouble(returnString(tag));
    }

    private String returnString(String tag) {
        return document.getElementsByTagName(tag).item(0).getTextContent();
    }

    public List<Double> getExtraParameters() {
        return this.extraParameters;
        /*
        List<Double> extraParameters = new ArrayList<>();
        if (name.equals("SpreadingOfFire")) {
            extraParameters.add(probCatch);
            extraParameters.add(probGrowth);
            extraParameters.add(burningCount);
        } else if (name.equals("Segregation")) {
            extraParameters.add(threshold);
        } else if (name.equals("Wator")) {
            extraParameters.add(reproductionFish);
            extraParameters.add(reproductionShark);
        }
        */
    }

    public String getName(){return name;}

    public int getRow(){return row;}

    public int getColumn(){return column;}

    public int getWidth(){return width;}

    public int getHeight(){return height;}

    public int[][] getCellState(){return cellState;}
    /*
    private void readByType(){
        name = document.getElementsByTagName("name").item(0).getTextContent();
        if (name.equals("SpreadingOfFire"))
            readFire();
        else if (name.equals("Segregation"))
            readSegregation();
        else if (name.equals("Wator"))
            readWator();
    }

    public double getThreshold(){return threshold;}

    public double getReproductionFish(){return reproductionFish;}

    public double getReproductionShark(){return reproductionShark;}

    public double getProbCatch(){return probCatch;}

    public HashMap<Integer,String> getStateNum(){return stateNum;}

    private void readWator() {
        reproductionFish = returnDouble("reproductionFish");
        reproductionShark = returnDouble("reproductionShark");
    }


    private void readSegregation() {
        threshold = returnDouble("threshold");
    }

    public void readFire(){
        probCatch = returnDouble("probCatch");
        probGrowth = returnDouble("probGrowth");
        burningCount = returnDouble("burningCount");
    }
    */
}


