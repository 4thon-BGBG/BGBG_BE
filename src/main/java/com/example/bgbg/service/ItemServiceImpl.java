package com.example.bgbg.service;

import com.example.bgbg.dto.request.ItemCreatedRequest;
import com.example.bgbg.dto.response.ItemCreatedResponse;
import com.example.bgbg.entity.Item;
import com.example.bgbg.mapper.ItemMapper;
import com.example.bgbg.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public ItemCreatedResponse saveItem(ItemCreatedRequest request) {
        Item item = ItemMapper.toEntity(request);

        Item savedItem = itemRepository.save(item);

        return new ItemCreatedResponse(savedItem.getId(), "item 등록 완료");
    }
}
