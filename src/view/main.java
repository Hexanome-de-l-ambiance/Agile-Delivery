import java.io.File;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
public class main {

    public static void main(String[] args) {
        XMLOpener opener = XMLOpener.getInstance();
        try {
            opener.ReadFile("smallMap.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}