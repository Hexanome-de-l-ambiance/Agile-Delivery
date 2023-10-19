package view;

public class main {

    public static void main(String[] args) {
        XMLOpener opener = XMLOpener.getInstance();
        try {
            opener.ReadFile("data/xml/smallMap.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}