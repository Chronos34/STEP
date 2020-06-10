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
import com.google.sps.data.Comment;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.DatastoreServiceFactory;


/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data-secular")
public class SecularDataServlet extends HttpServlet {

  // This is the number of comments that should
  // be printed when the comments page is loaded
  private int commentCount = 99;
  private String name = "";

  private UserService userService = UserServiceFactory.getUserService();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    Query query = new Query("Secular").addSort("time", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<Comment> comments = new ArrayList<>();
    // The loop below is storing Comment objects to make it
    // easier for the function displayComments to create a list element
    // on HTML.
    for (Entity entity : results.asIterable(FetchOptions.Builder.withLimit(commentCount))) {
      long id = entity.getKey().getId();
      String Statement = (String) entity.getProperty("comment");
      String identity = (String) entity.getProperty("identity");
      long Time = (long) entity.getProperty("time");

      Comment messages = new Comment(id, Statement, Time, identity);
      comments.add(messages);
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
      String names = request.getParameter("name");

      if (names != null) {name = names.trim();}

      if (commentCount < 0) {
        response.setContentType("text/html");
        response.getWriter().println("Please enter an integer between 0 and 100.");
        return;
      }

      addComment(statement, time, name);

      response.sendRedirect("/secular.html");
  }

  private String comment(HttpServletRequest request, String message) {
    String content = request.getParameter(message);

    // An empty string is returned so the program doesn't
    // crash when trim is called on the statement string.
    // This makes it easier for addComment to function optimally.
    
    if (content == null) {return "  ";}

    return content;
  }

  private void addComment(String statements, long timestamp, String nickname) {

    if (statements.trim().length() == 0) {return;}

    String identifier;

    if (nickname.length() != 0) {identifier = nickname;}
    else {identifier = userService.getCurrentUser().getEmail();}

    Entity comments = new Entity("Secular");
    comments.setProperty("comment", statements);
    comments.setProperty("time", timestamp);
    comments.setProperty("identity", identifier);

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