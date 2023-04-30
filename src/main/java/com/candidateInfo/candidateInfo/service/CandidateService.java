package com.candidateInfo.candidateInfo.service;

import com.candidateInfo.candidateInfo.payload.CandidateDto;
import com.candidateInfo.candidateInfo.payload.CandidateResponse;

public interface CandidateService {
    CandidateDto saveCandidate(CandidateDto candidateDto);

    CandidateDto updateCandidate(CandidateDto candidateDto, Long id);

    CandidateResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    CandidateDto getCandidateById(Long id);
}
