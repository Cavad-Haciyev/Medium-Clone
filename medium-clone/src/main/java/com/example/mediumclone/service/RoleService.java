package com.example.mediumclone.service;

import com.example.mediumclone.model.Role;
import com.example.mediumclone.repository.RoleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleService {

  private final RoleRepository roleRepository;

  public Role saveRole(Role role) {
    return roleRepository.save(role);
  }

  public List<Role> getAllRole() {
    return roleRepository.findAll();
  }
}
