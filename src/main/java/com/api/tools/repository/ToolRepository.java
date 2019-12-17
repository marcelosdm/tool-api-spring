package com.api.tools.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.api.tools.entity.Tool;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {

}
