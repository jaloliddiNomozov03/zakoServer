package uz.zako.example.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    private Long id;
    private String categoryNameUz;
    private String categoryNameRu;
}
