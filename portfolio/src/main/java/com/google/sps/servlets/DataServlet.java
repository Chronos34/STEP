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
import com.google.sps.data.Comment;


/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  private int commentCount;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    Query query = new Query("Comments").addSort("time", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<Comment> comments = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      if (commentCount == 0) {break;}
      long id = entity.getKey().getId();
      String Statement = (String) entity.getProperty("comment");
      long Time = (long) entity.getProperty("time");

      Comment messages = new Comment(id, Statement, Time);
      comments.add(messages);
      commentCount--;
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(comments));
  }
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      long time = System.currentTimeMillis();
      String statement = comment(request, "texts");
      commentCount = maxComments(request, "preferedNumber");

      if (commentCount < 0) {
        response.setContentType("text/html");
        response.getWriter().println("Please enter an integer between 0 and 100.");
        return;
      }

      addcomment(statement, time);

      response.sendRedirect("/comments.html");
  }

  private String comment(HttpServletRequest request, String message) {
    return request.getParameter(message);
  }

  private void addcomment(String statements, long timestamp) {

    Entity comments = new Entity("Comments");
    comments.setProperty("comment", statements);
    comments.setProperty("time", timestamp);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(comments);
  }

  private int maxComments(HttpServletRequest request, String preferredNumber) {
      String choice = request.getParameter(preferredNumber);
      int maxNumber;
      try {
      maxNumber = Integer.parseInt(choice);
    } catch (NumberFormatException e) {
      System.err.println("Could not convert to int: " + choice);
      return -1;
    }
      return maxNumber;
  }
}