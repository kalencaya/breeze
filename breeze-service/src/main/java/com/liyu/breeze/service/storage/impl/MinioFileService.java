package com.liyu.breeze.service.storage.impl;

import com.liyu.breeze.common.exception.Rethrower;
import com.liyu.breeze.service.storage.BlobService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;

import java.io.InputStream;
import java.io.OutputStream;

public class MinioFileService implements BlobService {

    private final MinioClient client;

    /**
     * MinIO server playground <a href="https://play.min.io"/>. Feel free to use this service for test and development.
     */
    public MinioFileService() {
        this.client = MinioClient.builder()
                .endpoint("https://play.min.io")
                .credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG")
                .build();
    }

    @Override
    public boolean exists(String path) {
        return false;
    }

    @Override
    public InputStream get(String path) {
        try {
            return client.getObject(GetObjectArgs.builder().bucket("my-bucketname").object(path).build());
        } catch (Exception e) {
            Rethrower.throwAs(e);
            return null;
        }
    }

    @Override
    public void upload(String path) {

    }

    @Override
    public void upload(OutputStream outputStream) {

    }

    @Override
    public void delete(String path) {

    }
}
