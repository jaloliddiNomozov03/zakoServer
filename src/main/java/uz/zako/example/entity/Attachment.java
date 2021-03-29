package uz.zako.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import uz.zako.example.Enums.AttachmentStatus;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String name;

    private String hashId;

    private String extension;

    private String contentType;

    private Long size;

    private String uploadPath;

    @Enumerated(EnumType.STRING)
    private AttachmentStatus attachmentStatus;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createAt;

    @UpdateTimestamp
    private Date updateAt;

}
