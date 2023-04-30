package com.candidateInfo.candidateInfo.service.impl;


import com.candidateInfo.candidateInfo.entity.Candidate;
import com.candidateInfo.candidateInfo.exception.ResourceNotFoundException;
import com.candidateInfo.candidateInfo.payload.CandidateDto;
import com.candidateInfo.candidateInfo.payload.CandidateResponse;
import com.candidateInfo.candidateInfo.repository.CandidateRepository;
import com.candidateInfo.candidateInfo.service.CandidateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CandidateDto saveCandidate(CandidateDto candidateDto) {
        Candidate candidate = mapToEntity(candidateDto);
        Candidate save = candidateRepository.save(candidate);
       return mapToDto(save);
    }

    @Override
    public CandidateDto updateCandidate(CandidateDto candidateDto, Long id) {
        Candidate candidate = candidateRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Candidate", "id", id)
        );
        candidate.setName(candidateDto.getName());
        candidate.setMobile(candidateDto.getMobile());
        candidate.setEmail(candidateDto.getEmail());
        candidate.setAddress(candidateDto.getAddress());

        return mapToDto(candidateRepository.save(candidate));
    }

    @Override
    public CandidateResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();;
        Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
        Page<Candidate> candidates = candidateRepository.findAll(pageable);
        List<Candidate> content = candidates.getContent();
        List<CandidateDto> contents = content.stream().map(candidate -> mapToDto(candidate)).collect(Collectors.toList());

        CandidateResponse candidateResponse = new CandidateResponse();
        candidateResponse.setContents(contents);
        candidateResponse.setPageNo(candidates.getNumber());
        candidateResponse.setPageSize(candidates.getSize());
        candidateResponse.setTotalElements(candidates.getTotalElements());
        candidateResponse.setTotalPages(candidates.getTotalPages());
        candidateResponse.setLast(candidates.isLast());
        return candidateResponse;
    }


    @Override
    public CandidateDto getCandidateById(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate", "id", id));
        return mapToDto(candidate);
    }

    public CandidateDto mapToDto(Candidate candidate) {
        return modelMapper.map(candidate, CandidateDto.class);
    }


    public Candidate mapToEntity(CandidateDto candidateDto) {
        return modelMapper.map(candidateDto, Candidate.class);
    }
}

