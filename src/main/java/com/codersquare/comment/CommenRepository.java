package com.codersquare.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommenRepository extends JpaRepository<Comment,Long> {
}
