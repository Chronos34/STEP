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

//This file requests usernames and displays a welcome message.

package com.google.sps.servlets;

import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/background")
public class Background extends HttpServlet {

  private String name = "";


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
   
    response.setContentType("text/html;");
    response.getWriter().println(name);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String firstName = retrieveFirstName(request, "firstname").trim();
      String lastName = retrieveLastName(request, "lastname").trim();
      name = firstName + ' ' + lastName;

      if (firstName.concat(lastName).length() == 0) {
        name = "";
        response.sendRedirect("/comments.html");
        return;
      }

      response.sendRedirect("/background.html");
  }

  private String retrieveFirstName(HttpServletRequest request, String firstName) {
    return request.getParameter(firstName);
  }

  private String retrieveLastName(HttpServletRequest request, String lastName) {
    return request.getParameter(lastName);
  }

}