package org.example.mrilki.service;

import org.example.mrilki.models.Image;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public interface ImageService {
    void saveFileToStorage(InputStream file, String originalFileName, String contentType, Long dayId, Long size);

    void writeFileFromStorage(Long fileId, OutputStream outputStream);

    Image getFileInfo(Long fileId);

    void deleteImage(Long fileId) throws SQLException, IOException;

    Image getImageByDay(Long dayId) throws SQLException;

    Image getImageByParam(String originalName, Long dayId, Long size) throws SQLException;
}
