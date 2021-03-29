package uz.zako.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Teacher extends User {

    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment attachment;

    @Column(length = 999999)
    private String aboutUz;
    @Column(length = 999999)
    private String aboutRu;

    private String telegram;

    private String instagram;

    private String facebook;
}
