package uz.zako.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.zako.example.entity.Role;
import uz.zako.example.entity.User;
import uz.zako.example.model.JwtProvider;
import uz.zako.example.model.JwtResponse;
import uz.zako.example.model.Result;
import uz.zako.example.payload.LoginReq;
import uz.zako.example.payload.UserReq;
import uz.zako.example.repository.RoleRepository;
import uz.zako.example.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<JwtResponse> signin(LoginReq loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @Override
    public UserReq getMe(User user) {
     if (user!=null){
         UserReq userReq=new UserReq();
         userReq.setFirstName(user.getFirstName());
         userReq.setLastName(user.getLastName());
         userReq.setPhoneNumber(user.getPhoneNumber());
         userReq.setUsername(user.getUsername());
         return userReq;
     }else {
         return null;
     }
    }

    @Override
    public User addUser(User user) {
        try {
            User newUser=   new User();
            newUser.setUserType("USER");
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
//            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setPassword(user.getPassword());
            newUser.setEmail(user.getEmail());
            newUser.setPhoneNumber(user.getPhoneNumber());
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findByName("ROLE_USER"));
            newUser.setRoles(roles);
            return newUser;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public Result deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return new Result(true,"Successful deleted");
        }catch (Exception e){
            System.out.println(e);
            return  new Result(false,"Not deleted");
        }
    }

    @Override
    public Page<User> findAllWithPage(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page,size, Sort.by("createAt").descending());
            return userRepository.findAll(pageable);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
