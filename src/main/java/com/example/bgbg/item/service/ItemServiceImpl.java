package com.example.bgbg.item.service;

import com.example.bgbg.code.ErrorCode;
import com.example.bgbg.entity.User;
import com.example.bgbg.exception.GlobalException;
import com.example.bgbg.item.dto.request.ItemCreatedRequest;
import com.example.bgbg.item.dto.response.ItemCreatedResponse;
import com.example.bgbg.item.dto.response.ItemDetailResponse;
import com.example.bgbg.item.entity.Item;
import com.example.bgbg.item.mapper.ItemMapper;
import com.example.bgbg.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public ItemCreatedResponse saveItem(User user, ItemCreatedRequest request) {

        Item item = ItemMapper.toEntity(user, request);

        Item savedItem = itemRepository.save(item);

        return new ItemCreatedResponse(savedItem.getId(), "item 등록 완료");
    }

    @Override
    @Transactional(readOnly = true)
    public ItemDetailResponse getItemById(User user, Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new GlobalException(ErrorCode.ITEM_NOT_FOUND));

        if (!item.getUser().getId().equals(user.getId())) {
            throw new GlobalException(ErrorCode.ITEM_ACCESS_DENIED);
        }
        return ItemMapper.toDetailResponse(item);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemDetailResponse> getAllItems(User user) {
        List<Item> items = itemRepository.findByUser(user);

        return items.stream()
                .map(ItemMapper::toDetailResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemDetailResponse> getItemsByCategory(User user, String category) {
        List<Item> items = itemRepository.findByUserAndItemCategory(user, category);

        return items.stream()
                .map(ItemMapper::toDetailResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public  List<ItemDetailResponse> getDepletedItems(User user) {
        List<Item> items = itemRepository.findByUserAndItemCount(user, 0);

        return items.stream()
                .map(ItemMapper::toDetailResponse)
                .collect(Collectors.toList());
    }
}
