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
public class ServerXmlHandler {

    private static File fXmlServerFile;
    private static File fXmlCardFile;
    private static Document parsedServerXml;
    private static Document parsedCardXml;
    private static ServerCardDirectory scd = ServerCardDirectory.getInstance();

    private static void log(String msg) {
        System.out.println(msg);
    }

    private static void createServerXMLFile() throws IOException {
        fXmlServerFile = new File("server/Server.xml");
        fXmlServerFile.createNewFile();
        Document serverFile;
        Element e = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            //use factory to get new instance of DocumentBuilder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //create new instance of the document
            serverFile = db.newDocument();

            //create root element of card list
            Element rootElement = serverFile.createElement("Users");

            serverFile.appendChild(rootElement);
            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty(OutputKeys.STANDALONE, "true");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                //send Document to file
                tr.transform(new DOMSource(serverFile), new StreamResult(new FileOutputStream(fXmlServerFile.getAbsolutePath())));
            } catch (IOException | TransformerException ex) {
                log("[!] XML Creation Error(folder permission error?): " + ex.toString());
            }

        } catch (ParserConfigurationException pce) {
            log("[!] Error Building XML Internals: " + pce.toString());
        }

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

    public static Boolean addUser(String username, String hashpassword) throws SAXException, IOException, ParserConfigurationException, TransformerConfigurationException, TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("server/Server.xml");

        Element root = document.getDocumentElement();

        Element newUsr = document.createElement("user");

        Element fXmlusername = document.createElement("username");
        fXmlusername.appendChild(document.createTextNode(username));
        newUsr.appendChild(fXmlusername);

        Element fXmlpass = document.createElement("password");
        fXmlpass.appendChild(document.createTextNode(hashpassword));
        newUsr.appendChild(fXmlpass);

        Element usrWins = document.createElement("wins");
        usrWins.appendChild(document.createTextNode("0"));
        newUsr.appendChild(usrWins);

        Element usrLosses = document.createElement("losses");
        usrLosses.appendChild(document.createTextNode("0"));
        newUsr.appendChild(usrLosses);

        root.appendChild(newUsr);

        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult("server/Server.xml");
        transformer.transform(source, result);

        //String[] test = getByUsername(username);
        //log("add user test: " + test[1]);
        return false;

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
                createServerXMLFile();
            }
            if (!fXmlCardFile.exists()) {
                createCardXMLFile();
            }
        } catch (IOException e) {
            log("[!] An error occurred creating Server.xml file: \n" + e.getMessage());
        }
        readServerXml(true);
        readCardXml(true);

    }

    public static void readServerXml(Boolean isFirstLoad) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            parsedServerXml = dBuilder.parse(fXmlServerFile);
            parsedServerXml.getDocumentElement().normalize();
            if (isFirstLoad) {
                log("[+] Loaded Server.xml");
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            log("Error parsing Server.xml: \n" + e.toString());
        }

    }

    public static void readCardXml(Boolean isFirstLoad) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            parsedCardXml = dBuilder.parse(fXmlCardFile);
            parsedCardXml.getDocumentElement().normalize();
            if (isFirstLoad) {
                log("[+] Loaded CardList.xml");
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            log("[!] Error parsing CardList.xml: \n" + e.toString());
        }
    }

    public static void updateCardList() {
        if (scd.cardList.isEmpty() == false) {
            scd.removeAll(scd.cardList);
        }
        readCardXml(false);
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
        NodeList nListRoot = parsedServerXml.getElementsByTagName("Users");
        for (int temp = 0; temp < nListRoot.getLength(); temp++) {
            Node nNode = nListRoot.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                NodeList nListUsers = nNode.getChildNodes();
                for (int i = 0; i < nListUsers.getLength(); i++) {
                    Node nNodeUser = nListUsers.item(i);
                    if (nNodeUser.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNodeUser;
                        if (eElement.getElementsByTagName("username").item(0).getTextContent().equals(username)) {
                            String hashedpw = eElement.getElementsByTagName("password").item(0).getTextContent();
                            String wins = eElement.getElementsByTagName("wins").item(0).getTextContent();
                            String losses = eElement.getElementsByTagName("losses").item(0).getTextContent();
                            String[] attributes = {username, hashedpw, wins, losses};
                            return attributes;
                        }
                    }
                }
            }
        }
        return null;
    }
}
