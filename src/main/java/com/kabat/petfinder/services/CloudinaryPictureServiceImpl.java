package com.kabat.petfinder.services;

import com.cloudinary.Cloudinary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.cloudinary.utils.ObjectUtils.asMap;
import static com.kabat.petfinder.utils.CloudinaryConstants.*;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonList;


@Service
public class CloudinaryPictureServiceImpl implements PictureService {
    private Cloudinary cloudinary;
    private Logger LOG = LoggerFactory.getLogger(CloudinaryPictureServiceImpl.class);


    public CloudinaryPictureServiceImpl() {
        cloudinary = new Cloudinary(
                asMap(
                        "cloud_name", CLOUDINARY_CLOUD_NAME,
                        "api_key", CLOUDINARY_API_KEY,
                        "api_secret", CLOUDINARY_API_SECRET)
        );
    }

    @Async
    public void deleteFromRemoteServerById(String pictureId) {
        LOG.info("Cloudinary - Attempting to delete picture with id: " + pictureId);
        if(pictureId != null) {
            try {
                cloudinary.api().deleteResources(singletonList(pictureId), emptyMap());
                LOG.info("Cloudinary - Deleted picture with id: " + pictureId);
            } catch (Exception e) {
                LOG.error("Cloudinary error - Could not delete image with id: " + pictureId);
            }
        }
    }
}
