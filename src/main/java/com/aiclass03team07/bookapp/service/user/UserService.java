package com.aiclass03team07.bookapp.service.user;


import com.aiclass03team07.bookapp.dto.user.UserJoinRequestDto;
import com.aiclass03team07.bookapp.entity.PreferenceGenreEntity;
import com.aiclass03team07.bookapp.entity.UserEntity;
import com.aiclass03team07.bookapp.repository.PreferenceRepository;
import com.aiclass03team07.bookapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aiclass03team07.bookapp.exception.DuplicateUserIdException;
import com.aiclass03team07.bookapp.exception.LoginFailedException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PreferenceRepository preferenceRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true) //중복 체크
    public boolean checkUserIdDuplicate(String userId){
        return userRepository.existsByUserId(userId);
    }

    @Transactional
    public void join(UserJoinRequestDto dto){
        if(checkUserIdDuplicate(dto.getUserId())){
            throw new DuplicateUserIdException(dto.getUserId());
        }
        UserEntity user = new UserEntity();
        user.setUserId(dto.getUserId());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole() != null ? dto.getRole() : "USER");

        UserEntity savedUser = userRepository.save(user);

        if(dto.getGenres() != null){
            for(String genreName : dto.getGenres()){
                if(genreName != null && !genreName.isBlank()){
                    PreferenceGenreEntity preferenceGenre= new PreferenceGenreEntity();
                    preferenceGenre.setUser(savedUser);
                    preferenceGenre.setGenre(genreName);
                    preferenceRepository.save(preferenceGenre);
                }
            }
        }
    }

    @Transactional(readOnly = true)
    public UserEntity login(String userId, String password){
        UserEntity user = userRepository.findByUserId(userId).orElseThrow(LoginFailedException::new);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new LoginFailedException();
        }
        return user;
    }
}