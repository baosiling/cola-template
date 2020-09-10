package com.netflix.discovery.converters;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

/**
 * An <tt>XStream</tt> specific implementation for serializing and deserializing
 * to/from JSON format.
 * <p>
 * This class also allows configuration of custom serializers with XStream.
 * </p>
 */
public class JsonXStream extends XStream {

    private static final JsonXStream s_instance = new JsonXStream();

    static {
        XStream.setupDefaultSecurity(s_instance);
        s_instance.allowTypesByWildcard(new String[]{
                "com.netflix.discovery.**", "com.netflix.appinfo.**"
        });
    }

    public JsonXStream() {
        super(new JettisonMappedXmlDriver() {
           private final NameCoder coder = initializeNameCoder();

           protected NameCoder getNameCoder() {
               return this.coder;
           }
        });
        registerConverter(new Converters.ApplicationConverter());
        registerConverter(new Converters.ApplicationsConverter());
        registerConverter(new Converters.DataCenterInfoConverter());
        registerConverter(new Converters.InstanceInfoConverter());
        registerConverter(new Converters.LeaseInfoConverter());
        registerConverter(new Converters.MetadataConverter());
        setMode(XStream.NO_REFERENCES);
        processAnnotations(new Class[]{InstanceInfo.class, Application.class, Applications.class});
    }

    public static JsonXStream getInstance() {
        return s_instance;
    }

    private static XmlFriendlyNameCoder initializeNameCoder() {
        return new XmlFriendlyNameCoder();
    }

}
