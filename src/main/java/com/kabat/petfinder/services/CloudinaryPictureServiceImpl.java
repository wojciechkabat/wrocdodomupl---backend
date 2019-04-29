package com.kabat.petfinder.services;

import com.cloudinary.Cloudinary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cloudinary.utils.ObjectUtils.asMap;
import static com.kabat.petfinder.utils.CloudinaryConstants.*;
import static java.util.Collections.emptyMap;


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

    public void deleteFromRemoteServerByIds(List<String> pictureIds) {
        LOG.info("Cloudinary - Attempting to delete picture with ids: {}", pictureIds);
        try {
            cloudinary.api().deleteResources(pictureIds, emptyMap());
            LOG.info("Cloudinary - Deleted pictures with ids: {}", pictureIds);
        } catch (Exception e) {
            LOG.error("Cloudinary error - Could not delete pictures with ids: {}", pictureIds);
        }
    }
}
