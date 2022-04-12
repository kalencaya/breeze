package com.liyu.breeze.service.blob;

import java.io.IOException;

public interface FileService {

    boolean exists(String path) throws IOException;

    void mkdirs(String path) throws IOException;

    void delete(String path) throws IOException;

    void mkCleanDirs(String path) throws IOException;

    default void upload(String srcPath, String destPath) throws IOException {
        upload(srcPath, destPath, false, true);
    }

    void upload(String srcPath, String destPath, boolean delSrc, boolean overWrite) throws IOException;

    default void copy(String srcPath, String destPath) throws IOException {
        copy(srcPath, destPath, false, true);
    }

    void copy(String srcPath, String destPath, boolean delSrc, boolean overWrite) throws IOException;

    default void copyDir(String srcPath, String destPath) throws IOException {
        copyDir(srcPath, destPath, false, true);
    }

    void copyDir(String srcPath, String destPath, boolean delSrc, boolean overWrite) throws IOException;

    void move(String srcPath, String destPath) throws IOException;

    String fileMd5(String path) throws IOException;
}
