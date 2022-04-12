package com.liyu.breeze.service.storage;

import java.io.InputStream;
import java.io.OutputStream;

public interface BlobService {

    boolean exists(String path);

    InputStream get(String path);

    void upload(String path);

    void upload(OutputStream outputStream);

    void delete(String path);

}
