package uz.zako.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String blogDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category blogCategory;

    private String blogTitleUz;
    private String blogTitleRu;

    @Column(columnDefinition = "TEXT")
    private String blogValueUz;
    @Column(columnDefinition = "TEXT")
    private String blogValueRu;

    private String hashids;

    private String  status;

    private String blogType;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createAt;

    @UpdateTimestamp
    private Date updateAt;
}
