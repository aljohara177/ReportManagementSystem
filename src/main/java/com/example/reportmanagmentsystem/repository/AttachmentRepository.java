package com.example.reportmanagmentsystem.repository;

import com.example.reportmanagmentsystem.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    public Attachment findAttachmentByFileName(String fileName);

}
