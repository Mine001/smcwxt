package com.hulqframe.system.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.system.mapper.AttachmentMapper;
import com.hulqframe.system.model.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachmentService extends BaseService<Attachment,Integer> {
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Override
    public BaseMapper<Attachment, Integer> getBaseMapper() {
        return attachmentMapper;
    }
}
