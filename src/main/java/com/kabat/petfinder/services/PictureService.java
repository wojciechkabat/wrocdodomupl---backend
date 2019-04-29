package com.kabat.petfinder.services;

import java.util.List;

public interface PictureService {
    void deleteFromRemoteServerByIds(List<String> pictureIds);
}
