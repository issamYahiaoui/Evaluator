package com.codebros.evaluator.workspace.Controllers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.codebros.evaluator.workspace.Utils.StorageFileNotFoundException;
import com.codebros.evaluator.workspace.model.Attachment;
import com.codebros.evaluator.workspace.model.Requirement;
import com.codebros.evaluator.workspace.repository.AttachmentRepository;
import com.codebros.evaluator.workspace.repository.RequirementRepository;
import com.codebros.evaluator.workspace.service.FolderService;
import com.codebros.evaluator.workspace.service.StorageService;
import com.codebros.evaluator.workspace.service.StorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private RequirementRepository requirementRepository ;

    @Autowired
    private FolderService folderService ;

    @Autowired
    private AttachmentRepository attachmentRepository;



    @Autowired
    private StorageServiceImpl storageServiceImpl;


    @GetMapping("applicant-folder/{type}")
    public ModelAndView folders (@PathVariable int type ){
        ModelAndView modelAndView = new ModelAndView() ;

        modelAndView.addObject("files",storageService.loadAll(storageServiceImpl.rootLocation).map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList())) ;


        modelAndView.setViewName("workspace/applicant/folder");
        return modelAndView ;
    }
    @GetMapping("testFile")
    public ModelAndView testFile (){
        ModelAndView modelAndView = new ModelAndView() ;

        modelAndView.setViewName("uploadForm");
        return modelAndView ;
    }


    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename,storageServiceImpl.rootLocation);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @PostMapping("/uploadForm")
    public String handleFileUpload(
                                   @RequestParam("1") MultipartFile[] _1 ,
                                   @RequestParam("2") MultipartFile[] _2,
                                   @RequestParam("3") MultipartFile[] _3,
                                   @RequestParam("4") MultipartFile[] _4,
                                   @RequestParam("5") MultipartFile[] _5,
                                   @RequestParam("6") MultipartFile[] _6,
                                   @RequestParam("7") MultipartFile[] _7,
                                   @RequestParam("8") MultipartFile[] _8,
                                   @RequestParam("9") MultipartFile[] _9,
                                   @RequestParam("10") MultipartFile[] _10,
                                   @RequestParam("11") MultipartFile[] _11,
                                   @RequestParam("12") MultipartFile[] _12,
                                   @RequestParam("13") MultipartFile[] _13,
                                   @RequestParam("14") MultipartFile[] _14,
                                   @RequestParam("15") MultipartFile[] _15,
                                   @RequestParam("16") MultipartFile[] _16,
                                   @RequestParam("17") MultipartFile[] _17,
                                   @RequestParam("18") MultipartFile[] _18,
                                   @RequestParam("19") MultipartFile[] _19,
                                   @RequestParam("20") MultipartFile[] _20,
                                   @RequestParam("21") MultipartFile[] _21,
                                   @RequestParam("22") MultipartFile[] _22,
                                   RedirectAttributes redirectAttributes) {
        String msg ="" ;
        Set<MultipartFile[]> folder = new HashSet<MultipartFile[]>(Arrays.asList(_1,_2,_3,_4,_5,_6,_7,_8,_9,_10,_11,_12,_13,_14,_15,_16,_17,_18,_19,_20,_21,_22));

        System.out.println(folder);
        Integer  index = 1 ;
        for (MultipartFile[] subfolder : folder){
            if (subfolder.length != 0) {
                Requirement requirement = requirementRepository.findById(index) ;
                System.out.println(index);
                System.out.println(requirement.getId());
                Set<Attachment> attachments = new HashSet<Attachment>() ;

                Attachment attachment =  new Attachment();
                System.out.println("subfolder lenght");
                System.out.println(subfolder[0]);
                for(MultipartFile file : subfolder) {
                    System.out.println(file);
                    attachmentRepository.save(attachment);
                    attachment.setUrl(attachment.getId()+"_"+file.getOriginalFilename());
                    attachments.add(attachment);
                    storageService.store(file,attachment.getId());
                    msg = msg + " & " + file.getOriginalFilename() ;
                }
                requirement.setAttachments(attachments);
                requirementRepository.save(requirement) ;

            }
            index++ ;

        }

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + msg + "!");

        return "redirect:/applicant/folder";
    }
    @PostMapping("/uploadForm/{id}")
    public String handleFileUpload(
            @PathVariable("id") Integer id ,
            @RequestParam("file") MultipartFile[] files,
            RedirectAttributes redirectAttributes) {
        String msg ="" ;


        Requirement requirement = requirementRepository.findById(id) ;
        System.out.println(requirement.getId());
        Set<Attachment> attachments = new HashSet<Attachment>() ;
        Attachment attachment =  new Attachment();
        for(MultipartFile file : files) {
                    System.out.println(file);
                    attachmentRepository.save(attachment);
                    attachment.setUrl(attachment.getId()+"_"+file.getOriginalFilename());
                    attachments.add(attachment);
                    storageService.store(file,attachment.getId());
                    msg = msg + " & " + file.getOriginalFilename() ;
        }
        requirement.setAttachments(attachments);
        requirementRepository.save(requirement) ;

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + msg + "!");

        return "redirect:/applicant/folder";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}