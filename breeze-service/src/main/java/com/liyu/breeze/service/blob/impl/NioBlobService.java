package com.liyu.breeze.service.blob.impl;

import com.liyu.breeze.common.exception.Rethrower;
import com.liyu.breeze.service.blob.BlobService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NioBlobService implements BlobService {

    @Override
    public boolean exists(String path) throws IOException {
        return Files.exists(Paths.get(path));
    }

    @Override
    public void mkdirs(String path) throws IOException {
        Path directory = Paths.get(path);
        if (Files.exists(directory)) {
            if (Files.isDirectory(directory) == false) {
                throw new IOException("File " + path + " exists and is " + "not a directory. Unable to create directory.");
            }
        } else {
            FileAttribute<Set<PosixFilePermission>> attributes = PosixFilePermissions.asFileAttribute(
                    new HashSet<>(Arrays.asList(PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_EXECUTE,
                            PosixFilePermission.GROUP_READ, PosixFilePermission.GROUP_WRITE, PosixFilePermission.GROUP_EXECUTE)));
            Files.createDirectories(directory, attributes);
        }
    }

    @Override
    public void delete(String path) throws IOException {
        Files.deleteIfExists(Paths.get(path));
    }

    @Override
    public void mkCleanDirs(String path) throws IOException {
        delete(path);
        mkdirs(path);
    }

    @Override
    public void upload(String srcPath, String destPath, boolean delSrc, boolean overWrite) throws IOException {
        if (Files.isDirectory(Paths.get(srcPath))) {
            copyDir(srcPath, destPath, delSrc, overWrite);
        } else {
            copy(srcPath, destPath, delSrc, overWrite);
        }
    }

    @Override
    public void copy(String srcPath, String destPath, boolean delSrc, boolean overWrite) throws IOException {
        if (exists(srcPath) == false) {
            return;
        }

        if (exists(destPath) == false && Files.notExists(Paths.get(destPath).getParent())) {
            throw new IllegalArgumentException(destPath + " is invalid and does not exist.");
        }

        Path srcFile = Paths.get(srcPath);
        if (Files.isDirectory(srcFile)) {
            throw new IllegalArgumentException(srcPath + " must be a file.");
        }

        Path dstFile = Paths.get(destPath);
        if (Files.isDirectory(dstFile)) {
            dstFile = dstFile.resolve(srcFile.getFileName());
        }
        if (Files.isSameFile(srcFile, dstFile)) {
            throw new IllegalStateException(srcPath + " can't be copied to same dstPath.");
        }

        boolean shouldCopy;
        if (overWrite || Files.notExists(dstFile)) {
            shouldCopy = true;
        } else {
            shouldCopy = srcFile.getFileName().equals(dstFile.getFileName()) == false;
        }

        if (shouldCopy) {
            Files.copy(srcFile, dstFile);
            if (delSrc) {
                Files.deleteIfExists(srcFile);
            }
        }
    }

    @Override
    public void copyDir(String srcPath, String destPath, boolean delSrc, boolean overWrite) throws IOException {
        if (exists(srcPath) == false) {
            return;
        }

        Path srcDir = Paths.get(srcPath);
        if (Files.isDirectory(Paths.get(srcPath)) == false) {
            throw new IllegalArgumentException(srcPath + " must be a directory.");
        }

        Path destDir = Paths.get(destPath);

        boolean shouldCopy = false;
        if (overWrite || Files.notExists(destDir)) {
            shouldCopy = true;
        } else {
            shouldCopy = srcDir.getFileName().equals(destDir.getFileName()) == false;
        }

        if (shouldCopy) {
            Files.copy(srcDir, destDir);
            if (delSrc) {
                Files.deleteIfExists(srcDir);
            }
        }
    }

    @Override
    public void move(String srcPath, String destPath) throws IOException {
        final Path path = Paths.get(srcPath);
        if (Files.isDirectory(path)) {
            copyDir(srcPath, destPath, true, true);
        } else {
            copy(srcPath, destPath, true, true);
        }
    }

    @Override
    public String fileMd5(String path) throws IOException {
        if (exists(path) == false) {
            throw new IllegalArgumentException(path + " must exists.");
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            InputStream fis = Files.newInputStream(Paths.get(path));
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                md5.update(buffer, 0, len);
            }
            fis.close();

            byte[] byteArray = md5.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : byteArray) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            Rethrower.throwAs(e);
            return null;
        }
    }
}
