/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.server;

import uk.ac.tees.v8084582.pocketbeasts.networkutil.ServerCardDirectory;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Card;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

/**
 *
 * @author v8084582
 */
public class serverXmlHandler {

    private static File fXmlServerFile;
    private static File fXmlCardFile;
    private static Document parsedServerXml;
    private static Document parsedCardXml;
    private static ServerCardDirectory scd = ServerCardDirectory.getInstance();

    private static void log(String msg) {
        System.out.println(msg);
    }

    private static void createServerXMLFile() {
        fXmlServerFile = new File("server/Server.xml");

    }

    private static void createCardXMLFile() {
        fXmlCardFile = new File("server/CardList.xml");
        String[][] baseCards;
        // String[] card = {"ID", "Full Name", "mana cost", "base attack", "base health", "element", "type(s)(ie; beast:warrior"}

        //base earth cards
        String[] baseEarth1 = new String[]{"RABB", "Rabbit", "1", "1", "2", "earth", "beast"};
        String[] baseEarth2 = new String[]{"UPRI", "Undead Priest", "4", "3", "7", "earth", "beast:magic"};
        String[] baseEarth3 = new String[]{"TPSY", "Trainee Psychic", "1", "2", "1", "earth", "magic"};
        String[] baseEarth4 = new String[]{"GSOL", "Grass Soldier", "3", "3", "5", "earth", "warrior"};
        String[] baseEarth5 = new String[]{"CTHF", "Countryside Theif", "4", "2", "4", "earth", "rouge"};
        String[] baseEarth6 = new String[]{"GLBR", "Grassland Boar", "5", "3", "9", "earth", "beast:tank"};
        String[] baseEarth7 = new String[]{"HBDR", "Herbal Doctor", "3", "1", "4", "earth", "healer"};
        String[] baseEarth8 = new String[]{"CKNI", "Countryside Knight", "5", "5", "5", "earth", "warrior"};
        String[] baseEarth9 = new String[]{"PBAT", "Psychic Bat", "2", "3", "2", "earth", "beast:magic"};
        String[] baseEarth10 = new String[]{"RMAN", "Rat Man", "4", "5", "3", "earth", "beast:warrior"};
        String[] baseEarth11 = new String[]{"HSHM", "Hardened Shaman", "5", "3", "5", "earth", "healer:warrior"};
        String[] baseEarth12 = new String[]{"GGOB", "Grass Goblin", "2", "3", "3", "earth", "beast:rouge"};

        //base fire cards
        String[] baseFire1 = new String[]{"FLIZ", "Fire Lizard", "1", "1", "2", "fire", "beast"};
        String[] baseFire2 = new String[]{"FZOM", "Flaming Zombie", "4", "3", "7", "fire", "beast:magic"};
        String[] baseFire3 = new String[]{"MTOD", "Magma Toad", "1", "3", "1", "fire", "beast"};
        String[] baseFire4 = new String[]{"FSOL", "Fire Soldier", "3", "3", "5", "fire", "warrior"};
        String[] baseFire5 = new String[]{"SARS", "Serial Arsonist", "4", "3", "3", "fire", "rouge"};
        String[] baseFire6 = new String[]{"EFHL", "Eastern Fire Healer", "3", "1", "4", "fire", "healer"};
        String[] baseFire7 = new String[]{"FSPN", "Fire Spinner", "5", "3", "9", "fire", "tank"};
        String[] baseFire8 = new String[]{"FGOB", "Fire Goblin", "2", "3", "3", "fire", "beast:rouge"};
        String[] baseFire9 = new String[]{"SMAG", "Scorched Magician", "2", "3", "3", "fire", "magic"};
        String[] baseFire10 = new String[]{"FBAT", "Flaming Bat", "4", "5", "2", "fire", "beast:magic"};
        String[] baseFire11 = new String[]{"FMAN", "Fire Man", "2", "3", "3", "fire", "beast:warrior"};
        String[] baseFire12 = new String[]{"SHLR", "Scorched Healer", "5", "3", "5", "fire", "healer:warrior"};

        //base water cards
        /*
                TODO
         */
        baseCards = new String[][]{
            baseEarth1,
            baseEarth2,
            baseEarth3,
            baseEarth4,
            baseEarth5,
            baseEarth6,
            baseEarth7,
            baseEarth8,
            baseEarth9,
            baseEarth10,
            baseEarth11,
            baseEarth12,
            baseFire1,
            baseFire2,
            baseFire3,
            baseFire4,
            baseFire5,
            baseFire6,
            baseFire7,
            baseFire8,
            baseFire9,
            baseFire10,
            baseFire11,
            baseFire12

        };
        try {
            fXmlCardFile.createNewFile();
        } catch (IOException ex) {
            log("[!] Card List File Creation Error(do you have the correct filesystem permissions?)\n" + ex.toString());
        }
        Document cardList;
        Element e = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            //use factory to get new instance of DocumentBuilder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //create new instance of the document
            cardList = db.newDocument();

            //create root element of card list
            Element rootElement = cardList.createElement("Cards");

            for (String[] baseCard : baseCards) {
                //create card node with Card ID
                Element cardNode = cardList.createElement(baseCard[0]);

                //create attributes for card node
                Element cardName = cardList.createElement("Name");
                cardName.appendChild(cardList.createTextNode(baseCard[1]));

                Element manaCost = cardList.createElement("Mana-Cost");
                manaCost.appendChild(cardList.createTextNode(baseCard[2]));

                Element baseAttack = cardList.createElement("Base-Attack");
                baseAttack.appendChild(cardList.createTextNode(baseCard[3]));

                Element baseHealth = cardList.createElement("Base-Health");
                baseHealth.appendChild(cardList.createTextNode(baseCard[4]));

                Element elementType = cardList.createElement("Element");
                elementType.appendChild(cardList.createTextNode(baseCard[5]));

                Element cardTypes = cardList.createElement("Card-Type");
                cardTypes.appendChild(cardList.createTextNode(baseCard[6]));

                //append elements to card node
                cardNode.appendChild(cardName);
                cardNode.appendChild(manaCost);
                cardNode.appendChild(baseAttack);
                cardNode.appendChild(baseHealth);
                cardNode.appendChild(elementType);
                cardNode.appendChild(cardTypes);

                //append cardnode to root element
                rootElement.appendChild(cardNode);

            }
            //append root element to Document
            cardList.appendChild(rootElement);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty(OutputKeys.STANDALONE, "true");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                //send Document to file
                tr.transform(new DOMSource(cardList), new StreamResult(new FileOutputStream(fXmlCardFile.getAbsolutePath())));
            } catch (IOException | TransformerException ex) {
                log("[!] XML Creation Error(folder permission error?): " + ex.toString());
            }

        } catch (ParserConfigurationException pce) {
            log("[!] Error Building XML Internals: " + pce.toString());
        }

    }

    public static void initialLoadXml() {
        fXmlServerFile = new File("server/Server.xml");
        fXmlCardFile = new File("server/CardList.xml");
        Path path = Paths.get("server");
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            if (!fXmlServerFile.exists()) {
            }
            if (!fXmlCardFile.exists()) {
                createCardXMLFile();
            }
        } catch (IOException e) {
            log("[!] An error occurred creating Server.xml file: \n" + e.getMessage());
        }
        readServerXml();
        readCardXml();
        updateCardList();

    }

    public static void readServerXml() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            parsedServerXml = dBuilder.parse(fXmlServerFile);
            parsedServerXml.getDocumentElement().normalize();
            log("[+] Loaded Server.xml");
        } catch (IOException | ParserConfigurationException | SAXException e) {
            log("Error parsing Server.xml: \n" + e.toString());
        }

    }

    public static void readCardXml() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            parsedCardXml = dBuilder.parse(fXmlCardFile);
            parsedCardXml.getDocumentElement().normalize();
            log("[+] Refreshed CardList.xml");
        } catch (IOException | ParserConfigurationException | SAXException e) {
            log("[!] Error parsing CardList.xml: \n" + e.toString());
        }
    }

    public static void updateCardList() {
        if(scd.cardList.isEmpty() == false){
            scd.removeAll(scd.cardList);
        }
        readCardXml();
        NodeList nl;
        nl = parsedCardXml.getElementsByTagName("Cards").item(0).getChildNodes();
        String cardList = "";
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            //fix for whitespace issue creating extra nodes
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                NodeList attList = node.getChildNodes();
                String[] cardAttArr = new String[7];
                cardAttArr[0] = node.getNodeName();
                //fix for whitespace nodes messing with for loop
                int cardRef = 0;
                for (int j = 0; j < attList.getLength(); j++) {
                    Node attNode = attList.item(j);
                    if (attNode.getNodeType() == Node.ELEMENT_NODE) {
                        cardRef++;
                        cardAttArr[cardRef] = attList.item(j).getTextContent();
                    }
                    
                }
                scd.add(new Card(cardAttArr[0], cardAttArr[1], Integer.parseInt(cardAttArr[2]), Integer.parseInt(cardAttArr[3]), Integer.parseInt(cardAttArr[4]), cardAttArr[5], cardAttArr[6]));
                
                //cardList = cardList + Card + "|";
            }
        }
         log("" + scd.cardList.size());
    }

    public static String[] getByUsername(String username) {
        NodeList nList = parsedServerXml.getElementsByTagName("user");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("username").item(0).getTextContent().equals(username)) {
                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    String password = eElement.getElementsByTagName("password").item(0).getTextContent();
                    String wins = eElement.getElementsByTagName("wins").item(0).getTextContent();
                    String losses = eElement.getElementsByTagName("losses").item(0).getTextContent();
                    String status = eElement.getElementsByTagName("status").item(0).getTextContent();
                    String[] attributes = {name, username, password, wins, losses, status};
                    return attributes;
                }
            }
        }
        return null;
    }
}
