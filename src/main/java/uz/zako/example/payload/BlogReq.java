package uz.zako.example.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogReq {
    private Long id;
    private String blogDate;
    private Long blogCategoryId;
    private String blogTitleUz;
    private String blogTitleRu;
    private String blogValueUz;
    private String blogValueRu;
    private String hashids;
    private String  status;
    private String blogType;
}
