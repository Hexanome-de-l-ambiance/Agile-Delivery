import java.io.File;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XMLOpener {

    private String pathname;

    public XMLOpener(String path) {
        this.pathname = path;
    }

    public void ReadFile() throws Exception {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new MyHandler();
            saxParser.parse(new File(pathname), handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class MyHandler extends DefaultHandler {

        private boolean inIntersection = false;
        private boolean inSegment = false;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if ("intersection".equals(qName)) {
                inIntersection = true;
                String id = attributes.getValue("id");
                String latitude = attributes.getValue("latitude");
                String longitude = attributes.getValue("longitude");
                System.out.println("Intersection - ID: " + id + ", Latitude: " + latitude + ", Longitude: " + longitude);
            } else if ("segment".equals(qName)) {
                inSegment = true;
                String destination = attributes.getValue("destination");
                String length = attributes.getValue("length");
                String name = attributes.getValue("name");
                String origin = attributes.getValue("origin");
                System.out.println("Segment - Destination: " + destination + ", Length: " + length + ", Name: " + name + ", Origin: " + origin);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ("intersection".equals(qName)) {
                inIntersection = false;
            } else if ("segment".equals(qName)) {
                inSegment = false;
            }
        }
    }
}