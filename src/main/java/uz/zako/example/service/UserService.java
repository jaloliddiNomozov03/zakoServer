package uz.zako.example.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.zako.example.entity.User;
import uz.zako.example.model.JwtResponse;
import uz.zako.example.model.Result;
import uz.zako.example.payload.LoginReq;
import uz.zako.example.payload.UserReq;


public interface UserService {
    public ResponseEntity<JwtResponse> signin(LoginReq loginRequest);
    public UserReq getMe(User user);
    public User addUser(User user);
    public Result deleteUser(Long id);
    public Page<User> findAllWithPage(int page, int size);
}
