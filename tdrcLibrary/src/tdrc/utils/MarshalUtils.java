/**
 * Copyright 2013 SPeCS Research Group.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. under the License.
 */

package tdrc.utils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.sun.xml.bind.IDResolver;

public class MarshalUtils {

    public static <T> T unmarshal(File fileSource, String sourceName, InputStream schemaFile, Class<T> rootType,
            Class<?> objectFactoryClass, boolean validate) throws JAXBException, SAXException {
        // String packageName, boolean validate) throws JAXBException, SAXException {
        // return unmarshal(new StreamSource(fileSource), sourceName, schemaFile, rootType, packageName, validate, null,
        return unmarshal(new StreamSource(fileSource), sourceName, schemaFile, rootType, objectFactoryClass, validate,
                null,
                null);
    }

    public static <T> T unmarshal(File fileSource, String sourceName, InputStream schemaFile, Class<T> rootType,
            Class<?> objectFactoryClass, boolean validate, ValidationEventHandler handler, IDResolver resolver)
            // String packageName, boolean validate, ValidationEventHandler handler, IDResolver resolver)
            throws JAXBException, SAXException {
        // return unmarshal(new StreamSource(fileSource), sourceName, schemaFile, rootType, packageName, validate,
        return unmarshal(new StreamSource(fileSource), sourceName, schemaFile, rootType, objectFactoryClass, validate,
                handler, resolver);
    }

    public static <T> T unmarshal(Source source, String sourceName, InputStream schemaFile, Class<T> rootType,
            Class<?> objectFactoryClass, boolean validate) throws JAXBException, SAXException {
        // String packageName, boolean validate) throws JAXBException, SAXException {
        return unmarshal(source, sourceName, schemaFile, rootType, objectFactoryClass, validate, null, null);
    }

    public static <T> T unmarshal(Source source, String sourceName, InputStream schemaFile, Class<T> rootType,
            Class<?> objectFactoryClass, boolean validate, ValidationEventHandler handler, IDResolver resolver)
            // String packageName, boolean validate, ValidationEventHandler handler, IDResolver resolver)
            throws JAXBException, SAXException {

        final SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);// W3C_XML_SCHEMA_NS_URI);
        // System.out.println("BEFORE MarshalUtils.unmarshal");
        // final JAXBContext jc = JAXBContext.newInstance(packageName);
        final JAXBContext jc = JAXBContext.newInstance(objectFactoryClass);
        // final JAXBContext jc = JAXBContext.newInstance(objectFactoryClass.getPackage().getName(),
        // JAXBContext.class.getClassLoader());
        // System.out.println("AFTER MarshalUtils.unmarshal");

        final Unmarshaller u = jc.createUnmarshaller();

        if (validate && schemaFile != null) {
            final Source schemaSource = new StreamSource(schemaFile);
            // SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            final Schema schema = sf.newSchema(schemaSource);
            u.setSchema(schema);
        }
        if (handler == null) {
            handler = new ValidationEventCollector();
        }

        u.setEventHandler(handler);
        if (resolver != null) {
            u.setProperty(IDResolver.class.getName(), resolver);
        }
        // u.setProperty("com.sun.xml.internal.bind.IDResolver", resolver2);
        // u.setProperty(UnmarshallerProperties.ID_RESOLVER, resolver);
        JAXBElement<T> jaxbEl = null;
        jaxbEl = u.unmarshal(source, rootType);

        return jaxbEl.getValue();
    }

    // public static <T> void marshal(T value, Class<T> elementClass, Class<?> packageName, QName q_name,
    public static <T> void marshal(T value, Class<T> elementClass, Class<?> packageClass, QName q_name,
            OutputStream oStream) throws JAXBException {

        final JAXBElement<T> jaxbEl = createRootElement(value, q_name, elementClass);
        // System.out.println("BEFORE MarshalUtils.marshal");
        // final JAXBContext jc = JAXBContext.newInstance(packageName);
        final JAXBContext jc = JAXBContext.newInstance(packageClass);
        // final JAXBContext jc = JAXBContext.newInstance(packageClass.getPackage().getName(),
        // JAXBContext.class.getClassLoader());
        // System.out.println("AFTER MarshalUtils.marshal");
        final Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        m.marshal(jaxbEl, oStream);

    }

    private static <T> JAXBElement<T> createRootElement(T value, QName q_name, Class<T> elementClass) {
        return new JAXBElement<>(q_name, elementClass, null, value);
    }
}
