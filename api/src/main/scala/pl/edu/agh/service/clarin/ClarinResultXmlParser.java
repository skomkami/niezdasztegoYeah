package pl.edu.agh.service.clarin;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import pl.edu.agh.common.AppLogger;
import pl.edu.agh.model.TokenModel;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ClarinResultXmlParser {

    private static final List<String> INCLUDE_TYPES = List.of(
            "nam_loc",
            "nam_liv",
            "nam_org"
    );

    public List<TokenModel> parse(String resultXml) throws ParserConfigurationException {
        DocumentBuilderFactory dbf = getDocumentBuilderFactory();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(resultXml)));
            return parseXml(doc.getDocumentElement());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static DocumentBuilderFactory getDocumentBuilderFactory() throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        dbf.setNamespaceAware(true);
        dbf.setFeature("http://xml.org/sax/features/namespaces", false);
        dbf.setFeature("http://xml.org/sax/features/validation", false);
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        return dbf;
    }

    private List<TokenModel> parseXml(Element root) {
        List<TokenModel> result = new ArrayList<>();
        List<Element> tokens = getChildElementsWithName(root, "tok");
        tokens.forEach(token -> {
            Element orth = getSingleChildElementWithName(token, "orth");
            List<Element> annotations = getChildElementsWithName(token, "ann");
            annotations.stream()
                    .filter(annotation -> annotation.getTextContent().equals("1"))
                    .forEach(activeAnnotation -> {
                        String type = activeAnnotation.getAttributes().getNamedItem("chan").getTextContent();
                        if (INCLUDE_TYPES.contains(type)) {
                            Node headNode = activeAnnotation.getAttributes().getNamedItem("head");
                            if (headNode == null) {
                                result.get(result.size() - 1).text += " " + orth.getTextContent();
                            } else {
                                result.add(new TokenModel(
                                        orth.getTextContent(),
                                        type
                                ));
                            }
                        }
                    });
        });

        return result;
    }

    private static List<Element> getChildElementsWithName(Element parent, String name) {
        List<Element> result = new ArrayList<>();
        NodeList nodeList = parent.getElementsByTagName(name);
        for (int i = 0; i < nodeList.getLength(); i++) {
            result.add((Element) nodeList.item(i));
        }
        return result;
    }

    private static Element getSingleChildElementWithName(Element parent, String name) {
        return (Element) parent.getElementsByTagName(name).item(0);
    }

}
