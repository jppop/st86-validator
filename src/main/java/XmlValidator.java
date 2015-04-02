import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlValidator {

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			System.out.printf("checking file %s", args[i]);
			try {
				List<SAXParseException> exceptions = XmlValidator.validateXMLSchema(args[i]);
				if (exceptions.isEmpty()) {
					System.out.printf(": compliant\n", args[i]);
				} else {
					System.out.println(": parsing failed.");
					for (SAXParseException pe : exceptions) {
						System.out.printf(" + line %d, col %d: %s\n", pe.getLineNumber(), pe.getColumnNumber(),
								pe.getMessage());
					}
				}
			} catch (SAXException | IOException e) {
				System.out.println(": fatal error: " + e.getMessage());
			}
		}
	}

	public static List<SAXParseException> validateXMLSchema(String xmlPath) throws SAXException, IOException {

		final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		schemaFactory.setResourceResolver(new ClasspathResolver(schemaFactory.getResourceResolver()));
		Schema schema = schemaFactory.newSchema();
		Validator validator = schema.newValidator();
		validator.setResourceResolver(schemaFactory.getResourceResolver());
		final List<SAXParseException> exceptions = new LinkedList<SAXParseException>();
		validator.setErrorHandler(new ErrorHandler() {
			@Override
			public void warning(SAXParseException exception) throws SAXException {
				exceptions.add(exception);
			}

			@Override
			public void fatalError(SAXParseException exception) throws SAXException {
				exceptions.add(exception);
			}

			@Override
			public void error(SAXParseException exception) throws SAXException {
				exceptions.add(exception);
			}
		});
		validator.validate(new StreamSource(new File(xmlPath)));
		return exceptions;
	}
}
