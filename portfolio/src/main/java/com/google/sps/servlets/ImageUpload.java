// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.googl.sps.servlets;

import java.net.URL;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.Gson;
import java.net.MalformedURLException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

/**
 * When the user submits the form, Blobstore processes the file upload and then forwards the request
 * to this servlet. This servlet can then process the request using the file URL we get from
 * Blobstore.
 */
@WebServlet("/image-upload")
public class ImageUpload extends HttpServlet {

  private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    Query query = new Query("Image");

    PreparedQuery results = datastore.prepare(query);

    List<String> images = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      images.add( (String) entity.getProperty("url"));
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(images));
  }


  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // Get the URL of the image that the user uploaded to Blobstore.
    List<String> imageUrl = getUploadedFileUrl(request, "image");

    for (int i = 0; i < imageUrl.size(); i++) {

        Entity image = new Entity("Image");
        image.setProperty("url", imageUrl.get(i));
        datastore.put(image);
    }

    response.sendRedirect("/image.jsp");

  }

  /** Returns a URL that points to the uploaded file, or null if the user didn't upload a file. */
  private List<String> getUploadedFileUrl(HttpServletRequest request, String formInputElementName) {
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
    List<BlobKey> blobKeys = blobs.get(formInputElementName);

    // User submitted form without selecting a file, so we can't get a URL. (dev server)
    if (blobKeys == null || blobKeys.isEmpty()) {
      return null;
    }

    List<String> imageUrls = new ArrayList<>();
    for (int i = 0; i < blobKeys.size(); i++) {
        // User submitted form without selecting a file, so we can't get a URL. (live server)
        BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKeys.get(i));
        if (blobInfo.getSize() == 0) {
            blobstoreService.delete(blobKeys.get(i));
            return null;
        }

        // Use ImagesService to get a URL that points to the uploaded file.
        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKeys.get(i));

        // To support running in Google Cloud Shell with AppEngine's dev server, we must use the relative
        // path to the image, rather than the path returned by imagesService which contains a host.
        try {
            URL url = new URL(imagesService.getServingUrl(options));
            imageUrls.add(url.getPath());
        } catch (MalformedURLException e) {
            imageUrls.add(imagesService.getServingUrl(options));
        }
    }
    return imageUrls;
  }
}