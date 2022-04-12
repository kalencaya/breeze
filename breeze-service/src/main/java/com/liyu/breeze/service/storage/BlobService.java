package com.liyu.breeze.service.storage;

import io.minio.Result;
import io.minio.messages.Item;

import java.io.InputStream;

public interface BlobService {

    boolean exists(String fileName);

    Iterable<String> list(String prefix);

    InputStream get(String fileName);

    void upload(String fileName, String path);

    void upload(String fileName, String contentType, InputStream inputStream);

    void delete(String fileName);

}
