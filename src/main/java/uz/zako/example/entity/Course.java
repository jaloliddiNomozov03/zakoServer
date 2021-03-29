package uz.zako.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseTitleUz;
    private String courseTitleRu;

    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment attachment;

    @Column(length = 999999)
    private String courseDescUz;
    @Column(length = 999999)
    private String courseDescRu;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category categorie;

    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    @Column(name = "start_date")
    private String startDate;

    private String lectures;


    private Long duration;

    private Boolean certification;

    private String status;

    private String price;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createAt;
    @UpdateTimestamp
    private Date updateAt;
}
