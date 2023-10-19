import java.io.File;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
public class main {

    public static void main(String[] args) {
        XMLOpener opener = new XMLOpener("./smallMap.xml");
        try {
            opener.ReadFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}