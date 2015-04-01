import java.net.URL;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

public class ClasspathResolver implements LSResourceResolver {

	private String prefix = "/schemas";
	private final LSResourceResolver parentResolver;

	public ClasspathResolver(LSResourceResolver resourceResolver) {
		this.parentResolver = resourceResolver;
	}

	public LSInput resolveResource(final String type, final String namespaceURI, final String publicId,
			final String systemId, final String baseURI) {

		String classpathRelatedSystemId = systemId;
		try {
			URL url = this.getClass().getResource(classpathRelatedSystemId);
			if (url == null) {
				if (!classpathRelatedSystemId.startsWith("/")) {
					classpathRelatedSystemId = "/" + systemId;
					if (!classpathRelatedSystemId.startsWith(prefix)) {
						classpathRelatedSystemId = prefix + systemId;
					}
					url = this.getClass().getResource(classpathRelatedSystemId);
				}
			}
			if (url == null) {
				return delegateResolution(type, namespaceURI, publicId, systemId, baseURI);
			} else {
				return new CustomLSInput(publicId, systemId, url.openStream());
			}
		} catch (Exception e) {
			return delegateResolution(type, namespaceURI, publicId, systemId, baseURI);
		}
	}

	private LSInput delegateResolution(String type, String namespaceURI, String publicId, String systemId,
			String baseURI) {
		if (parentResolver != null) {
			return parentResolver.resolveResource(type, namespaceURI, publicId, systemId, baseURI);
		}
		return null;

	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
