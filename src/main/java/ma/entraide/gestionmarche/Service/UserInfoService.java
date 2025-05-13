package ma.entraide.formationcentres.Service;


import jakarta.validation.constraints.Null;
import ma.entraide.formationcentres.Entity.Province;
import ma.entraide.formationcentres.Entity.UserInfo;
import ma.entraide.formationcentres.Repository.ProvinceRepo;
import ma.entraide.formationcentres.Repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private ProvinceRepo provinceRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProvinceService provinceService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByEmail(username);
        return userInfo.map(UserInfoDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"+username));
    }
    public String addUser(UserInfo userInfo){
        String password = userInfo.getPassword();
        String email = userInfo.getEmail();
        if(password == null )
        {
            throw new IllegalArgumentException("Password cannot be null");
        }
        else if(password.trim().matches("")){
            throw new IllegalArgumentException("Password cannot be empty");
        }
        else if(password.length()<6){
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        else if (!email.trim().matches("^[a-zA-Z0-9_!#$%&'*+/=?^.-]+@[a-zA-Z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email");
        }
        else{
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        if(userInfo.getProvince() != null){
            Optional<Province> provinceOptional = provinceRepo.findById(userInfo.getProvince().getId());
            if(provinceOptional.isPresent()){
                Province province = provinceOptional.get();
                userInfo.setProvince(province);
            }
        }
        userInfoRepository.save(userInfo);
        return "User added successfully ";
        }
    }
    public List<UserInfo> getAllUser(){
         return userInfoRepository.findAll();
    }

    public UserInfo findUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> userInfoOp = userInfoRepository.findByEmail(email);
        UserInfo userInfo = null;
        if(userInfoOp.isPresent()){
            userInfo = userInfoOp.get();
        }
        else{
            throw new UsernameNotFoundException("User not found");
        }
        return userInfo;
    }

    public UserInfo getUserById(Integer id) {
        Optional<UserInfo> userOp = userInfoRepository.findById(id);
        UserInfo user = null;
        if(userOp.isPresent()){
            user = userOp.get();
        }
        else{
            throw new ResourceNotFoundException("User not found");
        }
        return user;
    }

    public String deleteUser(Integer id){
        Optional<UserInfo> userOp = userInfoRepository.findById(id);
        UserInfo user = null;
        if(userOp.isPresent()){
            user = userOp.get();
        }
        else{
            throw new ResourceNotFoundException("User not found");
        }
        userInfoRepository.deleteById(user.getId());
        return "User deleted successfully";
    }

    public String updateUser(Integer id,UserInfo updatedUserInfo){
            Province province = provinceService.getProvinceById(updatedUserInfo.getProvince().getId());
            Optional<UserInfo> optionalUserInfo = userInfoRepository.findById(id);
            if (!optionalUserInfo.isPresent()) {
                throw new ResourceNotFoundException("User not found");
            }
                String pwd = updatedUserInfo.getPassword();
                if(pwd == null || pwd.trim().equals("") || pwd.length()<6){
                    throw new IllegalArgumentException("password is too short or empty");
                } else if (!updatedUserInfo.getEmail().trim().matches("^[a-zA-Z0-9_!#$%&'*+/=?^.-]+@[a-zA-Z0-9.-]+$")) {
                    throw new IllegalArgumentException("email format invalid");
                }
                UserInfo existingUser = optionalUserInfo.get();
                existingUser.setName(updatedUserInfo.getName());
                existingUser.setEmail(updatedUserInfo.getEmail());
                existingUser.setRoles(updatedUserInfo.getRoles());
                existingUser.setProvince(province);
                existingUser.setPassword(passwordEncoder.encode(updatedUserInfo.getPassword())); // Update password

                // Save the updated user
                userInfoRepository.save(existingUser);
                return "User updated successfully";
            }

    public boolean updatePassword(UserInfo user, String oldPassword, String newPassword, String confirmPassword) {
        if (userInfoRepository.findById(user.getId()).isEmpty()) {
            return false;
        }

        UserInfo existingUser = userInfoRepository.findById(user.getId()).get();

        // Vérification de l'ancien mot de passe
        if (!passwordEncoder.matches(oldPassword, existingUser.getPassword())) {
            return false;
        }

        // Vérification de la correspondance entre le nouveau mot de passe et sa confirmation
        if (!newPassword.equals(confirmPassword)) {
            return false;
        }

        // Hachage du nouveau mot de passe
        String newPasswordHash = passwordEncoder.encode(newPassword);

        // Mise à jour du mot de passe dans la base de données
        existingUser.setPassword(newPasswordHash);
        userInfoRepository.save(existingUser);

        return true;
    }



}
