package com.example.reportmanagmentsystem.service;

import com.example.reportmanagmentsystem.model.Attachment;
import com.example.reportmanagmentsystem.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AttachmentService  {
        @Autowired
        private AttachmentRepository attachmentRepository;

    public Attachment getFileByFileName(String fileName) {
        return attachmentRepository.findAttachmentByFileName(fileName);
    }
}
