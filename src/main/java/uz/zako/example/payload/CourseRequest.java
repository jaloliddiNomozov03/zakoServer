package uz.zako.example.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {
    private Long id;
    private String courseTitleUz;
    private String courseTitleRu;
    private String courseDescUz;
    private String courseDescRu;
    private String startDate;
    private String lectures;
    private Long duration;
    private Boolean certification;
    private String status;
    private String price;
    private String imgUrl;
    private Long teacheId;
    private Long categoryId;
}
