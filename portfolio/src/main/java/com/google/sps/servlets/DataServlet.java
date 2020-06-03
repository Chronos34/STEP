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

import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  private String comments = "";
  private ArrayList<String> messages = new ArrayList<>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    response.setContentType("text/html;");
    response.getWriter().println(comments);
  }
  /*
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String statement = comment(request, "texts");
      messages.add(statement);

      addcomment(messages, comments, statement);

      response.sendRedirect("/comments.html");
  }

  private String comment(HttpServletRequest request, String message) {
    String comment = request.getParameter(message);
    return fname;
  }

  private void addcomment(ArrayList<String> msglist, \
  String statementlist, String statements) {
      //To self, fix this line later.
      if (msglist.size() != 101) {
          statementlist.concat(statement).concat('\n\n');
      } else {
          statementlist = "";
          int starting_com = msglist.size() - 80;
          for (int i = starting_com; i < msglist.size(); i++) {
              statementlist.concat(msglist[i]).concat(' \n\n');
          }
      }
  }*/

}