package com.dopamines.backend.review.repository;

import com.dopamines.backend.review.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
