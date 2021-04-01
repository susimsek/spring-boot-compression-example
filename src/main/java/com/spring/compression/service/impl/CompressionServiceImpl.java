package com.spring.compression.service.impl;

import com.spring.compression.mapper.FileMapper;
import com.spring.compression.model.FileInfo;
import com.spring.compression.service.CompressionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import net.sf.sevenzipjbinding.util.ByteArrayStream;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
@RequiredArgsConstructor
public class CompressionServiceImpl implements CompressionService {

    final Environment environment;
    final FileMapper fileMapper;

    @Override
    public List<FileInfo> extractCompressionFile(FileInfo fileInfo) {
        try {
            ByteArrayStream byteArrayStream = new ByteArrayStream(fileInfo.getData(),false);
            IInArchive inArchive = SevenZip.openInArchive(null, byteArrayStream);
            return Arrays.stream(inArchive.getSimpleInterface().getArchiveItems()).filter(this::isValidFile).map(fileMapper::iSimpleInArchiveItemToFileInfo).collect(Collectors.toList());
        } catch (SevenZipException e) {
            return new ArrayList<FileInfo>();
        }
    }

   public Boolean isValidFile(ISimpleInArchiveItem item){
       try {
           if(item.isFolder()){
               return false;
           }
           String name = Path.of(item.getPath()).getFileName().toString();
           if(name.startsWith(".")){
               return false;
           }
           FileNameMap fileNameMap = URLConnection.getFileNameMap();
           String mimeType = fileNameMap.getContentTypeFor(name);
           Boolean supportedContentType = Arrays.stream(environment.getProperty("supported.content.type", String[].class)).anyMatch(x -> Objects.equals(x, mimeType));
           if(!supportedContentType){
               return false;
           }
           return true;
       } catch (SevenZipException e) {
           return false;
       }
   }
}
