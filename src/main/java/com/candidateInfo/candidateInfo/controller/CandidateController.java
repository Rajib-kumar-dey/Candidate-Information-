package com.candidateInfo.candidateInfo.controller;

import com.candidateInfo.candidateInfo.payload.CandidateDto;
import com.candidateInfo.candidateInfo.payload.CandidateResponse;
import com.candidateInfo.candidateInfo.service.CandidateService;
import com.candidateInfo.candidateInfo.utility.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;


    @PostMapping
    public ResponseEntity<Object> createCandidate(@Valid @RequestBody CandidateDto candidateDto, BindingResult result) {
        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CandidateDto dto = candidateService.saveCandidate(candidateDto);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateDto> updateCandidate ( @PathVariable("id") Long id,
                                                         @RequestBody CandidateDto candidateDto) {

        CandidateDto  dto =candidateService.updateCandidate(candidateDto,id);
        return  new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping
    public CandidateResponse getAllCandidates(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false)int pageNo,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false)String sortDir
    ) {
        return candidateService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDto> getCandidateById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(candidateService.getCandidateById(id));
    }
}

