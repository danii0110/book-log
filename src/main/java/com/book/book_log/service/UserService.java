package com.book.book_log.service;

import com.book.book_log.dto.UserRequestDTO;
import com.book.book_log.dto.UserResponseDTO;
import com.book.book_log.entity.AgeGroup;
import com.book.book_log.entity.Gender;
import com.book.book_log.entity.User;
import com.book.book_log.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository uRepo;

    // 사용자 생성
    public UserResponseDTO createUser(UserRequestDTO dto) {
        if (uRepo.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setGender(Gender.valueOf(dto.getGender().toUpperCase())); //String -> ENUM
        user.setAge_group(AgeGroup.valueOf(dto.getAgeGroup().toUpperCase())); //String -> ENUM

        User savedUser = uRepo.save(user);
        return toResponseDTO(savedUser); // User -> DTO 변환
    }

    // 사용자 조회
    public UserResponseDTO getUserById(String id) {
        User user =  uRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        return toResponseDTO(user);
    }

    // 사용자 정보 업데이트
    public UserResponseDTO updateUser(String id, UserRequestDTO dto) {
        User user = uRepo.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        user.setUsername(dto.getUsername());
        user.setGender(Gender.valueOf(dto.getGender().toUpperCase())); // String -> ENUM
        user.setAge_group(AgeGroup.valueOf(dto.getAgeGroup().toUpperCase())); //String -> ENUM

        User updatedUser = uRepo.save(user);
        return toResponseDTO(updatedUser); // User -> DTO 변환
    }

    // 사용자 삭제
    public void deleteUser(String id) {
        if (!uRepo.existsById(id)) {
            throw new EntityNotFoundException("사용자를 찾을 수 없습니다.");
        }
        uRepo.deleteById(id);
    }

    // 닉네임 중복 확인
    public boolean isUsernameAvailable(String username) {
        return !uRepo.existsByUsername(username);
    }

    // 이메일 기반 사용자 조회 (소셜 로그인에 사용됨)
    public Optional<UserResponseDTO> findUserByEmail(String email) {
        return uRepo.findByEmail(email)
                .map(this::toResponseDTO); // User -> DTO 변환
    }

    // DTO 변환 메서드
    private UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getGender().toString(), // ENUM -> String
                user.getAge_group().toString() // ENUM -> String
        );
    }
}