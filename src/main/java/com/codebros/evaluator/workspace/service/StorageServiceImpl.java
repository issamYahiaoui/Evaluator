package com.codebros.evaluator.workspace.service;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import com.codebros.evaluator.workspace.Utils.StorageException;
import com.codebros.evaluator.workspace.Utils.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceImpl implements StorageService {

    public final Path rootLocation = Paths.get("folders");
    public final Path administratif =  Paths.get("folders/administratif") ;
    public final Path scientific =  Paths.get("folders/scientific") ;
    public final Path other =  Paths.get("folders/other") ;



    @Override
    public void store(MultipartFile file , int type) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                switch(type) {
                    case 1 :
                        Files.copy(inputStream, this.administratif.resolve(filename),
                                StandardCopyOption.REPLACE_EXISTING);
                        break ;
                    case 2 :
                        Files.copy(inputStream, this.scientific.resolve(filename),
                                StandardCopyOption.REPLACE_EXISTING);
                        break ;
                    case 3 :
                        Files.copy(inputStream, this.other.resolve(filename),
                                StandardCopyOption.REPLACE_EXISTING);
                        break ;
                }

            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll(Path path) {
        try {
            return Files.walk(path, 1)
                    .filter(p -> !p.equals(path))
                    .map(path::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename, Path path) {
        return path.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename,Path path) {
        try {
            Path file = load(filename,path);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
        FileSystemUtils.deleteRecursively(administratif.toFile());
        FileSystemUtils.deleteRecursively(scientific.toFile());
        FileSystemUtils.deleteRecursively(other.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
            Files.createDirectories(administratif);
            Files.createDirectories(scientific);
            Files.createDirectories(other);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}