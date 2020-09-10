package com.netflix.discovery.converters;

import com.netflix.discovery.provider.ISerializer;
import com.thoughtworks.xstream.XStream;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A custom <tt>jersey</tt> provider implementation for eureka.
 *
 * <p>
 * The implementation uses <tt>Xstream</tt> to provide
 * serialization/deserialization capabilities. If the users to wish to provide their
 * own implementation they can do so by plugging in their own provider here
 * and annotating their classes with that provider by specifying the
 * {@link com.netflix.discovery.provider.Serializer} annotation.
 * </p>
 */
public class EntityBodyConverter implements ISerializer {

    private static final String XML = "xml";
    private static final String JSON = "json";

    @Override
    public Object read(InputStream is, Class type, MediaType mediaType) throws IOException {
        XStream xStream = getXStreamInstance(mediaType);
        if(xStream != null){
            return xStream.fromXML(is);
        }else{
            throw new IllegalArgumentException("Content-type: "
                    + mediaType.getType() + " is currently not supported for "
                    + type.getName());
        }
    }

    @Override
    public void write(Object object, OutputStream os, MediaType mediaType) throws IOException {
        XStream xStream = getXStreamInstance(mediaType);
        if(xStream != null){
            xStream.toXML(object, os);
        }else{
            throw new IllegalArgumentException("Content-type: "
                    + mediaType.getType() + " is currently not supported for "
                    + object.getClass().getName());
        }
    }

    private XStream getXStreamInstance(MediaType mediaType) {
        XStream xStream = null;
        if(JSON.equalsIgnoreCase(mediaType.getSubtype())){
            xStream = JsonXStream.getInstance();
        } else if (XML.equalsIgnoreCase(mediaType.getSubtype())){
            xStream = XmlXStream.getInstance();
        }
        return xStream;
    }
}
