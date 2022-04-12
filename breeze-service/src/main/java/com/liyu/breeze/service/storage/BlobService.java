package com.liyu.breeze.service.storage;

import java.io.InputStream;

public interface BlobService {

    boolean exists(String fileName);

    void list(String prefix);

    InputStream get(String fileName);

    void upload(String fileName, String path);

    void upload(String fileName, String contentType, InputStream inputStream);

    void delete(String fileName);

}
