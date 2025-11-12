package com.example.bgbg.own.service;

import com.example.bgbg.entity.User;
import com.example.bgbg.own.dto.request.OwnCreatedRequest;
import com.example.bgbg.own.dto.request.OwnUpdateRequest;
import com.example.bgbg.own.dto.response.OwnCreatedResponse;
import com.example.bgbg.own.dto.response.OwnDetailResponse;

import java.util.List;

public interface OwnService {
    OwnCreatedResponse saveOwn(User user, OwnCreatedRequest request);
    OwnDetailResponse getOwnById(User user, Long OwnId);
    List<OwnDetailResponse> getAllOwns(User user);
    List<OwnDetailResponse> getOwnsByCategory(User user, String category);
    List<OwnDetailResponse> getDepletedOwns(User user);

    void updateOwn(User user, Long ownId, OwnUpdateRequest request);
    void deleteOwn(User user, Long ownId);
}
