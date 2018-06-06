package com.codebros.evaluator.workspace.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(MultipartFile file , int type);

    Stream<Path> loadAll(Path path);

    Path load(String filename,Path path);

    Resource loadAsResource(String filename, Path path);

    void deleteAll();

}