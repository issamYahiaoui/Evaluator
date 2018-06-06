package com.codebros.evaluator.workspace.Controllers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;

import com.codebros.evaluator.workspace.Utils.StorageFileNotFoundException;
import com.codebros.evaluator.workspace.service.StorageService;
import com.codebros.evaluator.workspace.service.StorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class FileController {

    private final StorageService storageService;

    @Autowired
    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }


    @Autowired
    private StorageServiceImpl storageServiceImpl;


    @GetMapping("applicant-folder/{type}")
    public ModelAndView folders (@PathVariable int type ){
        ModelAndView modelAndView = new ModelAndView() ;

        modelAndView.addObject("administratifFiles",storageService.loadAll(storageServiceImpl.administratif).map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileController.class,
                        "serveFileAdministratif", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList())) ;

        modelAndView.addObject("scientificFiles",storageService.loadAll(storageServiceImpl.scientific).map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileController.class,
                        "serveFileScientific", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList())) ;

        modelAndView.addObject("otherFiles",storageService.loadAll(storageServiceImpl.other).map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileController.class,
                        "serveFileOther", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList())) ;

        modelAndView.setViewName("workspace/applicant/folder");
        return modelAndView ;
    }


    @GetMapping("/files/administratif/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFileAdministratif(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename,storageServiceImpl.administratif);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    @GetMapping("/files/scientific/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFileScientific(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename,storageServiceImpl.scientific);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    @GetMapping("/files/other/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFileOther(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename,storageServiceImpl.other);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/uploadForm/{type}")
    public String handleFileUpload(@PathVariable int type ,@RequestParam("file") MultipartFile[] files,
                                   RedirectAttributes redirectAttributes) {
        String msg ="" ;
        for(MultipartFile file : files) {
            storageService.store(file,type);
            msg = msg + " & " + file.getOriginalFilename() ;
        }
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + msg + "!");

        return "redirect:/uploadForm";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}