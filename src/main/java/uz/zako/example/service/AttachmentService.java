package uz.zako.example.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.zako.example.entity.Attachment;
import uz.zako.example.model.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;


public interface AttachmentService {
    public Attachment save(MultipartFile multipartFile);
    public Attachment findByHashId(String hashids);
    public Result delete(String hashids);
    public Page<Attachment> findAllWithPage(int page, int size);

    public ResponseEntity<InputStreamResource> downloadToServer(String id, HttpServletResponse response) throws IOException;
}
