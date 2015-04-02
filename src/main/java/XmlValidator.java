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
		if (args.length == 1) {
			boolean isValid = XmlValidator.validateXMLSchema(args[0]);
			if (isValid) {
				System.out.printf("file %s is ST.86 compliant\n", args[0]);
			}
		}

	}

	public static boolean validateXMLSchema(String xmlPath){
           
          try {
              final SchemaFactory schemaFactory = 
                      SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
              schemaFactory.setResourceResolver(new ClasspathResolver(schemaFactory.getResourceResolver()));
              Schema schema = schemaFactory.newSchema();
              Validator validator = schema.newValidator();
              validator.setResourceResolver(schemaFactory.getResourceResolver());
              validator.validate(new StreamSource(new File(xmlPath)));
          } catch (IOException | SAXException e) {
              System.out.println("Exception: "+e.getMessage());
              return false;
          }
          return true;
      }
}
