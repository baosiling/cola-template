package com.netflix.discovery.provider;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ISerializer {

    Object read(InputStream is, Class type, MediaType mediaType) throws IOException;

    void write(Object object, OutputStream os, MediaType mediaType) throws IOException;
}
