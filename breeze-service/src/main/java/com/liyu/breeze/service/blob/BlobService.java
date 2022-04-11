package com.liyu.breeze.service.blob;

import java.io.IOException;

public interface BlobService {

    boolean exists(String path) throws IOException;

    void mkdirs(String path) throws IOException;

    void delete(String path) throws IOException;

    void mkCleanDirs(String path) throws IOException;

    default void upload(String srcPath, String dstPath) throws IOException {
        upload(srcPath, dstPath, false, true);
    }

    void upload(String srcPath, String dstPath, boolean delSrc, boolean overWrite) throws IOException;

    default void copy(String srcPath, String dstPath) throws IOException {
        copy(srcPath, dstPath, false, true);
    }

    void copy(String srcPath, String dstPath, boolean delSrc, boolean overWrite) throws IOException;

    default void copyDir(String srcPath, String dstPath) throws IOException {
        copyDir(srcPath, dstPath, false, true);
    }

    void copyDir(String srcPath, String dstPath, boolean delSrc, boolean overWrite) throws IOException;

    void move(String srcPath, String dstPath) throws IOException;

    String fileMd5(String path) throws IOException;
}
