package com.liyu.breeze.service.storage.impl;

import com.liyu.breeze.service.storage.BlobService;
import io.minio.MinioClient;

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


}
