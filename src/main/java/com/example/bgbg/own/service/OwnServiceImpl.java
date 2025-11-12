package com.example.bgbg.own.service;

import com.example.bgbg.code.ErrorCode;
import com.example.bgbg.entity.Category;
import com.example.bgbg.entity.User;
import com.example.bgbg.exception.GlobalException;
import com.example.bgbg.own.dto.request.OwnCreatedRequest;
import com.example.bgbg.own.dto.request.OwnUpdateRequest;
import com.example.bgbg.own.dto.response.OwnCreatedResponse;
import com.example.bgbg.own.dto.response.OwnDetailResponse;
import com.example.bgbg.own.entity.Own;
import com.example.bgbg.own.mapper.OwnMapper;
import com.example.bgbg.own.repository.OwnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnServiceImpl implements OwnService{
    private final OwnRepository ownRepository;

    @Override
    @Transactional
    public OwnCreatedResponse saveOwn(User user, OwnCreatedRequest request) {

        Own own = OwnMapper.toEntity(user, request);

        Own savedOwn = ownRepository.save(own);

        return new OwnCreatedResponse(savedOwn.getId(), "own 등록 완료");
    }

    @Override
    @Transactional(readOnly = true)
    public OwnDetailResponse getOwnById(User user, Long ownId) {
        Own own = ownRepository.findById(ownId)
                .orElseThrow(() -> new GlobalException(ErrorCode.OWN_NOT_FOUND));

        if (!own.getUser().getId().equals(user.getId())) {
            throw new GlobalException(ErrorCode.OWN_ACCESS_DENIED);
        }
        return OwnMapper.toDetailResponse(own);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OwnDetailResponse> getAllOwns(User user) {
        List<Own> owns = ownRepository.findByUser(user);

        return owns.stream()
                .map(OwnMapper::toDetailResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OwnDetailResponse> getOwnsByCategory(User user, String category) {
        Category categoryEnum = Category.valueOf(category.toUpperCase());
        List<Own> owns = ownRepository.findByUserAndOwnCategory(user, categoryEnum);

        return owns.stream()
                .map(OwnMapper::toDetailResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public  List<OwnDetailResponse> getDepletedOwns(User user) {
        List<Own> owns = ownRepository.findByUserAndOwnCount(user, 0);

        return owns.stream()
                .map(OwnMapper::toDetailResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateOwn(User user, Long ownId, OwnUpdateRequest request) {
        Own own = ownRepository.findById(ownId)
                .orElseThrow(() -> new GlobalException(ErrorCode.OWN_NOT_FOUND));

        if (!own.getUser().getId().equals(user.getId())) {
            throw new GlobalException(ErrorCode.OWN_ACCESS_DENIED);
        }

        own.updateOwn(request.ownName(), request.ownCount(), request.ownCategory());
    }

    @Override
    @Transactional
    public void deleteOwn(User user, Long ownId) {
        Own own = ownRepository.findById(ownId)
                .orElseThrow(() -> new GlobalException(ErrorCode.OWN_NOT_FOUND));

        if (!own.getUser().getId().equals(user.getId())) {
            throw new GlobalException(ErrorCode.OWN_ACCESS_DENIED);
        }

        ownRepository.delete(own);
    }
}
