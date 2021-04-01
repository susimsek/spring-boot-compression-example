package com.spring.compression.service;

import com.spring.compression.model.FileInfo;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;

import java.util.List;

public interface CompressionService {
    List<FileInfo> extractCompressionFile(FileInfo fileInfo);
    Boolean isValidFile(ISimpleInArchiveItem item);
}
