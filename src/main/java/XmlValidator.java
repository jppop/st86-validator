import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class XmlValidator {

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			System.out.printf("checking file %s", args[i]);
			try {
				XmlValidator.validateXMLSchema(args[i]);
				System.out.printf(": compliant\n", args[i]);
			} catch (SAXException | IOException e) {
				System.out.println(": error: " + e.getMessage());
			}
		}
	}

	public static void validateXMLSchema(String xmlPath) throws SAXException, IOException {

		final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		schemaFactory.setResourceResolver(new ClasspathResolver(schemaFactory.getResourceResolver()));
		Schema schema = schemaFactory.newSchema();
		Validator validator = schema.newValidator();
		validator.setResourceResolver(schemaFactory.getResourceResolver());
		validator.validate(new StreamSource(new File(xmlPath)));
	}
}
