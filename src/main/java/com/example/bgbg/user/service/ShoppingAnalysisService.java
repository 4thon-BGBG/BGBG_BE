package com.example.bgbg.user.service;

import com.example.bgbg.user.dto.ShoppingAnalysisReportDTO;
import com.example.bgbg.user.entity.User;

public interface ShoppingAnalysisService {
    ShoppingAnalysisReportDTO analyze(User user);
}
