package uz.zako.example.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserReq {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String aboutUz;
    private String aboutRu;
    private String telegram;
    private String instagram;
    private String facebook;
    private String imgUrl;
    private String email;
    private List<ObjectReq> groups;
}
