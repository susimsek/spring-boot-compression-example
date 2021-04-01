package com.spring.compression.mapper;


import com.spring.compression.decorator.FileMapperDecorator;
import com.spring.compression.model.FileInfo;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper
@DecoratedWith(FileMapperDecorator.class)
public interface FileMapper {

    @Mapping(source = "originalFilename", target = "name")
    @Mapping(source = "contentType", target = "type")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "bytes", target = "data")
    FileInfo fileToFileInfo(MultipartFile file) throws IOException;


    @Mapping(target = "size", ignore = true)
    FileInfo iSimpleInArchiveItemToFileInfo(ISimpleInArchiveItem item);
}
