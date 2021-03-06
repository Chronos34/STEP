<%--
Copyright 2019 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--%>

<%-- The Java code in this JSP file runs on the server when the user navigates
     to the homepage. This allows us to insert the Blobstore upload URL into the
     form without building the HTML using print statements in a servlet. --%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<% BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
   String uploadUrl = blobstoreService.createUploadUrl("/image-upload"); %>

<!DOCTYPE html>
<html>

  <head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
    <title>Images</title>

    <link rel="stylesheet" href="style.css">

    <script src="script.js"></script>
  </head>

  <body onload="logInStatus(); uploadedImages()">

      <div class="row">

        <div class="toprow">

          <h1>Welcome To 
            <i style="color:red" class="logoHeader">L</i>
            <i style="color:orange" class="logoHeader">O</i>
            <i style="color:magenta" class="logoHeader">O</i>
            <i style="color:green" class="logoHeader">K</i>
            <i style="color:blue" class="logoHeader">L</i>
            <i style="color:indigo" class="logoHeader">E</i>
          </h1>

        </div>

        <div class="poems">

            <ul id="poemspage">
                <li><a class="poemslink poemslinks"
                href="https://docs.google.com/document/d/1xqPC6ppFomAoO_QusfCwfv3-ngqruE7FIbb9IyLP4BI/edit?usp=sharing">
                Based on a Quote</a></li>
                <li><a class="poemslink poemslinks"
                href="https://docs.google.com/document/d/1elLNRgS5qW5JHn79W-dhSGqOj34e5_uke6qkbJ9BY70/edit?usp=sharing">
                Love</a></li>
                <li><a class="poemslink poemslinks"
                href="https://docs.google.com/document/d/1C6k8iKSnERFIdbIQWq72a4TD3EnXJLtX1HsMvWz698I/edit?usp=sharing">
                Nature</a></li>
                <li><a class="poemslink poemslinks"
                href="https://docs.google.com/document/d/1ck-utOsNx9MykUwFhnJxeEANOLlmLJGjf5Y5_vW-OZA/edit?usp=sharing">
                Life</a></li>
                <li><a class="poemslink poemslinks"
                href="https://docs.google.com/document/d/1EiSw-gRsUGGj5QrFrTKVdfLwclLj5X_EOs2kjYvbuOU/edit?usp=sharing">
                A Crying World</a></li>
                <li><a class="poemslink poemslinks"
                href="https://docs.google.com/document/d/1nkLm7pd3IvMunu5eH7XUaOU5N9NeODvWnK7AkkqWPXY/edit?usp=sharing">
                Discord</a></li>

            </ul>

        </div>

      </div>

      <div class="container">

          <div class="column lsidebar">

            <a rel="next" href="background.html" 
            class="blendin blendIn"><i>Background</i></a>
            <a rel="next" href="image.jsp" 
            class="blendin blendIn"><i>Images</i></a>
            <a rel="next" href="anime.html" 
            class="blendin blendIn"><i>Music</i></a>
            <a rel="external" href="/login" id="signIn"
            class="blendin blendIn"><i>Log In</i></a>
            <a rel="external" href="/logout" id="signOut"
            class="blendin blendIn"><i>Log Out</i></a>

          </div>

          <div class="column rsidebar">

            <a href="images/ab.jpg">
            <img src='/images/ab.jpg' alt='A winter-loving kid.' width='150' height='200'></a>
            <a href="images/bg.jpg">
            <img src='/images/bg.jpg' alt='A snowy day' width='150' height='200'></a>
            <a href="images/cb.jpg">
            <img src='/images/cb.jpg' alt='A rosy garden' width='150' height='200'></a>
            <a href="images/dg.jpg">
            <img src='/images/dg.jpg' alt='A chivalrous knight' width='150' height='200'></a>
            <a href="images/gf.jpg">
            <img src='/images/gf.jpg' alt='Classmates' width='150' height='200'></a>
            <a href="images/gh.jpg">
            <img src='/images/gh.jpg' alt='Graduation Picture' width='150' height='200'></a>
            <a href="images/h.jpg">
            <img src='/images/h.jpg' alt='A lad in a lovely suit' width='150' height='200'></a>
            <a href="images/hg.jpg">
            <img src='/images/hg.jpg' alt='A friend in a woven kente' width='150' height='200'></a>
            <a href="images/hhf.jpg">
            <img src='/images/hhf.jpg' alt='A Grumpy kid.' width='150' height='200'></a>
            <a href="images/it.jpg">
            <img src='/images/it.jpg' alt='A winter-loving kid.' width='150' height='200'></a>
            <a href="images/lk.jpg">
            <img src='/images/lk.jpg' alt='A winter-loving kid.' width='150' height='200'></a>
            <a href="images/mj.jpg">
            <img src='/images/mj.jpg' alt='A winter-loving kid.' width='150' height='200'></a>
            <a href="images/ng.jpg">
            <img src='/images/ng.jpg' alt='A winter-loving kid.' width='150' height='200'></a>
            <a href="images/qw.jpg">
            <img src='/images/qw.jpg' alt='A winter-loving kid.' width='150' height='200'></a>
            <a href="images/tr.jpg">
            <img src='/images/tr.jpg' alt='Happy Family' width='150' height='200'></a>
            <a href="images/vn.jpg">
            <img src='/images/vn.jpg' alt='UK Parliament' width='150' height='200'></a>
            <a href="images/sd.jpg">
            <img src='/images/sd.jpg' alt='Chelsea Jerseys' width='200' height='150'></a>
            <a href="images/hl.jpg">
            <img src='/images/hl.jpg' alt='Experimenting at the Jersey Shore with a smile' 
            width='200' height='150'></a>
            <a href="images/ac.jpg">
            <img src='/images/ac.jpg' alt='Words of encouragement' width='200' height='150'></a>
            <a href="images/jh.jpg">
            <img src='/images/jh.jpg' alt='Best Pic Yet' width='200' height='200'></a>
            <a href="images/lh.jpg">
            <img src='/images/lh.jpg' alt='Experimenting at the Jersey Shore' 
            width='200' height='150'></a>
            <a href="images/trd.jpg">
            <img src='/images/trd.jpg' alt='Buckingham Palace' width='200' height='150'></a>
            
            <a id="display-images"></a>

            <br><br>

            <form method="POST" enctype="multipart/form-data" action="<%= uploadUrl %>">
                <p>Upload Image(s):</p>
                <input type="file" name="image" multiple="true">
                <br/><br/>
                <button>Submit</button>
            </form>
            

          </div>

      </div>

      <footer>
          
            <hr>
            <div class="brow" style="text-align: center; padding:50px">
                <a href="https://github.com/Chronos34" target=_blank>GitHub</a>
                <a href="https://www.linkedin.com/in/nanabaafiagyekum/" target=_blank>LinkedIn</a>
                <a href="https://www.facebook.com/baafi.boatengagyekum" target=_blank>Facebook</a>
                <p style="padding-top: 10px"><i>All That Is Well Ends Well!</i></p> 
            </div>

      </footer>

  </body>
</html>