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

package com.google.sps.servlets;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;
//import com.google.sps.data.Task;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.DatastoreServiceFactory;


/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
  String comment = "Hello";
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    /*Query query = new Query("Comments").addSort("time", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<Task> comments = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      long id = entity.getKey().getId();
      String title = (String) entity.getProperty("comment");
      long timestamp = (long) entity.getProperty("time");

      Task message = new Task(id, title, timestamp);
      comments.add(message);gson.toJson(comments)
    }

    Gson gson = new Gson();*/

    response.setContentType("text/html;");
    response.getWriter().println(comment);
  }
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String statement = comment(request, "texts");
      long time = System.currentTimeMillis();

      addcomment(statement, time);

      response.sendRedirect("/comments.html");
  }

  private String comment(HttpServletRequest request, String message) {
    String client_comment = request.getParameter(message);
    return client_comment;
  }

  private void addcomment(String statements, long timestamp) {

    Entity COMMENTS = new Entity("Comments");
    COMMENTS.setProperty("comment", statements);
    COMMENTS.setProperty("time", timestamp);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(COMMENTS);
  }

}