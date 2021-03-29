package uz.zako.example.service;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.zako.example.Enums.AttachmentStatus;
import uz.zako.example.entity.Attachment;
import uz.zako.example.model.Result;
import uz.zako.example.repository.AttachmentRepository;


import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private Hashids hashids;

    @Value("${upload.path}")
    private String filePath;

    @Override
    public Attachment save(MultipartFile multipartFile) {
        try {
            Attachment attachment = new Attachment();
            attachment.setAttachmentStatus(AttachmentStatus.DRAFT);
            attachment.setContentType(multipartFile.getContentType());
            attachment.setExtension(multipartFile.getContentType());
            attachment.setSize(multipartFile.getSize());
            attachment.setName(UUID.randomUUID().toString());

            Path path= Paths.get(this.filePath);
            path=checkPackage(path);
            attachment.setUploadPath(multipartFile.getOriginalFilename());
            attachmentRepository.save(attachment);

            File file = new File(path.toFile().getAbsoluteFile()+"/"+multipartFile.getOriginalFilename());
//            if (!file.exists()&&file.mkdirs()){
//                System.out.println("file yaratildi");
//            }
            attachment.setHashId(hashids.encode(attachment.getId()));
            attachmentRepository.save(attachment);
            multipartFile.transferTo(file);
            return attachment;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Attachment findByHashId(String hashids) {
        try {
            return attachmentRepository.findByHashId(hashids);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Result delete(String hashids) {
        try {
            Attachment attachment = attachmentRepository.findByHashId(hashids);
          Path path=Paths.get(this.filePath);
          path=checkPackage(path);
          File file=new File(path.toFile().getAbsolutePath()+"/"+attachment.getUploadPath());
            if (file.delete()){
                attachmentRepository.delete(attachment);
                return new Result(true, "deleting success");
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "deleting failed");
    }

    @Override
    public Page<Attachment> findAllWithPage(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("createAt").descending());
            return attachmentRepository.findAll(pageable);
        }catch (Exception e){
            System.out.println(e
            );
        }
        return null;
    }


    @Bean
    public Hashids hashids(){
        return new Hashids(getClass().getName(), 6);
    }


    private Path checkPackage(Path file) {
        if (!file.toFile().exists())
            file.toFile().mkdirs();
        return file;
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadToServer(String id, HttpServletResponse response) throws IOException {
        Attachment attachment=attachmentRepository.findByHashId(id);
        Path path=Paths.get(this.filePath);
        path=checkPackage(path);
        File file=new File(path.toFile().getAbsolutePath()+"/"+attachment.getUploadPath());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+attachment.getUploadPath());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
