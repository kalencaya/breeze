package com.liyu.breeze.service.storage.impl;

import com.liyu.breeze.common.exception.Rethrower;
import com.liyu.breeze.service.storage.BlobService;
import io.minio.*;
import io.minio.messages.Item;

import java.io.InputStream;
import java.security.InvalidKeyException;

public class MinioFileService implements BlobService {

    private final String bucket;
    private final MinioClient client;

    /**
     * MinIO server playground <a href="https://play.min.io"/>. Feel free to use this service for test and development.
     */
    public MinioFileService(String bucket) {
        this.bucket = bucket;
        this.client = MinioClient.builder()
                .endpoint("https://play.min.io")
                .credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG")
                .build();
    }

    @Override
    public void list(String prefix) {
        Iterable<Result<Item>> results = client.listObjects(
                ListObjectsArgs.builder().bucket(bucket).prefix(prefix).build());
    }

    @Override
    public boolean exists(String fileName) {
        try {
            StatObjectResponse response =
                    client.statObject(
                            StatObjectArgs.builder().bucket(bucket).object(fileName).build());
            return true;
        } catch (InvalidKeyException e) {
            return false;
        } catch (Exception e) {
            Rethrower.throwAs(e);
            return false;
        }
    }

    @Override
    public InputStream get(String fileName) {
        try {
            return client.getObject(GetObjectArgs.builder().bucket(bucket).object(fileName).build());
        } catch (Exception e) {
            Rethrower.throwAs(e);
            return null;
        }
    }

    @Override
    public void upload(String fileName, String path) {
        try {
            client.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucket).object(fileName).filename(path).build());
        } catch (Exception e) {
            Rethrower.throwAs(e);
        }
    }

    @Override
    public void upload(String fileName, String contentType, InputStream inputStream) {
        try {
            ObjectWriteResponse response = client.putObject(
                    PutObjectArgs.builder().bucket(bucket).object(fileName).stream(inputStream, -1, 10485760)
                            .contentType(contentType)
                            .build());
        } catch (Exception e) {
            Rethrower.throwAs(e);
        }
    }

    @Override
    public void delete(String fileName) {
        try {
            client.removeObject(
                    RemoveObjectArgs.builder().bucket(bucket).object(fileName).build());
        } catch (Exception e) {
            Rethrower.throwAs(e);
        }
    }
}
