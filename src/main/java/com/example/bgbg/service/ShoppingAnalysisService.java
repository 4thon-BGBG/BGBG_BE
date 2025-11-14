package com.example.bgbg.service;

import com.example.bgbg.dto.user.ShoppingAnalysisReportDTO;
import com.example.bgbg.entity.User;

public interface ShoppingAnalysisService {
    ShoppingAnalysisReportDTO analyze(User user);
}
