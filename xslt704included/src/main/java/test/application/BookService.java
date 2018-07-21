/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package test.application;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import test.entities.Book;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;
import java.util.Locale;

@Stateless
public class BookService {

    @PersistenceContext(unitName = "book-pu")
    private EntityManager entityManager;

    public void addBook(Book book)
    {
      entityManager.persist(book);
    }

    public List<Book> getAllBooks()
    {
        CriteriaQuery<Book> cq = entityManager.getCriteriaBuilder().createQuery(Book.class);
        cq.select(cq.from(Book.class));
        return entityManager.createQuery(cq).getResultList();
    }
    
    public void test1() {
		System.out.println(Locale.getDefault().getDisplayName());
		System.out.println(getXMLParse("<someXml>BLah</someXml>", "/xslt/test1.xsl"));
	}
    
    public void test2() {
		System.out.println(Locale.getDefault().getDisplayName());
		System.out.println(getXMLParse("<someXml>BLah</someXml>", "/xslt/test2.xsl"));
	}

	private String getXMLParse(String xml, String xslpath) {
		ClassLoader classLoader = this.getClass().getClassLoader();
//		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL filepath = classLoader.getResource(xslpath.substring(1));
		return getXMLParse(xml, filepath);
	}

	private String getXMLParse(String xml, URL filepath) {
		try {
			if (filepath != null) {
				// File xslFile = new File(filepath);
				Reader xsl = new InputStreamReader(filepath.openStream());
//				TransformerFactory transformerfactory = TransformerFactory.newInstance(
//						"org.apache.xalan.processor.TransformerFactoryImpl",
//						Thread.currentThread().getContextClassLoader());
//				TransformerFactory transformerfactory = TransformerFactory.newInstance(
//						"org.apache.xalan.processor.TransformerFactoryImpl", this.getClass().getClassLoader());
				TransformerFactory transformerfactory = TransformerFactory.newInstance();
				System.out.println(transformerfactory.getClass().getName());
				// transformerfactory.setURIResolver(new MyUriResolver(prefix));
				StreamSource ssXsl = new StreamSource(xsl);
				ssXsl.setSystemId(filepath.toExternalForm());
				Templates templates = transformerfactory.newTemplates(ssXsl);
				Transformer transformer = templates.newTransformer();
//				if (parameters != null) {
//					Iterator<String> iterator = parameters.keySet().iterator();
//					while (iterator.hasNext()) {
//						String key = iterator.next();
//						String value = parameters.get(key);
//						if (value != null && !value.equals("")) {
//							transformer.setParameter(key, value);
//						}
//					}
//				}
				StringReader reader = new StringReader(xml);
				StringWriter writer = new StringWriter();
				transformer.transform(new StreamSource(reader), new StreamResult(writer));
				StringBuffer out = new StringBuffer(writer.toString());
				writer.close();
				reader.close();
				return out.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
