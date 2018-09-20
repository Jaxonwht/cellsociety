package sample;


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

    public ReadXML (String str) throws ParserConfigurationException, IOException, SAXException {
        File file = new File(str);
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
        else if (name.equals("gameOfLife"))
            readGameOfLife();
        else if (name.equals("wator"))
            readWator();
    }

    private void readState(){
        NodeList nodeList = document.getElementsByTagName("state");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            int stateNumber = Integer.valueOf(node.getAttributes().getNamedItem("stateNumber").getNodeValue());
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
        reproductionFish = Integer.valueOf( document.getElementsByTagName("reproductionFish").item(0).getTextContent());
        reproductionShark = Integer.valueOf( document.getElementsByTagName("reproductionShark").item(0).getTextContent());
    }

    private void readGameOfLife() {
    }

    private void readSegregation() {
        threshold = Integer.valueOf( document.getElementsByTagName("threshold").item(0).getTextContent());

    }

    public void readFire(){
        probCatch = Integer.valueOf( document.getElementsByTagName("probCatch").item(0).getTextContent());

    }

    public void readGrid(){
        width = Integer.valueOf( document.getElementsByTagName("width").item(0).getTextContent());
        height = Integer.valueOf( document.getElementsByTagName("height").item(0).getTextContent());
        row = Integer.valueOf( document.getElementsByTagName("row").item(0).getTextContent());
        column = Integer.valueOf( document.getElementsByTagName("column").item(0).getTextContent());
        cellState = new int[row][column];
    }

    public String getName(){
        return name;
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int getThreshold(){return threshold;}

    public int getReproductionFish(){return reproductionFish;}

    public int getReproductionShark(){return reproductionShark;}

    public int getProbCatch(){return probCatch;}

    public int[][] getCellState(){return cellState;}

    public HashMap<Integer,String> getStateNum(){
        return stateNum;
    }
}


