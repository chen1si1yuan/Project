package Dao;

import org.springframework.stereotype.Service;

import Pojo.Admin;

@Service
public interface AdminDao {
    public Admin findAdmin(String username);
}
