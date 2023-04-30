package com.candidateInfo.candidateInfo.repository;

import com.candidateInfo.candidateInfo.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate,Long> {
}
