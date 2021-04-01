package com.spring.compression.decorator;

import com.spring.compression.mapper.FileMapper;
import com.spring.compression.model.FileInfo;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.Path;

@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class FileMapperDecorator implements FileMapper {

    @Setter(onMethod = @__({@Autowired}))
    FileMapper fileMapper;

    @Override
    public FileInfo iSimpleInArchiveItemToFileInfo(ISimpleInArchiveItem item) {
        try {
            FileInfo fileInfo = new FileInfo();
            String name = Path.of(item.getPath()).getFileName().toString();
            FileNameMap fileNameMap = URLConnection.getFileNameMap();
            String mimeType = fileNameMap.getContentTypeFor(name);

            ExtractOperationResult result = item.extractSlow(data -> {
                fileInfo.setData(data);
                return data.length;
            });

            if (result != ExtractOperationResult.OK) {
                return FileInfo.builder().build();
            }

            fileInfo.setName(name);
            fileInfo.setSize(item.getSize());
            fileInfo.setType(mimeType);

            return fileInfo;
        } catch (SevenZipException e) {
            return FileInfo.builder().build();
        }
    }
}
