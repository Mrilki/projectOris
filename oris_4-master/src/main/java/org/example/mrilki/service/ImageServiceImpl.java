package org.example.mrilki.service;

import org.example.mrilki.models.Image;
import org.example.mrilki.repositoris.ImageRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class ImageServiceImpl implements ImageService {

    private ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void saveFileToStorage(InputStream file, String originalFileName, String contentType, Long dayId, Long size) {
        Image image = Image.builder()
                .originalFileName(originalFileName)
                .storageFileName(UUID.randomUUID().toString())
                .size(size)
                .dayId(dayId)
                .type(contentType)
                .build();

        try {
            Files.copy(file, Paths.get("C://Users//kim85//IdeaProjects//oris_4//src//main//webapp//image/" + image.getStorageFileName() + "." + image.getType().split("/")[1]));
            imageRepository.save(image);

        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeFileFromStorage(Long fileId, OutputStream outputStream) {
        // нашли файл в базе
        Image image = null;
        try {
            Optional<Image> imageOptional = imageRepository.findById(fileId);
            if (imageOptional.isPresent()) {
                image = imageOptional.get();
                File file = new File("C://Users//kim85//IdeaProjects//oris_4//src//main//webapp//image/" + image.getStorageFileName() + "." + image.getType().split("/")[1]);
                Files.copy(file.toPath(), outputStream);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Image getFileInfo(Long fileId) {
        try {
            Optional<Image> imageOptional = imageRepository.findById(fileId);
            if (imageOptional.isPresent()) {
                return imageOptional.get();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    @Override
    public void deleteImage(Long fileId) throws SQLException, IOException {
        Optional<Image> imageOptional = imageRepository.findById(fileId);
        if (imageOptional.isPresent()) {
            Image image = imageOptional.get();
            String filePath = "C://Users//kim85//IdeaProjects//oris_4//src//main//webapp//image/" + image.getStorageFileName() + "." + image.getType().split("/")[1];
            Files.delete(Paths.get(filePath));
            imageRepository.remove(fileId);

        }


    }

    @Override
    public Image getImageByDay(Long dayId) throws SQLException {
        Optional<Image> imageOptional = imageRepository.findDayById(dayId);
        if (imageOptional.isPresent()) {
            return imageOptional.get();
        }
        return null;
    }

    @Override
    public Image getImageByParam(String originalName, Long dayId, Long size) throws SQLException {
        Optional<Image> imageOptional = imageRepository.findIdByImage(originalName, dayId, size);
        if (imageOptional.isPresent()) {
            return imageOptional.get();
        }
        return null;
    }
}
