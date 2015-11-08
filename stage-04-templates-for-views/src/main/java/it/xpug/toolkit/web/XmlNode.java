package it.xpug.toolkit.web;

import static java.lang.String.*;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.xpath.*;

import org.w3c.dom.*;
import org.xml.sax.*;

public class XmlNode {

	private final Node node;
	private String xml;

	public XmlNode(String xml) {
		this.xml = xml;
		Document w3cDocument = getW3CDocument(treatEntitiesAsText(xml));
		this.node = getRootElement(w3cDocument);
	}

  public XmlNode(InputStream stream) {
    this(readAll(stream));
  }

  private static String readAll(InputStream stream) {
    try {
      InputStreamReader reader = new InputStreamReader(stream);
      char[] buffer = new char[100*1000];
      int nread = reader.read(buffer);
      return String.copyValueOf(buffer, 0, nread);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean matchesXPath(String xpath) {
		return numberOfMatches(xpath) > 0;
	}

	public int numberOfMatches(String xpath) {
		return getW3CNodeList(xpath).getLength();
	}

	public XmlNode getNode(String xpath, Object ... args) {
		Node node = getW3cNode(format(xpath, args));
		return new XmlNode(node);
	}

	public List<XmlNode> getNodes(String xpath, Object ... args) {
		NodeList nodes = getW3CNodeList(format(xpath, args));
		List<XmlNode> result = new ArrayList<XmlNode>();
		for(int i=0; i<nodes.getLength(); i++) {
			result.add(new XmlNode(nodes.item(i)));
		}
		return result ;
	}


	@Override
	public String toString() {
//		return node.toString();
		return this.xml;
	}

	@Override
	public int hashCode() {
		return node.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof XmlNode))
			return false;
		XmlNode other = (XmlNode) obj;

		if (!(this.node.getNodeType() == other.node.getNodeType()))
			return false;

		if (node.getNodeType() == Node.TEXT_NODE) {
			return this.getTextContent().equals(other.getTextContent());
		}

		boolean sameAttribute = true;
		if (this.node.hasAttributes()) {
			sameAttribute = other.node.hasAttributes() && sameAttributes(this.node.getAttributes(), other.node.getAttributes());
		}

		boolean sameName = this.getNodeName().equals(other.getNodeName());
		boolean sameChildren = this.getChildren().equals(other.getChildren());

		return sameName
			&& sameChildren
			&& sameAttribute;
	}

	private boolean sameAttributes(NamedNodeMap thisMap, NamedNodeMap otherMap) {
		boolean result = thisMap.getLength() == otherMap.getLength();
		for (int i=0; i<thisMap.getLength(); i++) {
			Node thisNode = thisMap.item(i);
			Node otherNode = otherMap.getNamedItem(thisNode.getNodeName());
			if (otherNode == null) {
				result = false;
			} else if (!otherNode.isEqualNode(thisNode)) {
				result = false;
			}
		}
		return result;
	}

	public List<XmlNode> getChildren() {
		List<XmlNode> result = new ArrayList<XmlNode>();
		NodeList childNodes = node.getChildNodes();
		for (int i=0; i<childNodes.getLength(); i++) {
			Node child = childNodes.item(i);
			if (isWhitespaceNode(child)) {
				continue;
			}
			result.add(new XmlNode(child));
		}
		return result;
	}

	public String getNodeName() {
		return node.getNodeName();
	}

	public String getTextContent() {
		return node.getTextContent();
	}

	public String getAttribute(String name) {
		return node.getAttributes().getNamedItem(name).getTextContent();
	}

	private String treatEntitiesAsText(String xml) {
		return xml.replaceAll("&", "&amp;");
	}

	private Node getRootElement(Document w3cDocument) {
		NodeList children = w3cDocument.getChildNodes();
		for (int i=0; i<children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE)
				return child;
		}
		throw new ElementNotFoundException("Unable to find root");
	}

	private boolean isWhitespaceNode(Node child) {
		return child.getNodeType() == Node.TEXT_NODE && isWhiteSpace(child.getTextContent());
	}

	private boolean isWhiteSpace(String text) {
		return text.matches("[ ]*");
	}

	private XmlNode(Node node) {
		this.node = node;
	}

	private NodeList getW3CNodeList(String xpathString) {
	    try {
	    	// see http://www.ibm.com/developerworks/library/x-javaxpathapi.html
	    	XPathFactory factory = XPathFactory.newInstance();
	    	XPath xpath = factory.newXPath();
			return (NodeList) xpath.evaluate(xpathString, node, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}

	private Node getW3cNode(String xpath) {
		NodeList nodes = getW3CNodeList(xpath);
		int length = nodes.getLength();
		if (length != 1) {
			throw new ElementNotFoundException(format("\"%s\": expected 1 node, found %d", xpath, length));
		}
		return nodes.item(0);
	}

	private Document getW3CDocument(String xml) {
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setValidating(false);
	    factory.setNamespaceAware(true);
	    try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			dontResolveEntities(builder);
			ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());
			return builder.parse(is);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void dontResolveEntities(DocumentBuilder builder) {
		builder.setEntityResolver(new EntityResolver() {
		    @Override
			public InputSource resolveEntity(String publicId, String systemId)
		        throws SAXException, IOException {
		        return new InputSource(new StringReader(""));
		    }
		});
	}

	@SuppressWarnings("serial")
	public class ElementNotFoundException extends RuntimeException {
		public ElementNotFoundException(String message) {
			super(message);
		}
	}
}