package com.spring.compression.controller.rest;


import com.spring.compression.mapper.FileMapper;
import com.spring.compression.model.FileInfo;
import com.spring.compression.service.CompressionService;
import com.spring.compression.validator.ValidCompressedFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Validated
@Tag(name = "Compression", description = "Retrieve and manage compression")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class CompressionController {

    final FileMapper fileMapper;
    final CompressionService compressionService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok",content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",content = @Content)

    })
    @Operation(summary = "Extract Compression File")
    @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    @ResponseStatus(HttpStatus.OK)
    public List<FileInfo> uploadFile(@ValidCompressedFile @RequestPart(name = "file") @Parameter(description = "File to be uploaded", content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)) MultipartFile file) throws IOException {
        FileInfo fileInfo = fileMapper.fileToFileInfo(file);
        return compressionService.extractCompressionFile(fileInfo);
    }

}
